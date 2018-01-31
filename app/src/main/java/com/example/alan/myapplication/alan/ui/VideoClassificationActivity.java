package com.example.alan.myapplication.alan.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.recycler.RecyclerRootItemFilmClassificationActivityAdapter;
import com.example.alan.myapplication.alan.bean.VideoClassificationBean;
import com.example.alan.myapplication.alan.constants.AppUrl;
import com.example.alan.myapplication.alan.http.HttpLoadStateUtil;
import com.example.alan.myapplication.alan.http.HttpManager;
import com.example.alan.myapplication.alan.http.ServerCallBack;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alan on 2018/1/29.
 * 功能：影视分类
 */

public class VideoClassificationActivity extends AutoLayoutActivity implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.recycler_view_video_classification_activity)
    RecyclerView mRecyclerViewVideoClassificationActivity;
    @Bind(R.id.sr_layout_video_classification_activity)
    SwipeRefreshLayout mSrLayoutVideoClassificationActivity;
    private List<VideoClassificationBean.DataBean.CategoryBean> mItemDataList = new ArrayList<>();
    public RecyclerRootItemFilmClassificationActivityAdapter mRootRecyclerItemAdapter;
    /**
     * 是否正在加载数据
     */
    private boolean isLoding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_classification);
        ButterKnife.bind(this);
        initSwipRefreshLayout();
        initRecyclerView();
        loadDataFromServer(false);

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
                            }else {
                                    HttpLoadStateUtil.getInstance().loadSateChange(false);
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
        mRootRecyclerItemAdapter = new RecyclerRootItemFilmClassificationActivityAdapter(R.layout.recycler_item_root_video_fragment, mItemDataList);
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
       if(!isLoding) {
           isLoding = true;
           loadDataFromServer(true);
       }
    }



    public void loadFinished(){
        isLoding = false;
        mSrLayoutVideoClassificationActivity.setRefreshing(false);
    }


}
