package com.example.test;

import com.example.schedule.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainReceiver extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_receiver);
		Button button = (Button) findViewById(R.id.set_appwidget_bu);

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText edit = (EditText) findViewById(R.id.set_appwidget);
				String text = edit.getText().toString();
				Intent intent = new Intent("com.example.schedule.MyAppWidgetProvider.CHANGE_TEXT");
				intent.putExtra("setText", text);
				sendBroadcast(intent);
			}
		});
	}
}
