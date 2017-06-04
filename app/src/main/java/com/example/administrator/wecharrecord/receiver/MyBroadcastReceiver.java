package com.example.administrator.wecharrecord.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/6/4.
 */

public class MyBroadcastReceiver extends BroadcastReceiver{

    private MyListener mListener;
    public MyBroadcastReceiver(MyListener listener){
        mListener=listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mListener.getDBDao();
    }
}
