package com.example.clara.bpapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConnectActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText iptext;
    private EditText porttext;
    private Button connectButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_frame);
        initView();


    }

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
                //连接服务器


                //连接成功跳转到登录界面
                Intent intent=new Intent(ConnectActivity.this,LoginActivity.class);
                startActivity(intent);

                //连接不成功留在本页面
                
                break;
        }
    }
}
