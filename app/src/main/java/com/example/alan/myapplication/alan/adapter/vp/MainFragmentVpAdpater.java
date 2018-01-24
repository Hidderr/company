package com.example.alan.myapplication.alan.adapter.vp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Alan on 2018/1/20.
 * 主界面的ViewpPager与Fragment
 */

public class MainFragmentVpAdpater extends FragmentPagerAdapter {
    private  String[] mTitles;
    private List<Fragment> mFragmentsList;

    public MainFragmentVpAdpater(FragmentManager fm, List<Fragment> fragmentsList, String[] titls) {
        super(fm);
        mFragmentsList = fragmentsList;
        mTitles = titls;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

}
