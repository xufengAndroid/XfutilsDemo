package com.nj.xufeng.xfutils.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nj.xufeng.xfutils.https.HttpClientManager;

import butterknife.ButterKnife;

/**
 * Created by xufeng on 15/11/2.
 */
public class XfFragment extends FragmentBase {

    public static <T extends XfFragment>T newInstance(Class<T> clasz,Bundle args) {
        try {
            T xfFragment = clasz.newInstance();
            if(null!=args){
                xfFragment.setArguments(args);
            }
            return xfFragment;
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, rootView);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        HttpClientManager.cancel(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
