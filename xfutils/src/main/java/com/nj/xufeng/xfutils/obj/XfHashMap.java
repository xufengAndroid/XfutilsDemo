package com.nj.xufeng.xfutils.obj;

import java.util.HashMap;

/**
 * Created by xufeng on 15/12/12.
 */
public class XfHashMap<K,V> extends HashMap<K,V> {


    public XfHashMap<K,V> putkv(K key, V value) {
        super.put(key, value);
        return this;
    }
}
