package com.nj.xufeng.xfutils.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.nj.xufeng.xfutils.base.XfFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 有状态的 ，只会有前3个存在 其他销毁，  前1个， 中间， 下一个
 */
public  class XfStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<FraArgs> mFragmentList = new ArrayList<>();

    public XfStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void put(Class xfFragmentClass){
       put("", xfFragmentClass, null);
    }

    public void put(String title,Class xfFragmentClass){
        put(title, xfFragmentClass, null);
    }
    public void put(String title,Class xfFragmentClass,Bundle bundle){
        FraArgs fraArgs = new FraArgs(title,xfFragmentClass,bundle);
        mFragmentList.add(fraArgs);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentList.get(position).title;
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    //得到每个item
    @Override
    public Fragment getItem(int position) {
        FraArgs fraArgs = mFragmentList.get(position);
        return XfFragment.newInstance(fraArgs.xfFragmentClass,fraArgs.build);
    }


    // 初始化每个页卡选项
    @Override
    public Object instantiateItem(ViewGroup arg0, int arg1) {
        // TODO Auto-generated method stub
        return super.instantiateItem(arg0, arg1);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    private static class FraArgs{
        String title;
        Class<XfFragment> xfFragmentClass;
        Bundle build;

        public FraArgs(String title, Class<XfFragment> xfFragmentClass, Bundle build) {
            this.title = title;
            this.xfFragmentClass = xfFragmentClass;
            this.build = build;
        }

        public void setBuild(Bundle build) {
            this.build = build;
        }
    }

}