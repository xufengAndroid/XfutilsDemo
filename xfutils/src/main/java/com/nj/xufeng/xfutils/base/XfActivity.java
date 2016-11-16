package com.nj.xufeng.xfutils.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by xufeng on 15/11/2.
 */
public class XfActivity extends ActivityBase {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        onViewCreated(getRootView(),getIntent().getExtras());
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
