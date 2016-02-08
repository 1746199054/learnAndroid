package com.example.tools;

import com.example.schedule.Schedule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmManagerUtil {
	public static AlarmManager getAlarmManager(Context ctx) {
		return (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
	}

	/**
	 * 指定时间后进行更新赛事信息(有如闹钟的设置) 注意: Receiver记得在manifest.xml中注册
	 * 
	 * @param ctx
	 */
	public static void sendUpdateBroadcast(Context ctx) {
		AlarmManager am = getAlarmManager(ctx);
		// 10秒后将产生广播
		
		Intent intent = new Intent("com.example.schedule.MyAppWidgetProvider.CHANGE_TEXT");
		intent.putExtra("setText", "我收到广播啦");
		PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, 0, intent, 0);
		am.set(AlarmManager.RTC_WAKEUP , System.currentTimeMillis() + 10000, pendingIntent);
	}

	/**
	 * 取消定时执行(有如闹钟的取消)
	 * 
	 * @param ctx
	 */
	/*public static void cancelUpdateBroadcast(Context ctx) {
		AlarmManager am = getAlarmManager(ctx);
		Intent i = new Intent(ctx, UpdateReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, 0, i, 0);
		am.cancel(pendingIntent);
	}*/
}
