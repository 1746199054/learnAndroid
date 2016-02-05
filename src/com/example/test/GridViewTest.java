package com.example.test;

import com.example.schedule.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class GridViewTest extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);
		GridView gridview = (GridView) findViewById(R.id.gridview);

	}
}
