package com.shallcheek.timetale;

import java.util.ArrayList;
import com.example.schedule.R;
import com.example.tools.timetable.Timetable;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	private TimeTableView mTimaTableView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.class_table);
		mTimaTableView = (TimeTableView) findViewById(R.id.main_timetable_ly);
		ArrayList<Timetable> tt = getIntent().getParcelableArrayListExtra("timetable");
		mTimaTableView.setTimeTable(tt);
	}
}
