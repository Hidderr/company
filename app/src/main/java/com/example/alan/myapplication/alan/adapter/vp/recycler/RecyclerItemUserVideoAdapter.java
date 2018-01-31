package com.example.alan.myapplication.alan.adapter.vp.recycler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.bean.UserVideoBean;
import com.example.alan.myapplication.alan.bean.UserVideoRootBean;
import com.example.alan.myapplication.alan.bean.VideoPlayHistoryBean;
import com.example.alan.myapplication.alan.ui.VideoScreeningActivity;
import com.example.alan.myapplication.alan.utils.AllUtils;

import java.util.List;

/**
 * Created by Alan on 2018/1/31.
 * 功能：我的影视分类
 */

public class RecyclerItemUserVideoAdapter extends BaseQuickAdapter<UserVideoRootBean.RooBean,AutoLayoutRecyclerBaseHolder> {
    private Context context;
    /**
     * 观影记录
     */
    private List<VideoPlayHistoryBean> historyBeanList;

    public void setContext(Context cxt){
        this.context = cxt;
    }

    /**
     * 收藏的片单或者推荐的片单
     */
    public UserVideoBean.DataBean.CollectVideoListBean collect_video_list;
    /**
     * 收藏的影视和影视不足时补充的影视
     */
    public UserVideoBean.DataBean.CollectVideoBean collect_video;
    /**
     * 没有收藏的影视与观影记录使用的推荐影视
     */
    public List<UserVideoBean.DataBean.RecommendVideoBean> recommend_video;

    public void setData(UserVideoBean.DataBean data,List<VideoPlayHistoryBean> historyBeanList){
        if (data != null) {
            this.collect_video_list = data.collect_video_list;
            this.collect_video  =  data.collect_video;
            this.recommend_video = data.recommend_video;

        }
        this.historyBeanList = historyBeanList;
        notifyDataSetChanged();
    }
    public RecyclerItemUserVideoAdapter(@LayoutRes int layoutResId, @Nullable List<UserVideoRootBean.RooBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, UserVideoRootBean.RooBean item) {
        helper.setText(R.id.tv_desc_recycler_item_root_video_fragment,item.titleName+"");
        LinearLayout rootView = helper.getView(R.id.ll_root_recycler_item_root_video_fragment);
        RecyclerView recyclerView = helper.getView(R.id.recyclerview_recycler_item_root_video_fragment);
        TextView tv_more = helper.getView(R.id.tv_enter_recycler_item_root_video_fragment);
        tv_more.setVisibility(View.VISIBLE);
        int position = helper.getLayoutPosition();
        rootView.setVisibility(View.VISIBLE);
        switch (position){
            case 0://推荐片单或者收藏的片单
                if (collect_video_list != null && collect_video_list.video_list!=null&&collect_video_list.video_list.size()>0) {
                    helper.setText(R.id.tv_desc_recycler_item_root_video_fragment,collect_video_list.type==2?"推荐片单":"收藏片单");
                    final List<UserVideoBean.DataBean.CollectVideoListBean.VideoListBean> video_list = collect_video_list.video_list;
                    RecyclerFormItemUserVideoAdapter mFormRecyclerAdpater0 = new RecyclerFormItemUserVideoAdapter(R.layout.recycler_item_form_video_fragment, video_list);
                    mFormRecyclerAdpater0.setContext(context);
                    mFormRecyclerAdpater0.setEnableLoadMore(false);
                    mFormRecyclerAdpater0.setHasStableIds(true);

                    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager2);
                    recyclerView.getItemAnimator().setChangeDuration(0);
                    recyclerView.setAdapter(mFormRecyclerAdpater0);
                }else {
//                    rootView.setVisibility(View.GONE);
                    helper.setText(R.id.tv_desc_recycler_item_root_video_fragment,"收藏片单(暂无收藏)");
                    tv_more.setVisibility(View.INVISIBLE);
                }
                break;

            case 1://收藏的影视
                if (collect_video != null && collect_video.collect!=null && collect_video.collect.size()>0 ) {
                    final List<UserVideoBean.DataBean.CollectVideoBean.CollectBean> collect = collect_video.collect;
                    RecyclerPTItemUserVideoAdapter mRecyclerPTItemAdapter1 = new RecyclerPTItemUserVideoAdapter(R.layout.recycler_item_p_t_video_fragment, collect);
                    mRecyclerPTItemAdapter1.setContext(context);
                    mRecyclerPTItemAdapter1.setEnableLoadMore(false);
                    mRecyclerPTItemAdapter1.setHasStableIds(true);
                    mRecyclerPTItemAdapter1.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            startDetailActivity(collect.get(position).content_id);
                        }
                    });
                    LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager4);
                    recyclerView.getItemAnimator().setChangeDuration(0);
                    recyclerView.setAdapter(mRecyclerPTItemAdapter1);
                }else {
//                    rootView.setVisibility(View.GONE);
                    helper.setText(R.id.tv_desc_recycler_item_root_video_fragment,"收藏影视(暂无收藏)");
                    tv_more.setVisibility(View.INVISIBLE);
                }
                break;

            case 2://观影记录
                if (historyBeanList != null && historyBeanList.size()>0) {
                    RecyclerItemPTHistoryUserVideoAdapter mRecyclerPTItemAdapter2 = new RecyclerItemPTHistoryUserVideoAdapter(R.layout.recycler_item_p_t_video_fragment, historyBeanList);
                    mRecyclerPTItemAdapter2.setContext(context);
                    mRecyclerPTItemAdapter2.setEnableLoadMore(false);
                    mRecyclerPTItemAdapter2.setHasStableIds(true);
                    mRecyclerPTItemAdapter2.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            startDetailActivity(historyBeanList.get(position).video_id);
                        }
                    });
                    LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager4);
                    recyclerView.getItemAnimator().setChangeDuration(0);
                    recyclerView.setAdapter(mRecyclerPTItemAdapter2);
                }else {
//                    rootView.setVisibility(View.GONE);
                    helper.setText(R.id.tv_desc_recycler_item_root_video_fragment,"观影记录(暂无记录)");
                    tv_more.setVisibility(View.INVISIBLE);
                }
                break;

            case 3://补充的推荐影视
//                if (collect_video !=null &&collect_video.complement != null && collect_video.complement.size()>0) {
//                    final List<UserVideoBean.DataBean.CollectVideoBean.ComplementBean> complement = collect_video.complement;
//                    RecyclerPTComplementUserVideoAdapter mRecyclerPTItemAdapter3 = new RecyclerPTComplementUserVideoAdapter(R.layout.recycler_foot_item_detail_video_fragment, complement);
//                    mRecyclerPTItemAdapter3.setContext(context);
//                    mRecyclerPTItemAdapter3.setEnableLoadMore(false);
//                    mRecyclerPTItemAdapter3.setHasStableIds(true);
//                    mRecyclerPTItemAdapter3.setOnItemClickListener(new OnItemClickListener() {
//                        @Override
//                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                            startDetailActivity(complement.get(position).content_id);
//                        }
//                    });
//                    LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
//                    recyclerView.setLayoutManager(linearLayoutManager4);
//                    recyclerView.getItemAnimator().setChangeDuration(0);
//                    recyclerView.setAdapter(mRecyclerPTItemAdapter3);
//                }else
                if (recommend_video!=null &&recommend_video.size()>0  ) {//无观影记录以及收藏影视下，推荐影视
                    if (collect_video==null ||collect_video.collect==null || collect_video.collect.size()==0) {
                        RecyclerDetailRecommendUserVideoAdapter mRecyclerDeatilItemAdapter4 = new RecyclerDetailRecommendUserVideoAdapter(R.layout.recycler_foot_item_detail_video_fragment, recommend_video);
                        mRecyclerDeatilItemAdapter4.setContext(context);
                        mRecyclerDeatilItemAdapter4.setEnableLoadMore(false);
                        mRecyclerDeatilItemAdapter4.setHasStableIds(true);
                        mRecyclerDeatilItemAdapter4.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                startDetailActivity(recommend_video.get(position).content_id);
                            }
                        });
                        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                        recyclerView.setLayoutManager(linearLayoutManager4);
                        recyclerView.getItemAnimator().setChangeDuration(0);
                        recyclerView.setAdapter(mRecyclerDeatilItemAdapter4);
                    }else {
                        helper.setText(R.id.tv_desc_recycler_item_root_video_fragment,"暂无推荐");
                        tv_more.setVisibility(View.INVISIBLE);
                    }


                }else {helper.setText(R.id.tv_desc_recycler_item_root_video_fragment,"暂无推荐");tv_more.setVisibility(View.INVISIBLE);}
                break;
            default:
                break;
        }

    }


    private void startDetailActivity(String contentId) {
        if (!TextUtils.isEmpty(contentId)) {
            AllUtils.getInstance().startVideoDetailActivity(context,contentId);
        }
    }


    /**跳转到影视筛选
     * @param title
     * @param tv_more
     * @param type
     */
    private void startUserCollectionActivity(final String title, TextView tv_more, final int type) {
        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(title)) {
                    Intent view = new Intent(context, VideoScreeningActivity.class);
                    view.putExtra("title",title);
                    view.putExtra("type",type);
                    context.startActivity(view);
                }
            }
        });
    }
}
