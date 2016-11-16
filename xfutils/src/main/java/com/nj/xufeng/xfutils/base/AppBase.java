package com.nj.xufeng.xfutils.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.nj.xufeng.xfutils.https.HttpClientManager;
import com.nj.xufeng.xfutils.https.calback.HeadleResultCalBack;

/**
 * Created by xufeng on 15/10/12.
 */
public class AppBase extends Application {

    private static Context mContext;
    private static Context mApplicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = getApplicationContext();
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(mApplicationContext, HttpClientManager.getOkHttpClient()).build();
        Fresco.initialize(mApplicationContext, config);
        HttpClientManager.setHeadleResultCalBack(new HeadleResultCalBack() {
            @Override
            public boolean isSuccess(Object data) {
                return true;
            }
        });
    }

    public static Context getAgepplicationContext(){
        return mApplicationContext;
    }

    public synchronized static void setContext(Context context){
        mContext = context;
    }

    public synchronized static Context getContext(){
        return mContext==null?mApplicationContext:mContext;
    }

    public synchronized static void clearContext(){
        mContext = null;
    }

}
