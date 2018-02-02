package com.example.alan.myapplication.alan.adapter.vp.recycler;

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
import com.example.alan.myapplication.alan.bean.UserVideoCassificationFormBean;
import com.example.alan.myapplication.alan.gimi.LogUtil;
import com.example.alan.myapplication.alan.listener.OnDeleteChooseAllChangeListener;
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
    protected void convert(AutoLayoutRecyclerBaseHolder helper, final UserVideoCassificationFormBean.DataBean.VideoListBean item) {
        LinearLayout rootView = helper.getView(R.id.ll_root_form_recycler_item_video_fragment);
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


//        ImageView delete = helper.getView(R.id.iv_delete_history_recycler_foot_item_history_activity);
//        if (isChooseClick) {
//            delete.setVisibility(View.VISIBLE);
//            if (chooseAll) {
//                delete.setSelected(true);
//            }else {
//                delete.setSelected(false);
//            }
//        }else {
//            delete.setVisibility(View.GONE);
//        }




        ////////////////////////////////////////////////
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

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChooseClick) {
                    if (delete.isSelected()) {
                        delete.setSelected(false);
                        item.isSelcted = false;
                        if (item.allChooseTrue && onDeleteChooseAllChangeListener != null) {
                            onDeleteChooseAllChangeListener.showChooseAllTrueTitle();
                        }
                    }else {
                        delete.setSelected(true);
                        item.isSelcted = true;
                        List<UserVideoCassificationFormBean.DataBean.VideoListBean>  data =  getData();
                        int noSelectedCount = 0;
                        int position = layoutManager.findLastVisibleItemPosition();
                        for (int i = 0; i < position; i++) {
                            if (!data.get(i).isSelcted) {
                                noSelectedCount++;

                            }
                            LogUtil.w("DELETE:item",noSelectedCount+"");
                        }
                        LogUtil.w("DELETE:",data.size()+"");
                        if (noSelectedCount==0 && onDeleteChooseAllChangeListener != null) {
                            onDeleteChooseAllChangeListener.showChooseAllFalseTitle();
                            LogUtil.w("DELETE:回调 ",data.size()+"");
                        }
                    }

                }else {

                }
            }
        });


    }
    @Override
    public long getItemId(int position) {
        return position;
    }





    ////////////////////////////////////
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

