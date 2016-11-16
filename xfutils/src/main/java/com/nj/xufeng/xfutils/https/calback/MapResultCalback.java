package com.nj.xufeng.xfutils.https.calback;

import java.util.Map;

/**
 * Created by xufeng on 15/10/8.
 */
public class MapResultCalback extends HttpResultCalback<Map<String, Object>> {


    @Override
    public Class getSuperclassTypeParameter() {
        return Map.class;
    }

    @Override
    public void onSuccess(Map<String, Object> map) {
        super.onSuccess(map);
    }

    @Override
    public void onCancelled() {
        super.onCancelled();
    }

    @Override
    public void onLoading(long total, long current, boolean isUploading) {
        super.onLoading(total, current, isUploading);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onFailure(int errCode, String errMsg, Exception e) {
        super.onFailure(errCode, errMsg, e);
    }

    @Override
    public void onComplete() {
        super.onComplete();
    }
}
