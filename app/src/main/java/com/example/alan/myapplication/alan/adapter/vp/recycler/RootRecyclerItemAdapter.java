package com.example.alan.myapplication.alan.adapter.vp.recycler;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.bean.VideoFragmentProjectBean;
import com.example.alan.myapplication.alan.gimi.LogUtil;
import com.example.alan.myapplication.alan.utils.AllUtils;

import java.util.List;

/**
 * Created by Alan on 2018/1/24.
 * 功能：影视的根RecyclerViewItme
 */

public class RootRecyclerItemAdapter extends BaseQuickAdapter<VideoFragmentProjectBean.DataBean.SubjectsBean,AutoLayoutRecyclerBaseHolder> {
    private Context context;
    public void setContext(Context cxt){
        this.context = cxt;
    }

    public RootRecyclerItemAdapter(@LayoutRes int layoutResId, @Nullable List<VideoFragmentProjectBean.DataBean.SubjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, VideoFragmentProjectBean.DataBean.SubjectsBean item) {
        helper.setText(R.id.tv_desc_recycler_item_root_video_fragment,item.title);
        RecyclerView recyclerView = helper.getView(R.id.recyclerview_recycler_item_root_video_fragment);
        final int type = item.type;
        final List<VideoFragmentProjectBean.DataBean.SubjectsBean.SubjectDataBean> subject_data= item.subject_data;

        if (type==2) {
            RecyclerFormItemAdapter mFormRecyclerAdpater = new RecyclerFormItemAdapter(R.layout.recycler_item_form_video_fragment, subject_data);
            mFormRecyclerAdpater.setContext(context);
            mFormRecyclerAdpater.setEnableLoadMore(false);
            mFormRecyclerAdpater.setHasStableIds(true);
//            startVideoDetailActivity(subject_data, mFormRecyclerAdpater);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
            recyclerView.setLayoutManager(linearLayoutManager2);
            recyclerView.getItemAnimator().setChangeDuration(0);
            recyclerView.setAdapter(mFormRecyclerAdpater);

        }else if(type==1){

            String style = item.style;
            switch (style){
                case "101" :
                    RecyclerPTDItemAdapter mRecyclerPTDItemAdapter = new RecyclerPTDItemAdapter(R.layout.recycler_item_p_t_d_video_fragment, subject_data);
                    mRecyclerPTDItemAdapter.setContext(context);
                    mRecyclerPTDItemAdapter.setEnableLoadMore(false);
                    mRecyclerPTDItemAdapter.setHasStableIds(true);
                    startVideoDetailActivity(subject_data, mRecyclerPTDItemAdapter);
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager1);
                    recyclerView.getItemAnimator().setChangeDuration(0);
                    recyclerView.setAdapter(mRecyclerPTDItemAdapter);
                    break;

                case "103":
                    RecyclerTimePTItemAdapter mRecyclerTimePTItemAdapter = new RecyclerTimePTItemAdapter(R.layout.recycler_item_time_p_t_video_fragment, subject_data);
                    mRecyclerTimePTItemAdapter.setContext(context);
                    mRecyclerTimePTItemAdapter.setEnableLoadMore(false);
                    mRecyclerTimePTItemAdapter.setHasStableIds(true);
                    startVideoDetailActivity(subject_data, mRecyclerTimePTItemAdapter);
                    LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager3);
                    recyclerView.getItemAnimator().setChangeDuration(0);
                    recyclerView.setAdapter(mRecyclerTimePTItemAdapter);
                    break;

                case "104":
                    RecyclerPTItemAdapter mRecyclerPTItemAdapter = new RecyclerPTItemAdapter(R.layout.recycler_item_p_t_video_fragment, subject_data);
                    mRecyclerPTItemAdapter.setContext(context);
                    mRecyclerPTItemAdapter.setEnableLoadMore(false);
                    mRecyclerPTItemAdapter.setHasStableIds(true);
                    startVideoDetailActivity(subject_data, mRecyclerPTItemAdapter);
                    LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager4);
                    recyclerView.getItemAnimator().setChangeDuration(0);
                    recyclerView.setAdapter(mRecyclerPTItemAdapter);
                    break;

                case "105":
                    RecyclerStarItemAdapter mRecyclerStarItemAdapter = new RecyclerStarItemAdapter(R.layout.recycler_item_star_video_fragment, subject_data);
                    mRecyclerStarItemAdapter.setContext(context);
                    mRecyclerStarItemAdapter.setEnableLoadMore(false);
                    mRecyclerStarItemAdapter.setHasStableIds(true);
                    startVideoDetailActivity(subject_data, mRecyclerStarItemAdapter);
                    final GridLayoutManager gridLayoutManager = new GridLayoutManager(context,3,GridLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.getItemAnimator().setChangeDuration(0);
                    recyclerView.setAdapter(mRecyclerStarItemAdapter);

/////////////////////////////////////////////////////////////////////////////////////////////
                    final RecyclerView.SmoothScroller areaScroller = new LinearSmoothScroller(context) {
                        @Override
                        protected int getVerticalSnapPreference() {
                            return LinearSmoothScroller.SNAP_TO_START;
                        }
                    };
                    final boolean[] rightScroll = {true};
                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if (newState== RecyclerView.SCROLL_STATE_IDLE) {
                                if (gridLayoutManager.findLastVisibleItemPosition() == 5 && rightScroll[0]) {
                                    areaScroller.setTargetPosition(5);
                                    gridLayoutManager.startSmoothScroll(areaScroller);
                                    rightScroll[0] = false;
                                    LogUtil.w("SC","向右滑动");
                                }else if(gridLayoutManager.findFirstVisibleItemPosition() == 0 && !rightScroll[0]){
                                    areaScroller.setTargetPosition(0);
                                    gridLayoutManager.startSmoothScroll(areaScroller);
                                    rightScroll[0] = true;
                                    LogUtil.w("SC","向左滑动。。。。。。。。。。");
                                }
                            }


                        }

                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                        }
                    });



                    break;


            }
        }


    }

    private void startVideoDetailActivity(final List<VideoFragmentProjectBean.DataBean.SubjectsBean.SubjectDataBean> subject_data, BaseQuickAdapter Adpater) {
        Adpater.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtil.w("TAG","position: :  "+position);
                if (subject_data != null && subject_data.size()>0) {
                    String contentId =  subject_data.get(position).content_id;
//                    ToastUtil.getToast("contentId:"+contentId+position,context);
                    LogUtil.w("TAG","CONTENT_ID:  "+contentId);
                    if (!TextUtils.isEmpty(contentId)) {
                        AllUtils.getInstance().startVideoDetailActivity(context,contentId);
//                        ToastUtil.getToast(""+position,context);
                    }

                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
