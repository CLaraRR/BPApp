package com.example.bpapp.entity;

/**
 * Created by chenq on 2017/5/30.
 */

/**
 * 好友列表
 */

public class Friends {
    private String name;

    public Friends(String name){
        this.name=name;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }

}
