package com.smile.gifshowhookclient;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.util.Log;
import android.content.ContentValues;
import android.widget.EditText;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.regex.*;

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
    public int num = 33;
    public Context context;
    public Object sendm;


    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.processName.equals("com.smile.gifmaker")) {
            Log.d(tag, "过滤程序：" + loadPackageParam.packageName.toString());
            return;
        }
        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
                if (param.args != null && param.args.length > 0) {
                    context = (Context) param.args[0];
                }
            }
        });
        XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                final Class<?> cls = (Class<?>) param.getResult();
                if (param.args[0].toString().contains("com.yxcorp.plugin.message.b.b.p")) {
                    findAndHookConstructor(cls, int.class, String.class, String.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            String user_id = "1547601" + num;
                            String context = "嘻嘻嘻嘻嘻";
                            num = num + 1;
                            Log.d(tag, "用户：" + num);
                            param.args[1] = user_id;
                            param.args[2] = context;
                        }
                    });

                }

                if (param.args[0].toString().equals("com.yxcorp.utility.ay")) {
                    findAndHookMethod(cls, "a", EditText.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            EditText haha = new EditText(context);
                            haha.setText("12312312321");
                            param.setResult(haha.getText());
                        }
                    });

                }
                if (param.args[0].toString().equals("com.yxcorp.plugin.message.chat.presenter.MsgChatKeyboardPresenter")) {
                    Log.d(tag, "私信类：" + param.thisObject);
                    final Constructor c = cls.getDeclaredConstructor();
                    c.setAccessible(true);
                    XposedHelpers.findAndHookMethod(cls, "sendMsg", new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            sendm = param.thisObject;
                        }
                    });
                    new Handler(context.getMainLooper()).postDelayed(new Runnable() {
                        public void run() {
                            Log.d(tag, "开始--------------------------");
                            try {
                                Method method = cls.getDeclaredMethod("sendMsg");
                                Object nsend = cls.newInstance();
                                method.setAccessible(true);
                                method.invoke(nsend);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 30000);
                }
            }
        });
    }
}