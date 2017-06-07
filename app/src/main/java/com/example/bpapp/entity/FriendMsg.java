package com.example.bpapp.entity;

/**
 * 好友消息类
 * Created by chenq on 2017/5/29.
 */

public class FriendMsg {
    private String name;
    private String content;
    private int imageId;

    public FriendMsg(String name, String content,int imageId){
        this.name=name;
        this.content=content;
        this.imageId=imageId;
    }
    public FriendMsg(String name, String content){
        this.name=name;
        this.content=content;

    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }

    public void setContent(String content){
        this.content=content;
    }
    public String getContent(){
        return content;
    }


    public void setImageId(int imageId){
        this.imageId=imageId;
    }
    public int getImageId(){
        return imageId;
    }

}

