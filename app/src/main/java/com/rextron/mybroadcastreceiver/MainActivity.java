package com.rextron.mybroadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "EventBroadcastReceiver";
    private static final String ACTION_ADD = "com.avc.app.myservice.action.add";//這個intent action看不懂 應該跟android原生的broadcast intent無關
    private static final String ACTION_VIEW = "com.avc.app.myservice.action.view";//
    private static final String ACTION_DELETE = "com.avc.app.myservice.action.delete";//
    private static final String ACTION_UPDATE = "com.avc.app.myservice.action.update";//
    private EventBroadcastReceiver eventBroadcastReceiver;
    private HandlerThread handlerThread = null;
    private Button button ;
    public int time = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventBroadcastReceiver = new EventBroadcastReceiver();

        // Run on another thread to avoid ANRs
        handlerThread = new HandlerThread("EventHandler", Process.THREAD_PRIORITY_FOREGROUND);
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("com.androvideo.va.action.USER_EVENT");
        intentFilter.addAction("com.androvideo.action.RESET_DEFAULT");
        intentFilter.addAction("com.androvideo.action.STREAM_RESTARTED");
        intentFilter.addAction(ACTION_ADD);
        intentFilter.addAction(ACTION_VIEW);
        intentFilter.addAction(ACTION_DELETE);
        intentFilter.addAction(ACTION_UPDATE);
        registerReceiver(eventBroadcastReceiver, intentFilter, null, handler);

        button = findViewById(R.id.button);
        button.setOnClickListener(buttonListener);

        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new TestEvent(500));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(eventBroadcastReceiver);

        EventBus.getDefault().unregister(this);
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // 寫要做的事...

                EventBus.getDefault().post(new TestEvent(500));

        }
    };
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TestEvent event){
       // progressBar.setProgress(event.getMsg());
       button.setText("POI:"+event.getMsg());
        Log.i(TAG,"@Subscribe(threadMode = ThreadMode.MAIN)");
    }

}
