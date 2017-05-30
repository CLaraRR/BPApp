package com.example.bpapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.bpapp.bpapp.R;

/**
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
    private String sex;

    
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

        sexRadio.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(maleRadio.getId()==checkedId){
            this.sex="男";
        }
        else if(femaleRadio.getId()==checkedId){
            this.sex="女";
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


                break;
            case R.id.save_button:
                usernameText.setText("");
                passwordText.setText("");
                confirmText.setText("");
                birthText.setText("");
                maleRadio.setSelected(true);

                break;
        }
    }
}
