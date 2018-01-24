package com.example.alan.myapplication.alan.fragment;

import android.support.v4.app.Fragment;

import butterknife.ButterKnife;

/**
 * Created by Alan on 2018/1/24.
 * 功能：
 */

public class ABaseFragment extends  Fragment{
    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
