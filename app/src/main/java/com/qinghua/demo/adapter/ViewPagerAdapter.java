package com.qinghua.demo.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.View;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private SparseArray<Fragment> mFragmentList;
    private SparseArray<String> mTiltles;
    public ViewPagerAdapter(FragmentManager fm, SparseArray list) {
        super(fm);
        mFragmentList = list;
    }

    public ViewPagerAdapter(FragmentManager fm, SparseArray list, SparseArray<String> titles) {
        super(fm);
        mFragmentList = list;
        this.mTiltles = titles;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(mTiltles != null && mTiltles.size() > position) {
            return mTiltles.get(position);
        }
        return super.getPageTitle(position);
    }


    public View getSlidableView (int index) {
        return mFragmentList.get(index).getView();
    }
}
