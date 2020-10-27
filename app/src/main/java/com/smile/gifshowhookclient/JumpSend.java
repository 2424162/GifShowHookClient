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
    }
}
