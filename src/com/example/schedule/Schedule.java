package com.example.schedule;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.tools.Encode;
import com.example.tools.Rege;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Schedule extends Activity implements OnClickListener {

	public static final int SHOW_RESPONSE = 0;

	private EditText user, pass;
	private Button requset;
	private TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		requset = (Button) findViewById(R.id.request);
		text = (TextView) findViewById(R.id.text);
		requset.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.request) {
			sendRequestWithHttpURLConnection();
			Log.e("wang", "start uel");
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_RESPONSE:
				String response = (String) msg.obj;
				text.setText(Rege.match(response));
				
			}
		}
	};

    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            public void run() {
                HttpURLConnection conn = null;
                String cookie = null;
                String[] info = Schedule.this.getLoginInfo();
                try {
                    conn = (HttpURLConnection) new URL("http://202.202.1.176:8080/").openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);
                    conn.setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36");
                    Map<String, List<String>> maps = conn.getHeaderFields();
                    List<String> coolist = maps.get("Set-Cookie");
                    Iterator<String> localIterator = coolist.iterator();

                    while (localIterator.hasNext()) {
                        String tmp = localIterator.next();
                        if (tmp.startsWith("ASP.NET_Session"))
                            cookie = tmp.substring(0, 1 + tmp.indexOf(";"));
                    }
                    conn.disconnect();

                    conn = (HttpURLConnection) new URL("http://202.202.1.176:8080/_data/index_login.aspx")
                            .openConnection();
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);
                    conn.setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36");
                    conn.setRequestProperty("Cookie", cookie);
                    conn.setRequestProperty("Referer", "http://202.202.1.176:8080/");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    StringBuffer localStringBuffer = new StringBuffer();
                    localStringBuffer
                            .append("__VIEWSTATEGENERATOR=CAA0A5A7&Sel_Type=STU&txt_ysdsdsdskgf=&pcInfo=&typeName=&aerererdsdxcxdfgfg=&efdfdfuuyyuuckjg=")
                            .append(info[2]).append("&txt_dsdsdsdjkjkjc=").append(info[0]).append("&txt_dsdfdfgfouyy=")
                            .append(info[1]);
                    byte[] arrayOfByte1 = localStringBuffer.toString().getBytes();
                    conn.getOutputStream().write(arrayOfByte1);
                    conn.getInputStream();
                    conn.disconnect();

                    conn = (HttpURLConnection) new URL("http://202.202.1.176:8080/znpk/Pri_StuSel_rpt.aspx")
                            .openConnection();
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);
                    conn.setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36");
                    conn.setRequestProperty("Cookie", cookie);
                    conn.setRequestProperty("Referer", "http://202.202.1.176:8080/znpk/Pri_StuSel.aspx");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    byte[] arrayOfByte2 = "Sel_XNXQ=20151&rad=on&px=1&Submit01=%BC%EC%CB%F7".getBytes();
                    conn.getOutputStream().write(arrayOfByte2);
                    InputStream localInputStream = conn.getInputStream();
                    InputStreamReader localInputStreamReader = new InputStreamReader(localInputStream, "gbk");
                    BufferedReader reader = new BufferedReader(localInputStreamReader);

                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Message localMessage = new Message();
                    localMessage.what = 0;
                    localMessage.obj = response.toString();
                    handler.sendMessage(localMessage);

                } catch (Exception e) {
                    Toast.makeText(Schedule.this, "获取课程表数据失败，请稍后再试", Toast.LENGTH_SHORT).show();
                } finally {
                    if (conn != null)
                        conn.disconnect();
                }
            }
        }).start();

    }

	protected String[] getLoginInfo() {
		user = (EditText) findViewById(R.id.user);
		pass = (EditText) findViewById(R.id.pwd);
		String auser = user.getText().toString();
		String apass = pass.getText().toString();
		String encode = Encode.getMd5(auser, apass);
		String[] res = { auser, apass, encode };
		return res;
	}
}
