package com.example.bpapp.entity;

/**
 * 社区消息类
 * Created by chenq on 2017/5/29.
 */

public class SocialMsg {
    private String name;
    private String content;
    private String time;
    private int imageId;

    public SocialMsg(String content,String name,String time,int imageId){
        this.name=name;
        this.content=content;
        this.time=time;
        this.imageId=imageId;
    }
    public SocialMsg(String content,String name,String time){
        this.name=name;
        this.content=content;
        this.time=time;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }

    public void setContent(String content){
        this.content=content;
    }
    public String getContent(){
        return this.content;
    }

    public void setTime(String time){
        this.time=time;
    }
    public String getTime(){
        return this.time;
    }

    public void setImageId(int imageId){
        this.imageId=imageId;
    }
    public int getImageId(){
        return this.imageId;
    }

}

