package com.smile.gifshowhookclient;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.util.Log;
import android.content.ContentValues;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.IXposedHookLoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static javax.xml.xpath.XPathFactory.newInstance;

public class SendMsg implements IXposedHookLoadPackage {
    String tag = "kuaishouhaha";
    public static Boolean IsPage = false;
    public static boolean LikeLock1 = false;
    public static Boolean photoclick = false;

    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals("com.smile.gifmaker")) {
            Log.d(tag, "过滤程序：" + loadPackageParam.packageName.toString());
            return;

        }
// 过防止调用loadClass加载 de.robv.android.xposed.
        XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                //Log.d(tag,""+param.args[0].toString());
                if (param.args != null && param.args[0] != null && param.args[0].toString().startsWith("de.robv.android.xposed.")) {
                    param.args[0] = "de.crobv.android.xposed.ThTest";
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

        XposedHelpers.findAndHookMethod(Class)




        XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Class<?> cls = (Class<?>) param.getResult();

                if (param.args[0].toString().contains("com.yxcorp.plugin.message.b.b.p")) {



                    findAndHookConstructor(cls, int.class, String.class, String.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            String user_id = "154710112";
                            String context ="xixixixixix";

                            param.args[1] = user_id;
                            param.args[2] = context;
                        }
                    });

                }
                if(param.args[0].toString().equals("com.yxcorp.plugin.message.chat.presenter.MsgChatSinglePresenter")){
                    Log.d(tag, "zhaodao");
                    Log.d(tag,"-----------------------------------------");

                    Constructor[] constructors = cls.getDeclaredConstructors();
                    for(int i=0;i<constructors.length;i++){
                        Log.d(tag,"hahhaha"+constructors[i].getName());
                        Class[] types = constructors[i].getParameterTypes();
                        Log.d(tag,""+types.length);
                    }
                    String name = "com.yxcorp.plugin.message.chat.presenter.MsgChatSinglePresenter";
                    Object con;


                    con = (Object)cls.newInstance();
                    Log.d(tag,con.toString());

                    Method method = cls.getDeclaredMethod("sendMsg");
                    method.invoke(cls);
                    Log.d(tag,"fanshewan");


                }
            }
        });
    }


}





