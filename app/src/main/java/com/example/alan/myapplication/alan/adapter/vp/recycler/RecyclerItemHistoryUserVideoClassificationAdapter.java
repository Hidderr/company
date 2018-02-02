package com.example.alan.myapplication.alan.adapter.vp.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.bean.UserVideoPlayHistoryBean;
import com.example.alan.myapplication.alan.picture.loadpicture.PictureManager;
import com.example.alan.myapplication.alan.utils.AllUtils;

import java.util.List;

/**
 * Created by Alan on 2018/2/1.
 * 功能：我的影视-影视浏览记录专页
 */

public class RecyclerItemHistoryUserVideoClassificationAdapter extends BaseQuickAdapter<UserVideoPlayHistoryBean,AutoLayoutRecyclerBaseHolder> {
    private Context context;

    public RecyclerItemHistoryUserVideoClassificationAdapter(@LayoutRes int layoutResId, @Nullable List<UserVideoPlayHistoryBean> data) {
        super(layoutResId, data);
    }

    public void setContext(Context cxt){
        this.context = cxt;
    }

    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, UserVideoPlayHistoryBean item) {
        ImageView iv =  (ImageView) helper.getView(R.id.iv_history_recycler_foot_item_history_activity);
        ImageView icon =  (ImageView) helper.getView(R.id.iv_source_icon_history_recycler_foot_item_history_activity);
        PictureManager.getInstance().loadServerPic(context,item.image, iv,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,8);
        PictureManager.getInstance().loadServerPic(context,item.sourceicon, icon,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,3);
        TextView tv = helper.getView(R.id.tv_title_history_recycler_item_history_activity);
        AllUtils.getInstance().setTextBold(tv);
        tv.setText(item.title);
        helper.setText(R.id.tv_duration_history_recycler_item_history_activity,"时长："+item.duration);
        helper.setText(R.id.tv_year_history_recycler_item_history_activity,"年份："+item.year);
        helper.setText(R.id.tv_source_history_recycler_item_history_activity,item.playsource);

        ImageView delete = helper.getView(R.id.iv_delete_history_recycler_foot_item_history_activity);
        if (isChooseClick) {
            delete.setVisibility(View.VISIBLE);
            if (chooseAll) {
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

    /**设置是否选择全部
     * @param chooseAll
     */
    public void setChooseAll(boolean chooseAll) {
        this.chooseAll = chooseAll;
    }

    /**
     * 是否选择全部
     */
    public boolean chooseAll;
}
