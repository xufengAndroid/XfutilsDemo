/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.nj.xufeng.xfutils.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nj.xufeng.xfutils.utils.ListUtils;

import java.util.List;


/**
 * ImagePagerAdapter
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

    private Context       context;
    private List<String> imageIdList;

    private int           size;
    private boolean       isInfiniteLoop;

    private OnItemClickListener onItemClickListener;

    public ImagePagerAdapter(Context context, List<String> imageIdList) {
        this.context = context;
        this.imageIdList = imageIdList;
        this.size = ListUtils.getSize(imageIdList);
        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : ListUtils.getSize(imageIdList);
    }

    /**
     * get really position
     * 
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new SimpleDraweeView(context);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }
        Uri uri = Uri.parse(imageIdList.get(getPosition(position)));
        holder.imageView.setImageURI(uri);
//        if(null!=onItemClickListener){
//            holder.imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickListener.onItemClick((SimpleDraweeView) v,position);
//                }
//            });
//        }
        return view;
    }




    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(SimpleDraweeView view,int position);
    }

    private static class ViewHolder {
        SimpleDraweeView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}