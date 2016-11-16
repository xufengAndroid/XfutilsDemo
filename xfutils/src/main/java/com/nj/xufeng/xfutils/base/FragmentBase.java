package com.nj.xufeng.xfutils.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xufeng on 15/9/24.
 */
public abstract class FragmentBase extends Fragment {

    protected View rootView;
    protected int layoutResID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setContentView(@LayoutRes int layoutResID) {
        this.layoutResID = layoutResID;
    }

    public View getRootView(){
        return rootView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(layoutResID,container,false);
        return rootView;
    }


}
