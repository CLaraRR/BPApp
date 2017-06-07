package com.example.bpapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bpapp.bpapp.R;
import com.example.bpapp.entity.FriendMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ningrun
 * @version 1.0
 * @date 2017/6/5
 */
public class FriendMsgAdapter2  extends BaseAdapter {
    private Context mContext;
    private List<FriendMsg> mFriendMsgList;
    private List<FriendMsg> friendMsgList2=new ArrayList<>();
    private ListView listView;
    
    public FriendMsgAdapter2(Context context,List<FriendMsg> objects) {
        //super(context, resource,objects);
        super();
        this.mContext=context;
        this.mFriendMsgList=objects;
        
    }
    
    public void setListView(ListView listView){
        this.listView=listView;
    }
    @Override
    public int getCount() {
        return mFriendMsgList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FriendMsg friendMsg=mFriendMsgList.get(position);
        //View view;
        ViewHolder viewHolder;

        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.layout_msg,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.touxiang=(ImageView)convertView.findViewById(R.id.image_touxiang);
            viewHolder.contact=(TextView)convertView.findViewById(R.id.text_contacts);
            viewHolder.digest=(TextView)convertView.findViewById(R.id.text_digest);

            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        int flag=0;
        for(int i=0;i<friendMsgList2.size();i++){
            if(friendMsgList2.get(i).getName().equals(friendMsg.getName())){
                updataView(friendMsg,listView);
                flag=1;
                break;
            }
        }
        if(flag==0){
            viewHolder.touxiang.setImageResource(friendMsg.getImageId());
            viewHolder.digest.setText(friendMsg.getContent());
            viewHolder.contact.setText(friendMsg.getName());
        }
        
        
        return convertView;
    }
    
    
    public void updataView(FriendMsg newMsg,ListView listView) {
        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();
        int posi=-1;
        int flag=0;
        int i=0;
        for(i=0;i<friendMsgList2.size();i++){
            if(friendMsgList2.get(i).getName().equals(newMsg.getName())){
                posi=i;
                flag=1;
                break;
            }
        }
        if(flag==1){
            if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
                View view = listView.getChildAt(posi - visibleFirstPosi);
                ViewHolder holder = (ViewHolder) view.getTag();
                holder.digest.setText(newMsg.getContent());
                friendMsgList2.set(posi,newMsg);
                mFriendMsgList.add(newMsg);

            }else{
                friendMsgList2.set(posi,friendMsgList2.get(posi));
            }
        }else if(flag==0){
            
            View convertView= LayoutInflater.from(mContext).inflate(R.layout.layout_msg,null);
            ViewHolder viewHolder;
            viewHolder=(ViewHolder)convertView.getTag();
            viewHolder.touxiang.setImageResource(newMsg.getImageId());
            viewHolder.digest.setText(newMsg.getContent());
            viewHolder.contact.setText(newMsg.getName());
            mFriendMsgList.add(newMsg);
            friendMsgList2.add(newMsg);
        }
        
       
    }
    
    
    
    private static class ViewHolder {
        ImageView touxiang;
        TextView contact;
        TextView digest;
    }
}
