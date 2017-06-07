package com.example.bpapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.bpapp.bpapp.R;
import com.example.bpapp.entity.SocialMsg;

import java.util.List;

/**
 * Created by chenq on 2017/5/29.
 */

public class SocialMsgAdapter extends ArrayAdapter<SocialMsg> {
    private int resourceId;
    public SocialMsgAdapter(Context context, int resource, List<SocialMsg>objects) {
        super(context, resource,objects);
        resourceId=resource;
    }

    public View getView(int postion, View convertView, ViewGroup parent){
        SocialMsg socialMsg=getItem(postion);
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
        viewHolder.touxiang.setImageResource(socialMsg.getImageId());
        viewHolder.time.setText(socialMsg.getTime().toString());
        viewHolder.digest.setText(socialMsg.getContent());
        viewHolder.contact.setText(socialMsg.getName());
        return view;
    }

    class ViewHolder{
        ImageView touxiang;
        TextView contact;
        TextView digest;
        TextView time;
    }
}
