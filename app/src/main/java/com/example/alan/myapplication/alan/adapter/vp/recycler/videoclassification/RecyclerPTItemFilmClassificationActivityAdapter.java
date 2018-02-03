package com.example.alan.myapplication.alan.adapter.vp.recycler.videoclassification;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.recycler.AutoLayoutRecyclerBaseHolder;
import com.example.alan.myapplication.alan.bean.VideoClassificationBean;
import com.example.alan.myapplication.alan.picture.loadpicture.PictureManager;

import java.util.List;

/**
 * Created by Alan on 2018/1/29.
 * 功能：影视分类的item-iteme
 */

public class RecyclerPTItemFilmClassificationActivityAdapter extends BaseQuickAdapter<VideoClassificationBean.DataBean.CategoryBean.CategoryDataBean,AutoLayoutRecyclerBaseHolder> {
    private Context context;

    public RecyclerPTItemFilmClassificationActivityAdapter(@LayoutRes int layoutResId, @Nullable List<VideoClassificationBean.DataBean.CategoryBean.CategoryDataBean> data) {
        super(layoutResId, data);
    }


    public void setContext(Context cxt){
        this.context = cxt;
    }


    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, VideoClassificationBean.DataBean.CategoryBean.CategoryDataBean item) {
        ImageView iv =  (ImageView) helper.getView(R.id.iv_p_t_recycler_item_video_fragment);
        PictureManager.getInstance().loadServerPic(context,item.cover, iv,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,8);
        helper.setText(R.id.tv_title_p_t_recycler_item_video_fragment,item.name+"");
    }
}
