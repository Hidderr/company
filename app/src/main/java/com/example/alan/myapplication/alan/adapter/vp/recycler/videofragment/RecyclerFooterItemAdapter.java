package com.example.alan.myapplication.alan.adapter.vp.recycler.videofragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.recycler.AutoLayoutRecyclerBaseHolder;
import com.example.alan.myapplication.alan.bean.VideoFragmentFooterBean;
import com.example.alan.myapplication.alan.picture.loadpicture.PictureManager;

import java.util.List;

/**
 * Created by Alan on 2018/1/24.
 * 功能：影视底部条目footer
 */

public class RecyclerFooterItemAdapter extends BaseMultiItemQuickAdapter<VideoFragmentFooterBean.DataBean.IndividualityBean,AutoLayoutRecyclerBaseHolder> {

    private Context context;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization mItemDataList.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public RecyclerFooterItemAdapter(List<VideoFragmentFooterBean.DataBean.IndividualityBean> data) {
        super(data);
        addItemType(VideoFragmentFooterBean.DataBean.IndividualityBean.TYPE_DETAIL, R.layout.recycler_foot_item_detail_video_fragment);//类型1影视
        addItemType(VideoFragmentFooterBean.DataBean.IndividualityBean.TYPE_FORM, R.layout.recycler_foot_item_form_video_fragment);//类型2影视片单
    }

    public void setContext(Context cxt){
        this.context = cxt;
    }

    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, VideoFragmentFooterBean.DataBean.IndividualityBean item) {

        switch (helper.getItemViewType()) {
            case VideoFragmentFooterBean.DataBean.IndividualityBean.TYPE_DETAIL://类型1影视
                ImageView iv =  (ImageView) helper.getView(R.id.iv_detail_recycler_foot_item_video_fragment);
                PictureManager.getInstance().loadServerPic(context,item.img, iv,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,8);
                helper.setText(R.id.tv_title_detail_recycler_item_video_fragment,item.name+"");
                helper.setText(R.id.tv_source_detail_recycler_item_video_fragment,"豆瓣 "+item.score);
                helper.setText(R.id.tv_area_detail_recycler_item_video_fragment,"地区："+item.area);
                helper.setText(R.id.tv_director_detail_recycler_item_video_fragment,"导演："+item.directors);
                helper.setText(R.id.tv_actor_detail_recycler_item_video_fragment,"演员："+item.actors);
                helper.setText(R.id.tv_release_time_detail_recycler_item_video_fragment,"上映时间："+item.showtime);

                break;

            case VideoFragmentFooterBean.DataBean.IndividualityBean.TYPE_FORM://类型2影视片单
                ImageView iv1 =  (ImageView) helper.getView(R.id.iv_form_recycler_foot_item_video_fragment);
                PictureManager.getInstance().loadServerPic(context,item.img, iv1,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,8);
                helper.setText(R.id.tv_title_form_recycler_foot_item_video_fragment,item.name+"");
                TextView t1 = helper.getView(R.id.tv1_title_form_recycler_foot_item_video_fragment);
                TextView t2 = helper.getView(R.id.tv2_title_form_recycler_foot_item_video_fragment);
                TextView t3 = helper.getView(R.id.tv3_title_form_recycler_foot_item_video_fragment);
                String tag = item.tag;
                if (!TextUtils.isEmpty(tag)) {
                    String[] tags =null;
                    if (tag.contains(";")) {
                        tags =tag.split(";");
                    }else if(tag.contains("、")){
                        tags =tag.split("、");
                    }else {
                        tags =tag.split(",");
                    }

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
                break;
        }


    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
