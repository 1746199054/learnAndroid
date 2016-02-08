package com.example.test;

import com.example.service.MyService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

public class ServiceTest extends Activity implements OnClickListener {
	Button startService, endService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout view = new LinearLayout(this);
		startService = new Button(this);
		endService = new Button(this);
		startService.setOnClickListener(this);
		endService.setOnClickListener(this);
		startService.setText("Start");
		endService.setText("End");
		view.addView(startService);
		view.addView(endService);
		setContentView(view);

	}

	@Override
	public void onClick(View v) {
		if (v == startService) {
			Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, MyService.class);
            startService(intent);
		} else if (v == endService) {
			Toast.makeText(this, "End Service", Toast.LENGTH_SHORT).show();
			stopService(new Intent(this, MyService.class));
		}
	}
}
