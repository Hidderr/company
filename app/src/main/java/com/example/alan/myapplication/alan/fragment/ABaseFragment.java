package com.example.alan.myapplication.alan.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Alan on 2018/1/24.
 * 功能：
 */

public class ABaseFragment extends  Fragment{
    public Context mFragmentContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mFragmentContext = context;
    }

}
