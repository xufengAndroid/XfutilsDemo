package com.nj.xufeng.xfutils.https;

import android.os.Handler;

import com.google.gson.Gson;
import com.nj.xufeng.xfutils.https.calback.HeadleResultCalBack;
import com.nj.xufeng.xfutils.https.calback.HttpResultCalback;
import com.nj.xufeng.xfutils.utils.JacksonUtils;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by xufeng on 15/10/9.
 */
public class HttpHandler {
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;
    private Request.Builder mBuilder;
    private Object mTag;
    private HeadleResultCalBack headleResultCalBack;

    private boolean isExeCache = true;

    public HttpHandler(OkHttpClient okHttpClient, Request.Builder builder, Handler delivery, Gson gson) {
        this.mOkHttpClient = okHttpClient;
        this.mDelivery = delivery;
        this.mGson = gson;
        this.mBuilder = builder;
        this.mBuilder.tag(this);
    }

    public void setHeadleResultCalBack(HeadleResultCalBack headleResultCalBack) {
        this.headleResultCalBack = headleResultCalBack;
    }

    /**
     * 设置缓存
     *
     * @param cacheTime
     */
    public HttpHandler cacheNet(int cacheTime, TimeUnit timeUnit) {
        if (!isExeCache) {
            return this;
        }
        if (cacheTime > 0) {
//            mBuilder.header("Cache-Control","max-stale=" + cacheTime);
            mBuilder.cacheControl(new CacheControl.Builder().maxAge(cacheTime, timeUnit).build());
        }
        return this;
    }

    /**
     * 设置缓存
     *
     * @param cacheTime
     */
    public HttpHandler cacheLocal(int cacheTime, TimeUnit timeUnit) {
        if (!isExeCache) {
            return this;
        }
        if (cacheTime > 0) {
//            mBuilder.header("Cache-Control","max-stale=" + cacheTime);
            mBuilder.cacheControl(new CacheControl.Builder().maxStale(cacheTime, timeUnit).build());
        }
        return this;
    }

    public HttpHandler setIsExeCache(boolean isExeCache) {
        this.isExeCache = isExeCache;
        return this;
    }

    public HttpHandler tag(Object tag) {
        this.mTag = tag;
        mBuilder.tag(mTag);
        return this;
    }

    public void cancel() {
        if (null == mTag) {
            mOkHttpClient.cancel(this);
        } else {
            mOkHttpClient.cancel(mTag);
        }
    }

    public void enqueue(HttpResultCalback callback) {
        deliveryResult(mBuilder.build(), callback);
    }

    public Response enqueue() {
        try {
            return mOkHttpClient.newCall(mBuilder.build()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void deliveryResult(Request request, HttpResultCalback callback) {
        start(callback);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            HttpResultCalback callback;

            public Callback set(HttpResultCalback callback) {
                this.callback = callback;

                return this;
            }

            @Override
            public void onFailure(Request request, IOException e) {
                try {
                    if (e instanceof java.net.SocketException) {
                        cancelled(callback);
                        Logger.d("请求url:" + request.urlString() + "\n被取消请求.");
                    } else {
                        sendFailedResultCallback(-1, e, callback, request, null);
                        Logger.d("请求url:" + request.urlString() + "\n连接不到服务器.");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
//                if (response.isSuccessful() && response.code() == 200) {
                    try {
                        String string = response.body().string();
                        if (callback.getSuperclassTypeParameter() == String.class) {
                            sendSuccessResultCallback(string, callback, request, response);
                        } else if (callback.getSuperclassTypeParameter() == Map.class) {
//                            Map o = mGson.fromJson(string, new TypeToken<Map>() {
//                            }.getType());
                            Map o = JacksonUtils.jsonToObj(string, Map.class);
                            sendSuccessResultCallback(o, callback, request, response);
                        } else {
                            Object o = mGson.fromJson(string, callback.getSuperclassTypeParameter());
                            sendSuccessResultCallback(o, callback, request, response);
                        }
                        Logger.d("请求url:" + request.urlString() + "\n响应:" + string);
                    } catch (IOException e) {
                        sendFailedResultCallback(-1, e, callback, request, response);
                        e.printStackTrace();
                    } catch (com.google.gson.JsonParseException e) {//Json解析的错误
                        sendFailedResultCallback(-1, e, callback, request, response);
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

//            }
        }.set(callback));
    }

    private void cancelled(HttpResultCalback callback) {
        mDelivery.post(() -> {
            if (null != callback) {
                callback.onCancelled();
            }
        });
    }

    private void start(HttpResultCalback callback) {
        mDelivery.post(() -> {
            if (null != callback) {
                callback.onStart();
            }
        });
    }

    private void sendSuccessResultCallback(Object object, HttpResultCalback callback, Request request, Response response) {
        mDelivery.post(() -> {
            try {
                if (null != callback) {
                    if (headleResultCalBack.isSuccess(object)) {
                        callback.onHeadleSuccess(object);
                    } else {
                        callback.onHeadleFailure(object);
                    }
                    callback.onSuccess(object);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void sendFailedResultCallback(int errCode, Exception e, HttpResultCalback callback, Request request, Response response) {
        mDelivery.post(() -> {
            try {
                if (null != callback)
                    callback.onFailure(errCode, "", e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

}
