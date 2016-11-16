package com.nj.xufeng.xfutils.https;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.nj.xufeng.xfutils.base.AppBase;
import com.nj.xufeng.xfutils.https.calback.HeadleResultCalBack;
import com.nj.xufeng.xfutils.utils.JacksonUtils;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by xufeng on 15/10/9.
 */
public class HttpClientManager {

    private static HttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;
    private HeadleResultCalBack headleResultCalBack;

    private  Map<String,String> headers;


    static {
        mInstance = new HttpClientManager();
    }

    public static Map<String,String> getHeaders(){
        return mInstance.headers;
    }

    protected HttpClientManager() {
        int cacheSize = 10*1024*1024;
        Cache cache = new Cache(AppBase.getAgepplicationContext().getCacheDir(),cacheSize);

        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setCache(cache);

        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        //cookie enabled
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));

        mDelivery = new Handler(Looper.getMainLooper());
//        mGson =new Gson();
         mGson = new GsonBuilder().
                registerTypeAdapter(Double.class, new JsonSerializer<Double>() {

                    @Override
                    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                        if (src == src.longValue())
                            return new JsonPrimitive(src.longValue());
                        return new JsonPrimitive(src);
                    }
                }).create();
    }

    public static OkHttpClient getOkHttpClient() {
        return mInstance.mOkHttpClient;
    }

    public static void setHeadleResultCalBack(HeadleResultCalBack headleResultCalBack) {
        mInstance.headleResultCalBack = headleResultCalBack;
    }

    public static void header(String name,String value){
        if(null==mInstance.headers){
            mInstance.headers = new HashMap<>();
        }
        mInstance.headers.put(name,value);
    }

    private static void header(Request.Builder builder){
        if(mInstance.headers==null){
            return;
        }
        for (Map.Entry<String, String> kv:mInstance.headers.entrySet()) {
            builder.header(kv.getKey(),kv.getValue());
        }
    }


    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    public static HttpHandler postJSONObjAsyn(String url,Map<String,Object> params){
        String paramsStr = JacksonUtils.objToJson(params);
        Logger.d("请求类型:POST,请求中url:" + url+paramsStr);
        RequestBody requestBody = RequestBody.create(JSON, paramsStr);
        Request.Builder builder = new Request.Builder()
                .url(url).post(requestBody);
        header(builder);
        HttpHandler httpHandler = new HttpHandler(mInstance.mOkHttpClient,builder,mInstance.mDelivery,mInstance.mGson);
        httpHandler.setHeadleResultCalBack(mInstance.headleResultCalBack);
        return httpHandler;
    }

    public static HttpHandler postJSONAsyn(String url,Map<String,String> params){
        Logger.d("请求类型:POST,请求中url:" + url+mapChangeUrlParam(params));
        RequestBody requestBody = RequestBody.create(JSON, JacksonUtils.objToJson(params));
        Request.Builder builder = new Request.Builder()
                .url(url).post(requestBody);
        header(builder);
        HttpHandler httpHandler = new HttpHandler(mInstance.mOkHttpClient,builder,mInstance.mDelivery,mInstance.mGson);
        httpHandler.setHeadleResultCalBack(mInstance.headleResultCalBack);
        return httpHandler;
    }

    /**
     * get异步请求
     */
    public static HttpHandler getAsyn(String url){
        return getAsyn(url,null);
    }
    public static HttpHandler getAsyn(String url,Map<String,String> params){
        if(null!=params){
            url = url+mapChangeUrlParam(params);
        }
        Logger.d("请求类型:GET,请求中url:" + url);
        Request.Builder builder = new Request.Builder()
                .url(url);
        header(builder);
        HttpHandler httpHandler = new HttpHandler(mInstance.mOkHttpClient,builder,mInstance.mDelivery,mInstance.mGson);
        httpHandler.setHeadleResultCalBack(mInstance.headleResultCalBack);
        return httpHandler;
    }

    /**
     * post异步请求
     */
    public static HttpHandler postAsyn(String url){
        return postAsyn(url,null);
    }
    public static HttpHandler postAsyn(String url,Map<String,String> params){
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        if(null!=params){
            for (Map.Entry<String, String> p:
                    params.entrySet()) {
                formEncodingBuilder.add(p.getKey(), p.getValue());
            }
        }
        Logger.d("请求类型:POST,请求中url:" + url+mapChangeUrlParam(params));
        RequestBody requestBody = formEncodingBuilder.build();

        Request.Builder builder = new Request.Builder()
                .url(url).post(requestBody);
        header(builder);
        HttpHandler httpHandler = new HttpHandler(mInstance.mOkHttpClient,builder,mInstance.mDelivery,mInstance.mGson);
        httpHandler.setHeadleResultCalBack(mInstance.headleResultCalBack);
        return httpHandler;
    }

    /**
     * 取消请求
     * @param tag
     */
    public static void cancel(Object tag){
        mInstance.mOkHttpClient.cancel(tag);
    }

    /**
     * map转URL参数
     *
     * @param params
     * @return
     */
    private static String mapChangeUrlParam(Map<String, String> params) {
        if (null == params || params.size() == 0) {
            return "";
        }
        StringBuffer url = new StringBuffer();
        url.append("?");
        Set<Map.Entry<String, String>> set = params.entrySet();
        for (Iterator<Map.Entry<String, String>> it = set.iterator(); it
                .hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            url.append(entry.getKey());
            url.append("=");
            url.append(entry.getValue()+"");
            if (it.hasNext()) {
                url.append("&");
            }
        }
        return url.toString();
    }

}
