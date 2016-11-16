package com.nj.xufeng.xfutils.adapter;

import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;

import com.nj.xufeng.xfutils.utils.MapUtils;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Title: ViewValFactory.java
 * @Package com.xufeng.xflibrary.adapter
 * @Description: TODO(View设值工厂)
 * @author 徐峰004245 (QQ：284535970)
 * @version V1.0
 */
public class ViewValFactory {

	private ViewValFactory() {
	}

	/**
	 * 设单个值
	 * 
	 * @param v
	 * @param data
	 */
	public static void set(View v, Object data) {
		if (v != null) {
			if (v instanceof Checkable) {
				if (data instanceof Boolean) {
					((Checkable) v).setChecked((Boolean) data);
				} else {
					((Checkable) v).setChecked(false);
					Logger.e(v.getClass().getName() + " should be bound to a Boolean, not a "
							+ (data == null ? "<unknown type>" : data.getClass()));
				}
			} else if (v instanceof TextView) {
				// LogUtils.d("TextView data:"+data);
				((TextView) v).setText(data == null ? "" : data.toString());
			}else {
				Logger.e(v.getClass().getName() + " is not a " + " view that can be bounds by this SimpleAdapter");
			}
		}
	}

	public static void set(View v, Object data,IViewValFilter vvf) {
		vvf.setVal(v, data, null,new Object());
	}

	public static void set(View v, Object data,View rootView,Object datas ,IViewValFilter vvf) {
		vvf.setVal(v, data, rootView, datas);
	}

	public static void sets(View rootView, Map<String, Object> data, List<String> from, List<Object> to) {
		sets(rootView, data, from, to, null);
	}

	/**
	 * 设多个值
	 * 
	 * @param rootView
	 * @param data
	 * @param vva
	 */
	public static void sets(View rootView, Map<String, Object> data, ViewValAdapter vva) {
		sets(rootView, data, vva.fromList, vva.toList, vva.viewValFilterList);
	}

	/**
	 * 设多个值
	 * 
	 * @param rootView
	 * @param data
	 * @param from
	 */
	public static void sets(View rootView, Map<String, Object> data, List<String> from, List<Object> to,
			Map<String, Object> viewValFilterList) {
		int length = from.size();
		for (int i = 0; i < length; i++) {
			if (null != viewValFilterList && viewValFilterList.containsKey(from.get(i))) {
				Object obj = viewValFilterList.get(from.get(i));
				if(obj instanceof IViewValFilter){
					View v = rootView.findViewById((int) to.get(i));
					((IViewValFilter)obj).setVal(v, MapUtils.getVal(data, from.get(i)), rootView, data);
				}else{
					List<IViewValFilter> list = (List<IViewValFilter>)obj;
					List<Integer> intList = (List<Integer>) to.get(i);
					for (int j = 0; j < list.size(); j++) {
						View v = rootView.findViewById(intList.get(j));
						list.get(j).setVal(v, MapUtils.getVal(data, from.get(i)), rootView, data);
					}
				}
			} else {
				View v = rootView.findViewById((int) to.get(i));
				ViewValFactory.set(v, MapUtils.getVal(data, from.get(i)));
			}

		}
	}


}
