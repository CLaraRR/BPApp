package com.example.clara.bpapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//import com.example.clara.net.ClientService;

import com.example.clara.net.ClientService;

import java.io.IOException;

import static java.lang.Boolean.*;

public class ConnectActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText iptext;
    private EditText porttext;
    private Button connectButton;
    public Boolean isConnect;
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
                String ip=iptext.getText().toString();
                String port=porttext.getText().toString();
//                String ip="49.123.92.128";
//                int port=8000;
                System.out.println("###"+ip+" "+port);
                connectServer(ip,Integer.parseInt(port.trim()));//连接服务器
                
                break;
        }
    }

    private  void connectServer(final String ip, final int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                ClientService clientService=ClientService.
                ClientService clientService=new ClientService(ip,port);
                try {
                    Boolean isConnect = clientService.start(ip,port);
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


