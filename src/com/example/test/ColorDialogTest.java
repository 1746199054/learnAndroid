package com.example.test;

import com.example.schedule.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.Toast;
import cn.refactor.lib.colordialog.ColorDialog;
import cn.refactor.lib.colordialog.PromptDialog;

public class ColorDialogTest extends Activity implements OnClickListener {
	private Button prompt, pic, text, picandtext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.colordialog);

		prompt = (Button) findViewById(R.id.Prompt);
		pic = (Button) findViewById(R.id.Pic);
		text = (Button) findViewById(R.id.Text);
		picandtext = (Button) findViewById(R.id.TextAndPic);

		prompt.setOnClickListener(this);
		pic.setOnClickListener(this);
		text.setOnClickListener(this);
		picandtext.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v==prompt){
			showPromptDialog(v);
		}else if(v==pic){
			showPicDialog(v);
		}else if(v==text){
			showTextDialog(v);
		}else if(v==picandtext){
			showAllModeDialog(v);
		}
	}

	public void showPromptDialog(View view) {
		showPromptDlg();
	}

	private void showPromptDlg() {
		new PromptDialog(this).setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS).setAnimationEnable(true)
				.setTitleText("Success")
				.setContentText(
						"Your info text goes here. Loremipsum dolor sit amet, consecteturn adipisicing elit, sed do eiusmod.")
				.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
					@Override
					public void onClick(PromptDialog dialog) {
						dialog.dismiss();
					}
				}).show();
	}

	public void showTextDialog(View view) {
		ColorDialog dialog = new ColorDialog(this);
		dialog.setColor("#8ECB54");
		dialog.setAnimationEnable(true);
		dialog.setTitle("setTitle");
		dialog.setContentText("setContentText");
		dialog.setPositiveListener("text_iknow", new ColorDialog.OnPositiveListener() {
			@Override
			public void onClick(ColorDialog dialog) {
				Toast.makeText(ColorDialogTest.this, dialog.getPositiveText().toString(), Toast.LENGTH_SHORT).show();
			}
		}).show();
	}

	public void showPicDialog(View v) {
		ColorDialog dialog = new ColorDialog(this);
		dialog.setTitle("setTitle");
		dialog.setAnimationEnable(true);
		dialog.setAnimationIn(getInAnimationTest(this));
		dialog.setAnimationOut(getOutAnimationTest(this));
		dialog.setContentImage(getResources().getDrawable(R.drawable.sample_img));
		dialog.setPositiveListener("delete", new ColorDialog.OnPositiveListener() {
			@Override
			public void onClick(ColorDialog dialog) {
				Toast.makeText(ColorDialogTest.this, dialog.getPositiveText().toString(), Toast.LENGTH_SHORT).show();
			}
		}).setNegativeListener("cancel", new ColorDialog.OnNegativeListener() {
			@Override
			public void onClick(ColorDialog dialog) {
				Toast.makeText(ColorDialogTest.this, dialog.getNegativeText().toString(), Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		}).show();
	}

	public void showAllModeDialog(View view) {
		ColorDialog dialog = new ColorDialog(this);
		dialog.setTitle("setTitle");
		dialog.setAnimationEnable(true);
		dialog.setContentText("setContentText");
		dialog.setContentImage(getResources().getDrawable(R.drawable.sample_img));
		dialog.setPositiveListener("delete", new ColorDialog.OnPositiveListener() {
			@Override
			public void onClick(ColorDialog dialog) {
				Toast.makeText(ColorDialogTest.this, dialog.getPositiveText().toString(), Toast.LENGTH_SHORT).show();
			}
		}).setNegativeListener("cancel", new ColorDialog.OnNegativeListener() {
			@Override
			public void onClick(ColorDialog dialog) {
				Toast.makeText(ColorDialogTest.this, dialog.getNegativeText().toString(), Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		}).show();
	}

	public static AnimationSet getInAnimationTest(Context context) {
		AnimationSet out = new AnimationSet(context, null);
		AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
		alpha.setDuration(150);
		ScaleAnimation scale = new ScaleAnimation(0.6f, 1.0f, 0.6f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scale.setDuration(150);
		out.addAnimation(alpha);
		out.addAnimation(scale);
		return out;
	}

	public static AnimationSet getOutAnimationTest(Context context) {
		AnimationSet out = new AnimationSet(context, null);
		AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
		alpha.setDuration(150);
		ScaleAnimation scale = new ScaleAnimation(1.0f, 0.6f, 1.0f, 0.6f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scale.setDuration(150);
		out.addAnimation(alpha);
		out.addAnimation(scale);
		return out;
	}

}
