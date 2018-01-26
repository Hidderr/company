package com.example.alan.myapplication.alan.ui;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alan.myapplication.R;
import com.lcodecore.extextview.ExpandTextView;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alan on 2018/1/26.
 * 功能：
 */

public class VideoDetailActivityNew extends AutoLayoutActivity {

    @Bind(R.id.iv_delete_detail_recycler_foot_item_video_fragment)
    ImageView mIvDeleteDetailRecyclerFootItemVideoFragment;
    @Bind(R.id.iv_detail_recycler_foot_item_video_fragment)
    ImageView mIvDetailRecyclerFootItemVideoFragment;
    @Bind(R.id.tv_title_detail_recycler_item_video_fragment)
    TextView mTvTitleDetailRecyclerItemVideoFragment;
    @Bind(R.id.tv_source_detail_recycler_item_video_fragment)
    TextView mTvSourceDetailRecyclerItemVideoFragment;
    @Bind(R.id.tv_area_detail_recycler_item_video_fragment)
    TextView mTvAreaDetailRecyclerItemVideoFragment;
    @Bind(R.id.tv_director_detail_recycler_item_video_fragment)
    TextView mTvDirectorDetailRecyclerItemVideoFragment;
    @Bind(R.id.tv_actor_detail_recycler_item_video_fragment)
    TextView mTvActorDetailRecyclerItemVideoFragment;
    @Bind(R.id.tv_release_time_detail_recycler_item_video_fragment)
    TextView mTvReleaseTimeDetailRecyclerItemVideoFragment;
    @Bind(R.id.btn_play_video_detail_activity)
    Button mBtnPlayVideoDetailActivity;
    @Bind(R.id.iv_play_source_video_detail_activity)
    ImageView mIvPlaySourceVideoDetailActivity;
    @Bind(R.id.tv_share_video_detail_activity)
    TextView mTvShareVideoDetailActivity;
    @Bind(R.id.tv_collection_video_detail_activity)
    TextView mTvCollectionVideoDetailActivity;
    @Bind(R.id.tv_title_video_detail_activity)
    TextView mTvTitleVideoDetailActivity;
    @Bind(R.id.expand_tv_filem_desc_video_detail_activity)
    ExpandTextView mExpandTvFilemDescVideoDetailActivity;
    @Bind(R.id.tv_director_video_detail_activity)
    TextView mTvDirectorVideoDetailActivity;
    @Bind(R.id.flowlayout_directors_video_detail_activity)
    TagFlowLayout mFlowlayoutDirectorsVideoDetailActivity;
    @Bind(R.id.tv_actors_video_detail_activity)
    TextView mTvActorsVideoDetailActivity;
    @Bind(R.id.flowlayout_actors_video_detail_activity)
    TagFlowLayout mFlowlayoutActorsVideoDetailActivity;
    @Bind(R.id.tv_desc_recycler_item_root_video_fragment)
    TextView mTvDescRecyclerItemRootVideoFragment;
    @Bind(R.id.tv_enter_recycler_item_root_video_fragment)
    TextView mTvEnterRecyclerItemRootVideoFragment;
    @Bind(R.id.recyclerview_recycler_item_root_video_fragment)
    RecyclerView mRecyclerviewRecyclerItemRootVideoFragment;
    @Bind(R.id.ll_root_recycler_item_root_video_fragment)
    LinearLayout mLlRootRecyclerItemRootVideoFragment;
    @Bind(R.id.main_content)
    CoordinatorLayout mMainContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail_animation);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_play_video_detail_activity, R.id.iv_play_source_video_detail_activity, R.id.tv_share_video_detail_activity, R.id.tv_collection_video_detail_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_play_video_detail_activity:
                break;
            case R.id.iv_play_source_video_detail_activity:
                break;
            case R.id.tv_share_video_detail_activity:
                break;
            case R.id.tv_collection_video_detail_activity:
                break;
            default:
                break;
        }
    }
}
