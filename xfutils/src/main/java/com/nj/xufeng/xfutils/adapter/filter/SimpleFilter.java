package com.nj.xufeng.xfutils.adapter.filter;

import android.text.TextUtils;
import android.widget.Filter;

import com.nj.xufeng.xfutils.adapter.XfSimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xufeng on 15/11/23.
 */
public class SimpleFilter extends Filter {

    private XfSimpleAdapter adapter;
    private List<Map<String, Object>> typeAheadData;
    private String[] searchKey;
    private int defaultShowNum = 0;

    public SimpleFilter(XfSimpleAdapter adapter,List<Map<String, Object>> typeAheadData,String... searchKey) {
        this.typeAheadData = typeAheadData;
        this.adapter = adapter;
        this.searchKey = searchKey;
    }
    public SimpleFilter(XfSimpleAdapter adapter,List<Map<String, Object>> typeAheadData,int defaultShowNum,String... searchKey) {
        this.typeAheadData = typeAheadData;
        this.adapter = adapter;
        this.defaultShowNum = defaultShowNum;
        this.searchKey = searchKey;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();
        if (!TextUtils.isEmpty(constraint)) {
            // Retrieve the autocomplete results.
            List<Map<String, Object>> searchData = new ArrayList<>();
            for (Map<String, Object> map : typeAheadData) {
                for (String key : searchKey){
                    if ((map.get(key)+"").toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        searchData.add(map);
                        break;
                    }
                }
            }
            // Assign the data to the FilterResults
            filterResults.values = searchData;
            filterResults.count = searchData.size();
        }else {
            List<Map<String, Object>> searchData = new ArrayList<>();
            for (int i=0;i<defaultShowNum&&i<typeAheadData.size();i++){
                searchData.add(typeAheadData.get(i));
            }
            filterResults.values = searchData;
            filterResults.count = searchData.size();
        }
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        if (results.values != null) {
            adapter.reset((List<Map<String, Object>>) results.values);
        }
    }
}
