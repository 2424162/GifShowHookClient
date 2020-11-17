package com.smile.gifshowhookclient;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.smile.gifshowhookclient.MyApplication;
import java.util.Map;
import java.util.Set;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.IXposedHookLoadPackage;



public class BasicInfo  implements IXposedHookLoadPackage{

    public String tag = "kuaishouhaha";
    public Context context;
    public boolean HeaderS = true;



    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.processName.equals("com.smile.gifmaker")) {
            Log.d(tag, "过滤程序：" + loadPackageParam.processName.toString());
            return;
        }


        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {
                if (param.args != null && param.args.length > 0) {
                    context = (Context) param.args[0];
                    Log.d(tag,"Application哈哈");

                }
            }
        });
        XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Class<?> cls = (Class<?>) param.getResult();
                XposedBridge.log(cls.toString());



                if (cls != null){
                    if (cls.getName().equals("com.yxcorp.retrofit.f")) {
                        Log.d(tag, cls.getName());
                        Class<?> Request = (Class<?>) Class.forName("okhttp3.Request", true, loadPackageParam.classLoader);
                        XposedHelpers.findAndHookMethod(cls, "a", Request, Map.class, Map.class, String.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                Map header = (Map) param.args[1];
                                Log.d(tag, header.toString());
                                XposedBridge.log(header.toString());
                                if (header.size() == 26 && HeaderS == true) {
                                    Log.d(tag, "hahahhah");
                                    SharedPreferences info = context.getSharedPreferences("xiaolaji", Context.MODE_WORLD_READABLE | Context.MODE_MULTI_PROCESS);
                                    SharedPreferences.Editor editor = info.edit();
                                    Set<String> keys = header.keySet();
                                    for (String key : keys) {
                                        editor.putString(key, (String) header.get(key));
                                        Log.d(tag, "" + key);
                                        XposedBridge.log(key);
                                    }
                                    editor.commit();
                                    HeaderS = false;

                                }
                            }
                        });
                    }
            }
            }
        });
    }

}
