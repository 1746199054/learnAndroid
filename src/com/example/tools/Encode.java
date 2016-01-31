package com.example.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encode {
	// agu=hmd5(username+hmd5(passwd)[:30].upper()+'10611')[:30].upper()
	public static String getMd5(String name, String pass) {
		String p = MD5(pass);
		String data;
		data = name + p.substring(0, 30).toUpperCase() + "10611";
		data = MD5(data).substring(0, 30).toUpperCase();
		return data;
	}

	private static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}
