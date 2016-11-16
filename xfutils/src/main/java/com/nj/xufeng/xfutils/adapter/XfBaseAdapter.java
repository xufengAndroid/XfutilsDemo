package com.nj.xufeng.xfutils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nj.xufeng.xfutils.utils.ReflectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class XfBaseAdapter<T> extends BaseAdapter {

	private Context mContext;
	private List<T> mDatas;
	private int mResource;
	private LayoutInflater mInflater;
	private GetViewListener getView;



	public XfBaseAdapter(Context context, List<T> data, int resource) {
		mContext = context;
		mDatas = data;
		mResource = resource;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	/**
	 * 取得所有数据
	 * 
	 * @return
	 */
	public List<T> getDatas() {
		return mDatas;
	}

	/**
	 * 追加数据
	 * 
	 */
	public void append(T data) {
		if (null == data) {
			return;
		}
		mDatas.add(data);
		notifyDataSetChanged();
	}

	
	public void appedAll(int location, List<T> datas){
		if (null == datas||datas.size()==0) {
			return;
		}
		mDatas.addAll(location, datas);
		notifyDataSetChanged();
	}
	/**
	 * 追加数据
	 * 
	 * @param location
	 */
	public void append(int location, T data) {
		if (null == data) {
			return;
		}
		mDatas.add(location, data);
		notifyDataSetChanged();
	}

	/**
	 * 追加数据
	 * 
	 * @param datas
	 */
	public void append(List<T> datas) {
		if (null == datas) {
			return;
		}
		mDatas.addAll(datas);
		notifyDataSetChanged();
	}

	/**
	 * 重置数据
	 * 
	 * @param datas
	 */
	public void reset(List<T> datas) {
		if (null == datas) {
			mDatas.clear();
		} else {
			mDatas.clear();
			mDatas.addAll(datas);
		}
		notifyDataSetChanged();
	}

	/**
	 * 清空数据
	 */
	public void clear() {
		if (null != mDatas && mDatas.size() != 0) {
			mDatas.clear();
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		if (null == mDatas) {
			return 0;
		}
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	protected Object separatorkey;
	protected Map<Object, ItemViewType> itemViewTypeMap;
	
	public void addSeparatorItem(Object key,Object val,int resource) {
		if(itemViewTypeMap==null){
			itemViewTypeMap = new HashMap<>();
		}
		itemViewTypeMap.put(val, new ItemViewType(itemViewTypeMap.size(), resource));
		this.separatorkey = key;
		
	}

	protected class ItemViewType{
		private int type;
		private int resource;
		public ItemViewType(int type, int resource) {
			this.type =  type;
			this.resource = resource;
		}
		public int getType() {
			return type;
		}

		public int getResource() {
			return resource;
		}
		
	}
	
	
	@Override
	public int getItemViewType(int position) {
		if (null != itemViewTypeMap) {
			return getIVType(position).getType();
		}
		return super.getItemViewType(position);
	}
	
	protected ItemViewType getIVType(int position){
		if(mDatas.get(position) instanceof Map){
//			LogUtils.d("ItemViewType:Map,separatorkey:"+separatorkey+",val:"+((Map)mDatas.get(position)).get(separatorkey));
			return itemViewTypeMap.get(((Map)mDatas.get(position)).get(separatorkey));
		}else{
//			LogUtils.d("ItemViewType:OBJECT");
			try {
				return itemViewTypeMap.get(ReflectionUtils.getFieldValue(mDatas.get(position), separatorkey + ""));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	@Override
	public int getViewTypeCount() {
		if (null != itemViewTypeMap) {
			return itemViewTypeMap.size();
		}
		return super.getViewTypeCount();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, mResource);
	}

	private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
		View v;
		T data = mDatas.get(position);
		if (convertView == null) {
			if(null==itemViewTypeMap){
				v = mInflater.inflate(resource, parent, false);
			}else{
				v = mInflater.inflate(getIVType(position).getResource(), parent, false);
			}
			// if(v instanceof ViewGroup){
			// FontTool.changeFonts((ViewGroup) v, mContext);
			// }else if(v instanceof TextView){
			// FontTool.setFont((TextView) v);
			// }
			if (null != this.getView) {
				getView.before(position, v, data, parent, mDatas);
			}
			before(position, v, data, parent);
		} else {
			v = convertView;
		}
		if (null != this.getView) {
			if (getView.later(position, v, data, parent, mDatas)) {
				return v;
			}
			;
		}
		later(position, v, data, parent);
		return v;
	}

	/**
	 * getView初始化时执行一次
	 * 
	 * @param position
	 * @param rootView
	 * @param data
	 * @param parent
	 */
	protected abstract void before(int position, View rootView, T data, ViewGroup parent);

	/**
	 * 每次调用getView时执行
	 * 
	 * @param position
	 * @param rootView
	 * @param data
	 * @param parent
	 */
	protected abstract void later(int position, View rootView, T data, ViewGroup parent);

	public void setOnGetViewListener(GetViewListener<T> getViewListener) {
		this.getView = getViewListener;
	}

	public Context getContext() {
		return mContext;
	}

}
