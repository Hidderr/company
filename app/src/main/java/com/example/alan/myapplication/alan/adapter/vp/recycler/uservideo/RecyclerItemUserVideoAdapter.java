package com.example.alan.myapplication.alan.adapter.vp.recycler.uservideo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.recycler.AutoLayoutRecyclerBaseHolder;
import com.example.alan.myapplication.alan.bean.UserVideoBean;
import com.example.alan.myapplication.alan.bean.UserVideoPlayHistoryBean;
import com.example.alan.myapplication.alan.bean.UserVideoRootBean;
import com.example.alan.myapplication.alan.gimi.LogUtil;
import com.example.alan.myapplication.alan.ui.UserVideoClassificationActivity;
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
    private List<UserVideoPlayHistoryBean> historyBeanList;
    public boolean mHasStartActivity =false;
    private List<UserVideoRootBean.RooBean> itemDataList;

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

    /**
     * 收藏的片单类型
     */
    public  String TYPE_COLLECTION_FORM="0";
    /**
     * 收藏的影视类型
     */
    public String TYPE_COLLECTION_VIDEO="1";
    /**
     * 观影历史
     */
    public String TYPE_VIDEO_HISORY="2";

    public final String mName = "收藏片单";
    public final String mName1= "收藏影视";
    public final String mName2= "观影历史";
    public final String mName3= "你或许喜欢";
    public final String mName4= "推荐片单";

    public RecyclerItemUserVideoAdapter(@LayoutRes int layoutResId, @Nullable List<UserVideoRootBean.RooBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(AutoLayoutRecyclerBaseHolder helper, UserVideoRootBean.RooBean item) {
        TextView tv = helper.getView(R.id.tv_desc_recycler_item_root_video_fragment);
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(true);
        tv.setText(item.titleName+"");
        LinearLayout rootView = helper.getView(R.id.ll_root_recycler_item_root_video_fragment);
        RecyclerView recyclerView = helper.getView(R.id.recyclerview_recycler_item_root_video_fragment);
        final TextView tv_more = helper.getView(R.id.tv_enter_recycler_item_root_video_fragment);
        tv_more.setVisibility(View.VISIBLE);
        int position = helper.getAdapterPosition();
        rootView.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);

        switch (position){
            case 0://推荐片单或者收藏的片单
                form(helper, recyclerView, tv_more, linearLayoutManager);
                break;
            case 1://收藏的影视、影视记录、推荐片单三选一
                String title = itemDataList.get(1).titleName;
                switch(title){
                    case mName1://
                        video(helper, rootView, recyclerView, tv_more, linearLayoutManager);
                    break;
                    case mName2:
                        historyVideo(helper, recyclerView, tv_more, linearLayoutManager);
                        break;
                     case mName3:
                         recommand(helper, recyclerView, tv_more);
                         break;
                    default:
                        break;
                }
                break;
            case 2://观影记录
                historyVideo(helper, recyclerView, tv_more, linearLayoutManager);
                break;
            default:
                break;
        }

//        sroolEndEnterActivity(recyclerView,tv.getText().toString());//滑动到底跳转Activity
    }


    /**设置子item的数据
     * @param data
     * @param historyBeanList
     */
    public void setData(UserVideoBean.DataBean data,List<UserVideoPlayHistoryBean> historyBeanList){
        if (data != null) {
            this.collect_video_list = data.collect_video_list;
            this.collect_video  =  data.collect_video;
            this.recommend_video = data.recommend_video;

        }
        this.historyBeanList = historyBeanList;
        notifyDataSetChanged();
    }

    /**推荐影视
     * @param helper
     * @param recyclerView
     * @param tv_more
     */
    private void recommand(AutoLayoutRecyclerBaseHolder helper, RecyclerView recyclerView, TextView tv_more) {
        if (recommend_video!=null &&recommend_video.size()>0  ) {//无观影记录以及收藏影视下，推荐影视
                RecyclerDetailRecommendUserVideoAdapter mRecyclerDeatilItemAdapter4 = new RecyclerDetailRecommendUserVideoAdapter(R.layout.a_recycler_foot_item_detail_video_fragment, recommend_video);
                mRecyclerDeatilItemAdapter4.setContext(context);
                mRecyclerDeatilItemAdapter4.setEnableLoadMore(false);
                mRecyclerDeatilItemAdapter4.setHasStableIds(true);
                mRecyclerDeatilItemAdapter4.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        startDetailActivity(recommend_video.get(position).content_id);
                    }
                });
                LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(linearLayoutManager3);
                recyclerView.getItemAnimator().setChangeDuration(0);
                recyclerView.setAdapter(mRecyclerDeatilItemAdapter4);
                tv_more.setVisibility(View.INVISIBLE);
        }
    }

    /**影视记录
     * @param helper
     * @param recyclerView
     * @param tv_more
     * @param linearLayoutManager4
     */
    private void historyVideo(AutoLayoutRecyclerBaseHolder helper, RecyclerView recyclerView, TextView tv_more, LinearLayoutManager linearLayoutManager4) {
        if (historyBeanList != null && historyBeanList.size()>0) {
            RecyclerItemPTHistoryUserVideoAdapter mRecyclerPTItemAdapter2 = new RecyclerItemPTHistoryUserVideoAdapter(R.layout.a_recycler_item_p_t_video_fragment, historyBeanList);
            mRecyclerPTItemAdapter2.setContext(context);
            mRecyclerPTItemAdapter2.setEnableLoadMore(false);
            mRecyclerPTItemAdapter2.setHasStableIds(true);
            mRecyclerPTItemAdapter2.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startDetailActivity(historyBeanList.get(position).video_id);
                }
            });
            recyclerView.setLayoutManager(linearLayoutManager4);
            recyclerView.getItemAnimator().setChangeDuration(0);
            recyclerView.setAdapter(mRecyclerPTItemAdapter2);
            scollEndToEnterActivity(recyclerView, linearLayoutManager4,  historyBeanList.size(),TYPE_VIDEO_HISORY);
            AllUtils.getInstance().startActivityWithView(UserVideoClassificationActivity.class,tv_more,context,new String[]{"type"},new String[]{TYPE_VIDEO_HISORY});//点击跳转详情Activity
        }
    }

    /**收藏影视
     * @param helper
     * @param rootView
     * @param recyclerView
     * @param tv_more
     * @param linearLayoutManager4
     */
    private void video(AutoLayoutRecyclerBaseHolder helper, LinearLayout rootView, final RecyclerView recyclerView, TextView tv_more, final LinearLayoutManager linearLayoutManager4) {
        if (collect_video != null && collect_video.collect!=null && collect_video.collect.size()>0 ) {
            final List<UserVideoBean.DataBean.CollectVideoBean.CollectBean> collect = collect_video.collect;
            RecyclerPTItemUserVideoAdapter mRecyclerPTItemAdapter1 = new RecyclerPTItemUserVideoAdapter(R.layout.a_recycler_item_p_t_video_fragment, collect);
            mRecyclerPTItemAdapter1.setContext(context);
            mRecyclerPTItemAdapter1.setEnableLoadMore(false);
            mRecyclerPTItemAdapter1.setHasStableIds(true);
            mRecyclerPTItemAdapter1.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startDetailActivity(collect.get(position).content_id);
                }
            });
            recyclerView.setLayoutManager(linearLayoutManager4);
            recyclerView.getItemAnimator().setChangeDuration(0);
            recyclerView.setAdapter(mRecyclerPTItemAdapter1);
            LogUtil.w("DELETE","pofffffffff ");
            final int size = collect.size()-1;
            scollEndToEnterActivity(recyclerView, linearLayoutManager4,  size,TYPE_COLLECTION_VIDEO);
            AllUtils.getInstance().startActivityWithView(UserVideoClassificationActivity.class,tv_more,context,new String[]{"type"},new String[]{TYPE_COLLECTION_VIDEO});//点击跳转详情Activity

        }
    }

    /**当RecyclerView右滑到底部跳转activity
     * @param recyclerView
     * @param linearLayoutManager4
     * @param size
     */
    private void scollEndToEnterActivity(RecyclerView recyclerView, final LinearLayoutManager linearLayoutManager4, final int size, final String type) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = linearLayoutManager4.findLastCompletelyVisibleItemPosition();
                if (position>=0 &&size >position) {
                    AllUtils.getInstance().scrollEndEnterActivity(recyclerView,context,UserVideoClassificationActivity.class,AllUtils.HORIZONTAL,new String[]{"type"},new String[]{type});
                }

            }
        });
    }

    /**推荐片单、收藏片单
     * @param helper
     * @param recyclerView
     * @param tv_more
     * @param linearLayoutManager4
     */
    private void form(AutoLayoutRecyclerBaseHolder helper, RecyclerView recyclerView, TextView tv_more, LinearLayoutManager linearLayoutManager4) {
        if (collect_video_list != null && collect_video_list.video_list!=null&&collect_video_list.video_list.size()>0) {
            String title = collect_video_list.type==2?mName4:mName;
            helper.setText(R.id.tv_desc_recycler_item_root_video_fragment,title);
            if (collect_video_list.type==2) {
                tv_more.setVisibility(View.INVISIBLE);
            }
            final List<UserVideoBean.DataBean.CollectVideoListBean.VideoListBean> video_list = collect_video_list.video_list;
            RecyclerFormItemUserVideoAdapter mFormRecyclerAdpater0 = new RecyclerFormItemUserVideoAdapter(R.layout.a_recycler_item_form_video_fragment, video_list);
            mFormRecyclerAdpater0.setContext(context);
            mFormRecyclerAdpater0.setEnableLoadMore(false);
            mFormRecyclerAdpater0.setHasStableIds(true);
            recyclerView.setLayoutManager(linearLayoutManager4);
            recyclerView.getItemAnimator().setChangeDuration(0);
            recyclerView.setAdapter(mFormRecyclerAdpater0);
            if (!mName4.equals(title ) ) {
                scollEndToEnterActivity(recyclerView, linearLayoutManager4,  video_list.size(),TYPE_COLLECTION_FORM);
            }
            AllUtils.getInstance().startActivityWithView(UserVideoClassificationActivity.class,tv_more,context,new String[]{"type"},new String[]{TYPE_COLLECTION_FORM});//点击跳转详情Activity
        }
    }

//    /**滑动到底跳转Activity
//     * @param recyclerView
//     */
//    private void sroolEndEnterActivity(RecyclerView recyclerView) {
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                    AllUtils.getInstance().scrollEndEnterActivity(recyclerView,context,UserVideoClassificationActivity.class,AllUtils.HORIZONTAL,new String[]{"type"},new String[]{mType});
//            }
//        });
//
//    }


    /**跳转影视详情Activity
     * @param contentId
     */
    private void startDetailActivity(String contentId) {
        if (!TextUtils.isEmpty(contentId)) {
            AllUtils.getInstance().startVideoDetailActivity(context,contentId);
        }
    }


    public void setRootData(List<UserVideoRootBean.RooBean> itemDataList) {
        this.itemDataList = itemDataList;
    }
}
