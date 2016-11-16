package com.nj.xufeng.xfutils.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by xufeng on 15/12/1.
 */
public class JumpUtils {


    public static void wirelessSettings(Context context){
        // 跳转到系统的网络设置界面
        Intent intent = null;
        // 先判断当前系统版本
        if(android.os.Build.VERSION.SDK_INT > 10){  // 3.0以上
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        }else{
            intent = new Intent();
            intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
        }
        context.startActivity(intent);
    }



}
