package com.smile.gifshowhookclient;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.IXposedHookLoadPackage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.google.protobuf.TextFormat.print;
import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
    public class ChatRecord implements  IXposedHookLoadPackage{
    public String tag="kuaishouhaha";


        @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.processName.equals("com.smile.gifmaker")) {
            Log.d(tag, "过滤程序：" + loadPackageParam.packageName.toString());
            return;
        }
        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
                if (param.args != null && param.args.length > 0) {
                    ClassLoader cl = ((Context)param.args[0]).getClassLoader();
                    Class<?> hookclass = cl.loadClass("com.kwai.chat.messagesdk.sdk.internal.i.c");
                    XposedBridge.hookAllMethods(hookclass, "a", new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);

                            XposedBridge.log(""+param.thisObject.toString());
                        }
                    });
                }
            }
        });



        XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                final Class<?> cls = (Class<?>) param.getResult();
                if(cls.getName().equals("com.kwai.chat.messagesdk.sdk.internal.i.c")){
                    final Class Msg = Class.forName("com.kuaishou.d.b.d$c",true,loadPackageParam.classLoader);
                    XposedHelpers.findAndHookMethod(cls, "a", Msg, String.class, int.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            Log.d(tag,""+param.args[0]);
                            String string  = ""+param.args[0];
                            String stt = string.replaceAll("\\s*", "");
                            String[] strings = stt.split(":");
                            String strings1 =strings[2];
                            String send_time = strings1.substring(0,strings1.length()-4);
                            String strings2 = strings[5];
                            String sender = strings2.substring(0,strings2.length()-2);
                            String strings3 = strings[8];
                            String receiver = strings3.substring(0,strings3.length()-2);
                            String strings4 = strings[9];
                            String context = strings4.substring(0,strings4.length()-1);
                            Log.d(tag,send_time+"--"+sender+"--"+receiver+"--"+context);
                        }
                    });

                }

            }
        });
    }
}
