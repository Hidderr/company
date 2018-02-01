package com.example.alan.myapplication.alan.adapter.vp.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.bean.UserVideoClassificationVideoBean;
import com.example.alan.myapplication.alan.picture.loadpicture.PictureManager;

import java.util.List;

/**
 * Created by Alan on 2018/2/1.
 * 功能：我的影视-收藏的影视专页
 */

public class RecyclerItemVideoUserVideoClassificationAdapter extends BaseQuickAdapter<UserVideoClassificationVideoBean.DataBean.VideoBean,AutoLayoutRecyclerBaseHolder> {
    private Context context;

    public RecyclerItemVideoUserVideoClassificationAdapter(@LayoutRes int layoutResId, @Nullable List<UserVideoClassificationVideoBean.DataBean.VideoBean> data) {
        super(layoutResId, data);
    }

    public void setContext(Context cxt){
        this.context = cxt;
    }


    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, UserVideoClassificationVideoBean.DataBean.VideoBean item) {
        ImageView iv =  (ImageView) helper.getView(R.id.iv_detail_recycler_foot_item_video_fragment);
        PictureManager.getInstance().loadServerPic(context,item.img, iv,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,8);
        helper.setText(R.id.tv_title_detail_recycler_item_video_fragment,item.name+"");
        helper.setText(R.id.tv_source_detail_recycler_item_video_fragment,"豆瓣 "+item.score);
        helper.setText(R.id.tv_area_detail_recycler_item_video_fragment,"地区："+item.area);
        helper.setText(R.id.tv_director_detail_recycler_item_video_fragment,"导演："+item.directors);
        helper.setText(R.id.tv_actor_detail_recycler_item_video_fragment,"演员："+item.actors);
        helper.setText(R.id.tv_release_time_detail_recycler_item_video_fragment,"上映时间："+item.showtime);

        ImageView delete = helper.getView(R.id.iv_delete_detail_recycler_foot_item_video_fragment);
        if (isChooseClick) {
            delete.setVisibility(View.VISIBLE);
            if (item.isSelcted) {
                delete.setSelected(true);
            }else {
                delete.setSelected(false);
            }
        }else {
            delete.setVisibility(View.GONE);
        }

    }

    /**是否展示删除按钮
     * @param isChooseClick
     */
    public void setShowDelete(boolean isChooseClick) {
        this.isChooseClick = isChooseClick;
        notifyDataSetChanged();
    }

    public boolean isChooseClick;

}
