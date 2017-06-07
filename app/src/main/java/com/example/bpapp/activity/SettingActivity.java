package com.example.bpapp.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bpapp.bpapp.R;
import com.example.bpapp.service.ClientService;

/**
 * 设置Activity
 * Created by 宁润 on 2017/5/28.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{
    private EditText usernameText;
    private EditText userIDText;
    private EditText pwdText;
    private RadioGroup sexGroup;
    private RadioButton maleRadio;
    private RadioButton femaleRadio;
    private EditText birthText;
    private EditText deviceIDText;
    private Button saveButton;
    private Button cancleButton;
    private Button gobackButton;

    //    private ClientService clientService=ClientService.getInstance();

    //1--man;0--female
    private int sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_frame);
        initView();
    }

    public Handler mHander=new Handler() {
        public void handleMessage(Message msg){
            String string=msg.obj.toString();

            if(string.startsWith("+OKSET"))
            {
                Toast.makeText(SettingActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
            }else if(string.startsWith("+ERRORUSERNAME"))
            {
                Toast.makeText(SettingActivity.this,"该用户名已被占用",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(SettingActivity.this,"服务器开小差了~请重试",Toast.LENGTH_SHORT).show();
            }
        }

    };
    private void initView() {
        usernameText=(EditText) findViewById(R.id.usernametext);
        userIDText=(EditText) findViewById(R.id.userIDtext);
        pwdText=(EditText) findViewById(R.id.pwdtext);
        sexGroup=(RadioGroup) findViewById(R.id.sexradioGroup);
        sexGroup.setOnCheckedChangeListener(this);
        maleRadio=(RadioButton) findViewById(R.id.radiomale);
        femaleRadio=(RadioButton) findViewById(R.id.radiofemale);
        birthText=(EditText) findViewById(R.id.birthtext);
        deviceIDText=(EditText) findViewById(R.id.deviceIDtext);
        saveButton=(Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
        cancleButton=(Button) findViewById(R.id.cancel_button);
        cancleButton.setOnClickListener(this);
        gobackButton=(Button) findViewById(R.id.toolbar_left_btn);
        gobackButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.save_button:
                //保存动作。。
                try {
                    String username=this.usernameText.getText().toString();
                    ClientService clientService= ClientService.getInstance();
//                    int userID=clientService.getUserId();
                    String pwd=this.pwdText.getText().toString();
                    String birth=this.birthText.getText().toString();
                    int deviceID=Integer.parseInt(this.deviceIDText.getText().toString());

                    this.doSave(username,123,pwd,sex,birth,deviceID);

                }catch (Exception e){
                    Toast.makeText(SettingActivity.this,"设备ID为数字",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                break;
            case R.id.cancel_button:
                finish();
                break;
            case R.id.toolbar_left_btn:
                finish();
                break;

        }
    }

    private void doSave(final String username, final int userID, final String password, final int sex, final String brith, final int deciveID){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ClientService clientService=ClientService.getInstance();
                String resStr= clientService.set(username,userID,password,sex,brith,deciveID);
                Message msg=new Message();
                msg.obj=resStr;
                mHander.sendMessage(msg);
            }
        }).start();

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if(maleRadio.getId()==i){
            this.sex=1;
        }
        else if(femaleRadio.getId()==i){
            this.sex=0;
        }
    }
}
