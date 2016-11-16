package com.nj.xufeng.xfutils.listenerimpl;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Created by xufeng on 15/10/27.
 */
public class ReturnButtonFinishListener implements OnClickListener {

    private Context mContext;

    public ReturnButtonFinishListener(Context context){
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        if (mContext instanceof Activity){
            ((Activity)mContext).finish();
        }
    }
}
