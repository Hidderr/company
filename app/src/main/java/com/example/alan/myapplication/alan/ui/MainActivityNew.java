package com.example.alan.myapplication.alan.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.MainFragmentVpAdpater;
import com.example.alan.myapplication.alan.fragment.FragmentFactory;
import com.flyco.tablayout.SlidingTabLayout;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by linzh on 2018/1/20.
 */

public class MainActivityNew extends AutoLayoutActivity {
    @Bind(R.id.iv_user_head_activity_main_new)
    ImageView mIvUserHeadActivityMainNew;
    @Bind(R.id.tablayout_activity_main_new)
    SlidingTabLayout mTablayoutActivityMainNew;
    @Bind(R.id.iv_search_activity_main_new)
    ImageView mIvSearchActivityMainNew;
    @Bind(R.id.ill_head_activity_main_new)
    LinearLayout mIllHeadActivityMainNew;
    @Bind(R.id.vp_activity_main_new)
    ViewPager mVpActivityMainNew;
    private  String[] mTitles = {"工具", "影视", "音乐", "应用"};
    private  TextView lastTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_main_new);
        ButterKnife.bind(this);
        initVp();
        initTabLayout();
    }




    private void initVp() {
        MainFragmentVpAdpater adapter = new MainFragmentVpAdpater(getSupportFragmentManager(), FragmentFactory.getInstance().getMainFragment(),mTitles);
        mVpActivityMainNew.setAdapter(adapter);
        mVpActivityMainNew.setOffscreenPageLimit(3);//>=3
        mTablayoutActivityMainNew.setViewPager(mVpActivityMainNew);

    }




    private void initTabLayout() {
       mTablayoutActivityMainNew.setCurrentTab(1);

    }



    @OnClick({R.id.iv_user_head_activity_main_new, R.id.iv_search_activity_main_new})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_head_activity_main_new:
                break;
            case R.id.iv_search_activity_main_new:
                break;
        }
    }
}
