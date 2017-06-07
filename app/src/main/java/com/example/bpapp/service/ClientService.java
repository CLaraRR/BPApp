package com.example.bpapp.service;

import com.example.bpapp.entity.Data;
import com.example.bpapp.entity.FriendMsg;
import com.example.bpapp.entity.Friends;
import com.example.bpapp.entity.SocialMsg;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author jy_meng
 * @version 1.0
 * @date 2017/5/30
 */
public class ClientService implements Service{
    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    SelectionKey key;
    String address;
    int port;
    private String  userName;
    private Integer userId;
    Selector selector;
    SocketChannel socketChannel;
    Iterator<SelectionKey> keyIterator;

    private static  ClientService instance;


    public ClientService(String address, int port) {
        super();
        this.address = address;
        this.port = port;
    }


    public ClientService() {
        super();
    }

    public static ClientService getInstance(){
        if(instance==null){
            instance=new ClientService();
        }

        return instance;

    }

    public void setAddress(String address){
        this.address=address;
    }
    public String getAddress(){
        return this.address;
    }
    public void setPort(int port){
        this.port=port;
    }
    public int getPort(){
        return this.port;
    }

    public boolean start() throws IOException {
        socketChannel= ClientSocketChannel.getSocketChannel(address, port);
        selector = Selector.open();
        // 注册连接服务器socket的动作
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        // 选择一组键，其相应的通道已为 I/O 操作准备就绪。
        // 此方法执行处于阻塞模式的选择操作。
        selector.select();
        // 返回此选择器的已选择键集。
        keyIterator = selector.selectedKeys().iterator();
        if(keyIterator.hasNext()) {
            key = keyIterator.next();
            keyIterator.remove();
        }
        // 判断此通道上是否正在进行连接操作。
        if (key.isConnectable()) {
            try {
                socketChannel.finishConnect();
            } catch (Exception e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public String send(String data) {

        if(socketChannel==null){
            try {
                start();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        String message = data;
        String backMessage="";
        writeBuffer.clear();
        writeBuffer.put(message.getBytes());
        // 将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
        writeBuffer.flip();
        try {
            socketChannel.write(writeBuffer);
            socketChannel.register(selector, SelectionKey.OP_READ);
            selector.select();
            keyIterator = selector.selectedKeys().iterator();
            key = keyIterator.next();
            keyIterator.remove();
            //			System.out.print("receive message:");
            SocketChannel client = (SocketChannel) key.channel();
            // 将缓冲区清空以备下次读取
            readBuffer.clear();
            int num = client.read(readBuffer);
            backMessage=new String(readBuffer.array(), 0, num);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return backMessage;
        // 注册读操作，下一次读取
    }

    @Override
    public String login(String username, String password) {
        String message=send("LH "+username+" "+password+" LT");
        this.userName=username;
        if(message!="+ERRORLOGIN") {
            String[] line=message.split("#");
            String[] lineToLine=line[1].split(" ");
            System.out.println(line[1]);
            this.userId=Integer.valueOf(lineToLine[1]);
        }
        return message;
    }

    @Override
    public String register(String username, String password, int sex, String brith) {
        String message=send("GH "+username+" "+password+" "+sex+" "+change(brith)+" GT");
        return message;
    }

    @Override
    public String set(String username, int userID, String password, int sex, String brith, int deciveID) {
        String message=send("SH "+username+" "+userID+" "+password+" "+sex+" "+change(brith)+" "+deciveID+" ST");
        return message;
    }

    @Override
    public String sendMessageToPublic(String message, String time) {
        String message_1=send("CH "+change(message)+" "+userName+" "+change(time)+" CT");
        return message_1;
    }

    @Override
    public String sendMessageToOne(String receiveName, String message) {
        String message_1=send("TH "+receiveName+" "+change(message)+" TT");
        return message_1;
    }

    @Override
    public String addFriend(String userName) {
        String message=send("AH "+this.userName+' '+userName+" AT");
        return message;
    }

    @Override
    public String refreshData() {
        String message=send("RH DATA "+userId+" RT");
        return message;
    }

    @Override
    public String refreshCommnuity() {
        String message=send("RH COMMNUITY "+userId+" RT");
        return message;
    }

    @Override
    public String refreshFriend() {
        String message=send("RH FRIEND "+userId+" RT");
        return message;
    }

    @Override
    public String refreshSet() {
        String message=send("RH SET "+userId+" RT");
        return message;
    }

    public String change(String s)
    {
        String temp="";
        for(int i=0;i<s.length();i++)
            if(s.charAt(i)==' ')
                temp+='%';
            else
                temp+=s.charAt(i);
        return temp;
    }

    @Override
    public List parseData(String dataMsg) {
        System.out.println("$$$$$"+dataMsg);
        String[] split=dataMsg.split(" ");
        List<Data> dataList=new ArrayList<>();
        for(int i=1;i<split.length-1;i++){
            String[] ss=split[i].split("\\*");
            String highsocre=ss[0].substring(0,3);
            String lowscore=ss[0].substring(3,6);
            String heartbeat=ss[0].substring(6);

            dataList.add(new Data(Integer.parseInt(highsocre),Integer.parseInt(lowscore),
                    Integer.parseInt(heartbeat),ss[1]));

        }
        return dataList;
    }

    @Override
    public List parseFriendInfo(String friendInfo) {
        String[] split=friendInfo.split(" ");
        List<Friends> friendsList=new ArrayList<>();
        for(int i=1;i<split.length-1;i++){
            friendsList.add(new Friends(split[i]));
        }
        return friendsList;
    }

    @Override
    public List parseFriendMsg(String friendMsg) {
        String[] split=friendMsg.split(" ");
        List<FriendMsg> friendMsgList=new ArrayList<>();
        for(int i=1;i<split.length-1;i++){
            String[] ss=split[i].split("\\*");
            ss[0]=ss[0].replaceAll("%"," ");
            ss[1]= ss[1].replaceAll("%"," ");
            friendMsgList.add(new FriendMsg(ss[0],ss[1]));
        }
        return friendMsgList;
    }

    @Override
    public List parseSocialMsg(String socialMsg) {
        String[] split=socialMsg.split(" ");
        List<SocialMsg> socialMsgList=new ArrayList<>();
        for(int i=1;i<split.length-1;i++){
            String[] ss=split[i].split("\\*");
            ss[0]=ss[0].replaceAll("%"," ");
            ss[1]=ss[1].replaceAll("%"," ");
            ss[2]=ss[2].replaceAll("%"," ");
            socialMsgList.add(new SocialMsg(ss[0],ss[1],ss[2]));
        }
        return socialMsgList;
    }

    @Override
    public List parseChatMsg(String chatMsg) {
        return null;
    }

    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
