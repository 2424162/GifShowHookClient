package com.smile.gifshowhookclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

//import static com.smile.gifshowhookclient.PhotoIds.photolist;

public class MainActivity extends AppCompatActivity {
    public  PhotoIds photoids =new PhotoIds();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PhotoIds photoIds = PhotoIds.getIn();

        photoids.onAddId("5206724152687278323");
        photoids.onAddId("5208694475708373596");
        photoids.onAddId("5195465153387220035");

        Log.d("kuaishou==========",""+photoIds.haha.size());
        Log.d("kuaishou==========",""+photoIds.hashCode());

    }
    public   PhotoIds photoIds(){
        return this.photoids;
    }
}