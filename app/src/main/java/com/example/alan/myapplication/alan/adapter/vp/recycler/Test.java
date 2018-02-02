package com.example.alan.myapplication.alan.adapter.vp.recycler;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.alan.myapplication.alan.bean.UserVideoCassificationFormBean;

import java.util.List;

/**
 * Created by Alan on 2018/2/2.
 * 功能：
 */

public class Test extends BaseRecyclerViewAdapterBaseQuickAdapter<UserVideoCassificationFormBean.DataBean.VideoListBean,BaseViewHolder> {

    public Test(@Nullable List data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {

    }


}
