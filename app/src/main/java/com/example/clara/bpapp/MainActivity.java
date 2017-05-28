package com.example.clara.bpapp;


import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabSpec spec;
        Intent intent;  // Reusable Intent for each tab

        //第一个Tab
        intent = new Intent(this,IndexActivity.class);//新建一个Intent用作Tab1显示的内容
        spec = tabHost.newTabSpec("index")//新建一个 Tab
                .setIndicator("首页", res.getDrawable(android.R.drawable.ic_media_play))//设置名称以及图标
                .setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
        tabHost.addTab(spec);//添加进tabHost

        //第二个Tab
        intent = new Intent(this,MessageActivity.class);//第二个Intent用作Tab1显示的内容
        spec = tabHost.newTabSpec("message")//新建一个 Tab
                .setIndicator("消息", res.getDrawable(android.R.drawable.ic_menu_edit))//设置名称以及图标
                .setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
        tabHost.addTab(spec);//添加进tabHost

        //第三个Tab
        intent = new Intent(this,SettingActivity.class);//第二个Intent用作Tab1显示的内容
        spec = tabHost.newTabSpec("setting")//新建一个 Tab
                .setIndicator("我的", res.getDrawable(android.R.drawable.ic_menu_help))//设置名称以及图标
                .setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
        tabHost.addTab(spec);//添加进tabHost

        tabHost.setCurrentTab(0);//设置当前的选项卡,这里为index
    }
}
