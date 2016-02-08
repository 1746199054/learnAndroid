package com.example.service;

import com.example.tools.MyApplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
	private boolean threadDisable;
	private String activityValue = null;
	private int count;

	@Override
	public IBinder onBind(Intent intent) {
		Log.v("wang", "on bind");
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.v("wang", "on create");
		new Thread(new Runnable() {
			@Override
			public void run() {
			}
		}).start();
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
		Log.v("wang", "on rebind");
	}

	@Override
	public int onStartCommand(Intent intent,int flags,  int startId) {
		Log.v("wang", "onStartCommand");
		Intent aintent = new Intent("com.example.schedule.MyAppWidgetProvider.CHANGE_TEXT");
		aintent.putExtra("setText", "我收到广播啦");
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, aintent, 0);
		am.set(AlarmManager.RTC_WAKEUP , System.currentTimeMillis() + 70000, pendingIntent);
		return super.onStartCommand(intent,flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.v("wang", "on unbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.threadDisable = true;
		Log.v("wang", "on destroy");
	}

	public int getCount() {
		return count;
	}

}
