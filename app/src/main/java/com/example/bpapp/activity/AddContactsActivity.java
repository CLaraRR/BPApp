package com.example.bpapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bpapp.bpapp.R;


public class AddContactsActivity extends AppCompatActivity implements View.OnClickListener{
    private Button gobackButton;
    private Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontacts_frame);
        gobackButton=(Button)findViewById(R.id.toolbar_left_btn);
        addButton=(Button)findViewById(R.id.button_firmAdd);
        gobackButton.setOnClickListener(this);
        addButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_firmAdd:

                break;
            case R.id.toolbar_left_btn:
                finish();
                break;
        }
    }
}
