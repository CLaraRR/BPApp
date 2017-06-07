package com.example.bpapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.bpapp.bpapp.R;
import com.example.bpapp.entity.ChatMsg;
import com.example.bpapp.entity.SocialMsg;

import java.util.List;

/**
 * Created by chenq on 2017/5/30.
 */

public class ChatMsgAdapter extends ArrayAdapter<ChatMsg> {
    private int resourceId;

    public ChatMsgAdapter(Context context, int textViewResourceId, List<ChatMsg> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMsg chatMsg = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);
            viewHolder.rightLayout = (LinearLayout)view.findViewById(R.id.right_layout);
            viewHolder.leftMsg = (TextView)view.findViewById(R.id.text_leftMessage);
            viewHolder.rightMsg = (TextView)view.findViewById(R.id.text_rightMessage);
            viewHolder.head1 = (ImageView)view.findViewById(R.id.picBoy);
            viewHolder.head2 = (ImageView)view.findViewById(R.id.picGirl);
            viewHolder.MsgFrom=(TextView)view.findViewById(R.id.textView_msgFrineds) ;
            viewHolder.Myself=(TextView)view.findViewById(R.id.textView_msgMyself);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if(chatMsg.getType() == ChatMsg.TYPE_RECEIVED) {
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.head1.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.head2.setVisibility(View.GONE);
            viewHolder.leftMsg.setText(chatMsg.getContent());
            viewHolder.MsgFrom.setText(chatMsg.getUserName());
            viewHolder.Myself.setText("");
        } else if(chatMsg.getType() == ChatMsg.TYPE_SEND) {
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.head2.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.head1.setVisibility(View.GONE);
            viewHolder.rightMsg.setText(chatMsg.getContent());
            viewHolder.Myself.setText(chatMsg.getUserName());
            viewHolder.MsgFrom.setText("");
        }
        return view;
    }

    class ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        ImageView head1;
        ImageView head2;
        TextView MsgFrom;
        TextView Myself;
    }
}
