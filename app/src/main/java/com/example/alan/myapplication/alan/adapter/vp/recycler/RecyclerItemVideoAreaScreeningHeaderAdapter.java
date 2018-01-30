package com.example.alan.myapplication.alan.adapter.vp.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.bean.VideoScreeningActivityHeaderBean;

import java.util.List;

/**
 * Created by Alan on 2018/1/30.
 * 功能：影视筛选条件，影片地区
 */

public class RecyclerItemVideoAreaScreeningHeaderAdapter extends BaseQuickAdapter<VideoScreeningActivityHeaderBean.DataBean.AreaBean,AutoLayoutRecyclerBaseHolder> {
    private Context context;
    public int currSelectedPositon = -1;
    public void setContext(Context cxt){
        this.context = cxt;
    }

    public RecyclerItemVideoAreaScreeningHeaderAdapter(@LayoutRes int layoutResId, @Nullable List<VideoScreeningActivityHeaderBean.DataBean.AreaBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, final VideoScreeningActivityHeaderBean.DataBean.AreaBean item) {
        final TextView tv = helper.getView(R.id.tv_item_header_video_screening_activity);
        helper.setText(R.id.tv_item_header_video_screening_activity,item.name+"");
        final int positon = helper.getLayoutPosition();
        if (currSelectedPositon!=positon) {
            tv.setSelected(false);
        }
        if (currSelectedPositon==-1 && positon==0) {
            tv.setSelected(true);
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setSelected(true);
                currSelectedPositon = positon;
                notifyDataSetChanged();
                if (onRecyclerViewItemListener != null) {
                    onRecyclerViewItemListener.OnRecyclerViewItemListener(positon,item.name);
                }
            }
        });
    }

    private OnRecyclerViewItemListener onRecyclerViewItemListener;
    public void setOnRecyclerViewItemListener(OnRecyclerViewItemListener onRecyclerViewItemListener){
        this.onRecyclerViewItemListener = onRecyclerViewItemListener;
    }
}
