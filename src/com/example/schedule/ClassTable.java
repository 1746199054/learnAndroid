package com.example.schedule;

import com.example.tools.view.TableView;

import android.app.Activity;
import android.os.Bundle;

public class ClassTable extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TableView table = new TableView(this, 13, 8); 
		setContentView(table); 
	}
}
