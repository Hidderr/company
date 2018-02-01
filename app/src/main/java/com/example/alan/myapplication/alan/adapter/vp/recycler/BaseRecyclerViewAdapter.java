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

public abstract class BaseRecyclerViewAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter {
    public boolean isShowDelete;

    public BaseRecyclerViewAdapter(@LayoutRes int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    public BaseRecyclerViewAdapter(@Nullable List data) {
        super(data);
    }

    public BaseRecyclerViewAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }


    public void showDelete(boolean isShowDelete){
        this.isShowDelete = isShowDelete;
    }
}
