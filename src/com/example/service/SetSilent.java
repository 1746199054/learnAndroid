package com.example.service;

import com.example.tools.AudioManagerUtil;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SetSilent extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent.getBooleanExtra("Tag", false)){
			AudioManagerUtil.setSilent(this);
		}else{
			AudioManagerUtil.setResume(this);
		}
		stopSelf();
		return super.onStartCommand(intent, flags, startId);
	}
}
