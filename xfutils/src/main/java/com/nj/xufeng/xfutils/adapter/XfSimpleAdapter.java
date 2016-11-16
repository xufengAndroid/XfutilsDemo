package com.nj.xufeng.xfutils.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.nj.xufeng.xfutils.utils.MapUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XfSimpleAdapter extends XfBaseAdapter<Map<String, Object>> implements Filterable {

	private List<String> mFrom = new ArrayList<String>();
	private List<Object> mTo = new ArrayList<Object>();
	private Map<String, Object> viewValFilterMap = new HashMap<String, Object>();
	private Filter filter;

	public XfSimpleAdapter(Context context, int resource) {
		super(context, new ArrayList<Map<String, Object>>(), resource);

	}


	public XfSimpleAdapter(Context context, int resource, String[] from,
			Integer[] to) {
		super(context, new ArrayList<Map<String, Object>>(), resource);
		Collections.addAll(mFrom, from);
		Collections.addAll(mTo, to);
	}

	public XfSimpleAdapter(Context context, List<Map<String, Object>> data,
			int resource) {
		super(context, data, resource);
	}

	public XfSimpleAdapter(Context context, List<Map<String, Object>> data,
			int resource, String[] from, Integer[] to) {
		super(context, data, resource);
		Collections.addAll(mFrom, from);
		Collections.addAll(mTo, to);
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	@Override
	public Filter getFilter() {

		return filter;
	}

	public <T> T getVal(int position, String key) {
		return (T) getDatas().get(position).get(key);
	}

	@Override
	protected void before(int position, View rootView,
			Map<String, Object> data, ViewGroup parent) {

	}

	public void addSeparatorItem(Object key,Object val,int resource,ViewValAdapter vva) {
		if(null==itemViewTypeMap){
			itemViewTypeMap = new HashMap<>();
		}
		itemViewTypeMap.put(val, new ItemViewType(itemViewTypeMap.size(), resource,vva));
		this.separatorkey = key;
	}
	
	
	@Override
	protected ItemViewType getIVType(int position) {
		return (ItemViewType) super.getIVType(position);
	}
	
	protected class ItemViewType extends XfBaseAdapter.ItemViewType{
		private ViewValAdapter vva;
		public ItemViewType( int type, int resource,ViewValAdapter vva) {
			super(type, resource);
			this.vva = vva;
		}
		public ViewValAdapter getVva() {
			return vva;
		}
	}
	
	
	@Override
	protected void later(int position, View rootView, Map<String, Object> data,
			ViewGroup parent) {
		bindView(position, rootView, data);
	}

	public void setViewVal(View rootView, int viewId, Object val) {
		View v = rootView.findViewById(viewId);
		ViewValFactory.set(v, val);
	}

	private void bindView(int position, View rootView, Map<String, Object> m) {
		// int length = mTo.length<=mFrom.length?mTo.length:mFrom.length;
		if(null==itemViewTypeMap){
			if (null == mTo || null == mFrom) {
				new Throwable("to and from 不能为空");
//				ViewValFactory.setsAuto(rootView, m, viewValFilterMap);
			} else {
				ViewValFactory.sets(rootView, m, mFrom, mTo, viewValFilterMap);
			}
		}else{
			ViewValFactory.sets(rootView, m, getIVType(position).getVva());
		}
		
	}

	
	public void del(String key,String val){
		List<Map<String, Object>> datas = getDatas();
		for (int i = 0; i < datas.size(); i++) {
			Map<String, Object> data = datas.get(i);
			if (val.equals(data.get(key) + "")) {
				getDatas().remove(i);
				notifyDataSetChanged();
				break;
			}
		}
	}
	
	/**
	 * 数据变化
	 * @param key
	 * @param val
	 * @param changeData
	 */
	public void change(String key, String val, Map<String, Object> changeData) {
		List<Map<String, Object>> datas = getDatas();
		for (int i = 0; i < datas.size(); i++) {
			Map<String, Object> data = datas.get(i);
			if (val.equals(data.get(key) + "")) {
				MapUtils.replace(data, changeData);
				notifyDataSetChanged();
				break;
			}
		}
	}

	public void put(String from, Integer to) {
		mFrom.add(from);
		mTo.add(to);
	}

	public void put(String from, Integer to, IViewValFilter vvf) {
		putViewValFilter(from, to, vvf);
	}

	@Deprecated
	public void putViewValFilter(String from, Integer to, IViewValFilter vvf) {
		mFrom.add(from);
		mTo.add(to);
		viewValFilterMap.put(from, vvf);
	}

}
