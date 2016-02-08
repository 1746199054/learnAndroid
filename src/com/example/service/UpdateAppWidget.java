package com.example.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.example.tools.AudioManagerUtil;
import com.example.tools.timetable.Timetable;
import com.example.tools.timetable.TimetableMaker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class UpdateAppWidget extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putInt("update", pref.getInt("update", 0) + 1);
		editor.commit();

		GregorianCalendar now = new GregorianCalendar();
		now.setTime(new Date());
		TimetableMaker ttm = new TimetableMaker();
		ArrayList<Timetable> weekTimeTable = ttm.getTimeTable(now);
		int weekday = TimetableMaker.getDayOfWeek(now);
		ArrayList<Timetable> dayTimetable = ttm.getDayTimeTable(weekTimeTable, weekday);
		StringBuilder todayclass = new StringBuilder();
		todayclass.append("½ñÌì¿Î±í\n");
		for (Timetable tt : dayTimetable) {
			todayclass.append(tt.toString() + "\n");
		}

		Intent aintent = new Intent("com.example.schedule.MyAppWidgetProvider.CHANGE_TEXT");
		aintent.putExtra("setText", todayclass.toString());
		sendBroadcast(aintent);

		int[][] ct = new int[][] { { 510, 555 }, { 565, 610 }, { 630, 675 }, { 685, 730 }, { 840, 885 }, { 895, 940 },
				{ 960, 1005 }, { 1015, 1060 }, { 1140, 1185 }, { 1195, 1240 }, { 1250, 1295 }, { 1305, 1350 } };
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		long todayMillis = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH),
				now.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
		if (pref.getBoolean("AudioManagerMode", false)) {
			for (Timetable tt : dayTimetable) {
				Intent bintent = new Intent(this, SetSilent.class);
				bintent.putExtra("Tag", true);
				PendingIntent pendingIntent = PendingIntent.getService(this, weekday * 10 + tt.startclass, bintent,
						PendingIntent.FLAG_UPDATE_CURRENT);
				am.set(AlarmManager.RTC_WAKEUP, todayMillis + ct[tt.startclass - 1][0] * 60000, pendingIntent);

				Intent cintent = new Intent(this, SetSilent.class);
				cintent.putExtra("Tag", false);
				PendingIntent apendingIntent = PendingIntent.getService(this, weekday * 10 + tt.endclass, cintent,
						PendingIntent.FLAG_UPDATE_CURRENT);
				am.set(AlarmManager.RTC_WAKEUP, todayMillis + ct[tt.endclass - 1][1] * 60000, apendingIntent);
			}
		}

		return super.onStartCommand(intent, flags, startId);
	}
}
