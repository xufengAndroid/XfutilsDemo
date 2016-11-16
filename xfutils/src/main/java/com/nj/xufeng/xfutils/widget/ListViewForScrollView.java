package com.nj.xufeng.xfutils.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListViewForScrollView extends ListView {
    public ListViewForScrollView(Context context) {
        super(context);
    }
    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ListViewForScrollView(Context context, AttributeSet attrs,
        int defStyle) {
        super(context, attrs, defStyle);
    }
    
    
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
        MeasureSpec.AT_MOST);
        if(isSetHeight){
        	isSetHeight  = false;
        	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }else{
        	super.onMeasure(widthMeasureSpec, expandSpec);
        }
    }
    
    int mHeight = 0;
    
    public int getViewHeight() {
    	if(mHeight==0){
    		return  super.getHeight();
    	}
    	if(mHeight>=super.getHeight()){
    		return mHeight;
    	}
		return getHeight();
	}
    
    private boolean isSetHeight = false;
    public void setHeight(int height){
    	this.mHeight = height;
    	isSetHeight = true;
//    	int width = getMeasuredWidth();
//		setMeasuredDimension(width, height);
    	getLayoutParams().height = height;
    }
    
}