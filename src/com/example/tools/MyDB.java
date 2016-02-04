package com.example.tools;

import java.text.StringCharacterIterator;

import com.example.schedule.Schedule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.widget.Toast;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDB extends SQLiteOpenHelper {
	
	private Context context;
	
	public MyDB(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String create = "create table lesson ("
				+ "id integer primary key, "
				+ "name text, "
				+ "address text, "
				+ "teacher text, "
				+ "oddeven integer, "
				+ "startweek integer, "
				+ "endweek integer, "
				+ "day integer, "
				+ "startclass integer, "
				+ "endclass integer)";
		db.execSQL(create);
		Toast.makeText(context, "数据库创建成功", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists lesson");
		onCreate(db);
	}

}
