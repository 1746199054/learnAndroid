package com.example.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.example.schedule.R;
import com.example.tools.timetable.Timetable;
import com.example.tools.timetable.TimetableMaker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);

		ListView listview = (ListView) findViewById(R.id.listview);
		ListView listview2 = (ListView) findViewById(R.id.listview2);

		GregorianCalendar now = new GregorianCalendar();
		now.setTime(new Date());
		TimetableMaker ttm = new TimetableMaker();
		ArrayList<Timetable> ts = ttm.getTimeTable(now);
		ArrayList<Timetable> td = ttm.getDayTimeTable(ts, ttm.getDayOfWeek(now));
		TimeTableAdapter ttadapter = new TimeTableAdapter(this, R.layout.listviewadapter, ts);
		listview2.setAdapter(ttadapter);
		
		TimeTableAdapter ttadapter2 = new TimeTableAdapter(this, R.layout.listviewadapter, td);
		listview.setAdapter(ttadapter2);
	}
}

class TimeTableAdapter extends ArrayAdapter<Timetable> {

	private int resourceId;

	public TimeTableAdapter(Context context, int textViewResourceId, List<Timetable> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Timetable timetable = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.classtime = (TextView) view.findViewById(R.id.textView1);
			viewHolder.name = (TextView) view.findViewById(R.id.textView2);
			viewHolder.address = (TextView) view.findViewById(R.id.textView3);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.classtime.setText(timetable.startclass+"-"+timetable.endclass);
		viewHolder.name.setText(timetable.name);
		viewHolder.address.setText(timetable.address);
		return view;
	}

	class ViewHolder {
		TextView classtime,name,address;
	}

}
