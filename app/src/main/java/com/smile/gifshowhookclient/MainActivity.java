package com.smile.gifshowhookclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.Map;

//import static com.smile.gifshowhookclient.PhotoIds.photolist;

public class MainActivity extends AppCompatActivity {
    public String tag = "kuaishouhaha";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInfo();
        getInfo();

    }

    public  void setInfo(){
        SharedPreferences gifshowInfo= getSharedPreferences("values",MODE_PRIVATE);
        SharedPreferences.Editor editor = gifshowInfo.edit();
        editor.putString("user","hahahha");
        editor.commit();
        Log.d(tag,"提交");
    }

    public void getInfo(){
        Context mycon = null;
        try {
            mycon = getApplicationContext().createPackageContext("com.smile.gifmaker", Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        SharedPreferences info = mycon.getSharedPreferences("xiaolaji",Context.MODE_WORLD_READABLE | Context.MODE_MULTI_PROCESS);
        Map haha = info.getAll();
        Log.d(tag, haha.toString());
        String name = info.getString("user",null);
        String name2 = info.getString("xiaolaji",null);
        Log.d(tag,"主程序："+haha.toString());
;
    }



}