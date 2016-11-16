package com.nj.xufeng.xfutils.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;

    public static final int VERTICAL = LinearLayout.VERTICAL;


    private int mOrientation = VERTICAL;

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    public SpaceItemDecoration(int space,int orientation) {
        this.space = space;
        mOrientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildPosition(view) != 0){
            switch (mOrientation){
                case VERTICAL:
                    outRect.top = space;
                    break;
                case HORIZONTAL:
                    outRect.left = space;
                    break;
            }

        }
    }
}