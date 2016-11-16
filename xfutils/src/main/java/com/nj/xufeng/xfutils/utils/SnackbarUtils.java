package com.nj.xufeng.xfutils.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * ToastUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-12-9
 */
public class SnackbarUtils {

    private SnackbarUtils() {
        throw new AssertionError();
    }

    public static void show(View view, CharSequence text, int duration) {
        final Snackbar snackbar = Snackbar.make(view, text, duration);
//        View.OnClickListener close = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                snackbar.dismiss();
//            }
//        };
//        snackbar.setAction("关闭", close);
        snackbar.show();
    }
    public static void showClose(View view, CharSequence text) {
        final Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        View.OnClickListener close = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        };
        snackbar.setAction("关闭", close);
        snackbar.show();
    }
}
