package com.example.bpapp.entity;

/**
 * Created by chenq on 2017/5/30.
 */

/**
 * 好友聊天消息类
 */
public class ChatMsg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;

    private String content;
    private String userName;
    private int type;

    public ChatMsg(String content, String userName, int type) {
        this.content = content;
        this.userName=userName;
        this.type = type;
    }


    public void setContent(String content){
        this.content=content;
    }
    public String getContent() {
        return content;
    }

    public void setUserName(String userName){
        this.userName=userName;
    }
    public String getUserName(){
        return userName;
    }

    public void setType(int type){
        this.type=type;
    }
    public int getType() {
        return type;
    }
}
