package com.example.tools.timetable;

public class Timetable {
	public String name, address, teacher;
	public int oddeven, startweek, endweek, day, startclass, endclass;

	public Timetable(String n, String a, String t, int o, int sw, int ew, int d, int sc, int ec) {
		name = n;
		address = a;
		teacher = t;
		oddeven = o;
		startweek = sw;
		endweek = ew;
		day = d;
		startclass = sc;
		endclass = ec;
	}

	@Override
	public String toString() {
		String r = String.format("ÖÜ%s  %s,%s,%s  %sµ½%s½Ú", day, name, address, teacher, startclass, endclass);
		return r;
	}
}
