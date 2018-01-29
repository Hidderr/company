package com.example.alan.myapplication.alan.adapter.vp.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.bean.VideoDetailBean;
import com.example.alan.myapplication.alan.picture.loadpicture.PictureManager;

import java.util.List;

/**
 * Created by Alan on 2018/1/29.
 * 功能：影视详情片单
 */

public class RecyclerFormItemVideoDetailActivityAdapter extends BaseQuickAdapter<VideoDetailBean.DataBean.RecommendBean,AutoLayoutRecyclerBaseHolder> {
    private Context context;
    public void setContext(Context cxt){
        this.context = cxt;
    }
    public RecyclerFormItemVideoDetailActivityAdapter(@LayoutRes int layoutResId, @Nullable List<VideoDetailBean.DataBean.RecommendBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, VideoDetailBean.DataBean.RecommendBean item) {

        ImageView iv =  (ImageView) helper.getView(R.id.iv_form_recycler_item_video_fragment);
        Log.w("TAG","影视url  ："+item.cover);
        PictureManager.getInstance().loadServerPic(context,item.cover, iv,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,8);

        helper.setText(R.id.tv_title_form_recycler_item_video_fragment,item.name+"");
        TextView t1 = helper.getView(R.id.tv1_title_form_recycler_item_video_fragment);
        TextView t2 = helper.getView(R.id.tv2_title_form_recycler_item_video_fragment);
        TextView t3 = helper.getView(R.id.tv3_title_form_recycler_item_video_fragment);
        String tag = item.tag;
        if (!TextUtils.isEmpty(tag)) {
            String[] tags =tag .split(",");
            t1.setText(tags[0]);
            switch (tags.length){
                case 2:
                    t2.setText(tags[1]);
                    break;
                case 3:
                    t2.setText(tags[1]);
                    t3.setText(tags[2]);
                    break;
            }
        }else {
            t1.setVisibility(View.INVISIBLE);
            t2.setVisibility(View.INVISIBLE);
            t3.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
