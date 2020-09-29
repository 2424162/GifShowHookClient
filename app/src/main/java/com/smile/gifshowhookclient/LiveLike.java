package com.smile.gifshowhookclient;


import android.widget.FrameLayout;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.IXposedHookLoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class LiveLike implements IXposedHookLoadPackage {
    String tag = "kuaishouhaha";
    public static Boolean IsPage = false;
    public static boolean LikeLock1 = false;
    public static Boolean photoclick = false;
    public Class<?> par;

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
                final Class<?> cls = (Class<?>) param.getResult();

                if(param.args[0].toString().contains("com.yxcorp.plugin.live.ag")){
                      par = (Class<?>)param.getResult();
                }
                //Log.d(tag,"----------="+par);

                if (photoclick == false) {
                    if (param.args[0].toString().contains("live.parts.LiveLikeParticlesPart")) {
                        Log.d(tag,"zhaodaole=========================================="+param.getResult().toString());

//                        Constructor[] cons = cls.getDeclaredConstructors();
//                        for(int i=0; i<cons.length;i++){
//                            Log.d(tag,"gouzao"+cons[i].getName());
//
//                            Class[] cont = cons[i].getParameterTypes();
//                            for(int j=0;j<cont.length;j++){
//                                Log.d(tag,"canshu"+cont[j]);
//                            }
//                        }

//                        Constructor constructor = cls.getDeclaredConstructor();
//                        Log.d(tag,"nmd"+constructor.getName());
//                        Log.d(tag,""+constructor.getParameterTypes());
//                        Class[] cont = constructor.getParameterTypes();
//                        Log.d(tag,""+cont.length);
//                        for(int j=0;j<cont.length;j++){
//                            Log.d(tag,"canshu"+cont[j]);
//                        }


                        findAndHookConstructor(cls, View.class,par, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Object resa = param.thisObject;
                                Field field = cls.getDeclaredField("mParticleLayout");
                                field.setAccessible(true);
                                field.set("cls","");
                                Object fie = field.get(resa);
                                Log.d(tag,fie.toString());
                                final FrameLayout layout = (FrameLayout) fie;

                                Log.d(tag,"类型："+field.getType());
                                Log.d(tag,"layout:"+field.getName());



                                if (LikeLock1 == false) {
                                    LikeLock1 = false;
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            layout.performClick();

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    layout.performClick();


                                                }
                                            },200);

                                            Log.d(tag, "点完喜欢de按钮==========");
                                        }
                                    }, 4000);
                                }
                            }
                        });
                    }
                }

            }
        });


    }


}





