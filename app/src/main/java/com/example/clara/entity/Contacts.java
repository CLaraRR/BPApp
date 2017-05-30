package com.example.clara.entity;

/**
 * Created by chenq on 2017/5/29.
 */

public class Contacts {
    private String name;
    private String digest;
    private String time;
    private int imageId;

    public Contacts(String name,String digest,String time,int imageId){
        this.name=name;
        this.digest=digest;
        this.time=time;
        this.imageId=imageId;
    }

    public String getName(){
        return name;
    }

    public String getDigest(){
        return digest;
    }

    public String getTime(){
        return time;
    }

    public int getImageId(){
        return imageId;
    }

}

