package com.nj.xufeng.xfutils.tool.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageIdParamMap implements PageParam<Map<String, String>> {

	private String lid;//最近的 ID
	private String more;//0上一页,1下一页
	private int resetRow;//行数
	private int addRow;//增量行数
	private int row;

	private String lidKey = "lid";
	private String moreKey = "more";
	private String rowKey = "size";

	private String oidKey = "oid";

	private List<Map<String,Object>> mData;

	public PageIdParamMap(List<Map<String,Object>> data,int resetRow,int addRow){
		mData = data;
		this.resetRow = resetRow;
		this.addRow = addRow;
		this.row =resetRow;
	}

	public void initKey(String lidKey,String moreKey,String rowKey,String oidKey){
		this.lidKey = lidKey;this.moreKey = moreKey;this.rowKey = rowKey;this.oidKey=oidKey;
	}

	@Override
	public Map<String, String> reset() {
		this.row =resetRow;
		Map<String, String> m = new HashMap<>();
		m.put(moreKey,"1");
		m.put(rowKey,resetRow+"");
		return m;
	}

	@Override
	public Map<String, String> next() {
		this.row =addRow;
		Map<String, String> m = new HashMap<>();
		m.put(moreKey,"1");
		m.put(rowKey,addRow+"");
		m.put(lidKey,mData.get(mData.size()-1).get(oidKey)+"");
		return m;
	}

	@Override
	public Map<String, String> last() {
		this.row =addRow;
		Map<String, String> m = new HashMap<>();
		m.put(moreKey,"0");
		m.put(rowKey,addRow+"");
		m.put(lidKey,mData.get(0).get(oidKey)+"");
		return m;
	}


	public boolean isHasMore(){

		return false;
	}


	@Override
	public int getRow() {
		return row;
	}


	@Override
	public void onSuccess(Map<String, String> t) {

	}

	@Override
	public void onFailure(Map<String, String> t) {
//		mIDataAnalysis.analysis(t);
	}

}
