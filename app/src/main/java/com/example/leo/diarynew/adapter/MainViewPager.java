package com.example.leo.diarynew.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by leo on 2017/8/18.
 * ViewPager 需要的adapter
 */

public class MainViewPager extends PagerAdapter{
    private List<View> listView;

    public MainViewPager(List<View> mList) {
        this.listView = mList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(listView.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View nowView = listView.get(position);
        container.addView(nowView);
        return nowView;
    }

    @Override
    public int getCount() {
        return listView.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
