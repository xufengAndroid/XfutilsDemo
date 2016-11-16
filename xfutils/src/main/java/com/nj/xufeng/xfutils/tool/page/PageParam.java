package com.nj.xufeng.xfutils.tool.page;


public interface PageParam<T>{
	
	public T reset();

	public T next();
	
	public T last();
	
	public int getRow();
	
	public void onSuccess(T t);
	
	public void onFailure(T t);
	
}
