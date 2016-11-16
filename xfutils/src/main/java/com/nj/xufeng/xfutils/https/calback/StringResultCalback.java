package com.nj.xufeng.xfutils.https.calback;


/**
 * Created by xufeng on 15/10/8.
 */
public class StringResultCalback extends HttpResultCalback<String> {

    @Override
    public Class getSuperclassTypeParameter() {
        return String.class;
    }

    @Override
    public void onSuccess(String data) {
        super.onSuccess(data);
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
