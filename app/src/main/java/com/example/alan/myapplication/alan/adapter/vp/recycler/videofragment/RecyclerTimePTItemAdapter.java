package com.example.alan.myapplication.alan.adapter.vp.recycler.videofragment;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.recycler.AutoLayoutRecyclerBaseHolder;
import com.example.alan.myapplication.alan.bean.VideoFragmentProjectBean;
import com.example.alan.myapplication.alan.picture.loadpicture.PictureManager;

import java.util.List;

/**
 * Created by Alan on 2018/1/24.
 * 功能：影视条目，含有上线时间
 */

public class RecyclerTimePTItemAdapter extends BaseQuickAdapter<VideoFragmentProjectBean.DataBean.SubjectsBean.SubjectDataBean,AutoLayoutRecyclerBaseHolder> {

    private Context context;
    List<VideoFragmentProjectBean.DataBean.SubjectsBean.SubjectDataBean> mData;

    public RecyclerTimePTItemAdapter(@LayoutRes int layoutResId, @Nullable List<VideoFragmentProjectBean.DataBean.SubjectsBean.SubjectDataBean> data) {
        super(layoutResId, data);
        mData = data;
    }

    public void setContext(Context cxt){
        this.context = cxt;
    }

    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, VideoFragmentProjectBean.DataBean.SubjectsBean.SubjectDataBean item) {
        ImageView iv =  (ImageView) helper.getView(R.id.iv_time_p_t_recycler_item_video_fragment);
        PictureManager.getInstance().loadServerPic(context,item.img, iv,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,8);
        helper.setText(R.id.tv_title_time_p_t_recycler_item_video_fragment,item.name+"");


        View circleView = helper.getView(R.id.v_circle_time_p_t_recycler_item_video_fragment);
        View lineView = helper.getView(R.id.v_line_time_p_t_recycler_item_video_fragment);
        TextView showTimeView = helper.getView(R.id.tv_date_time_p_t_recycler_item_video_fragment);
        int position = helper.getLayoutPosition();
        String nowShowTime = item.showtime;

        if (position==0) {
            showTimeView.setText(nowShowTime+"");
        }else if(position>0){
            String lastShowTime = mData.get(position-1).showtime;
            if (!TextUtils.isEmpty(lastShowTime)) {
                if (lastShowTime.equals(nowShowTime)) {
                    circleView.setVisibility(View.INVISIBLE);
                    showTimeView.setVisibility(View.INVISIBLE);
                }else {
                    showTimeView.setText(nowShowTime+"");
                }
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}