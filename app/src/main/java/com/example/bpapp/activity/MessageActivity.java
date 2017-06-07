package com.example.bpapp.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.MutableShort;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.example.bpapp.adapter.FriendMsgAdapter;
import com.example.bpapp.adapter.FriendMsgAdapter2;
import com.example.bpapp.bpapp.R;
import com.example.bpapp.entity.FriendMsg;
import com.example.bpapp.broadcast.ReceiveBroadCast;
import com.example.bpapp.entity.Msglist;
import com.example.bpapp.service.ClientService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * 好友消息Activity
 */
public class MessageActivity extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener,ReceiveBroadCast.BRInteraction{
    private List<FriendMsg> friendMsgList=new ArrayList<FriendMsg>();
    private HashMap<Integer,List<FriendMsg>> friendMsgMap=new HashMap<>();
    private ListView listView;
    private Button checkContactsButton;
    private Button addContactsButton;
    private SwipeRefreshLayout swipeLayout;
    private ReceiveBroadCast receiveBroadCast;
    private ClientService clientService;
    private  FriendMsgAdapter2 friendMsgAdapter;
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
        setContentView(R.layout.message_frame);

        /**
         * 实例化一个广播接收器
         */
        receiveBroadCast=new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("TH");    //只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiveBroadCast, filter);
        receiveBroadCast.setBRInteractionListener(this);

        clientService=ClientService.getInstance();//获得ClientService实例
        friendMsgList= Msglist.getFriendMsgList();//获得静态类中的friendMsgList


        /**
         * 初始化一些组件
         */
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        swipeLayout.setOnRefreshListener(this);

        checkContactsButton=(Button)findViewById(R.id.toolbar_right_btn);
        checkContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MessageActivity.this,FriendsActivity.class);
//                intent.putExtra();
                startActivity(intent);
            }
        });
        addContactsButton=(Button)findViewById(R.id.toolbar_right_btn2);
        addContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MessageActivity.this,AddContactsActivity.class);
//                intent.putExtra();
                startActivity(intent);
            }
        });

        listView=(ListView)findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MessageActivity.this,ChatActivity.class);
                Bundle data=new Bundle();
//                data.putSerializable("friendMsg", (Serializable) friendMsgMap.get(position));
                data.putString("friendName",friendMsgMap.get(position).get(0).getName());
                intent.putExtras(data);
                startActivity(intent);
            }
        });
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
        //friendMsgAdapter=new FriendMsgAdapter(MessageActivity.this,R.layout.layout_msg,friendMsgList);
        /**
         * 初始化数据
         */
        initFriendMsg(friendMsgList);
        friendMsgAdapter=new FriendMsgAdapter2(MessageActivity.this,friendMsgList);
        friendMsgAdapter.setListView(listView);
        listView.setAdapter(friendMsgAdapter);

    }

    /**
     * 页面刷新完成更新数据操作
     */
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

                    swipeLayout.setRefreshing(false);
                    break;

            }
        }
    };
    private void initFriendMsg(List<FriendMsg> list){
        System.out.println("get friendMsg success!");
//        for(int i=0;i<n;i++){
//            Date date=new Date();
//            DateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:MM");
//            Random random = new Random();
//            int index=random.nextInt(16);
//            FriendMsg friendMsg=new FriendMsg("Danel","Hello,Marry",formater.format(date).toString(),txImg[index]);
//            friendMsgList.add(friendMsg);
//        }
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i).getName()+" "+list.get(i).getContent());
            String name=list.get(i).getName();
            FriendMsg msg = list.get(i);
            //如果map里面已经有这个好友的消息记录，则要add到一个list用好友名字进行管理
            Iterator iterator = friendMsgMap.keySet().iterator();
            int index=0;
            int flag=0;
            while(iterator.hasNext()){
                index= (int) iterator.next();
                List<FriendMsg> ll=friendMsgMap.get(index);
                if(ll.get(0).getName().equals(name)){
                    ll.add(msg);
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                Random random = new Random();
                int indexImg=random.nextInt(16);
                msg.setImageId(txImg[indexImg]);
                List<FriendMsg> msglist1 = new ArrayList<>();
                msglist1.add(msg);
                friendMsgMap.put(index+1, msglist1);
            }




        }


    }

    @Override
    public void onRefresh() {

        mHandler.sendEmptyMessageDelayed(1, 2000);//刷新完成
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiveBroadCast);
    }

    @Override
    public void setMsg(String content) {
        if(content!=null){
            System.out.println("message content:"+content);
            List<FriendMsg> list = clientService.parseFriendMsg(content);
            friendMsgList.addAll(list);
            Msglist.addFriendMsg(list);
            Msglist.friendMsg2chatMsg(list);
            initFriendMsg(list);//更新数据
            for(int i=0;i<list.size();i++){
                friendMsgAdapter.updataView(list.get(i),listView);
            }

        }
    }
}
