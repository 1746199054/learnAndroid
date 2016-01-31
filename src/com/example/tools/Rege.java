package com.example.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rege {
	public static String match(String data) {
		StringBuilder s = new StringBuilder();
		Pattern p2 = Pattern.compile("<tr ><td(.*?)</td></tr>");
		Matcher m2 = p2.matcher(data);
		String[] header = { "序号", "课程", "学分", "总学时", "讲授学时", "上机学时", "类别", "授课方式", "考核方式", "任课教师", "周次", "节次", "地点" };
		p(header,s);

		while (m2.find()) {
			String[] line = m2.group().replace("<br>", "||").replaceAll("<(.*?)>", "").split("\\|\\|");
			if (line.length == 13 || line.length == 12)
				p(line,s);
		}
		return s.toString();
	}

	public static void p(String[] o,StringBuilder s) {
		int a = 0;
		for (String i : o) {
			byte[] b = i.getBytes();
			if (a == 1) {
				s.append(i + sp(45 - b.length));
			} else if (a == 0) {
				s.append(i + sp(5 - b.length));
			} else if (a == 6) {
				s.append(i + sp(15 - b.length));
			} else if (a == 11) {
				s.append(i + sp(12 - b.length));
			} else {
				s.append(i + sp(10 - b.length));
			}
			a++;
		}
		s.append("\n");
	}

	public static String sp(int mount) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < mount; i++) {
			b.append(" ");
		}
		return b.toString();
	}
}
