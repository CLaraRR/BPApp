package com.example.bpapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.example.bpapp.adapter.FriendsAdapter;
import com.example.bpapp.bpapp.R;
import com.example.bpapp.entity.Friends;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity {
    private List<Friends> friendsList=new ArrayList<Friends>();
    private ListView listView;
    private Button gobackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_frame);
        gobackButton=(Button)findViewById(R.id.toolbar_left_btn);
        gobackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initFriends(20);

        FriendsAdapter friendsAdapter=new FriendsAdapter(FriendsActivity.this,R.layout.layout_friends,friendsList);
        listView=(ListView)findViewById(R.id.listView_friends);
        listView.setAdapter(friendsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent inten=new Intent(FriendsActivity.this,ChatCommunityActivity.class);
                startActivity(inten);
            }
        });

    }

    private void initFriends(int n){
        for(int i=0;i<n;i++){
            Friends friends=new Friends("Matthow");
            friendsList.add(friends);
        }

    }

}
