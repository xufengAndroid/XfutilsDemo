package com.nj.xufeng.xfutils.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by xufeng on 15/9/23.
 */
public class ActivityBase extends AppCompatActivity {


    /**
     * 获取Context
     *
     * @return
     */
    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AppBase.setContext(getContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }



    public View getRootView(){
        return findViewById(android.R.id.content);
    }

    @Override
    protected void onStart() {
        AppBase.setContext(getContext());
        super.onStart();
    }

    @Override
    protected void onStop() {
        AppBase.clearContext();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
