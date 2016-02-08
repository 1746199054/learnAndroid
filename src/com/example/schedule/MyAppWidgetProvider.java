package com.example.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import com.example.service.MyService;
import com.example.service.UpdateAppWidget;
import com.example.tools.timetable.Timetable;
import com.example.tools.timetable.TimetableMaker;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.style.UpdateAppearance;
import android.widget.RemoteViews;

import android.widget.Toast;

public class MyAppWidgetProvider extends AppWidgetProvider {

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		if ((intent.getAction().equals("com.example.schedule.MyAppWidgetProvider.CHANGE_TEXT"))
				&& intent.getStringExtra("setText") != null) {
			Toast.makeText(context, "设置完毕，来桌面看一下吧", Toast.LENGTH_SHORT).show();
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);
			views.setTextViewText(R.id.appwidget, intent.getStringExtra("setText"));
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			int[] appIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, MyAppWidgetProvider.class));
			appWidgetManager.updateAppWidget(appIds, views);
		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Intent aintent = new Intent(context, UpdateAppWidget.class);
		context.startService(aintent);
		
		final int N = appWidgetIds.length;
		// Perform this loop procedure for each App Widget that belongs to this
		// provider
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];

			// Create an Intent to launch ExampleActivity
			Intent intent = new Intent(context, SetSaying.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

			// Get the layout for the App Widget and attach an on-click listener
			// to the button
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);
			views.setOnClickPendingIntent(R.id.appwidget, pendingIntent);
			// Tell the AppWidgetManager to perform an update on the current App
			// Widget
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}

}
