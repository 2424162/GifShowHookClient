package com.smile.gifshowhookclient;


import java.util.ArrayDeque;

public  class PhotoIds{
    private  static  PhotoIds photoids  = new PhotoIds();
    private void PhotoIds(){}
    public static ArrayDeque haha = new ArrayDeque();

    public static PhotoIds getIn(){
        return photoids;
    }

    public static String  onGetId(){
         String id = (String)haha.pollLast();
         return id;
    }
    public static void onAddId(String photoid){
        haha.offerFirst(photoid);
    }

}
