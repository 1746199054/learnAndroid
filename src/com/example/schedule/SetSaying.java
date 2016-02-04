package com.example.schedule;

import com.example.tools.HttpRequest;
import com.example.tools.Response;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetSaying extends Activity {
	EditText edit;
	TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setsaying);
		Button button = (Button) findViewById(R.id.setsay_bu);
		Button cancel = (Button) findViewById(R.id.setsay_cancel);
		edit = (EditText) findViewById(R.id.setsaying);
		text = (TextView) findViewById(R.id.send_result);

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				query();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				edit.setText("");
			}
		});
		edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					query();
				}
				return false;
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();

		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		if (Intent.ACTION_SEND.equals(action) && type != null) {
			String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
			String sharedTitle = intent.getStringExtra(Intent.EXTRA_TITLE);
			if (sharedText != null) {
				edit.setText(sharedTitle + "\n" + sharedText);
			}
		} else {
			ClipboardManager clipboard = (ClipboardManager) SetSaying.this.getSystemService(Context.CLIPBOARD_SERVICE);
			edit.setText(clipboard.getPrimaryClip().getItemAt(0).getText());
		}

	}

	private void query() {
		new Thread(new Runnable() {
			public void run() {
				String data = edit.getText().toString();
				HttpRequest req = new HttpRequest("http://121.42.12.235/note/post.php",
						"user=1746199054&pass=31415926535&data=" + data);
				Response res = req.query();

				Message localMessage = new Message();
				localMessage.obj = res;
				handler.sendMessage(localMessage);
			}
		}).start();

	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			Response res = (Response) msg.obj;
			if (res.getStatus()) {
				if (res.getResult().equals("success"))
					Toast.makeText(SetSaying.this, "发送成功，请在电脑端查看", Toast.LENGTH_SHORT).show();
				else {
					Toast.makeText(SetSaying.this, "发送失败", Toast.LENGTH_SHORT).show();
					text.setText("发送失败，" + res.getResult());
				}
			} else {
				Toast.makeText(SetSaying.this, "发送失败", Toast.LENGTH_SHORT).show();
				text.setText("发送失败，" + res.getResult());
			}
		}
	};

}
