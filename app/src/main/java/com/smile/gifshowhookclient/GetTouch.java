package com.smile.gifshowhookclient;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;



public class GetTouch implements IXposedHookLoadPackage {
    public String tag = "kuaishouhaha";
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.processName.equals("com.smile.gifmaker")) {
            Log.d(tag, "过滤程序：" + loadPackageParam.processName.toString());
            return;
        }
        XposedHelpers.findAndHookMethod("android.view.ViewGroup", loadPackageParam.classLoader, "onInterceptTouchEvent", MotionEvent.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.d(tag,"onInterceptTouch类："+param.getResult().toString()+param.thisObject.toString());
            }
        });

        XposedHelpers.findAndHookMethod("android.view.View", loadPackageParam.classLoader, "onTouchEvent",MotionEvent.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                if (param.getResult().toString() =="true") {
                    Log.d(tag, "ontouch类：" + param.getResult().toString() + "--" + param.thisObject.toString());
                    View view = (View)param.thisObject;

                    Log.d(tag,view.getId()+"");
                }
            }
        });

    }
}
