package com.example.bpapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.bpapp.bpapp.R;

/**
 * 设置Activity
 * Created by 宁润 on 2017/5/28.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_frame);
        initView();
    }

    private void initView() {
        usernameText=(EditText) findViewById(R.id.usernametext);
        userIDText=(EditText) findViewById(R.id.userIDtext);
        pwdText=(EditText) findViewById(R.id.pwdtext);
        sexGroup=(RadioGroup) findViewById(R.id.sexradioGroup);
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


                break;
            case R.id.cancel_button:
                finish();
                break;
            case R.id.toolbar_left_btn:
                finish();
                break;

        }
    }
}
