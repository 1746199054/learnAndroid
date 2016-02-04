package com.example.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.test.ListViewTest;
import com.example.test.MainReceiver;
import com.example.tools.HttpRequest;
import com.example.tools.MyDB;
import com.example.tools.Response;
import com.example.tools.timetable.Timetable;
import com.example.tools.timetable.TimetableMaker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Schedule extends Activity implements OnClickListener {
	private CheckBox checkbox;
	private EditText user, pass;
	private Button requset, class_table;
	private TextView text;

	private SharedPreferences.Editor editor;
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		user = (EditText) findViewById(R.id.user);
		pass = (EditText) findViewById(R.id.pwd);
		requset = (Button) findViewById(R.id.request);
		class_table = (Button) findViewById(R.id.class_table);
		text = (TextView) findViewById(R.id.text);
		checkbox = (CheckBox) findViewById(R.id.remember_pass);
		requset.setOnClickListener(this);
		class_table.setOnClickListener(this);

		pref = PreferenceManager.getDefaultSharedPreferences(this);
		boolean isRemember = pref.getBoolean("remember", false);
		if (isRemember) {
			user.setText(pref.getString("user", ""));
			pass.setText(pref.getString("pass", ""));
			checkbox.setChecked(true);
		}
		
		GregorianCalendar now = new GregorianCalendar();
		now.setTime(new Date());
		TimetableMaker ttm = new TimetableMaker();
		ArrayList<Timetable> ts = ttm.getTimeTable(now);
		ArrayList<Timetable> dts = ttm.getDayTimeTable(ts, TimetableMaker.getDayOfWeek(now));
		
		text.append(String.format("今天是第%s周 星期%s", ttm.getWeek(now), TimetableMaker.getDayOfWeek(now)));
		text.append("\n全周课表\n");
		for (Timetable tt : ts) {
			text.append(tt.toString()+"\n");
		}
		text.append("今天课表\n");
		StringBuilder todayclass = new StringBuilder();
		todayclass.append("今天课表\n");
		for (Timetable tt : dts) {
			text.append(tt.toString()+"\n");
			todayclass.append(tt.toString()+"\n");
		}
		text.append(ttm.getClassStatus(now).toString()+"\n");
		Intent intent = new Intent("com.example.schedule.MyAppWidgetProvider.CHANGE_TEXT");
		intent.putExtra("setText", todayclass.toString());
		sendBroadcast(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.request) {
			editor = pref.edit();
			if (checkbox.isChecked()) {
				editor.putBoolean("remember", true);
				editor.putString("user", user.getText().toString());
				editor.putString("pass", pass.getText().toString());
			} else {
				editor.clear();
			}
			editor.commit();
			sendRequestWithHttpURLConnection();
		} else if (v.getId() == R.id.class_table) {
			Intent intent = new Intent(this, ClassTable.class);
			startActivity(intent);
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Response res = (Response) msg.obj;
			if (res.getStatus()) {
				try {
					JSONObject jsonobj = new JSONObject(res.getResult());
					boolean status = jsonobj.getBoolean("status");
					if (status) {
						MyDB dbHelper = new MyDB(Schedule.this, "lesson.db", null, 2);
						SQLiteDatabase db = dbHelper.getWritableDatabase();
						db.execSQL("delete from lesson");
						JSONArray data = jsonobj.getJSONArray("data");
						for (int i = 0; i < data.length(); i++) {
							JSONArray l = data.getJSONArray(i);
							String[] sql = { Integer.toString(i), l.getString(0), l.getString(1), l.getString(2),
									l.getString(3), l.getString(4), l.getString(5), l.getString(6), l.getString(7),
									l.getString(8) };
							db.execSQL(
									"insert into lesson (id,name,address,teacher,oddeven,startweek,endweek,day,startclass,endclass) values (?,?,?,?,?,?,?,?,?,?)",
									sql);
							text.append(l.toString() + "\n");
						}
						Toast.makeText(Schedule.this, "课表数据写入成功", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(Schedule.this, "课表数据获取失败" + jsonobj.getString("msg"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(Schedule.this, "课表数据解析失败", Toast.LENGTH_SHORT).show();
					text.setText("课表数据解析失败，" + res.getResult());
				}

			} else {
				Toast.makeText(Schedule.this, "获取数据失败", Toast.LENGTH_SHORT).show();
				text.setText("获取数据失败，" + res.getResult());
			}
		}
	};

	private void sendRequestWithHttpURLConnection() {
		new Thread(new Runnable() {
			public void run() {
				String[] info = getLoginInfo();
				HttpRequest req = new HttpRequest(
						"http://121.42.12.235/class_table/getdata.php?name=" + info[0] + "&pass=" + info[1]);
				Response res = req.query();
				Message localMessage = new Message();
				localMessage.obj = res;
				handler.sendMessage(localMessage);
			}
		}).start();

	}

	protected String[] getLoginInfo() {
		String auser = user.getText().toString();
		String apass = pass.getText().toString();
		String[] res = { auser, apass };
		return res;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.receiver:
			startActivity(new Intent(this, MainReceiver.class));
			break;
		case R.id.send_computer_menu:
			startActivity(new Intent(this, SetSaying.class));
			break;
		case R.id.listview:
			startActivity(new Intent(this, ListViewTest.class));
		default:
			break;
		}
		return true;
	}
}
