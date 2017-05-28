package com.example.clara.bpapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameText;
    private EditText pwdText;
    private Button loginButton;
    private TextView registerTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_frame);
        initView();
    }

    private void initView() {

        usernameText=(EditText) findViewById(R.id.username);
        pwdText=(EditText) findViewById(R.id.pwd);
        loginButton=(Button) findViewById(R.id.login_button);
        registerTV=(TextView) findViewById(R.id.register);
        loginButton.setOnClickListener(this);
        registerTV.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_button:
                //验证用户
                String username=usernameText.getText().toString();
                String pwd=pwdText.getText().toString();
                //add your code here



                //跳转到首页
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.register:
                //跳转到注册页面
                Intent intent2=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
