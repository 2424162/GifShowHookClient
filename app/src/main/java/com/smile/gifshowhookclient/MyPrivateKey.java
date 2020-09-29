package com.smile.gifshowhookclient;


import android.os.Build;
import android.widget.FrameLayout;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.Signature;


import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import android.util.Base64;


import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.IXposedHookLoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class MyPrivateKey implements IXposedHookLoadPackage {
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

                if(param.args[0].toString().equals("com.yxcorp.gifshow.util.b")){
                    Log.d(tag,"----------="+param.args[0].toString());
                    XposedHelpers.findAndHookMethod(cls,"a", PrivateKey.class,String.class,new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            Log.d(tag,param.args[0].toString());
                            PrivateKey privateKey = (PrivateKey)param.args[0];
                            Log.d(tag,param.args[0].toString());
                            String base64encodedString = Base64.encodeToString("runoob?java8".getBytes("utf-8"),Base64.DEFAULT);
                            Log.d(tag,base64encodedString);
                           // Log.d(tag,"私钥长度："+privateKey.getEncoded().length);
                            param.args[1] = "1599027306094";

//                            Signature instance = Signature.getInstance("SHA256withRSA");
//                            instance.initSign(privateKey);
//                            instance.update(str.getBytes("utf-8"));
//                            for (int k=0;k<instance.sign().length;k++)
//                            {
//                                Log.d(tag,"lala"+instance.sign()[k]);
//
//                            }
//
//                            Log.d(tag,"===ppkey====");
//
//                            String privateKeyValue = Base64.encodeToString(privateKey.getEncoded(),Base64.DEFAULT);
//                            Log.d(tag,privateKeyValue);
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            Log.d(tag,"jiegou"+param.getResult().toString());
                        }
                    });

                }
            }
        });


    }


}





