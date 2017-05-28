package com.example.clara.bpapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        TextView textview = new TextView(this);
        textview.setText("这是消息界面");
        setContentView(textview);
    }
}
