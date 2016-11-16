package com.nj.xufeng.xfutils.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewValAdapter{
	public List<String> fromList = new ArrayList<String>();
	public List<Object> toList = new ArrayList<Object>();
	public Map<String,Object> viewValFilterList = new HashMap<String, Object>();
	
	public void put(String from,Integer to){
		put(from, to, null);
	}
	public void put(String from,Integer to,IViewValFilter ivvf){
		if(null!=ivvf){
			if(viewValFilterList.containsKey(from)){
				if(viewValFilterList.get(from) instanceof IViewValFilter){
					int point = findFromPoint(from);
					List<Integer> intList = new ArrayList<>();
					intList.add((Integer) toList.get(point));
					intList.add(to);
					toList.remove(point);
					toList.add(point,intList);
					
					List<IViewValFilter> list = new ArrayList<>();
					list.add((IViewValFilter) viewValFilterList.get(from));
					list.add(ivvf);
					viewValFilterList.put(from, list);
				}else{
					int point = findFromPoint(from);
					((List)toList.get(point)).add(to);
					((List)viewValFilterList.get(from)).add(ivvf);
				}
			}else{
				fromList.add(from);
				toList.add(to);
				viewValFilterList.put(from, ivvf);
			}
		}else{
			fromList.add(from);
			toList.add(to);
		}
	}
	
	private int findFromPoint(String from){
		for (int i = 0; i < fromList.size(); i++) {
			if(fromList.get(i).equals(from)){
				return i;
			}
		}
		return -1;
	}
	
}