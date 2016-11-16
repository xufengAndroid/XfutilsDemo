package com.nj.xufeng.xfutils.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 
 * @Title: XfBaseAdapter.java
 * @Package com.xufeng.xflibrary.adapter
 * @Description: TODO(getview监听)
 * @author 徐峰004245 (QQ：284535970)
 * @version V1.0
 */
public class GetViewListener<T> {
	/**
	 * 初始化布局时执行
	 * 
	 * @param position
	 * @param rootView
	 * @param data
	 * @param parent
	 * @param mDatas
	 */
	public void before(int position, View rootView, T data, ViewGroup parent,
			List<T> mDatas) {
	}

	/**
	 * 初始化布局后时执行
	 * 
	 * @param position
	 * @param rootView
	 * @param data
	 * @param parent
	 * @param mDatas
	 * @return
	 */
	public boolean later(int position, View rootView, T data, ViewGroup parent,
			List<T> mDatas) {
		return false;
	}

}