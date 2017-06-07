package com.example.bpapp.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bpapp.adapter.SocialMsgAdapter;
import com.example.bpapp.bpapp.R;
import com.example.bpapp.entity.Msglist;
import com.example.bpapp.entity.SocialMsg;
import com.example.bpapp.broadcast.ReceiveBroadCast;
import com.example.bpapp.service.ClientService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * 社区消息页面Activity
 */
public class SocialActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,ReceiveBroadCast.BRInteraction{
    private ListView listView;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeLayout;
    private ReceiveBroadCast receiveBroadCast;
    private List<SocialMsg> socialMsgList=new ArrayList<>();
    private ClientService clientService;
    private static int[] txImg={
            R.drawable.tx0,
            R.drawable.tx1,
            R.drawable.tx2,
            R.drawable.tx3,
            R.drawable.tx4,
            R.drawable.tx5,
            R.drawable.tx6,
            R.drawable.tx7,
            R.drawable.tx8,
            R.drawable.tx9,
            R.drawable.tx10,
            R.drawable.tx11,
            R.drawable.tx12,
            R.drawable.tx13,
            R.drawable.tx14,
            R.drawable.tx15,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_frame);

        /**
         * 实例化一个广播接收器
         */
        receiveBroadCast=new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("CH");    //只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiveBroadCast, filter);
        receiveBroadCast.setBRInteractionListener(this);

        clientService=ClientService.getInstance();//获得ClientService实例
        socialMsgList= Msglist.getSocialMsgList();//获得静态类中的socialMsgList

        /**
         * 初始化一些组件
         */
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        swipeLayout.setOnRefreshListener(this);

        listView=(ListView) findViewById(R.id.list_view);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    swipeLayout.setEnabled(true);
                else
                    swipeLayout.setEnabled(false);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SocialMsg contacts=socialMsgList.get(position);
                Toast.makeText(SocialActivity.this,contacts.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        fab=(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SocialActivity.this,EditSocialMsgActivity.class);
//                intent.putExtra();
                startActivity(intent);
            }
        });

        /**
         * 初始化数据显示界面
         */
        initSocialMsg();



    }

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case 1:
//                    mDatas.addAll(Arrays.asList("Lucene", "Canvas", "Bitmap"));
//                    mAdapter.notifyDataSetChanged();
                    //数据更新操作
                    initSocialMsg();
                    swipeLayout.setRefreshing(false);
                    break;

            }
        }
    };
    private void initSocialMsg(){

        System.out.println("get socialMsg success!");
        for (int i=0;i<socialMsgList.size();i++){
//            Date date=new Date();
//            DateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:MM");
//
//            SocialMsg socialMsg=new SocialMsg("Danel","Hello,Marry",formater.format(date).toString(),R.drawable.touxiang);
//            socialMsgList.add(socialMsg);
            System.out.println(socialMsgList.get(i).getName()+" "+socialMsgList.get(i).getContent()+" "
                    +socialMsgList.get(i).getTime());
            Random random = new Random();
            int index=random.nextInt(16);
            socialMsgList.get(i).setImageId(txImg[index]);
        }
        SocialMsgAdapter socialMsgAdapter=new SocialMsgAdapter(SocialActivity.this,R.layout.layout_msg,socialMsgList);
        listView.setAdapter(socialMsgAdapter);
    }

    @Override
    public void onRefresh() {
        String message=clientService.refreshCommnuity();
        List<SocialMsg> list=clientService.parseSocialMsg(message);
        socialMsgList.addAll(list);
        Msglist.addSocialMsg(list);
        mHandler.sendEmptyMessageDelayed(1, 2000);//刷新完成
    }

    @Override
    public void setMsg(String content) {
        if(content!=null){
            System.out.println("social content:"+content);
            List<SocialMsg> list = clientService.parseSocialMsg(content);
            socialMsgList.addAll(list);
            Msglist.addSocialMsg(list);
            initSocialMsg();//更新数据显示界面
        }
    }

    public void onDestroy(){
        super.onDestroy();
    }
}
