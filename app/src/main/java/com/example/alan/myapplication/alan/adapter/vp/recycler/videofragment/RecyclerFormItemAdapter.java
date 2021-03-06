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
import com.example.alan.myapplication.alan.ui.FormWebActivity;
import com.example.alan.myapplication.alan.utils.AllUtils;

import java.util.List;

/**
 * Created by Alan on 2018/1/24.
 * 功能：影视节目片单
 */

public class RecyclerFormItemAdapter extends BaseQuickAdapter<VideoFragmentProjectBean.DataBean.SubjectsBean.SubjectDataBean,AutoLayoutRecyclerBaseHolder> {

    private Context context;
    public void setContext(Context cxt){
        this.context = cxt;
    }

    public RecyclerFormItemAdapter(@LayoutRes int layoutResId, @Nullable List<VideoFragmentProjectBean.DataBean.SubjectsBean.SubjectDataBean> data) {
        super(layoutResId, data);
    }





    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, final VideoFragmentProjectBean.DataBean.SubjectsBean.SubjectDataBean item) {
        ImageView iv =  (ImageView) helper.getView(R.id.iv_form_recycler_item_video_fragment);
        PictureManager.getInstance().loadServerPic(context,item.img, iv,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,8);

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
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllUtils.getInstance().startActivityWithParamas(context, FormWebActivity.class,new String[]{"list_id"},new String[]{item.content_id});
            }
        });


    }@Override
    public long getItemId(int position) {
        return position;
    }
}
