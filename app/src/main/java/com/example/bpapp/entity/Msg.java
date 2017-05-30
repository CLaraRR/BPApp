package com.example.bpapp.entity;

/**
 * Created by chenq on 2017/5/30.
 */

/**
 * 消息类
 */
public class Msg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;

    private String content;
    private String userName;
    private int type;

    public Msg(String content, String userName,int type) {
        this.content = content;
        this.userName=userName;
        this.type = type;
    }


    public String getContent() {
        return content;
    }

    public String getUserName(){
        return userName;
    }

    public int getType() {
        return type;
    }
}
