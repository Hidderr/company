package com.example.alan.myapplication.alan.adapter.vp;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Alan on 2018/1/24.
 * 功能：影视页VideoFragmentHeaderVp
 */

public class VideoFragmentHeaderVpAdapter extends PagerAdapter {


    private ArrayList<View> mList = new ArrayList<>();

    public VideoFragmentHeaderVpAdapter() {

    }

    public VideoFragmentHeaderVpAdapter(ArrayList<View> viewList ) {
        this.mList = viewList;
    }

    public void setList(ArrayList<View> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public ArrayList<View> getList() {
        return mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position < mList.size()) {
            container.removeView(mList.get(position));
        }
    }



}
