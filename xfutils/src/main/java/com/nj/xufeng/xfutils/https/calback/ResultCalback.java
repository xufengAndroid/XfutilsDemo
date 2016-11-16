package com.nj.xufeng.xfutils.https.calback;

/**
 * Created by xufeng on 15/10/10.
 */
public interface ResultCalback<T> {



    public void onHeadleSuccess(T t);

    public void onHeadleFailure(T t);

    /**
     * 请求成功
     *
     * @param t
     */
    public void onSuccess(T t);

    /**
     * 取消请求
     */
    public void onCancelled();

    /**
     * 加载中
     *
     * @param total
     * @param current
     * @param isUploading
     */
    public void onLoading(long total, long current, boolean isUploading);

    /**
     * 请求开始
     */
    public void onStart();

    /**
     * 请求失败
     *
     * @param errCode
     * @param errMsg
     * @param e
     */
    public void onFailure(int errCode, String errMsg, Exception e);

    /**
     * 完成请求
     */
    public void onComplete();
}
