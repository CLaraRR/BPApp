package com.example.bpapp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bpapp.bpapp.R;
import com.example.bpapp.service.ClientService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditSocialMsgActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private FloatingActionButton fab;
    private Button gobackButton;
    private   ClientService clientService= ClientService.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_social_msg_frame);
        editText=(EditText)findViewById(R.id.editText);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(this);
        gobackButton=(Button)findViewById(R.id.toolbar_left_btn);
        gobackButton.setOnClickListener(this);
    }
    private Handler mHandler = new Handler() {
        public void handleMessage (Message msg) {//此方法在ui线程运行
            String string=msg.obj.toString();
            System.out.println(string);
            if(string.startsWith("+OKSEND")){
                Toast.makeText(EditSocialMsgActivity.this,"发表成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(EditSocialMsgActivity.this,SocialActivity.class);

                startActivity(intent);
            }else if(string.startsWith("+ERRORSEND")){
                Toast.makeText(EditSocialMsgActivity.this,"发送失败，请稍后操作",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(EditSocialMsgActivity.this,"发送失败，请稍后操作",Toast.LENGTH_SHORT).show();
            }
        }

    };
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fab:
                String sendText=editText.getText().toString();
                //发送数据
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YY-MM-dd HH:mm:ss");
                String time=simpleDateFormat.format(new Date()).toString();
                System.out.println(sendText+time);
                sendMessageToPublic(sendText,time);

                //返回到社区界面
                finish();
                break;
            case R.id.toolbar_left_btn:
                finish();
                break;
        }
    }

    private void sendMessageToPublic(final String content, final String time){
        new Thread(new Runnable() {
            @Override
            public void run() {

                String resString=clientService.sendMessageToPublic(content,time);
//                Log.d("EditSocialMsgActivity",resString);
                Message msg=new Message();
                msg.obj=resString;
                mHandler.sendMessage(msg);
            }
        }).start();
    }
}
