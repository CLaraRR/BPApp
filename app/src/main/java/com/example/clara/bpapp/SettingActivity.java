package com.example.clara.bpapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_frame);
        initView();
    }

    private void initView() {
        usernameText=(EditText) findViewById(R.id.username3);
        userIDText=(EditText) findViewById(R.id.userID);
        pwdText=(EditText) findViewById(R.id.pwd3);
        sexGroup=(RadioGroup) findViewById(R.id.sexradioGroup);
        maleRadio=(RadioButton) findViewById(R.id.radiomale);
        femaleRadio=(RadioButton) findViewById(R.id.radiofemale);
        birthText=(EditText) findViewById(R.id.birthtext);
        deviceIDText=(EditText) findViewById(R.id.devicetext);
        saveButton=(Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.save_button:
                //保存动作。。


                break;
        }
    }
}
