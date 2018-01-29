package com.example.alan.myapplication.alan.adapter.vp.recycler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.bean.VideoClassificationBean;
import com.example.alan.myapplication.alan.gimi.LogUtil;
import com.example.alan.myapplication.alan.ui.VideoDetailActivityNew;
import com.example.alan.myapplication.alan.utils.AllUtils;

import java.util.List;

/**
 * Created by Alan on 2018/1/29.
 * 功能：影视分类item
 */

public class RecyclerRootItemFilmClassificationActivityAdapter extends BaseQuickAdapter<VideoClassificationBean.DataBean.CategoryBean,AutoLayoutRecyclerBaseHolder> {
    private Context context;
    public void setContext(Context cxt){
        this.context = cxt;
    }

    public RecyclerRootItemFilmClassificationActivityAdapter(@LayoutRes int layoutResId, @Nullable List<VideoClassificationBean.DataBean.CategoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, VideoClassificationBean.DataBean.CategoryBean item) {
        List<VideoClassificationBean.DataBean.CategoryBean.CategoryDataBean> category_data = item.category_data;
        final String title = item.title;
        helper.setText(R.id.tv_desc_recycler_item_root_video_fragment,title);
        TextView tv_more = helper.getView(R.id.tv_enter_recycler_item_root_video_fragment);
        tv_more.setVisibility(View.VISIBLE);
        final int type =item.type;
        startVideoScreeningActivity(title, tv_more, type);

        tv_more.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = helper.getView(R.id.recyclerview_recycler_item_root_video_fragment);
        RecyclerPTItemFilmClassificationActivityAdapter mRecyclerPTItemAdapter = new RecyclerPTItemFilmClassificationActivityAdapter(R.layout.recycler_item_p_t_video_fragment, category_data);
        mRecyclerPTItemAdapter.setContext(context);
        mRecyclerPTItemAdapter.setEnableLoadMore(false);
        mRecyclerPTItemAdapter.setHasStableIds(true);
        startVideoDetailActivity(category_data,mRecyclerPTItemAdapter);
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager4);
        recyclerView.getItemAnimator().setChangeDuration(0);
        recyclerView.setAdapter(mRecyclerPTItemAdapter);

    }

    /**跳转到影视筛选
     * @param title
     * @param tv_more
     * @param type
     */
    private void startVideoScreeningActivity(final String title, TextView tv_more, final int type) {
        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(title)) {
                    Intent view = new Intent(context, VideoDetailActivityNew.class);
                    view.putExtra("title",title);
                    view.putExtra("type",type);
                    context.startActivity(view);
                }
            }
        });
    }

    private void startVideoDetailActivity(final List<VideoClassificationBean.DataBean.CategoryBean.CategoryDataBean> category_data, BaseQuickAdapter Adpater) {
        Adpater.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtil.w("TAG","影视分类position: :  "+position);
                if (category_data != null && category_data.size()>0) {
                    String contentId =  category_data.get(position).video_id;
                    LogUtil.w("TAG","影视分类:  "+contentId);
                    if (!TextUtils.isEmpty(contentId)) {
                        AllUtils.getInstance().startVideoDetailActivity(context,contentId);
                    }

                }
            }
        });
    }

}
