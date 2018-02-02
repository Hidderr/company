package com.example.alan.myapplication.alan.adapter.vp.recycler;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Alan on 2018/2/1.
 * 功能：
 */

abstract class BaseRecyclerViewAdapterBaseQuickAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter {


    public BaseRecyclerViewAdapterBaseQuickAdapter(@Nullable List data) {
        super(data);
    }

    public BaseRecyclerViewAdapterBaseQuickAdapter(@LayoutRes int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    public BaseRecyclerViewAdapterBaseQuickAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }
}
