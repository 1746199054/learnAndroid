package com.example.tools.timetable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.example.tools.MyApplication;
import com.example.tools.MyDB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/*
 * ����ʱ���ÿγ���Ϣ
 */
public class TimetableMaker {
	private ArrayList<Timetable> data = new ArrayList<Timetable>();
	private int[][] classStatus;
	private GregorianCalendar start = new GregorianCalendar(2016, 0, 4);// �·ݴ�0��ʼ

	public ArrayList<Timetable> getTimeTable(Date t) {
		GregorianCalendar now = new GregorianCalendar();
		now.setTime(t);
		return getTimeTable(now);
	}

	public int getWeek(GregorianCalendar n){
		return (int) ((n.getTimeInMillis() - start.getTimeInMillis()) / 604800000 + 1);
	}
	
	public ArrayList<Timetable> getTimeTable(GregorianCalendar n) {
		int week = getWeek(n);
		ArrayList<Timetable> res = new ArrayList<Timetable>();
		for (Timetable tt : data) {
			if ((week >= tt.startweek) && (week <= tt.endweek) && (week % 2 == tt.oddeven || tt.oddeven == 2)) {
				res.add(tt);
			}
		}
		return res;
	}

	public ArrayList<Timetable> getDayTimeTable(ArrayList<Timetable> wdata, int d) {
		ArrayList<Timetable> res = new ArrayList<Timetable>();
		for (Timetable tt : wdata) {
			if (d == tt.day) {
				res.add(tt);
			}
		}
		return res;
	}

	public ClassTime getClassStatus(GregorianCalendar n) {
		int minuteOfDay = n.get(Calendar.HOUR_OF_DAY) * 60 + n.get(Calendar.MINUTE);
		ClassTime classtime = null;
		if (minuteOfDay >= classStatus[0][0] && minuteOfDay <= classStatus[3][1]) {
			classtime = getClassStatusTool(0, minuteOfDay);
		} else if (minuteOfDay >= classStatus[4][0] && minuteOfDay <= classStatus[7][1]) {
			classtime = getClassStatusTool(4, minuteOfDay);
		} else if (minuteOfDay >= classStatus[8][0] && minuteOfDay <= classStatus[11][1]) {
			classtime = getClassStatusTool(8, minuteOfDay);
		} else if (minuteOfDay > classStatus[11][1]) {
			classtime = new ClassTime(12, ClassTime.NIGHT);
		} else if (minuteOfDay < classStatus[0][0]) {
			classtime = new ClassTime(12, ClassTime.MORNING);
		} else if (minuteOfDay > classStatus[3][1] && minuteOfDay < classStatus[4][0]) {
			classtime = new ClassTime(4, ClassTime.NOON);
		} else if (minuteOfDay > classStatus[7][1] && minuteOfDay < classStatus[8][0]) {
			classtime = new ClassTime(8, ClassTime.AFTERNOON);
		}
		return classtime;
	}

	protected ClassTime getClassStatusTool(int s, int minuteOfDay) {
		ClassTime classtime = null;
		for (int i = s; i < s + 4; i++) {
			if (minuteOfDay >= classStatus[i][0] && minuteOfDay <= classStatus[i][1]) {
				classtime = new ClassTime(i + 1, ClassTime.CLASSING);
			} else if (minuteOfDay >= classStatus[i][1] && minuteOfDay <= classStatus[i + 1][0]) {
				classtime = new ClassTime(i + 1, ClassTime.BREAK);
			}
		}
		return classtime;
	}

	public static int getDayOfWeek(GregorianCalendar cal) {
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			return 7;
		case 2:
			return 1;
		case 3:
			return 2;
		case 4:
			return 3;
		case 5:
			return 4;
		case 6:
			return 5;
		case 7:
			return 6;
		}
		return 0;
	}
	
	public TimetableMaker() {
		// data.add(new Timetable("�ߵ���ѧ2��������Ϣ�ࣩ", "D1235", "����", 2, 1, 18, 1, 3,
		// 4));
		// data.add(new Timetable("˼����������뷨�ɻ���", "D1136", "������", 2, 1, 16, 1, 5,
		// 6));
		// data.add(new Timetable("������ƻ���", "D1142", "���۾�", 2, 1, 16, 1, 7, 8));
		// data.add(new Timetable("��ɢ��ѧ", "D1245", "������", 2, 1, 18, 2, 1, 2));
		// data.add(new Timetable("��ѧ�����-1", "D1231", "����", 2, 1, 19, 2, 3, 4));
		// data.add(new Timetable("ѧҵ����Ӣ�3��", "D1204", "������", 2, 1, 17, 2, 5,
		// 6));
		// data.add(new Timetable("�ߵ���ѧ2��������Ϣ�ࣩ", "D1433", "����", 2, 1, 18, 3, 3,
		// 4));
		// data.add(new Timetable("���������ߣ�2��", "DZ121", "Ԭ����", 2, 8, 11, 3, 5,
		// 6));
		// data.add(new Timetable("��ѧ��ְҵ���Ĺ滮", "DYC205", "�����", 2, 1, 16, 3, 7,
		// 8));
		// data.add(new Timetable("��ɢ��ѧ", "D1245", "������", 2, 1, 14, 4, 1, 2));
		// data.add(new Timetable("��ѧ�����-1", "DYC103", "����", 0, 2, 18, 4, 3,
		// 4));
		// data.add(new Timetable("�ߵ���ѧ2��������Ϣ�ࣩ", "DYC104", "����", 2, 1, 18, 5,
		// 3, 4));
		// data.add(new Timetable("Ӣ����ｻ�ʼ��ܣ�3��", "DZ314", "Thomas D", 2, 10, 18,
		// 5, 5, 6));
		// data.add(new Timetable("Ӣ����ｻ�ʼ��ܣ�3��", "DZ109", "������", 2, 1, 9, 5, 5,
		// 6));
		// data.add(new Timetable("˼����������뷨�ɻ���ʵ��", "", "����", 2, 8, 8, 7, 11,
		// 12));
		SQLiteDatabase db = new MyDB(MyApplication.getContext(), "lesson.db", null, 2).getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from lesson", null);
		while (cursor.moveToNext()) {
			data.add(new Timetable(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4),
					cursor.getInt(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9)));
		}
		classStatus = new int[][] { { 510, 555 }, { 565, 610 }, { 630, 675 }, { 685, 730 }, { 840, 885 }, { 895, 940 },
				{ 960, 1005 }, { 1015, 1060 }, { 1140, 1185 }, { 1195, 1240 }, { 1250, 1295 }, { 1305, 1350 } };
	}
}
