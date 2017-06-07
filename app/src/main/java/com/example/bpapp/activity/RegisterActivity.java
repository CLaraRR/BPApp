package com.example.bpapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bpapp.bpapp.R;
import com.example.bpapp.service.ClientService;

/**
 * 注册Activity
 * Created by 宁润 on 2017/5/28.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    private EditText usernameText;
    private EditText passwordText;
    private EditText confirmText;
    private EditText birthText;
    private RadioGroup sexRadio;
    private RadioButton maleRadio;
    private RadioButton femaleRadio;
    private Button registerButton;
    private Button cancleButton;

    private int sex; //1--man;0--female

    private Button gobackButton;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_frame);
        initView();
    }

    private void initView() {
        usernameText=(EditText) findViewById(R.id.usernametext);
        passwordText=(EditText) findViewById(R.id.pwdtext);
        confirmText=(EditText) findViewById(R.id.pwdconfirmtext);
        birthText=(EditText) findViewById(R.id.birthtext);
        sexRadio=(RadioGroup) findViewById(R.id.sexradioGroup);
        maleRadio=(RadioButton) findViewById(R.id.radiomale);
        femaleRadio=(RadioButton) findViewById(R.id.radiofemale);
        registerButton=(Button) findViewById(R.id.register_button);
        cancleButton=(Button) findViewById(R.id.cancel_button);
        gobackButton=(Button) findViewById(R.id.toolbar_left_btn);
        registerButton.setOnClickListener(this);
        cancleButton.setOnClickListener(this);
        gobackButton.setOnClickListener(this);
        sexRadio.setOnCheckedChangeListener(this);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage (Message msg) {//此方法在ui线程运行
            String str=msg.obj.toString();
            System.out.println(str);

            if(str.startsWith("+OKREGISTER")){
                //登陆成功跳转到首页
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
//                intent.putExtra();
                startActivity(intent);

            }else if(str.startsWith("+ERRORUSERNAM")){
                Toast.makeText(RegisterActivity.this,"用户名已经存在",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(RegisterActivity.this,"服务器忙，请稍后操作",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(maleRadio.getId()==checkedId){
            this.sex=1;
        }
        else if(femaleRadio.getId()==checkedId){
            this.sex=0;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register_button:
                String username=usernameText.getText().toString();
                String pwd=passwordText.getText().toString();
                String pwdcomfirm=confirmText.getText().toString();
                String birth=birthText.getText().toString();
                //注册动作。。。
                //add your code here
                if(pwd.equals(pwdcomfirm)){
                    register(username,pwd,sex,birth);
                }else{
                    Toast.makeText(RegisterActivity.this,"输入密码不一致",Toast.LENGTH_SHORT).show();
                    passwordText.setText("");
                    confirmText.setText("");
                }

                break;
            case R.id.cancel_button:
                usernameText.setText("");
                passwordText.setText("");
                confirmText.setText("");
                birthText.setText("");
                maleRadio.setSelected(true);

                break;
            case R.id.toolbar_left_btn:
                finish();
                break;
        }
    }

    private void register(final String username, final String pwd, final int pwdcomfirm, final String birth){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ClientService clientService= ClientService.getInstance();
                String  resString=clientService.register(username,pwd,pwdcomfirm,birth);
                Message msg=new Message();
                msg.obj=resString;
                mHandler.sendMessage(msg);
            }
        }).start();
    }
}
