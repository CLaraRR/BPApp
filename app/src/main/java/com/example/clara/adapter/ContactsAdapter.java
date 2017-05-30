package com.example.clara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.clara.bpapp.R;
import com.example.clara.entity.Contacts;

import java.util.List;

/**
 * Created by chenq on 2017/5/29.
 */

public class ContactsAdapter extends ArrayAdapter<Contacts> {
    private int resourceId;
    public ContactsAdapter(Context context, int resource, List<Contacts>objects) {
        super(context, resource,objects);
        resourceId=resource;
    }

    public View getView(int postion, View convertView, ViewGroup parent){
        Contacts contacts=getItem(postion);
        View view;
        ViewHolder viewHolder;

        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.touxiang=(ImageView)view.findViewById(R.id.image_touxiang);
            viewHolder.contact=(TextView)view.findViewById(R.id.text_contacts);
            viewHolder.digest=(TextView)view.findViewById(R.id.text_digest);
            viewHolder.time=(TextView)view.findViewById(R.id.text_time);

            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.touxiang.setImageResource(contacts.getImageId());
        viewHolder.time.setText(contacts.getTime().toString());
        viewHolder.digest.setText(contacts.getDigest());
        viewHolder.contact.setText(contacts.getName());
        return view;
    }

    class ViewHolder{
        ImageView touxiang;
        TextView contact;
        TextView digest;
        TextView time;
    }
}
