package com.example.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.util.Log;

public class AudioManagerUtil {

	public static void setSilent(Context context) {
		AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.putInt("UserRingerMode", audio.getRingerMode());
		editor.commit();
		audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	}

	public static void setResume(Context context) {
		SharedPreferences pref1 = PreferenceManager.getDefaultSharedPreferences(context);
		AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		audio.setRingerMode(pref1.getInt("UserRingerMode", AudioManager.RINGER_MODE_NORMAL));
	}

	public static boolean getMode(Context context) {
		SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(context);
		return pref2.getBoolean("AudioManagerMode", false);
	}
 
	public static void setMode(Boolean mode, Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor2 = pref.edit();
		editor2.putBoolean("AudioManagerMode", mode);
		boolean res = editor2.commit();
		Log.e("wang", ""+res);
	}
}
