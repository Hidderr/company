package com.example.alan.myapplication.alan.adapter.vp.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.bean.UserVideoCassificationFormBean;
import com.example.alan.myapplication.alan.picture.loadpicture.PictureManager;

import java.util.List;

/**
 * Created by Alan on 2018/2/1.
 * 功能：我的影视-收藏的片单专页
 */

public class RecyclerItemFormUserVideoClassificationAdapter extends BaseQuickAdapter<UserVideoCassificationFormBean.DataBean.VideoListBean,AutoLayoutRecyclerBaseHolder> {
    private Context context;

    public RecyclerItemFormUserVideoClassificationAdapter(@LayoutRes int layoutResId, @Nullable List<UserVideoCassificationFormBean.DataBean.VideoListBean> data) {
        super(layoutResId, data);
    }

    public void setContext(Context cxt){
        this.context = cxt;
    }

    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, UserVideoCassificationFormBean.DataBean.VideoListBean item) {
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


        ImageView delete = helper.getView(R.id.iv_delete_history_recycler_foot_item_history_activity);
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
    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setShowDelete(boolean isChooseClick) {
        this.isChooseClick = isChooseClick;
        notifyDataSetChanged();
    }

    public boolean isChooseClick;
}

