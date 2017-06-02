package com.example.bpapp.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bpapp.adapter.ContactsAdapter;
import com.example.bpapp.bpapp.R;
import com.example.bpapp.entity.Contacts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 社区消息页面Activity
 */
public class SocialActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private List<Contacts> contactsList=new ArrayList<>();
    private ListView listView;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_frame);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        swipeLayout.setOnRefreshListener(this);

        initContacts(20);
        ContactsAdapter contactsAdapter=new ContactsAdapter(SocialActivity.this,R.layout.layout_contacts,contactsList);

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
        listView.setAdapter(contactsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacts contacts=contactsList.get(position);
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

                    swipeLayout.setRefreshing(false);
                    break;

            }
        }
    };
    private void initContacts(int n){
        for (int i=0;i<n;i++){
            Date date=new Date();
            DateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:MM");

            Contacts contacts=new Contacts("Danel","Hello,Marry",formater.format(date).toString(),R.drawable.touxiang);
            contactsList.add(contacts);
        }
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(1, 2000);//刷新完成
    }
}
