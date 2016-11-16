package com.nj.xufeng.xfutils.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FontUtils {

	private FontUtils() {
	}
	
	private static Typeface typeFace;
	
	private static void initTypeface(Context c){
		if(null==typeFace){
			typeFace = Typeface.createFromAsset(c.getAssets(),"fonts/dafFont.ttf");
		}
	}
	
	public static void setFont(TextView textView){
		initTypeface(textView.getContext());
		// 应用字体
		textView.setTypeface(typeFace);
	}
	
	
	/**
	 * 下划线
	 * @param textView
	 */
	public static void underline(TextView textView){
		textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
		textView.getPaint().setAntiAlias(true);//抗锯齿
	}
	
	/**
	 * 设置中划线并加清晰 
	 */
	public static void strikeThru(TextView textView){
//		textView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
		textView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰 
	}
	
	/**
	 * 取消设置的的划线
	 */
	public static void clearLine(TextView textView){
		textView.getPaint().setFlags(0);  // 取消设置的的划线
	}
	
	/**
	 * 设字体
	 * 
	 * @param root
	 * @param act
	 */
	public static void changeFonts(ViewGroup root, Context act) {
		initTypeface(root.getContext());
		for (int i = 0; i < root.getChildCount(); i++) {
			View v = root.getChildAt(i);
			if (v instanceof TextView) {
				((TextView) v).setTypeface(typeFace);
			} else if (v instanceof Button) {
				((Button) v).setTypeface(typeFace);
			} else if (v instanceof EditText) {
				((EditText) v).setTypeface(typeFace);
			} else if (v instanceof ViewGroup) {
				changeFonts((ViewGroup) v, act);
			}
		}

	}
	
	/**
	 * 加粗
	 * @param tv
	 */
	public static void bold(TextView tv) {
		TextPaint tp = tv.getPaint();
		tp.setFakeBoldText(true);
	}
}
