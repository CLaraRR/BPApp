package com.example.bpapp.service;

import java.util.List;

/**
 * Created by 宁润 on 2017/6/3.
 */
public interface Service {
    String login(String username,String password);

    String register(String username,String password,int sex,String brith);

    String set(String username,int userID,String password,int sex,String brith,int deciveID);

    String sendMessageToPublic(String message,String time);

    String sendMessageToOne(String receiveName,String message);

    String addFriend(String userName);

    String refreshData();
    String refreshCommnuity();
    String refreshFriend();
    String refreshSet();

    List parseData(String dataMsg);
    List parseFriendInfo(String friendInfo);
    List parseFriendMsg(String friendMsg);
    List parseSocialMsg(String socialMsg);
    List parseChatMsg(String chatMsg);
}
