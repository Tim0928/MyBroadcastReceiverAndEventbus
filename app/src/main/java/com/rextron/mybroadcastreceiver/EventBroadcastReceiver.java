package com.rextron.mybroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class EventBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "EventBroadcastReceiver";
    private static final String ACTION_ADD = "com.avc.app.myservice.action.add";//這個intent action看不懂 應該跟android原生的broadcast intent無關
    private static final String ACTION_VIEW = "com.avc.app.myservice.action.view";//
    private static final String ACTION_DELETE = "com.avc.app.myservice.action.delete";//
    private static final String ACTION_UPDATE = "com.avc.app.myservice.action.update";//
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_ADD.equals(intent.getAction())) {
            // TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
        }else if(ACTION_VIEW.equals(intent.getAction())){
            Bundle bundle0x01 =intent.getExtras();
            String Name=bundle0x01.getBundle("man").get("man").toString();
            Log.i(TAG,intent.getAction());
            Log.i(TAG,"ACTION_VIEW.equals(intent.getAction()");
            Log.i(TAG,Name);
            intent.setAction(ACTION_UPDATE);
            context.sendBroadcast(intent);
        }else if(ACTION_DELETE.equals(intent.getAction())){


        }else if(ACTION_UPDATE.equals(intent.getAction())){
            Bundle bundle0x01 =intent.getExtras();
            String Name=bundle0x01.getBundle("man").get("man").toString();
            Log.i(TAG,intent.getAction()+":"+Name);
            Toast.makeText(context,intent.getAction()+":"+Name, Toast.LENGTH_SHORT).show();
        }else{

        }


    }
}
