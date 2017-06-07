package com.example.bpapp.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ningrun on 2017/6/4.
 */
public  class Msglist {
    public static List<Data> dataList;
    public static List<FriendMsg> friendMsgList;
    public static List<Friends> friendsList;
    public static HashMap<String,List<ChatMsg>> chatMsgMap;
    public static List<SocialMsg> socialMsgList;

    public  Msglist(){
        dataList=new ArrayList<>();
        friendMsgList=new ArrayList<>();
        friendsList=new ArrayList<>();
        chatMsgMap=new HashMap<>();
        socialMsgList=new ArrayList<>();
    }

    public static void addData(List<Data> list){
        dataList.addAll(list);
    }
    public static List<Data> getDataList(){
        return dataList;
    }

    public static void addFriends(List<Friends> list){
        friendsList.addAll(list);
    }
    public static List<Friends> getFriendsList(){
        return friendsList;
    }

    public static void addFriendMsg(List<FriendMsg> list){ friendMsgList.addAll(list); }
    public static List<FriendMsg> getFriendMsgList(){
        return friendMsgList;
    }

    public static void addChatMsg(String friendName,List<ChatMsg> list){
        if(chatMsgMap.containsKey(friendName)){
            chatMsgMap.get(friendName).addAll(list);
        }else{
            chatMsgMap.put(friendName,list);
        }

    }
    public static void addChatMsg(String friendName,ChatMsg chatMsg){
        if(chatMsgMap.containsKey(friendName)){
            chatMsgMap.get(friendName).add(chatMsg);
        }else{
            List<ChatMsg> list = new ArrayList();
            list.add(chatMsg);
            chatMsgMap.put(friendName, list);
        }

    }
    public static HashMap<String,List<ChatMsg>> getChatMsgMap(){
        return chatMsgMap;
    }



    public static void addSocialMsg(List<SocialMsg> list){
        socialMsgList.addAll(list);
    }
    public static void addSocialMsg(SocialMsg socialMsg){
        socialMsgList.add(socialMsg);
    }
    public static List<SocialMsg> getSocialMsgList(){
        return socialMsgList;
    }

    public static void friendMsg2chatMsg(List<FriendMsg> list){
        for(int i=0;i<list.size();i++){
            FriendMsg friendMsg=list.get(i);
            ChatMsg msg=new ChatMsg(friendMsg.getContent(),friendMsg.getName(),ChatMsg.TYPE_RECEIVED);
            if(chatMsgMap.containsKey(friendMsg.getName())){
                chatMsgMap.get(friendMsg.getName()).add(msg);
            }else{
                List<ChatMsg> list1 = new ArrayList();
                list1.add(msg);
                chatMsgMap.put(friendMsg.getName(), list1);
            }
        }

    }

    public static void friendMsg2chatMsg(FriendMsg friendMsg){
        ChatMsg msg=new ChatMsg(friendMsg.getContent(),friendMsg.getName(),ChatMsg.TYPE_RECEIVED);
        if(chatMsgMap.containsKey(friendMsg.getName())){
            chatMsgMap.get(friendMsg.getName()).add(msg);
        }else{
            List<ChatMsg> list1 = new ArrayList();
            list1.add(msg);
            chatMsgMap.put(friendMsg.getName(), list1);
        }

    }
    public static void chatMsg2friendMsg(ChatMsg chatMsg){
        if(chatMsg.getType()==ChatMsg.TYPE_RECEIVED) {
            FriendMsg msg = new FriendMsg(chatMsg.getUserName(),chatMsg.getContent());
            friendMsgList.add(msg);
        }
    }
}
