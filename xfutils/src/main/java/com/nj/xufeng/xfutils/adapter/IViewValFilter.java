package com.nj.xufeng.xfutils.adapter;

import android.view.View;

/**
 * Created by xufeng on 15/9/21.
 */
public interface IViewValFilter<V extends View,D extends Object> {

    /**
     *
     * @param v  被操作的View
     * @param val  被塞的值
     * @param data  被塞的值对像
     */
     void setVal(V v, Object val, View rootView, D data);

}
