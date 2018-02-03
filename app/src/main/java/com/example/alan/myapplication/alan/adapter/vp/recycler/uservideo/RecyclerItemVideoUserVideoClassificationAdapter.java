package com.example.alan.myapplication.alan.adapter.vp.recycler.uservideo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.recycler.AutoLayoutRecyclerBaseHolder;
import com.example.alan.myapplication.alan.bean.UserVideoClassificationVideoBean;
import com.example.alan.myapplication.alan.gimi.LogUtil;
import com.example.alan.myapplication.alan.listener.OnDeleteChooseAllChangeListener;
import com.example.alan.myapplication.alan.picture.loadpicture.PictureManager;
import com.example.alan.myapplication.alan.ui.VideoDetailActivityNew;
import com.example.alan.myapplication.alan.utils.AllUtils;

import java.util.List;

/**
 * Created by Alan on 2018/2/1.
 * 功能：我的影视-收藏的影视专页
 */

public class RecyclerItemVideoUserVideoClassificationAdapter extends BaseQuickAdapter<UserVideoClassificationVideoBean.DataBean.VideoBean,AutoLayoutRecyclerBaseHolder> {
    private Context context;

    public RecyclerItemVideoUserVideoClassificationAdapter(@LayoutRes int layoutResId, @Nullable List<UserVideoClassificationVideoBean.DataBean.VideoBean> data) {
        super(layoutResId, data);
    }

    public void setContext(Context cxt){
        this.context = cxt;
    }


    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, final UserVideoClassificationVideoBean.DataBean.VideoBean item) {
        ImageView iv =  (ImageView) helper.getView(R.id.iv_detail_recycler_foot_item_video_fragment);
        LinearLayout rootView = helper.getView(R.id.ll_root_detail_recycler_foot_item_video_fragment);
        PictureManager.getInstance().loadServerPic(context,item.img, iv,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,8);
        helper.setText(R.id.tv_title_detail_recycler_item_video_fragment,item.name+"");
        helper.setText(R.id.tv_source_detail_recycler_item_video_fragment,"豆瓣 "+item.score);
        helper.setText(R.id.tv_area_detail_recycler_item_video_fragment,"地区："+item.area);
        helper.setText(R.id.tv_director_detail_recycler_item_video_fragment,"导演："+item.directors);
        helper.setText(R.id.tv_actor_detail_recycler_item_video_fragment,"演员："+item.actors);
        helper.setText(R.id.tv_release_time_detail_recycler_item_video_fragment,"上映时间："+item.showtime);

        final ImageView delete = helper.getView(R.id.iv_delete_detail_recycler_foot_item_video_fragment);
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
        LogUtil.w("DELETE 1:POSITION:  ", helper.getAdapterPosition()+"");

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<UserVideoClassificationVideoBean.DataBean.VideoBean> data =  getData();
                int noSelectedCount = 0;
                int position = layoutManager.findLastVisibleItemPosition();

                if (isChooseClick) {
                    if (item.isSelcted) {
                        delete.setSelected(false);
                        item.isSelcted = false;
                        for (int i = 0; i < data.size(); i++) {
                            if (!data.get(i).isSelcted) {
                                noSelectedCount++;
                            }
                        }
                        LogUtil.w("DELETE 11:item 为选择的条目",noSelectedCount+" 当前最后可见 positon "+position+"  总共个数size:" +data.size());
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
                        LogUtil.w("DELETE 22:item 为选择的条目：",noSelectedCount+" 当前最后可见 positon "+position+" 总共个数 size:" +data.size());
                        if (noSelectedCount==0 && onDeleteChooseAllChangeListener != null) {
                            onDeleteChooseAllChangeListener.showChooseAllFalseTitle();
                        }
                    }

                }else {
                    String video_id  = item.content_id;
                    if (!TextUtils.isEmpty(video_id)) {
                        AllUtils.getInstance().startActivityWithParamas(context, VideoDetailActivityNew.class,new String[]{"video_id"},new String[]{video_id});
                    }
                }
            }
        });

    }

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
