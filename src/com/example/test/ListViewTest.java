package com.example.test;

import com.example.schedule.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewTest extends Activity {

	private String[] data = { "java", "python", "javascript", "php", "html/css", "sql" ,"java", "python", "javascript", "php", "html/css", "sql","java", "python", "javascript", "php", "html/css", "sql"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
		ListView listview = (ListView) findViewById(R.id.listview);
		listview.setAdapter(adapter);
	}
}
