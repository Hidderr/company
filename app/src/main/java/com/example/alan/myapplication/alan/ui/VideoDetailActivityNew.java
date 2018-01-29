package com.example.alan.myapplication.alan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.FlowLayoutTagAdapter;
import com.example.alan.myapplication.alan.adapter.vp.recycler.RecyclerFormItemVideoDetailActivityAdapter;
import com.example.alan.myapplication.alan.bean.JsonConvertUtils;
import com.example.alan.myapplication.alan.bean.VideoDetailBean;
import com.example.alan.myapplication.alan.constants.AppUrl;
import com.example.alan.myapplication.alan.http.HttpManager;
import com.example.alan.myapplication.alan.http.ServerCallBack;
import com.example.alan.myapplication.alan.picture.loadpicture.PictureManager;
import com.example.alan.myapplication.alan.utils.AllUtils;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.alan.myapplication.alan.global.GlobalApplication.context;

/**
 * Created by Alan on 2018/1/26.
 * 功能：影视详情
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
    com.hymane.expandtextview.ExpandTextView mExpandTvFilemDescVideoDetailActivity;
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
    @Bind(R.id.view_divier_footer_video_detail_activity)
    View mDividerFooter;

    /**
     * 从其他界面跳转到详情界面的传入的详情ID用于从服务器获取数据
     */
    public String mVideoId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail_animation);
        ButterKnife.bind(this);
        initIntent();
        loadDataFromServer();

    }

    private void initView(VideoDetailBean.DataBean dataBean) {
        initHeaderDetail(dataBean);
//        initPlayShareCollection();
        initVideoDes(dataBean);
        initDirectorsAndActors(dataBean);
        initVideoForm(dataBean);


    }

    private void loadDataFromServer() {

        LinkedHashMap<String, String> paramas = new LinkedHashMap<>();
        paramas.put("version", AllUtils.getInstance().getappVersion(this)+"");
        paramas.put("video_id", mVideoId+"");
        HttpManager.getInstance().getCallWithParamas(AppUrl.VIDEO_DETAIL, paramas, new ServerCallBack() {
            @Override
            public void responseSucessful(String json) {
                VideoDetailBean videoDetailBean = JsonConvertUtils.AesJson2Json(json,new VideoDetailBean());
                if (videoDetailBean != null) {
                    if (videoDetailBean.data != null) {
                        initView(videoDetailBean.data);
                    }
                }
            }

            @Override
            public void responseClientFailure(String json, int code) {

            }

            @Override
            public void responseServerFailure(String json, int code) {
            }

            @Override
            public void netWorkFailure(String error) {
            }
        }, this);

    }


    private void initVideoForm(VideoDetailBean.DataBean dataBean) {
        List<VideoDetailBean.DataBean.RecommendBean> recommend =   dataBean.recommend;
        if (recommend != null && recommend.size()>0) {
            RecyclerFormItemVideoDetailActivityAdapter mFormRecyclerAdpater = new RecyclerFormItemVideoDetailActivityAdapter(R.layout.recycler_item_form_video_fragment,recommend );
            mFormRecyclerAdpater.setContext(this);
            mFormRecyclerAdpater.setEnableLoadMore(false);
            mFormRecyclerAdpater.setHasStableIds(true);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
            mRecyclerviewRecyclerItemRootVideoFragment.setLayoutManager(linearLayoutManager2);
            mRecyclerviewRecyclerItemRootVideoFragment.getItemAnimator().setChangeDuration(0);
            mRecyclerviewRecyclerItemRootVideoFragment.setAdapter(mFormRecyclerAdpater);
        }else {
            mLlRootRecyclerItemRootVideoFragment.setVisibility(View.GONE);
            mDividerFooter.setVisibility(View.GONE);
        }

    }

    private void initDirectorsAndActors(VideoDetailBean.DataBean dataBean) {
        initDirecotrs(dataBean);
        initActors(dataBean);

    }

    private void initActors(VideoDetailBean.DataBean dataBean) {
        String actors = dataBean.actors;
        if (!TextUtils.isEmpty(actors)) {
            final List<String> actorsList = string2List(actors);
            FlowLayoutTagAdapter actorsFlowLayoutTagAdapter = new FlowLayoutTagAdapter(actorsList, mFlowlayoutDirectorsVideoDetailActivity, this);
            mFlowlayoutActorsVideoDetailActivity.setAdapter(actorsFlowLayoutTagAdapter);
            mFlowlayoutActorsVideoDetailActivity.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    Toast.makeText(VideoDetailActivityNew.this, actorsList.get(position), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }else {
            mFlowlayoutActorsVideoDetailActivity.setVisibility(View.GONE);
            mTvActorsVideoDetailActivity.setText("演员（暂无）");
        }

    }

    private void initDirecotrs(VideoDetailBean.DataBean dataBean) {
        String directors = dataBean.director;
        if (!TextUtils.isEmpty(directors)) {
            final List<String> directorsList = string2List(directors);
            FlowLayoutTagAdapter directorsFlowLayoutTagAdapter = new FlowLayoutTagAdapter(directorsList, mFlowlayoutDirectorsVideoDetailActivity, this);
            mFlowlayoutDirectorsVideoDetailActivity.setAdapter(directorsFlowLayoutTagAdapter);
            mFlowlayoutDirectorsVideoDetailActivity.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    Toast.makeText(VideoDetailActivityNew.this, directorsList.get(position), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }else {
            mFlowlayoutDirectorsVideoDetailActivity.setVisibility(View.GONE);
            mTvDirectorVideoDetailActivity.setText("导演（暂无）");
        }

    }

    private void initVideoDes(VideoDetailBean.DataBean dataBean) {
        mExpandTvFilemDescVideoDetailActivity.setContent(dataBean.description + "");

    }

    private void initPlayShareCollection() {
    }

    private void initHeaderDetail(VideoDetailBean.DataBean dataBean) {
        if (dataBean != null) {
            PictureManager.getInstance().loadServerPic(context, dataBean.image, mIvDetailRecyclerFootItemVideoFragment, R.drawable.icon_default, R.drawable.icon_default, PictureManager.ROUND_TYPE, 8);
            mTvTitleDetailRecyclerItemVideoFragment.setText(dataBean.title + "");
            mTvSourceDetailRecyclerItemVideoFragment.setText("豆瓣：" + dataBean.score + "");
            mTvAreaDetailRecyclerItemVideoFragment.setText("地区：" + dataBean.area + "");
            mTvDirectorDetailRecyclerItemVideoFragment.setText("类别：" + dataBean.category + "");
            mTvActorDetailRecyclerItemVideoFragment.setText("片长：" + dataBean.duration / 60 + "分钟");
            mTvReleaseTimeDetailRecyclerItemVideoFragment.setText("上映时间：" + dataBean.year + "");
        }
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mVideoId = intent.getStringExtra("video_id");
        }
    }


    public List<String> string2List(String tag) {
        List<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(tag)) {
            String[] tags = null;
            if (tag.contains(";")) {
                tags = tag.split(";");
            } else if (tag.contains("、")) {
                tags = tag.split("、");
            } else {
                tags = tag.split(",");
            }
            for (String s : tags) {
                list.add(s);
            }
            return list;
        }
        return list;

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
