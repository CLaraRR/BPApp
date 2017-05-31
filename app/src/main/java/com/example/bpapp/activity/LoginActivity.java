package com.example.bpapp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bpapp.bpapp.R;
import com.example.bpapp.service.ClientService;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameText;
    private EditText pwdText;
    private Button loginButton;
    private TextView registerTV;
    private Button gobackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_frame);
        initView();
    }
    private Handler mHandler = new Handler() {
        public void handleMessage (Message msg) {//此方法在ui线程运行  
            String str=msg.obj.toString();
            System.out.println(str); 
            if(str.startsWith("+OK LOGIN")){
                //登陆成功跳转到首页
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                intent.putExtra();
                startActivity(intent);
                
            }else{
                //登陆不成功留在本页面
            }
        }
    };
    private void initView() {

        usernameText=(EditText) findViewById(R.id.username);
        pwdText=(EditText) findViewById(R.id.pwd);
        loginButton=(Button) findViewById(R.id.login_button);
        gobackButton=(Button) findViewById(R.id.toolbar_left_btn);
        registerTV=(TextView) findViewById(R.id.register);
        loginButton.setOnClickListener(this);
        registerTV.setClickable(true);
        registerTV.setOnClickListener(this);
        gobackButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_button:
                //用户登陆
                String username=usernameText.getText().toString();
                String pwd=pwdText.getText().toString();
                doLogin(username,pwd);
                


                
                break;
            case R.id.register:
                //跳转到注册页面
                Intent intent2=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent2);
                break;
            case R.id.toolbar_left_btn:
                finish();
                break;
        }
    }

    private void doLogin(final String username, final String pwd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String head="LH";
                String tail="LT";
                String loginMsg=head+" "+username+" "+pwd+" "+tail;
                ClientService clientService= ClientService.getInstance();
                String backMessage=clientService.send(loginMsg);
                Message msg=new Message();
                msg.obj=backMessage;
                mHandler.sendMessage(msg);
            }
        }).start();
    }
}
