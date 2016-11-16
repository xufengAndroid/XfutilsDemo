package com.nj.xufeng.xfutils.utils;

import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by xufeng on 15/10/26.
 */
public class GSONUtils {


    private GSONUtils() {
        throw new AssertionError();
    }

    public static String getString(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T>T getObjectByString(String gsonStr,Class<T> claz){
        Gson gson = new Gson();
        return gson.fromJson(gsonStr, claz);
    }


    public static <T>T getObjectByMap(Map map,Class<T> claz){
        return getObjectByString(getString(map), claz);
    }


    public static Map getMapByObj(Object Obj){
        return getObjectByString(getString(Obj),Map.class);
    }
}
