package com.example.alan.myapplication.alan.adapter.vp.recycler.uservideo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.recycler.AutoLayoutRecyclerBaseHolder;
import com.example.alan.myapplication.alan.bean.UserVideoPlayHistoryBean;
import com.example.alan.myapplication.alan.gimi.LogUtil;
import com.example.alan.myapplication.alan.listener.OnDeleteChooseAllChangeListener;
import com.example.alan.myapplication.alan.picture.loadpicture.PictureManager;
import com.example.alan.myapplication.alan.ui.VideoDetailActivityNew;
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
    protected void convert(AutoLayoutRecyclerBaseHolder helper, final UserVideoPlayHistoryBean item) {
        LinearLayout rootView = helper.getView(R.id.ll_root_history_recycler_foot_item_history_activity);
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

        final ImageView delete = helper.getView(R.id.iv_delete_history_recycler_foot_item_history_activity);
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
/////////////////////////////////////////
        if (isChooseClick) {
            delete.setVisibility(View.VISIBLE);
            if (chooseAllType==chooseAllTrue) {
                if (!item.allChooseTrue) {
                    delete.setSelected(true);
                    item.isSelcted = true;
                    item.allChooseTrue = true;
                }


            }else if(chooseAllType==chooseAllFalse){
                if (!item.allChooseFalse) {
                    delete.setSelected(false);
                    item.isSelcted = false;
                    item.allChooseFalse = true;
                }

            }
            if (item.isSelcted) {
                delete.setSelected(true);
            }else {
                delete.setSelected(false);
            }

        }else {
            delete.setVisibility(View.GONE);
            item.isSelcted = false;
            item.allChooseFalse = false;
            item.allChooseTrue = false;
        }

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<UserVideoPlayHistoryBean> data =  getData();
                int noSelectedCount = 0;
                if (isChooseClick) {
                    if (delete.isSelected()) {
                        delete.setSelected(false);
                        item.isSelcted = false;
                        for (int i = 0; i < data.size(); i++) {
                            if (!data.get(i).isSelcted) {
                                noSelectedCount++;
                            }
                        }
                        if (noSelectedCount>0  && onDeleteChooseAllChangeListener != null) {
                            onDeleteChooseAllChangeListener.showChooseAllTrueTitle();
                        }
                    }else {
                        delete.setSelected(true);
                        item.isSelcted = true;



                        for (int i = 0; i < data.size(); i++) {
                            if (!data.get(i).isSelcted) {
                                noSelectedCount++;
                            }
                        }
                        LogUtil.w("DELETE:",data.size()+"");
                        if (noSelectedCount==0 && onDeleteChooseAllChangeListener != null) {
                            onDeleteChooseAllChangeListener.showChooseAllFalseTitle();
                            LogUtil.w("DELETE:回调 ",data.size()+"");
                        }
                    }

                }else {
                    String video_id  = item.video_id;
                    if (!TextUtils.isEmpty(video_id)) {
                        AllUtils.getInstance().startActivityWithParamas(context, VideoDetailActivityNew.class,new String[]{"video_id"},new String[]{video_id});
                    }
                }
            }
        });


    }







    ///////////////////////////////////////////////
    public void setOnDeleteChooseAllChangeListener(OnDeleteChooseAllChangeListener onDeleteChooseAllChangeListener){
        this.onDeleteChooseAllChangeListener = onDeleteChooseAllChangeListener;
    }

    public OnDeleteChooseAllChangeListener onDeleteChooseAllChangeListener;


    /**是否展示删除按钮
     * @param isChooseClick
     */
    public void setShowDelete(boolean isChooseClick) {
        this.isChooseClick = isChooseClick;
        chooseAllType=-1;
        notifyDataSetChanged();
    }

    public boolean isChooseClick;

    /**设置是否选择全部
     * @param chooseAll
     */
    public void setChooseAll(boolean chooseAll) {
        this.chooseAll = chooseAll;
        if (chooseAll) {
            chooseAllType = chooseAllTrue;
        }else {
            chooseAllType = chooseAllFalse;
        }
    }

    /**
     * 是否选择全部
     */
    public boolean chooseAll;

    public int chooseAllType =-1;
    public int chooseAllTrue =1;
    public int chooseAllFalse =2;
    public LinearLayoutManager layoutManager;
    public void setLayoutManager(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }
}
