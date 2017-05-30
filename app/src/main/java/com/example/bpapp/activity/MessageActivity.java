package com.example.bpapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class MessageActivity extends AppCompatActivity {
    private List<Contacts> contactsList=new ArrayList<>();
    private Button button_addContatcs=null;
    private Button button_addMessage=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        TextView textview = new TextView(this);
//        textview.setText("这是消息界面");
//        setContentView(textview);

        setContentView(R.layout.message_frame);


        button_addContatcs=(Button)findViewById(R.id.button_addContacts);
        button_addMessage=(Button)findViewById(R.id.button_addMessage);

        initContacts(20);
        ContactsAdapter contactsAdapter=new ContactsAdapter(MessageActivity.this,R.layout.layout_contacts,contactsList);

        ListView listView=(ListView) findViewById(R.id.list_view);
        listView.setAdapter(contactsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacts contacts=contactsList.get(position);
                Toast.makeText(MessageActivity.this,contacts.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        button_addContatcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MessageActivity.this,AddContactsActivity.class);
                startActivity(intent);
            }
        });

        button_addMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MessageActivity.this,FriendsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initContacts(int n){
        for (int i=0;i<n;i++){
            Date date=new Date();
            DateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:MM");

            Contacts contacts=new Contacts("Danel","Hello,Marry",formater.format(date).toString(),R.drawable.touxiang);
            contactsList.add(contacts);
        }
    }
}
