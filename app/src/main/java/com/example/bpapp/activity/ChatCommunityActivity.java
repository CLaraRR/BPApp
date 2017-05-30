package com.example.bpapp.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.example.bpapp.adapter.MsgAdapter;
import com.example.bpapp.bpapp.R;
import com.example.bpapp.entity.Msg;

import java.util.ArrayList;
import java.util.List;

public class ChatCommunityActivity extends AppCompatActivity {

    private EditText inputText;
    private Button send;
    private ListView msgListView;
    private MsgAdapter adapter;

    private List<Msg> msgList = new ArrayList<Msg>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.chatcommunity_frame);

        initMsgs();
        adapter = new MsgAdapter(ChatCommunityActivity.this, R.layout.layout_msg, msgList);

        inputText = (EditText)findViewById(R.id.editText_mesaage);
        send = (Button)findViewById(R.id.button_sendMessage);
        msgListView = (ListView)findViewById(R.id.listView_messageList);
        Log.d("msgListView:","OK");
        msgListView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if(!"".equals(content)) {
                    Msg msg = new Msg(content,"Edith", Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(msgList.size());
                    inputText.setText("");
                }
            }
        });
//
//    }
//

}

    private void initMsgs() {
        Msg msg1 = new Msg("Hello, how are you?","Dannel" ,Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Fine, thank you,and you?", "Edith",Msg.TYPE_SEND);
        msgList.add(msg2);
        Msg msg3 = new Msg("I am fine, too!","Sybile", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }
}
