package com.example.bpapp.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//import com.example.clara.net.ClientService;

import com.example.bpapp.bpapp.R;
import com.example.bpapp.service.ClientService;

import java.io.IOException;

import static java.lang.Boolean.*;


/**
 * 连接服务器Activity
 * Created by 宁润 on 2017/5/28.
 */
public class ConnectActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText iptext;
    private EditText porttext;
    private Button connectButton;
    public Boolean isConnect;
    public String ip;
    public String port;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_frame);
        initView();


    }


    private Handler mHandler = new Handler() {
        public void handleMessage (Message msg) {//此方法在ui线程运行  
            if(msg.obj==TRUE){
                Intent intent=new Intent(ConnectActivity.this,LoginActivity.class);
                Bundle data=new Bundle();
                data.putString("ip",ip);
                data.putString("port",port);
                intent.putExtras(data);
                startActivity(intent);
            }else{
            }
        }
    };
    
    private void initView() {
        iptext=(EditText) findViewById(R.id.iptext);
        porttext=(EditText) findViewById(R.id.porttext);
        connectButton=(Button)findViewById(R.id.connect_button);
        connectButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.connect_button:
                ip=iptext.getText().toString();
                port=porttext.getText().toString();
                connectServer(ip,Integer.parseInt(port.trim()));//连接服务器
                
                break;
        }
    }

    private  void connectServer(final String ip, final int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ClientService clientService= ClientService.getInstance();
                clientService.setAddress(ip);
                clientService.setPort(port);

                try {
                    Boolean isConnect = clientService.start();
                    if(!isConnect){
                        System.out.println("connect fail!");
                    }else{
                        System.out.println("connect success!");
                    }
                    Message msg=new Message();
                    msg.obj=isConnect;
                    mHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}


