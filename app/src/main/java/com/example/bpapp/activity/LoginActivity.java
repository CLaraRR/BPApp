package com.example.bpapp.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bpapp.bpapp.R;
import com.example.bpapp.entity.Data;
import com.example.bpapp.entity.Friends;
import com.example.bpapp.entity.Msglist;
import com.example.bpapp.entity.SocialMsg;
import com.example.bpapp.service.ClientService;
import com.example.bpapp.service.ReceiveService;

import java.util.List;

/**
 * 登录Activity
 * Created by 宁润 on 2017/5/28.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameText;
    private EditText pwdText;
    private Button loginButton;
    private TextView registerTV;
    private Button gobackButton;
    private String ip;
    private String port;
    private ClientService clientService;

    private ReceiveService.ReceiveBinder receiveBinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_frame);
        Bundle data=getIntent().getExtras();//通过获得连系，获得资源变量;
        ip = data.getString("ip"); //通过资源变量去获得数据
        port = data.getString("port"); //通过资源变量去获得数据
        clientService= ClientService.getInstance();
        initView();
    }

    /**
     * 和后台服务进行通信连接
     */
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            receiveBinder=(ReceiveService.ReceiveBinder) service;
            ReceiveService service2=receiveBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * 对登录结果进行相应处理
     */
    private Handler mHandler = new Handler() {
        public void handleMessage (Message msg) {//此方法在ui线程运行  
            String str=msg.obj.toString();
            System.out.println(str);
            if(str.startsWith("+OK LOGIN")){
                //启动监听服务，在后台运行，监听服务器发来的数据
                runService();

                //登陆成功跳转到首页,并把取出来的数据保存到静态类中
                processData(str);

                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);

            }else{
                //登陆不成功留在本页面
            }
        }

        private void processData(String str) {
            String[] split=str.split("#");
            String friendInfo=split[2];
            String dataMsg=split[3];
            String socialMsg=split[4];

            System.out.println("friendInfo:"+friendInfo);
            System.out.println("dataMsg:"+dataMsg);
            System.out.println("socialMsg:"+socialMsg);
            List<Friends> friendsList=clientService.parseFriendInfo(friendInfo);
            List<Data> dataList=clientService.parseData(dataMsg);
            List<SocialMsg> socialMsgList=clientService.parseSocialMsg(socialMsg);

            Msglist msglist=new Msglist();
            msglist.addData(dataList);
            msglist.addFriends(friendsList);
            msglist.addSocialMsg(socialMsgList);

        }

        private void runService() {
            Intent bindIntent = new Intent(LoginActivity.this, ReceiveService.class);
            bindIntent.putExtra("ip",ip);
            bindIntent.putExtra("port",port);
            bindService(bindIntent,connection,BIND_AUTO_CREATE);
        }
    };

    /**
     * 初始化界面
     */
    private void initView() {

        usernameText=(EditText) findViewById(R.id.username);
        pwdText=(EditText) findViewById(R.id.pwd);
        loginButton=(Button) findViewById(R.id.login_button);
        gobackButton=(Button) findViewById(R.id.toolbar_left_btn);
        registerTV=(TextView) findViewById(R.id.register);
        loginButton.setOnClickListener(this);
        registerTV.setClickable(true);
        registerTV.setOnClickListener(this);
        gobackButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_button:
                //用户登陆
                String username=usernameText.getText().toString();
                String pwd=pwdText.getText().toString();
                doLogin(username,pwd);




                break;
            case R.id.register:
                //跳转到注册页面
                Intent intent2=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent2);
                break;
            case R.id.toolbar_left_btn:
                finish();
                break;
        }
    }

    private void doLogin(final String username, final String pwd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                String head="LH";
//                String tail="LT";
//                String loginMsg=head+" "+username+" "+pwd+" "+tail;

                String backMessage=clientService.login(username.trim(),pwd.trim());
                Message msg=new Message();
                msg.obj=backMessage;
                mHandler.sendMessage(msg);
            }
        }).start();
    }
}
