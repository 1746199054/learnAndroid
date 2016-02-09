package com.example.tools.timetable;

import java.util.Calendar;
import java.util.GregorianCalendar;

/*
 * 用户的状态
 */
public class ClassTime {
	public static final int CLASSING = 1;
	public static final int BREAK = 2;
	public static final int MORNING = 3;
	public static final int NOON = 4;
	public static final int AFTERNOON = 5;
	public static final int NIGHT = 6;

	public int latestClass = -1;
	public int status = 0;

	public ClassTime(int l, int s) {
		latestClass = l;
		status = s;
	}

	@Override
	public String toString() {
		String s;
		switch (status) {
		case 1:
			s = "CLASSING";
			break;
		case 2:
			s = "BREAK";
			break;
		case 3:
			s = "MORNING";
			break;
		case 4:
			s = "NOON";
			break;
		case 5:
			s = "AFTERNOON";
			break;
		case 6:
			s = "NIGHT";
			break;
		default:
			s = "未定义上课状态";
			break;
		}
		return String.format("第%s节课 ,状态 %s", latestClass, s);
	}

	public static ClassTime getInstance(GregorianCalendar n, int[][] classStatus) {
		int minuteOfDay = n.get(Calendar.HOUR_OF_DAY) * 60 + n.get(Calendar.MINUTE);
		ClassTime classtime = null; 
		if (minuteOfDay >= classStatus[0][0] && minuteOfDay <= classStatus[3][1]) {
			classtime = getClassStatusTool(0, minuteOfDay, classStatus);
		} else if (minuteOfDay >= classStatus[4][0] && minuteOfDay <= classStatus[7][1]) {
			classtime = getClassStatusTool(4, minuteOfDay, classStatus);
		} else if (minuteOfDay >= classStatus[8][0] && minuteOfDay <= classStatus[11][1]) {
			classtime = getClassStatusTool(8, minuteOfDay, classStatus);
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

	protected static ClassTime getClassStatusTool(int s, int minuteOfDay, int[][] classStatus) {
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
}
