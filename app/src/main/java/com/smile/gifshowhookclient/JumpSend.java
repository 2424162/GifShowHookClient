package com.smile.gifshowhookclient;

import android.accessibilityservice.FingerprintGestureController;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.util.AttributeSet;
import android.widget.TableLayout;
import android.widget.RelativeLayout;


import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.AttributedCharacterIterator;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class JumpSend implements IXposedHookLoadPackage {

    public String tag ="kuaishouhaha";
    public Object sendm;
    public int  ce = 1;
    public int msg =1;
    public int sw =0;
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.processName.equals("com.smile.gifmaker")) {
            Log.d(tag, "过滤程序：" + loadPackageParam.processName.toString());
            return;
        }


        XposedHelpers.findAndHookMethod("android.view.ViewGroup", loadPackageParam.classLoader, "getChildAt", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                   final View  chview = (View)param.getResult();
                   if (chview.isClickable()) {
                       if (chview.toString().contains("com.google.android.material.tabs.TabLayout$TabView")) {
                           if (chview.toString().contains("540,0-720")){
                           if (ce == 1) {
                               new Handler().postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       chview.performClick();
                                   }
                               }, 2400);
                           }
                       }
                   }
                   }
            }
        });
        XposedHelpers.findAndHookConstructor("com.yxcorp.gifshow.widget.ReminderTabView", loadPackageParam.classLoader,Context.class,AttributeSet.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                final View sview = (View) param.thisObject;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sview.performClick();
                        }
                    }, 3000);
            }
        });
            XposedHelpers.findAndHookMethod("android.view.ViewGroup", loadPackageParam.classLoader, "getChildAt", int.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    final View tableview = (View) param.getResult();
                    if (tableview.toString().contains("app:id/subject_wrap")) {
                        if (sw==0) {
                            sw = 1;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    tableview.performClick();
                                }
                            }, 4000);
                        }
                    }
                }
            });


//                XposedHelpers.findAndHookConstructor("android.widget.TableLayout", loadPackageParam.classLoader, Context.class, AttributeSet.class, new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                        final View tableview  = (View)param.thisObject;
//                        Log.d(tag,"对话框"+tableview.toString());
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                tableview.performClick();
//                            }
//                        }, 1000);
//
//                    }
//                });



















//        Class clazzs = XposedHelpers.findClass("com.google.android.material.tabs.NasaTabLayout",loadPackageParam.classLoader);
//
//        XposedHelpers.findAndHookMethod("com.google.android.material.tabs.NasaTabLayout", loadPackageParam.classLoader,"a", clazzs, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                Log.d(tag,"手动点击"+param.args[0]);
//                View view = (View)param.args[0];
//                Log.d(tag,view.isClickable()+"");
//                Log.d(tag,"---手动点击"+view.performClick());
//
//            }
//        });

        

//        XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", new XC_MethodHook() {
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                final Class<?> cls = (Class<?>) param.getResult();
//
//
//                if(param.args[0].toString().equals("com.yxcorp.plugin.message.chat.presenter.MsgChatKeyboardPresenter")){
//                    final Constructor c  = cls.getDeclaredConstructor();
//                    c.setAccessible(true);
//                    XposedHelpers.findAndHookConstructor(cls, new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            super.afterHookedMethod(param);
//                            Log.d(tag,"hook的类型");
//                            Log.d(tag,""+(param.thisObject == null));
//                            Log.d(tag,"开机进程"+android.os.Process.myPid());
//                             sendm = param.thisObject;
//                            Log.d(tag,"开机初始化："+sendm.toString());
//                        }
//                    });
//                    XposedHelpers.findAndHookMethod(cls, "sendMsg", new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            super.afterHookedMethod(param);
//                            sendm = param.thisObject;
//                            Log.d(tag,"手动执行"+sendm.toString());
//                            Log.d(tag,"手动进程"+android.os.Process.myPid());
//                        }
//                    });
//                    Log.d(tag,"总的内部类进程"+android.os.Process.myPid());
//                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable(){
//                        public void run() {
//                            Log.d(tag,"handler进程id:"+android.os.Process.myPid());
//                            XposedBridge.log("proceessID3:"+android.os.Process.myPid());
//                            Log.d(tag,"开始--------------------------");
//                            try {
//                                Method method = cls.getDeclaredMethod("sendMsg");
//
//                                Object nsend = c.newInstance();
//                                Log.d(tag,"对象:"+nsend.toString());
//                                method.setAccessible(true);
//                                method.invoke(nsend);
//                                Log.d(tag,"fanshewan+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+");
//                            } catch (IllegalAccessException e) {
//                                e.printStackTrace();
//                            } catch (NoSuchMethodException e) {
//                                e.printStackTrace();
//                            } catch (InvocationTargetException e) {
//                                e.printStackTrace();
//                            } catch (InstantiationException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, 30000);
//                }
//
//            }
//        });





    }
}
