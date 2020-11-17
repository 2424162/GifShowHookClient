package com.smile.gifshowhookclient;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;

import de.robv.android.xposed.XposedBridge;

public class MyApplication extends Application {

    private static Context context;
    private static MyApplication mMyApplication;

    public String tag = "kuaishouha";
    public static MyApplication getInstance(){
        return  mMyApplication;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMyApplication=this;
        Log.d(tag,mMyApplication.toString());
        Log.d(tag,android.os.Process.myPid()+"");

        context = getApplicationContext();
        Log.d(tag,context.toString());
    }
    public static Context getContext() {
        return context;
    }

    public void infoShared(){
        Log.d(tag,"开");

        SharedPreferences info = context.getSharedPreferences("values",MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = info.edit();
        editor.putString("hahha","lallal");
        Log.d(tag,"ffdfdfdff");


    }
}


