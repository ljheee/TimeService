package com.ljheee.timeservice;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

public class TimeService extends Service{
	
	Runnable task,timeTask;
	Thread thread;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		task = new Runnable() {
			@Override
			public void run() {
				while(!thread.isInterrupted()){
					try {
						Message msg = new Message(); //生成消息
                        //Message msg = Message.obtain(); //生成消息
                        msg.what = 0x0001; //设置消息类型
                        //生成Bundle携带数据
                        Bundle data = new Bundle();      
                        data.putString("value", ""+sdf.format(new Date()));
                        msg.setData(data);
                        //利用Handler发送消息  
                        MainActivity.myHandler.sendMessage(msg);

						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread = new Thread(task);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		
		if(!thread.isAlive()){
			thread.start();
		}
		return START_STICKY;//服务被kill后一定能重启
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
}
