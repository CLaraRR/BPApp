package com.example.bpapp.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.bpapp.adapter.FriendsAdapter;
import com.example.bpapp.bpapp.R;
import com.example.bpapp.entity.Friends;
import com.example.bpapp.broadcast.ReceiveBroadCast;
import com.example.bpapp.entity.Msglist;
import com.example.bpapp.service.ClientService;

import java.util.ArrayList;
import java.util.List;


/**
 * 好友列表activity
 */
public class FriendsActivity extends AppCompatActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,ReceiveBroadCast.BRInteraction{
    private List<Friends> friendsList=new ArrayList<Friends>();
    private ListView listView;
    private SwipeRefreshLayout swipeLayout;
    private Button gobackButton;
    private Button addContactButton;

    private ReceiveBroadCast receiveBroadCast;
    private ClientService clientService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_frame);

        /**
         * 实例化一个广播接收器
         */
        receiveBroadCast=new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("FH");    //只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiveBroadCast, filter);
        receiveBroadCast.setBRInteractionListener(this);

        clientService=ClientService.getInstance();//获得ClientService实例
        friendsList=Msglist.getFriendsList();//获得静态类中的friendsList


        /**
         * 初始化一些组件
         */
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        swipeLayout.setOnRefreshListener(this);
        gobackButton=(Button)findViewById(R.id.toolbar_left_btn);
        gobackButton.setOnClickListener(this);
        addContactButton=(Button)findViewById(R.id.toolbar_right_btn2);
        addContactButton.setOnClickListener(this);

        listView=(ListView)findViewById(R.id.listView_friends);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(FriendsActivity.this,ChatActivity.class);
                intent.putExtra("name",friendsList.get(position).getName());
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

        /**
         * 初始化数据显示界面
         */
        initFriends();

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
                    initFriends();//更新数据显示界面
                    swipeLayout.setRefreshing(false);
                    break;

            }
        }
    };

    private void initFriends(){
        System.out.println("get friends success!");
        for(int i=0;i<friendsList.size();i++){
            System.out.println(friendsList.get(i).getName());
        }
        FriendsAdapter friendsAdapter=new FriendsAdapter(FriendsActivity.this,R.layout.layout_friends,friendsList);
        listView.setAdapter(friendsAdapter);
    }


    @Override
    public void onRefresh() {
        String message=clientService.refreshFriend();
        List<Friends> list=clientService.parseFriendInfo(message);
        friendsList.addAll(list);
        mHandler.sendEmptyMessageDelayed(1, 2000);//刷新完成
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.toolbar_left_btn:
                finish();
                break;
            case R.id.toolbar_right_btn2:
                Intent intent=new Intent(FriendsActivity.this,AddContactsActivity.class);
//                intent.putExtra();
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setMsg(String content) {
        if(content!=null){
            System.out.println("message content:"+content);
            List<Friends> list = clientService.parseFriendInfo(content);
            friendsList.addAll(list);
            initFriends();//更新数据显示界面
        }
    }
}
