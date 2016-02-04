package com.example.tools.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TableView extends ViewGroup {
	private static final int STARTX = 0;// 起始X坐标
	private static final int STARTY = 0;// 起始Y坐标
	private static final int BORDER = 2;// 表格边框宽度
	private int mRow;// 行数
	private int mCol;// 列数

	public TableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mRow = 3;// 默认行数为3
		this.mCol = 3;// 默认列数为3
		// 添加子控件
		this.addOtherView(context);
	}

	public TableView(Context context, int row, int col) {
		super(context);
		if (row > 20 || col > 20) {
			this.mRow = 20;// 大于20行时，设置行数为20行
			this.mCol = 20;// 大于20列时，设置列数为20列
		} else if (row == 0 || col == 0) {
			this.mRow = 3;
			this.mCol = 3;
		} else {
			this.mRow = row;
			this.mCol = col;
		}
		// 添加子控件
		this.addOtherView(context);
	}

	public void addOtherView(Context context) {
		int value = 1;
		for (int i = 1; i <= mRow; i++) {
			for (int j = 1; j <= mCol; j++) {
				TextView view = new TextView(context);
				view.setText(String.valueOf(value++));
				view.setTextColor(Color.rgb(79, 129, 189));
				view.setGravity(Gravity.CENTER);
				if (i % 2 == 0) {
					view.setBackgroundColor(Color.rgb(219, 238, 243));
				} else {
					view.setBackgroundColor(Color.rgb(235, 241, 221));
				}
				this.addView(view);
			}
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStrokeWidth(BORDER);
		paint.setColor(Color.rgb(79, 129, 189));
		paint.setStyle(Style.STROKE);
		// 绘制外部边框
		canvas.drawRect(STARTX, STARTY, getWidth() - STARTX, getHeight() - STARTY, paint);
		// 画列分割线
		for (int i = 1; i < mCol; i++) {
			canvas.drawLine((getWidth() / mCol) * i, STARTY, (getWidth() / mCol) * i, getHeight() - STARTY, paint);
		}
		// 画行分割线
		for (int j = 1; j < mRow; j++) {
			canvas.drawLine(STARTX, (getHeight() / mRow) * j, getWidth() - STARTX, (getHeight() / mRow) * j, paint);
		}
		super.dispatchDraw(canvas);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int x = STARTX + BORDER;
		int y = STARTY + BORDER;
		int i = 0;
		int count = getChildCount();
		for (int j = 0; j < count; j++) {
			View child = getChildAt(j);
			child.layout(x, y, x + getWidth() / mCol - BORDER * 2, y + getHeight() / mRow - BORDER * 2);
			if (i >= (mCol - 1)) {
				i = 0;
				x = STARTX + BORDER;
				y += getHeight() / mRow;
			} else {
				i++;
				x += getWidth() / mCol;
			}
		}
	}

	public void setRow(int row) {
		this.mRow = row;
	}

	public void setCol(int col) {
		this.mCol = col;
	}
}