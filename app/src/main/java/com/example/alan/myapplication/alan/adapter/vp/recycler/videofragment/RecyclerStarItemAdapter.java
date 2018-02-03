package com.example.alan.myapplication.alan.adapter.vp.recycler.videofragment;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.recycler.AutoLayoutRecyclerBaseHolder;
import com.example.alan.myapplication.alan.bean.VideoFragmentProjectBean;
import com.example.alan.myapplication.alan.picture.loadpicture.PictureManager;

import java.util.List;

/**
 * Created by Alan on 2018/1/24.
 * 功能：影视条目 排行榜
 */

public class RecyclerStarItemAdapter extends BaseQuickAdapter<VideoFragmentProjectBean.DataBean.SubjectsBean.SubjectDataBean,AutoLayoutRecyclerBaseHolder> {

    private Context context;

    public RecyclerStarItemAdapter(@LayoutRes int layoutResId, @Nullable List<VideoFragmentProjectBean.DataBean.SubjectsBean.SubjectDataBean> data) {
        super(layoutResId, data);
    }


    public void setContext(Context cxt){
        this.context = cxt;
    }


    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, VideoFragmentProjectBean.DataBean.SubjectsBean.SubjectDataBean item) {
        ImageView iv =  (ImageView) helper.getView(R.id.iv_star_recycler_item_video_fragment);

        PictureManager.getInstance().loadServerPic(context,item.img, iv,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,8);
        Button btn = helper.getView(R.id.btn_ranking_star_recycler_item_video_fragment);
        btn.setText(helper.getLayoutPosition()+"");

        helper.setText(R.id.tv_title_star_recycler_video_fragment,item.name+"");
        RatingBar ratingBar = helper.getView(R.id.ratingbar_star_recycler_item_video_fragment);
        double force = item.score;
         if (force<2 && force==2) {
            ratingBar.setRating(1);
        }else {
             int f = (int) (force/2);
             ratingBar.setRating(f);
         }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
