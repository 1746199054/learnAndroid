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
	 * ָ��ʱ�����и���������Ϣ(�������ӵ�����) ע��: Receiver�ǵ���manifest.xml��ע��
	 * 
	 * @param ctx
	 */
	public static void sendUpdateBroadcast(Context ctx) {
		AlarmManager am = getAlarmManager(ctx);
		// 10��󽫲����㲥
		
		Intent intent = new Intent("com.example.schedule.MyAppWidgetProvider.CHANGE_TEXT");
		intent.putExtra("setText", "���յ��㲥��");
		PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, 0, intent, 0);
		am.set(AlarmManager.RTC_WAKEUP , System.currentTimeMillis() + 10000, pendingIntent);
	}

	/**
	 * ȡ����ʱִ��(�������ӵ�ȡ��)
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
