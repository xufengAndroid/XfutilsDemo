package com.nj.xufeng.xfutils.tool;

import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import com.nj.xufeng.xfutils.R;
import com.nj.xufeng.xfutils.listenerimpl.ReturnButtonFinishListener;

/**
 * Created by xufeng on 15/10/27.
 */
public class ToolbarManage {

    AppCompatDelegate delegate;
    Toolbar toolbar;

    public ToolbarManage(AppCompatDelegate delegate,Toolbar toolbar){
        this.delegate = delegate;
        this.toolbar = toolbar;
        toolbar.setTitle("");
        delegate.setSupportActionBar(toolbar);
    }

    public ToolbarManage returnButton(Context content){
        toolbar.setNavigationIcon(R.drawable.x_ic_break_u);
        toolbar.setNavigationOnClickListener(new ReturnButtonFinishListener(content));
        return this;
    }


}
