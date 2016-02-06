package com.example.tools.timetable;

import android.os.Parcel;
import android.os.Parcelable;

public class Timetable implements Parcelable {
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(address);
		dest.writeString(teacher);
		dest.writeInt(oddeven);
		dest.writeInt(startweek);
		dest.writeInt(endweek);
		dest.writeInt(day);
		dest.writeInt(startclass);
		dest.writeInt(endclass);
	}
	
	public static final Parcelable.Creator<Timetable> CREATOR = new Creator<Timetable>() {
        public Timetable createFromParcel(Parcel s) {
            Timetable tt = new Timetable(s.readString(),s.readString(),s.readString(),s.readInt(),s.readInt(),s.readInt(),s.readInt(),s.readInt(),s.readInt());
            return tt;
        }
        public Timetable[] newArray(int size) {
            return new Timetable[size];
        }
    };
}
