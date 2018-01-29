package com.example.alan.myapplication.alan.adapter.vp.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.bean.VideoScreeningActivityHeaderBean;

import java.util.List;

/**
 * Created by Alan on 2018/1/29.
 * 功能：
 */

public class RecyclerItemVideoTypeScreeningHeaderAdapter extends BaseQuickAdapter<VideoScreeningActivityHeaderBean.DataBean.TypeBean,AutoLayoutRecyclerBaseHolder> {
    private Context context;
    public TextView lastSelected ;
    public void setContext(Context cxt){
        this.context = cxt;
    }


    public RecyclerItemVideoTypeScreeningHeaderAdapter(@LayoutRes int layoutResId, @Nullable List<VideoScreeningActivityHeaderBean.DataBean.TypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, VideoScreeningActivityHeaderBean.DataBean.TypeBean item) {
      TextView tv = helper.getView(R.id.tv_item_header_video_screening_activity);
        helper.setText(R.id.tv_item_header_video_screening_activity,item.name+"");
       int position =  helper.getLayoutPosition();
        if (position==0) {
            tv.setSelected(true);
        }

    }
}
