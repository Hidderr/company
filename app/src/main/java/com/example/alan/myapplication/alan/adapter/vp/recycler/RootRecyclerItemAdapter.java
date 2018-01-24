package com.example.alan.myapplication.alan.adapter.vp.recycler;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.bean.VideoFragmentProjectBean;

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
        int type = item.type;




        if (type==2) {
            RecyclerFormItemAdapter mFormRecyclerAdpater = new RecyclerFormItemAdapter(R.layout.recycler_item_form_video_fragment, item.subject_data);
            mFormRecyclerAdpater.setContext(context);
            mFormRecyclerAdpater.setEnableLoadMore(false);
            mFormRecyclerAdpater.setHasStableIds(true);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
            recyclerView.setLayoutManager(linearLayoutManager2);
            recyclerView.getItemAnimator().setChangeDuration(0);
            recyclerView.setAdapter(mFormRecyclerAdpater);
//            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                    super.onScrollStateChanged(recyclerView, newState);
//                    linearLayoutManager.invalidateSpanAssignments();
////                mRecyclerAdpater.notifyDataSetChanged();
//                }
//            });




        }else if(type==1){

            String style = item.style;
            switch (style){
                case "101" :
                    RecyclerPTDItemAdapter mRecyclerPTDItemAdapter = new RecyclerPTDItemAdapter(R.layout.recycler_item_p_t_d_video_fragment, item.subject_data);
                    mRecyclerPTDItemAdapter.setContext(context);
                    mRecyclerPTDItemAdapter.setEnableLoadMore(false);
                    mRecyclerPTDItemAdapter.setHasStableIds(true);
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager1);
                    recyclerView.getItemAnimator().setChangeDuration(0);
                    recyclerView.setAdapter(mRecyclerPTDItemAdapter);
                    break;

                case "103":
                    RecyclerTimePTItemAdapter mRecyclerTimePTItemAdapter = new RecyclerTimePTItemAdapter(R.layout.recycler_item_time_p_t_video_fragment, item.subject_data);
                    mRecyclerTimePTItemAdapter.setContext(context);
                    mRecyclerTimePTItemAdapter.setEnableLoadMore(false);
                    mRecyclerTimePTItemAdapter.setHasStableIds(true);
                    LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager3);
                    recyclerView.getItemAnimator().setChangeDuration(0);
                    recyclerView.setAdapter(mRecyclerTimePTItemAdapter);
                    break;

                case "104":
                    RecyclerPTItemAdapter mRecyclerPTItemAdapter = new RecyclerPTItemAdapter(R.layout.recycler_item_p_t_video_fragment, item.subject_data);
                    mRecyclerPTItemAdapter.setContext(context);
                    mRecyclerPTItemAdapter.setEnableLoadMore(false);
                    mRecyclerPTItemAdapter.setHasStableIds(true);
                    LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager4);
                    recyclerView.getItemAnimator().setChangeDuration(0);
                    recyclerView.setAdapter(mRecyclerPTItemAdapter);
                    break;

                case "105":
                    RecyclerStarItemAdapter mRecyclerStarItemAdapter = new RecyclerStarItemAdapter(R.layout.recycler_item_star_video_fragment, item.subject_data);
                    mRecyclerStarItemAdapter.setContext(context);
                    mRecyclerStarItemAdapter.setEnableLoadMore(false);
                    mRecyclerStarItemAdapter.setHasStableIds(true);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(context,3,GridLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.getItemAnimator().setChangeDuration(0);
                    recyclerView.setAdapter(mRecyclerStarItemAdapter);
                    break;


            }
        }


    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
