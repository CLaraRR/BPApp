package com.example.bpapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.bpapp.bpapp.R;
import com.example.bpapp.entity.FriendMsg;

import java.util.List;

/**
 * Created by chenq on 2017/5/29.
 */

public class FriendMsgAdapter extends ArrayAdapter<FriendMsg> {
    private int resourceId;
    public FriendMsgAdapter(Context context, int resource, List<FriendMsg>objects) {
        super(context, resource,objects);
        resourceId=resource;
    }

    public View getView(int postion, View convertView, ViewGroup parent){
        FriendMsg friendMsg=getItem(postion);
        View view;
        ViewHolder viewHolder;

        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.touxiang=(ImageView)view.findViewById(R.id.image_touxiang);
            viewHolder.contact=(TextView)view.findViewById(R.id.text_contacts);
            viewHolder.digest=(TextView)view.findViewById(R.id.text_digest);

            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.touxiang.setImageResource(friendMsg.getImageId());
        viewHolder.digest.setText(friendMsg.getContent());
        viewHolder.contact.setText(friendMsg.getName());
        return view;
    }




    class ViewHolder{
        ImageView touxiang;
        TextView contact;
        TextView digest;
    }
}
