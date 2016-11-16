package com.nj.xufeng.xfutils.tool;

import android.support.annotation.NonNull;

import com.nj.xufeng.xfutils.utils.ListUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 *
 */
public class RxBus {
    private static RxBus instance;

    public static synchronized RxBus get() {
        if (null == instance) {
            instance = new RxBus();
        }
        return instance;
    }
 
    private RxBus() {
    }
 
    private ConcurrentHashMap<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<>();
 
    @SuppressWarnings("unchecked")
    public <T> Observable<T> register(@NonNull Object tag, @NonNull Class<T> clazz) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (null == subjectList) {
            subjectList = new ArrayList<>();
            subjectMapper.put(tag, subjectList);
        }
 
        Subject<T, T> subject;
        subjectList.add(subject = PublishSubject.create());
        Logger.d("[register]subjectMapper: " + subjectMapper);
        return subject;
    }
 
    public void unregister(@NonNull Object tag, @NonNull Observable observable) {
        List<Subject> subjects = subjectMapper.get(tag);
        if (null != subjects) {
            subjects.remove((Subject) observable);
            if (ListUtils.isEmpty(subjects)) {
                subjectMapper.remove(tag);
            }
        }
       Logger.d("[unregister]subjectMapper: " + subjectMapper);
    }
 
    public void post(@NonNull Object content) {
        post(content.getClass().getName(), content);
    }
 
    @SuppressWarnings("unchecked")
    public void post(@NonNull Object tag, @NonNull Object content) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (!ListUtils.isEmpty(subjectList)) {
            for (Subject subject : subjectList) {
                subject.onNext(content);
            }
        }
        Logger.d("[send]subjectMapper: " + subjectMapper);
    }
}