package com.nj.xufeng.xfutils.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.nj.xufeng.xfutils.R;

/**
 * 可以想Html中的div一样设置border的属性
 *
 * @author skg
 *
 */
public class BorderLinearLayout extends LinearLayout {
	private final static int DEFAULT_BORDER_SIZE = 0;
	private int mBorderColor = Color.parseColor("#3a000000");
	private int mBoderLeftSize = DEFAULT_BORDER_SIZE;
	private int mBoderTopSize = DEFAULT_BORDER_SIZE;
	private int mBoderRightSize = DEFAULT_BORDER_SIZE;
	private int mBoderBottomSize = DEFAULT_BORDER_SIZE;
	private boolean isBorderEnable = true;;
	private Paint borderPaint;

	public BorderLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.Border, 0, 0);
		mBorderColor = a.getColor(R.styleable.Border_border_color,
				mBorderColor);
		mBoderLeftSize = a.getDimensionPixelSize(
				R.styleable.Border_border_left_size,
				DEFAULT_BORDER_SIZE);
		mBoderTopSize = a.getDimensionPixelSize(
				R.styleable.Border_border_top_size,
				DEFAULT_BORDER_SIZE);
		mBoderRightSize = a.getDimensionPixelSize(
				R.styleable.Border_border_right_size,
				DEFAULT_BORDER_SIZE);
		mBoderBottomSize = a.getDimensionPixelSize(
				R.styleable.Border_border_bottom_size,
				DEFAULT_BORDER_SIZE);
		isBorderEnable = a.getBoolean(
				R.styleable.Border_border_enable, true);
		a.recycle();
		init();
	}

	public BorderLinearLayout(Context context) {
		super(context);
		init();
	}

	private void init() {
		borderPaint = new Paint();
		borderPaint.setColor(mBorderColor);
	}

	public void setBorderSize(int left, int top, int right, int bottom) {
		mBoderLeftSize = left;
		mBoderTopSize = top;
		mBoderRightSize = right;
		mBoderBottomSize = bottom;
		invalidate();
	}

	public void setBorderColor(int color) {
		mBorderColor = color;
		borderPaint.setColor(mBorderColor);
		invalidate();
	}

	public boolean isBorderEnable() {
		return isBorderEnable;
	}

	public void setBorderEnable(boolean enable) {
		if (enable == isBorderEnable) {
			return;
		}
		isBorderEnable = enable;
		invalidate();
	}

	private Rect tempRect = new Rect();

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (!isBorderEnable) {
			return;
		}
		// draw border
		int vw = getWidth();
		int vh = getHeight();
		if (mBoderLeftSize > 0) {
			tempRect.setEmpty();
			tempRect.set(0, 0, mBoderLeftSize, vh);
			canvas.drawRect(tempRect, borderPaint);
		}
		if (mBoderTopSize > 0) {
			tempRect.setEmpty();
			tempRect.set(0, 0, vw, mBoderTopSize);
			canvas.drawRect(tempRect, borderPaint);
		}
		if (mBoderRightSize > 0) {
			tempRect.setEmpty();
			tempRect.set(vw - mBoderRightSize, 0, vw, vh);
			canvas.drawRect(tempRect, borderPaint);
		}
		if (mBoderBottomSize > 0) {
			tempRect.setEmpty();
			tempRect.set(0, vh - mBoderBottomSize, vw, vh);
			canvas.drawRect(tempRect, borderPaint);
		}
	}
}