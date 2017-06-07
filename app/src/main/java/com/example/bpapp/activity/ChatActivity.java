package com.example.bpapp.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.example.bpapp.adapter.ChatMsgAdapter;
import com.example.bpapp.bpapp.R;
import com.example.bpapp.broadcast.ReceiveBroadCast;
import com.example.bpapp.entity.ChatMsg;
import com.example.bpapp.entity.Data;
import com.example.bpapp.entity.FriendMsg;
import com.example.bpapp.entity.Msglist;
import com.example.bpapp.service.ClientService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * 好友聊天Activity
 */
public class ChatActivity extends AppCompatActivity implements ReceiveBroadCast.BRInteraction{

    private EditText inputText;
    private Button send;
    private ListView msgListView;
    private ChatMsgAdapter adapter;
    private Button gobackButton;
    private List<ChatMsg> msgList = new ArrayList<ChatMsg>();

    private ReceiveBroadCast receiveBroadCast;
    private ClientService clientService;
    private String friendName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatcommunity_frame);


        /**
         * 实例化一个广播接收器
         */
        receiveBroadCast=new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("TH");    //只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiveBroadCast, filter);
        receiveBroadCast.setBRInteractionListener(this);

        clientService=ClientService.getInstance();//获得ClientService实例

        /**
         * 初始化界面
         */
        gobackButton=(Button)findViewById(R.id.toolbar_left_btn);
        gobackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inputText = (EditText)findViewById(R.id.editText_mesaage);
        msgListView = (ListView)findViewById(R.id.listView_messageList);
        Log.d("msgListView:","OK");
        send = (Button)findViewById(R.id.button_sendMessage);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if(!"".equals(content)) {
                    String name=clientService.getUserName();
                    String message=clientService.sendMessageToOne(friendName,content);
                    if(message.equals("+OKSEND")){

                        ChatMsg msg = new ChatMsg(content,name,ChatMsg.TYPE_SEND);
                        msgList.add(msg);
                        Msglist.addChatMsg(friendName,msg);
                        Msglist.chatMsg2friendMsg(msg);
                        adapter.notifyDataSetChanged();
                        msgListView.setSelection(msgList.size());
                        inputText.setText("");
                    }else if(message.equals("+ERRORSEND")){
                        new AlertDialog.Builder(ChatActivity.this)
                                .setTitle("提示")
                                .setMessage("用户不在线！")
                                .setPositiveButton("确认",null)
                                .show();
                    }

                }
            }
        });

        initMsgs();

    }

    private void friendMsgToChatMsg(List<FriendMsg> friendMsgList) {
        List<ChatMsg> list=new ArrayList<>();
        for(int i=0;i<friendMsgList.size();i++){
            FriendMsg friendMsg=friendMsgList.get(i);
            ChatMsg msg=new ChatMsg(friendMsg.getContent(),friendMsg.getName(),ChatMsg.TYPE_RECEIVED);
            msgList.add(msg);
            list.add(msg);
        }
        Msglist.addChatMsg(friendName,list);
    }

    private void initMsgs() {
        Intent intent=getIntent();
        friendName=intent.getStringExtra("friendName");
        if(friendName!=null){

        }else{
            friendName=intent.getStringExtra("name");
        }
        System.out.println(friendName);
        HashMap<String,List<ChatMsg>> map=Msglist.getChatMsgMap();
        Iterator it=map.keySet().iterator();
        while(it.hasNext()){
            if(it.next().equals(friendName)){
                msgList=map.get(friendName);
            }
        }

        adapter = new ChatMsgAdapter(ChatActivity.this, R.layout.layout_chat, msgList);
        msgListView.setAdapter(adapter);
    }

    @Override
    public void setMsg(String content) {
        if(content!=null){
            System.out.println("chat content:"+content);
            List<FriendMsg> list = clientService.parseFriendInfo(content);
            friendMsgToChatMsg(list);
            Msglist.addFriendMsg(list);
            adapter.notifyDataSetChanged();
            msgListView.setSelection(msgList.size());
        }
    }
}
