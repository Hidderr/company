package com.example.alan.myapplication.alan.fragment;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan on 2018/1/20.
 * Fragment生成 类
 */

public class FragmentFactory {
    List<Fragment> mFragmentsList = new ArrayList<>();

    public static FragmentFactory sMFragmentFactory;
    private FragmentFactory() {
        if (mFragmentsList ==null) {
            mFragmentsList = new ArrayList<>();
        }
        mFragmentsList.add(new UtilsFragment());
        mFragmentsList.add(new VideoFragment());
        mFragmentsList.add(new MusicNewFragment());
        mFragmentsList.add(new ApplicationFragment());
    }
    public static FragmentFactory getInstance() {
        if (sMFragmentFactory == null)
        {
            synchronized (FragmentFactory.class)
            {
                if (sMFragmentFactory == null)
                {
                    sMFragmentFactory = new FragmentFactory();
                }
            }
        }
        return sMFragmentFactory;
    }

    /**
     * 获取主界面的Fragment
     */
     public List<Fragment> getMainFragment(){

         return mFragmentsList;

     }
}
