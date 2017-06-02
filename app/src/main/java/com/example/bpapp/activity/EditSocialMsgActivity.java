package com.example.bpapp.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bpapp.bpapp.R;

public class EditSocialMsgActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private FloatingActionButton fab;
    private Button gobackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_social_msg_frame);
        editText=(EditText)findViewById(R.id.editText);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(this);
        gobackButton=(Button)findViewById(R.id.toolbar_left_btn);
        gobackButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fab:
                String sendText=editText.getText().toString();
                //发送数据




                //返回到社区界面
                finish();
                break;
            case R.id.toolbar_left_btn:
                finish();
                break;
        }
    }
}
