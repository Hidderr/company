package com.example.alan.myapplication.alan.adapter.vp.recycler.uservideo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.recycler.AutoLayoutRecyclerBaseHolder;
import com.example.alan.myapplication.alan.bean.UserVideoBean;
import com.example.alan.myapplication.alan.picture.loadpicture.PictureManager;

import java.util.List;

/**
 * Created by Alan on 2018/1/31.
 * 功能：当没有收藏影视与观影记录后，推荐的影视
 */

public class RecyclerDetailRecommendUserVideoAdapter extends BaseQuickAdapter<UserVideoBean.DataBean.RecommendVideoBean,AutoLayoutRecyclerBaseHolder> {

    private Context context;
    public void setContext(Context cxt){
        this.context = cxt;
    }


    public RecyclerDetailRecommendUserVideoAdapter(@LayoutRes int layoutResId, @Nullable List<UserVideoBean.DataBean.RecommendVideoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, UserVideoBean.DataBean.RecommendVideoBean item) {
        ImageView iv =  (ImageView) helper.getView(R.id.iv_detail_recycler_foot_item_video_fragment);
        PictureManager.getInstance().loadServerPic(context,item.img, iv,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,8);
        helper.setText(R.id.tv_title_detail_recycler_item_video_fragment,item.name+"");
        helper.setText(R.id.tv_source_detail_recycler_item_video_fragment,"豆瓣 "+item.score);
        helper.setText(R.id.tv_area_detail_recycler_item_video_fragment,"地区："+item.area);
        helper.setText(R.id.tv_director_detail_recycler_item_video_fragment,"导演："+item.directors);
        helper.setText(R.id.tv_actor_detail_recycler_item_video_fragment,"演员："+item.actors);
        helper.setText(R.id.tv_release_time_detail_recycler_item_video_fragment,"上映时间："+item.showtime);

    }
    @Override
    public long getItemId(int position) {
        return position;
    }
}
