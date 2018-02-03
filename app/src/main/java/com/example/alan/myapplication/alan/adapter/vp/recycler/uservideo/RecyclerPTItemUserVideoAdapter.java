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
 * 功能：我的影视收藏的影视
 */

public class RecyclerPTItemUserVideoAdapter extends BaseQuickAdapter<UserVideoBean.DataBean.CollectVideoBean.CollectBean,AutoLayoutRecyclerBaseHolder> {
    private Context context;
    public void setContext(Context cxt){
        this.context = cxt;
    }

    public RecyclerPTItemUserVideoAdapter(@LayoutRes int layoutResId, @Nullable List<UserVideoBean.DataBean.CollectVideoBean.CollectBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, UserVideoBean.DataBean.CollectVideoBean.CollectBean item) {
        ImageView iv =  (ImageView) helper.getView(R.id.iv_p_t_recycler_item_video_fragment);
        PictureManager.getInstance().loadServerPic(context,item.img, iv,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,8);
        helper.setText(R.id.tv_title_p_t_recycler_item_video_fragment,item.name+"");

    }
    @Override
    public long getItemId(int position) {
        return position;
    }
}
