package com.example.alan.myapplication.alan.adapter.vp.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by Alan on 2018/1/24.
 * 功能：机型适配的RecyclerViewHolder
 */

public class AutoLayoutRecyclerBaseHolder extends BaseViewHolder {
    public AutoLayoutRecyclerBaseHolder(View view) {
        super(view);
        AutoUtils.autoSize(itemView);
    }
}
