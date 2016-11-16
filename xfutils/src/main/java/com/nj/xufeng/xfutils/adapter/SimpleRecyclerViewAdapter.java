package com.nj.xufeng.xfutils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nj.xufeng.xfutils.utils.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xufeng on 15/10/13.
 */
public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Map<String, Object>> mDatas = new ArrayList<>();
    private int mResource;
    private LayoutInflater mInflater;

    private List<Item> items = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    private OnItemLongClickListener onItemLongClickListener;

    private OnItemBindViewHolderListener onItemBindViewHolderListener;


    public SimpleRecyclerViewAdapter(Context context) {
        this(context,0);
    }

    public SimpleRecyclerViewAdapter(Context context, int resource) {
        this.mContext = context;
        this.mResource = resource;
        mInflater = LayoutInflater.from(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Map<Integer,View> viewMap = new HashMap<>();
        public ViewHolder(View view,List<Item> items, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
            super(view);
            if(null==view.getBackground()){
//                TypedValue typedValue = new TypedValue();
//                view.getContext().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
//                view.setBackgroundDrawable(view.getContext().getResources().getDrawable(typedValue.resourceId));
            }
            for (Item item: items) {
                if(!viewMap.containsKey(item.id)){
                    View v = view.findViewById(item.id);
                    if(null!=v)
                    viewMap.put(item.id,v);
                }
            }
            if(null!=onItemClickListener){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(v,getAdapterPosition());
                    }
                });
            }
            if(null!=onItemLongClickListener) {
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return onItemLongClickListener.onItemLongClick(v,getAdapterPosition());
                    }
                });
            }
        }

    }

    protected String separatorkey;
    protected Map<Object, ItemViewType> itemViewTypeMap;

    public void addTypeItem(String key,Object val,int resource) {
        if(itemViewTypeMap==null){
            itemViewTypeMap = new HashMap<>();
        }
        itemViewTypeMap.put(val, new ItemViewType(itemViewTypeMap.size(), resource));
        this.separatorkey = key;

    }

    protected class ItemViewType{
        private int type;
        private int resource;
        public ItemViewType(int type, int resource) {
            this.type =  type;
            this.resource = resource;
        }
        public int getType() {
            return type;
        }

        public int getResource() {
            return resource;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (null != itemViewTypeMap) {
            return getIVType(position).getType();
        }
        return super.getItemViewType(position);
    }

    protected ItemViewType getIVType(int position){
       return itemViewTypeMap.get(MapUtils.getVal(mDatas.get(position), separatorkey));
    }

    protected ItemViewType getIVTypeByViewType(int viewType){
        for (ItemViewType ivt:itemViewTypeMap.values()
             ) {
            if(ivt.getType()==viewType){
                return ivt;
            }
        }
        return null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int res;
        if(null==itemViewTypeMap){
            res = mResource;
        }else{
            res = getIVTypeByViewType(viewType).getResource();
        }
//        Logger.d("viewType:"+viewType);
        View view = mInflater.inflate(res, parent, false);
        ViewHolder viewHolder = new ViewHolder(view,items,onItemClickListener,onItemLongClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Map<String, Object> stringObjectMap = mDatas.get(position);
        for (Item item: items) {
            if(null==item.viewValFilter){
                ViewValFactory.set(holder.viewMap.get(item.id),MapUtils.getVal(stringObjectMap,item.valueKey));
            }else{
                ViewValFactory.set(holder.viewMap.get(item.id),MapUtils.getVal(stringObjectMap, item.valueKey),holder.itemView,stringObjectMap,item.viewValFilter);
            }
        }
        if(null!=onItemBindViewHolderListener){
            onItemBindViewHolderListener.onItemBindViewHolder(position,stringObjectMap,holder);
        }
    }

    public void setOnItemBindViewHolderListener(OnItemBindViewHolderListener onItemBindViewHolderListener) {
        this.onItemBindViewHolderListener = onItemBindViewHolderListener;
    }

    public interface OnItemBindViewHolderListener{
        void onItemBindViewHolder(int position,Map<String, Object> stringObjectMap,ViewHolder holder);
    }

    @Override
    public int getItemCount() {
        if(null==mDatas){
            return 0;
        }
        return mDatas.size();
    }

    public List<Map<String, Object>> getDatas(){
        return mDatas;
    }


    public void remove(String key,String val){
        List<Map<String, Object>> datas = getDatas();
        for (int i = 0; i < datas.size(); i++) {
            Map<String, Object> data = datas.get(i);
            if (val.equals(data.get(key) + "")) {
                getDatas().remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    /**
     * 数据变化
     * @param key
     * @param val
     * @param changeData
     */
    public void replace(String key, String val, Map<String, Object> changeData) {
        List<Map<String, Object>> datas = getDatas();
        for (int i = 0; i < datas.size(); i++) {
            Map<String, Object> data = datas.get(i);
            if (val.equals(data.get(key) + "")) {
                MapUtils.replace(data, changeData);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void reset(List<Map<String, Object>> datas){
        if(null==datas){
            datas = new ArrayList<>();
        }

        if(null==mDatas){
            mDatas = datas;
            notifyItemRangeInserted(0,datas.size());
        }else{
            mDatas.clear();
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public void appedAll(List<Map<String, Object>> datas){
        if (null == datas||datas.size()==0) {
            return;
        }
        int location =0;
        if(null==mDatas){
            mDatas = datas;
        }else{
            location =mDatas.size();
            mDatas.addAll(datas);
        }
        notifyItemRangeInserted(location, datas.size());
    }

    public void appedAll(int location, List<Map<String, Object>> datas){
        if (null == datas||datas.size()==0) {
            return;
        }
        if(null==mDatas){
            mDatas = datas;
        }else{
            mDatas.addAll(location, datas);
        }
        notifyItemRangeInserted(location, datas.size());
    }

    public void apped(Map<String, Object> data){
        if (null == data) {
            return;
        }
        mDatas.add(data);
        notifyItemInserted(mDatas.size());
    }

    public void clear(){
        if(null!=mDatas){
            mDatas.clear();
        }
        notifyItemRemoved(0);
    }


    public void put(String from,Integer to){
        put(from, to, null);
    }

    public void put(String from,Integer to,IViewValFilter vvf){
        items.add(new Item(from,to,vvf));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    protected class Item {
        String valueKey;
        Integer id;
        IViewValFilter viewValFilter;
        public Item(String valueKey, Integer id, IViewValFilter viewValFilter) {
            this.valueKey = valueKey;
            this.id = id;
            this.viewValFilter = viewValFilter;
        }
    }


    public interface  OnItemClickListener{
        void onItemClick(View v,int postion);
    }

    public interface  OnItemLongClickListener{
        boolean onItemLongClick(View v,int postion);
    }

}
