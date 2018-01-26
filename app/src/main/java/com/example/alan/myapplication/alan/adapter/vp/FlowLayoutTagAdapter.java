package com.example.alan.myapplication.alan.adapter.vp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.alan.myapplication.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by Alan on 2018/1/26.
 * 功能：用于影视详情的导演与演员展示
 */

public class FlowLayoutTagAdapter extends TagAdapter<String> {


    private final FlowLayout mFlowLayout;
    private final Context context;

    public FlowLayoutTagAdapter(List<String> datas, FlowLayout flowLayout, Context context) {
        super(datas);
        this.mFlowLayout = flowLayout;
        this.context = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.flow_layout_item_textview,
                mFlowLayout, false);
        tv.setText(s);
        return tv;
    }
}
