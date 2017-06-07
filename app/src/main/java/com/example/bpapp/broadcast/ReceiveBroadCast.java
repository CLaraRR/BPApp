package com.example.bpapp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by ningrun on 2017/6/4.
 */
public class ReceiveBroadCast extends BroadcastReceiver {
    private BRInteraction brInteraction;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        System.out.println("action:"+action);

//        Bundle bundle = intent.getExtras();
        String msg=intent.getStringExtra("data");
        System.out.println("msg:"+msg);
        brInteraction.setMsg(msg);
    }

    public interface BRInteraction {
        public void setMsg(String content);
    }

    public void setBRInteractionListener(BRInteraction brInteraction) {
        this.brInteraction = brInteraction;
    }

}
