package com.example.alan.myapplication.alan.adapter.vp.recycler.videofragment;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.recycler.AutoLayoutRecyclerBaseHolder;
import com.example.alan.myapplication.alan.bean.VideoFragmentProjectBean;
import com.example.alan.myapplication.alan.picture.loadpicture.PictureManager;

import java.util.List;

/**
 * Created by Alan on 2018/1/24.
 * 功能：影视条目 图片-标题
 */

public class RecyclerPTItemAdapter extends BaseQuickAdapter<VideoFragmentProjectBean.DataBean.SubjectsBean.SubjectDataBean,AutoLayoutRecyclerBaseHolder> {

    private Context context;
    public void setContext(Context cxt){
        this.context = cxt;
    }

    public RecyclerPTItemAdapter(@LayoutRes int layoutResId, @Nullable List<VideoFragmentProjectBean.DataBean.SubjectsBean.SubjectDataBean> data) {
        super(layoutResId, data);
    }





    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, VideoFragmentProjectBean.DataBean.SubjectsBean.SubjectDataBean item) {
        ImageView iv =  (ImageView) helper.getView(R.id.iv_p_t_recycler_item_video_fragment);
        PictureManager.getInstance().loadServerPic(context,item.img, iv,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,8);
        helper.setText(R.id.tv_title_p_t_recycler_item_video_fragment,item.name+"");

    }
    @Override
    public long getItemId(int position) {
        return position;
    }
}
