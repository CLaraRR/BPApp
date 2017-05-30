package com.example.clara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.clara.bpapp.R;
import com.example.clara.entity.Friends;

import java.util.List;

/**
 * Created by chenq on 2017/5/30.
 */

public class FriendsAdapter extends ArrayAdapter<Friends> {
    private int resourceId;

    public FriendsAdapter(Context context, int textViewResourceId, List<Friends> objects) {
        super(context, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Friends friends=getItem(position);
        View view;
        ViewHolder viewHolder;

        if(convertView==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            viewHolder.FrindsView=(TextView)view.findViewById(R.id.textView_friends);

            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.FrindsView.setText(friends.getName());
        return view;
    }

    class ViewHolder{
        TextView FrindsView;
    }
}
