package com.nj.xufeng.xfutils.https.calback;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by xufeng on 15/10/8.
 */
public class HttpResultCalback<T> implements ResultCalback<T>{

    private ResultCalback addHttpResultCalbackList[];

    public HttpResultCalback() {
    }


    public Type getSuperclassTypeParameter() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public HttpResultCalback add(ResultCalback... addHttpResultCalback){
        addHttpResultCalbackList = addHttpResultCalback;
        return this;
    }

    @Override
    public void onHeadleSuccess(T t) {
        if (null != addHttpResultCalbackList) {
            for (ResultCalback addHttpResultCalback:addHttpResultCalbackList)
                addHttpResultCalback.onHeadleSuccess(t);
        }
    }

    @Override
    public void onHeadleFailure(T t) {
        if (null != addHttpResultCalbackList) {
            for (ResultCalback addHttpResultCalback:addHttpResultCalbackList)
                addHttpResultCalback.onHeadleFailure(t);
        }
    }

    /**
     * 请求成功
     *
     * @param t
     */
    public void onSuccess(T t) {
        onComplete();
        if (null != addHttpResultCalbackList) {
            for (ResultCalback addHttpResultCalback:addHttpResultCalbackList)
            addHttpResultCalback.onSuccess(t);
        }
    }

    /**
     * 取消请求
     */
    public void onCancelled() {
        onComplete();
        if (null != addHttpResultCalbackList) {
            for (ResultCalback addHttpResultCalback:addHttpResultCalbackList)
            addHttpResultCalback.onCancelled();
        }
    }

    /**
     * 加载中
     *
     * @param total
     * @param current
     * @param isUploading
     */
    public void onLoading(long total, long current, boolean isUploading) {
        if (null != addHttpResultCalbackList) {
            for (ResultCalback addHttpResultCalback:addHttpResultCalbackList)
            addHttpResultCalback.onLoading(total, current, isUploading);
        }
    }

    /**
     * 请求开始
     */
    public void onStart() {
        if (null != addHttpResultCalbackList) {
            for (ResultCalback addHttpResultCalback:addHttpResultCalbackList)
            addHttpResultCalback.onStart();
        }

    }

    /**
     * 请求失败
     *
     * @param errCode
     * @param errMsg
     * @param e
     */
    public void onFailure(int errCode, String errMsg, Exception e) {
        onComplete();
        if (null != addHttpResultCalbackList) {
            for (ResultCalback addHttpResultCalback:addHttpResultCalbackList)
            addHttpResultCalback.onFailure(errCode, errMsg, e);
        }
    }

    /**
     * 完成请求
     */
    public void onComplete() {
        if (null != addHttpResultCalbackList) {
            for (ResultCalback addHttpResultCalback:addHttpResultCalbackList)
            addHttpResultCalback.onComplete();
        }

    }
}
