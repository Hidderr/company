package com.example.alan.myapplication.alan.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.recycler.videoclassification.RecyclerRootItemFilmClassificationActivityAdapter;
import com.example.alan.myapplication.alan.bean.VideoClassificationBean;
import com.example.alan.myapplication.alan.constants.AppUrl;
import com.example.alan.myapplication.alan.http.HttpLoadStateUtil;
import com.example.alan.myapplication.alan.http.HttpManager;
import com.example.alan.myapplication.alan.http.ServerCallBack;
import com.example.alan.myapplication.alan.utils.AllUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alan on 2018/1/29.
 * 功能：影视分类
 */

public class VideoClassificationActivity extends AutoLayoutActivity implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {
    @Bind(R.id.recycler_view_video_classification_activity)
    RecyclerView mRecyclerViewVideoClassificationActivity;
    @Bind(R.id.sr_layout_video_classification_activity)
    SwipeRefreshLayout mSrLayoutVideoClassificationActivity;
    @Bind(R.id.iv_return_title_bar)
    ImageView mIvReturnTitleBar;
    @Bind(R.id.tv_delete_title_bar)
    TextView mTvDeleteTitleBar;
    @Bind(R.id.tv_choose_title_bar)
    TextView mTvChooseTitleBar;
    @Bind(R.id.tv_title_title_bar)
    TextView mTvTitleTitleBar;
    @Bind(R.id.tv_choose_all_title_bar)
    TextView mTvChooseAllTitleBar;
    @Bind(R.id.ll_root_title_bar)
    LinearLayout mLlRootTitleBar;
    private List<VideoClassificationBean.DataBean.CategoryBean> mItemDataList = new ArrayList<>();
    public RecyclerRootItemFilmClassificationActivityAdapter mRootRecyclerItemAdapter;
    /**
     * 是否正在加载数据
     */
    private boolean isLoding;
    private String mTitle="影视分类";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_video_classification);
        ButterKnife.bind(this);
        initTopBar();
        initSwipRefreshLayout();
        initRecyclerView();
        loadDataFromServer(false);

    }

    private void initTopBar() {
        AllUtils.getInstance().setTextBold(mTvTitleTitleBar);
        mIvReturnTitleBar.setOnClickListener(this);
        mTvTitleTitleBar.setText(mTitle+"");
    }

    private void loadDataFromServer(final boolean isRefresh) {
        HttpManager.getInstance().getCall(AppUrl.VIDEO_CLASSIFICATION, new ServerCallBack() {
            @Override
            public void responseSucessful(String json) {
                loadFinished();
                VideoClassificationBean videoClassificationBean = HttpManager.getInstance().getGson().fromJson(json, VideoClassificationBean.class);
                if (videoClassificationBean != null) {
                    if (videoClassificationBean.data != null) {
                        List<VideoClassificationBean.DataBean.CategoryBean> category = videoClassificationBean.data.category;
                        if (category != null) {
                            if (category.size() > 0) {
                                mRootRecyclerItemAdapter.setNewData(category);
                            } else {
                                HttpLoadStateUtil.getInstance().loadSateChangeNoContent();
                            }
                        }
                    }
                }

            }

            @Override
            public void responseClientFailure(String json, int code) {
                HttpLoadStateUtil.getInstance().loadSateChange(false);
                loadFinished();
            }

            @Override
            public void responseServerFailure(String json, int code) {
                HttpLoadStateUtil.getInstance().loadSateChange(false);
                loadFinished();
            }

            @Override
            public void netWorkFailure(String error) {
                HttpLoadStateUtil.getInstance().loadSateChange(true);
                loadFinished();
            }
        }, this);
    }


    private void initRecyclerView() {
        mRootRecyclerItemAdapter = new RecyclerRootItemFilmClassificationActivityAdapter(R.layout.a_recycler_item_root_video_fragment, mItemDataList);
        mRootRecyclerItemAdapter.setContext(this);
        mRootRecyclerItemAdapter.setEnableLoadMore(false);
        mRootRecyclerItemAdapter.setHasStableIds(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewVideoClassificationActivity.setLayoutManager(linearLayoutManager1);
        mRecyclerViewVideoClassificationActivity.getItemAnimator().setChangeDuration(0);
        mRecyclerViewVideoClassificationActivity.setAdapter(mRootRecyclerItemAdapter);
        mRootRecyclerItemAdapter.setEmptyView(HttpLoadStateUtil.getInstance().setContextAndInitView(this));
    }


    private void initSwipRefreshLayout() {
        mSrLayoutVideoClassificationActivity.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        mSrLayoutVideoClassificationActivity.setOnRefreshListener(this);
        mSrLayoutVideoClassificationActivity.post(new Runnable() {
            @Override
            public void run() {
                mSrLayoutVideoClassificationActivity.setRefreshing(true);
            }
        });//进入刷新
    }


    @Override
    public void onRefresh() {
        if (!isLoding) {
            isLoding = true;
            loadDataFromServer(true);
        }
    }


    public void loadFinished() {
        isLoding = false;
        mSrLayoutVideoClassificationActivity.setRefreshing(false);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_return_title_bar:
                finish();
                break;
            default:
                break;
        }
    }
}
