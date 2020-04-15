package com.example.rjq.myapplication.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rjq.myapplication.MyApplication;
import com.example.rjq.myapplication.R;
import com.example.rjq.myapplication.util.Sington;

public class OtherProcessActivity extends AppCompatActivity {
    String s = "s";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String s = "a";
        Log.d("Application create", this.toString());
        setContentView(R.layout.activity_other_process);
        Log.d("process test","other process :"+android.os.Process.myPid());
        Log.d("process test","other process current thread:"+android.os.Process.myTid());
        Log.d("single", Sington.singles.toString());
        final TextView tv = (TextView) findViewById(R.id.test);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Log.d("process test","other process main thread:"+android.os.Process.myTid());
//                                tv.setText("asas");
//                            }
//                        });
//                        Context context = MyApplication.getContext();
                        new Handler(getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("process test","other process main thread:"+android.os.Process.myTid());
                                // 获取activity任务栈
                                ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                                ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
                                tv.setText(info.baseActivity.getClassName());
                            }
                        });
                    }
                }).start();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("process test","other process son thread:"+ Process.myTid());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("process test","other process main thread:"+android.os.Process.myTid());
                    }
                });
            }
        }).start();

    }
}
