package com.smile.gifshowhookclient;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.util.Log;

import java.lang.reflect.Modifier;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.IXposedHookLoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class PhotoLike implements IXposedHookLoadPackage {
    String tag = "kuaishouhaha";
    public static Boolean IsPage = false;
    public static boolean LikeLock1 = false;
    public static Boolean photoclick = false;

    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals("com.smile.gifmaker")) {
            Log.d(tag, "过滤程序：" + loadPackageParam.packageName.toString());
            return;

        }
        //final PhotoIds photoIds = PhotoIds.getIn();
        //Log.d(tag, "" + photoIds.hashCode());

// 过防止调用loadClass加载 de.robv.android.xposed.
        XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                //Log.d(tag,""+param.args[0].toString());
                if (param.args != null && param.args[0] != null && param.args[0].toString().startsWith("de.robv.android.xposed.")) {
                    // 改成一个不存在的类
                    param.args[0] = "de.crobv.android.xposed.ThTest";
                    // 调整了一下，听说这样改更好，直接改部分手机有未知影响。
                    //Log.d(tag,"找到loadclass里的xopsed");
                    param.setThrowable(new ClassNotFoundException("not found"));
                }
                super.beforeHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod(StackTraceElement.class, "getClassName", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                String result = (String) param.getResult();
                //Log.d(tag,"检测堆栈"+result);
                if (result != null) {
                    if (result.contains("de.robv.android.xposed.")) {
                        // Log.d(tag,"getClassname里的异常");
                        param.setResult("");
                    } else if (result.contains("com.android.internal.os.ZygoteInit")) {
                        param.setResult("");
                    }
                }
                super.afterHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod(Modifier.class, "isNative", int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                //Log.d(tag,"判断是否Native");
                String modify = "ffj";
                //Log.d(tag,"对错："+param.args[0].toString());
                param.args[0] = modify;
                super.beforeHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                Class<?> cls = (Class<?>) param.getResult();
                //点击进入作品页面
                if (param.args[0].toString().contains("androidx.recyclerview.widget.s$2")) {
                    Log.d(tag, "zhaodao");
                    findAndHookMethod(cls, "a", View.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            final View photo = (View) param.args[0];
                            //Log.d(tag,"找到的作品page========================="+photo.getClass().getName()+"--------"+param.args[0].toString());
                            if (photo.getClass().getName().contains("RelativeLayout")) {
                                if (photoclick == false) {
                                    photoclick = true;

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            photo.performClick();
                                            Log.d(tag, "点击完================================");
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    IsPage = true;
                                                }
                                            }, 2000);
                                        }
                                    }, 5000);
                                }
                            }
                        }
                    });
                    Log.d(tag, "进入作品页面");
                }
                //hook isLiked使作品页一直处于未点赞状态
                if (param.args[0].toString().contains("model.mix.PhotoMeta")) {
                    Class<?> PhotoMeta = (Class<?>) param.getResult();
                    XposedHelpers.findAndHookMethod(PhotoMeta, "isLiked", new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            boolean isLiked = false;
                            param.setResult(isLiked);
                        }
                    });
                }


                //final String photoid = (String) photoIds.onGetId();

                //Log.d(tag, "=======传过来的photid:" + photoid);
                //if (photoid.length() > 0) {
                    //选择点赞的ID
                    if (param.args[0].toString().contains("gifshow.entity.QPhoto")) {
                        Class<?> QPhoto = (Class<?>) param.getResult();
                        XposedHelpers.findAndHookMethod(QPhoto, "getPhotoId", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                String LikePhotoId = "1945433977";
                                param.setResult(LikePhotoId);
                                //Log.d(tag,"id改完");
                            }
                        });
                    }
                    //点击喜欢按钮
                    if (photoclick == true) {
                            if (param.args[0].toString().contains("com.yxcorp.gifshow.detail.view.LikeView")) {
                            findAndHookConstructor(cls, Context.class, AttributeSet.class, new XC_MethodHook() {
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    super.afterHookedMethod(param);
                                    Object button = param.thisObject;

                                    final View haha = (View) button;
                                    Log.d(tag,"是否可以点击："+haha.isClickable());
                                    Log.d(tag, button.toString());
                                    if (LikeLock1 == false) {
                                        LikeLock1 = true;
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                haha.performClick();
                                                Log.d(tag, "点完喜欢按钮==========");
                                            }
                                        }, 2000);
                                    }
                                }
                            });
                        }
                    }
//                if(param.args[0].toString().contains("android.view.View")){
//                    findAndHookConstructor(cls, Context.class, AttributeSet.class, new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            super.afterHookedMethod(param);
//                            Object button = param.thisObject;
//                            Log.d(tag,""+button);
//                            if(button.toString().contains("follow")) {
//                                final View haha = (View) button;
//                                Log.d(tag, "关注按钮");
//                                Log.d(tag,"===="+haha.toString());
//
////                                new Handler().postDelayed(new Runnable() {
////                                    @Override
////                                    public void run() {
////                                        Log.d(tag,"===="+haha.toString());
////                                        haha.performClick();
////                                        Log.d(tag,"点完关注");
////                                    }
////                                }, 2000);
//                            }
//                        }
//                    });
//                }
                    //点关注
                        if(param.args[0].toString().contains("com.yxcorp.gifshow.widget.DetailFollowLinearLayout")){
                        findAndHookConstructor(cls, Context.class, AttributeSet.class, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Object button = param.thisObject;
                                Log.d(tag,""+button);
                                if(button.toString().contains("follow")) {
                                    final View haha = (View) button;
                                    Log.d(tag, "关注按钮");

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d(tag,"===="+haha.toString());
                                            haha.performClick();
                                            Log.d(tag,"点完关注");
                                        }
                                    }, 2000);
                                }
                            }
                        });
                    }



                //}
            }
        });


    }


}





