package com.example.alan.myapplication.alan.adapter.vp.recycler.videoclassification;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.recycler.AutoLayoutRecyclerBaseHolder;
import com.example.alan.myapplication.alan.bean.VideoScreeningResultBean;
import com.example.alan.myapplication.alan.picture.loadpicture.PictureManager;
import com.example.alan.myapplication.alan.utils.AllUtils;

import java.util.List;

/**
 * Created by Alan on 2018/1/29.
 * 功能：影视筛选结果
 */

public class RecyclerItemVideoScreeningResultActivityAdapter extends BaseQuickAdapter<VideoScreeningResultBean.DataBean,AutoLayoutRecyclerBaseHolder> {
    private Context context;
    public void setContext(Context cxt){
        this.context = cxt;
    }

    public RecyclerItemVideoScreeningResultActivityAdapter(@LayoutRes int layoutResId, @Nullable List<VideoScreeningResultBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, VideoScreeningResultBean.DataBean item) {
        ImageView iv =  (ImageView) helper.getView(R.id.iv_p_t_recycler_item_video_fragment);
        PictureManager.getInstance().loadServerPic(context,item.cover, iv,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,8);
        final String video_id = item.video_id;
        helper.setText(R.id.tv_title_p_t_recycler_item_video_fragment,item.name+"");
        helper.getView(R.id.ll_root_p_t_recycler_item_video_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(video_id)) {
                    AllUtils.getInstance().startVideoDetailActivity(context,video_id);
                }
            }
        });

    }
}
