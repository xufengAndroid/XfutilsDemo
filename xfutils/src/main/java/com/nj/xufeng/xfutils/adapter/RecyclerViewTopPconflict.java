package com.nj.xufeng.xfutils.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xufeng on 15/11/4.
 */
public class RecyclerViewTopPconflict extends RecyclerView.OnScrollListener {

    private View mView;

    public RecyclerViewTopPconflict(View parentView){
        mView = parentView;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(recyclerView.computeVerticalScrollOffset()==0){
            mView.setEnabled(true);
        }else{
            mView.setEnabled(false);
        }
    }
}
