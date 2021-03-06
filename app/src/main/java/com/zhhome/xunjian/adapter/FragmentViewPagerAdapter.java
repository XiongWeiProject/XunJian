package com.zhhome.xunjian.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by PC on 2018/4/4.
 */

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;

    public FragmentViewPagerAdapter(FragmentManager manager, List<Fragment> fragments, List<String> titles) {
        super(manager);
        this.fragments = fragments;
        this.titles = titles;

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

}
