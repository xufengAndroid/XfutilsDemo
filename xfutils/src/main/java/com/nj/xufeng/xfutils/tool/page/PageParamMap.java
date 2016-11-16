package com.nj.xufeng.xfutils.tool.page;

import java.util.HashMap;
import java.util.Map;

public class PageParamMap implements PageParam<Map<String, Object>> {

	private int resRow;// 初始请求行
	private int resPage;// 初始请求页

	private int row;// 请求行
	private int page;// 请求页

	private String mRowKey;// 请求行key
	private String mPageKey;// 请求页key


	private Map<String, Object> m = new HashMap<String, Object>();

	public PageParamMap(int row, int page) {
		this(row, page, "row", "page");
	}

	public PageParamMap(int row, int page, String rowKey, String pageKey) {
		this.row = row;
		this.page = page;
		this.resRow = row;
		this.resPage = page;
		this.mRowKey = rowKey;
		this.mPageKey = pageKey;
	}

	public void appedParam(Map<String, Object> param){
		m.putAll(param);
	}

	@Override
	public Map<String, Object> reset() {
		row = resRow;
		page = resPage;
		m.put(mRowKey, resRow);
		m.put(mPageKey, resPage);
		return m;
	}

	@Override
	public Map<String, Object> next() {
		page++;
		m.put(mRowKey, row);
		m.put(mPageKey, page);
		return m;
	}

	@Override
	public Map<String, Object> last() {
		page--;
		m.put(mRowKey, row);
		m.put(mPageKey, page);
		return m;
	}

	@Override
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getResRow() {
		return resRow;
	}

	public void setResRow(int resRow) {
		this.resRow = resRow;
	}

	public int getResPage() {
		return resPage;
	}

	public void setResPage(int resPage) {
		this.resPage = resPage;
	}

	@Override
	public void onSuccess(Map<String, Object> t) {
		
	}

	@Override
	public void onFailure(Map<String, Object> t) {
		
	}

}
