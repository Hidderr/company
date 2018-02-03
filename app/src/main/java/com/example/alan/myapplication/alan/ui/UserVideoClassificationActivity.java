package com.example.alan.myapplication.alan.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.recycler.uservideo.RecyclerItemFormUserVideoClassificationAdapter;
import com.example.alan.myapplication.alan.adapter.vp.recycler.uservideo.RecyclerItemHistoryUserVideoClassificationAdapter;
import com.example.alan.myapplication.alan.adapter.vp.recycler.uservideo.RecyclerItemVideoUserVideoClassificationAdapter;
import com.example.alan.myapplication.alan.bean.UserVideoCassificationFormBean;
import com.example.alan.myapplication.alan.bean.UserVideoClassificationVideoBean;
import com.example.alan.myapplication.alan.bean.UserVideoPlayHistoryBean;
import com.example.alan.myapplication.alan.constants.AppUrl;
import com.example.alan.myapplication.alan.gimi.LogUtil;
import com.example.alan.myapplication.alan.http.HttpLoadStateUtil;
import com.example.alan.myapplication.alan.http.HttpManager;
import com.example.alan.myapplication.alan.http.ServerCallBack;
import com.example.alan.myapplication.alan.listener.OnControlSqlFinishListener;
import com.example.alan.myapplication.alan.listener.OnDeleteChooseAllChangeListener;
import com.example.alan.myapplication.alan.utils.AllUtils;
import com.example.alan.myapplication.alan.utils.SqlUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alan on 2018/1/31.
 * 功能：我的影视详情，收藏的影视、片单，观影记录
 */

public class UserVideoClassificationActivity extends AutoLayoutActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener ,OnDeleteChooseAllChangeListener ,HttpLoadStateUtil.NetRetryListener{

    /**
     * 根据传入的Type判断展示 片单，影视，影视记录
     */
    public String mType = "-1";
    /**
     * 收藏的片单类型
     */
    public String type_collection_form = "0";
    /**
     * 收藏的影视类型
     */
    public String type_collection_video = "1";
    /**
     * 观影历史
     */
    public String type_video_hisory = "2";
    @Bind(R.id.recycler_view_user_video_classification_activity)
    RecyclerView mRecyclerViewUserVideoClassificationActivity;
    @Bind(R.id.sr_layout_usr_video_classification_activity)
    SwipeRefreshLayout mSrLayoutUsrVideoClassificationActivity;
    public int USER_ID = 13299;
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
    /**
     * 收藏的表单的数据
     */
    private List<UserVideoCassificationFormBean.DataBean.VideoListBean> mFormList = new ArrayList<>();
    private RecyclerItemFormUserVideoClassificationAdapter mFormAdapter;
    /**
     * 收藏的影视的数据
     */
    private List<UserVideoClassificationVideoBean.DataBean.VideoBean> mVideoList = new ArrayList<>();
    private RecyclerItemVideoUserVideoClassificationAdapter mVideoAdapter;
    /**
     * 观影记录
     */
    private List<UserVideoPlayHistoryBean> mHistoryList = new ArrayList<>();
    private RecyclerItemHistoryUserVideoClassificationAdapter mHistoryAdapter;
    private LinearLayoutManager mLayoutManger;
    private BaseQuickAdapter mAdapter;
    private int mCurrPage = 1;
    private String mPageSize = "7";
    /**
     * 是否正在加载
     */
    private boolean isLoading;
    public String formTitle = "收藏片单";
    public String videoTitle = "收藏影视";
    public String hisoryTitle = "观影历史";
    /**
     * 标题
     */
    public String mTitle = "";
    private String delete_success = "删除成功";
    public List<Integer> mRemoveVideoList;
    private String delete_fail="未知原因删除失败";
    /**
     * 删除服务器影视收藏参数
     */
    public String mRemoveVideoParamas="";
    private String mRemoveFormParamas="";
    /**
     * 要删除的片单
     */
    private List<Integer> mRemoveFormList;
    private ArrayList<Integer> mRemoveHisoryList;
    public String mContent="没有选择要删除的内容";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_video_classification);
        ButterKnife.bind(this);
        initIntent();
        initTopBar();
        initSwiRefreshLayout();
        initView();
        initData(false);
        initHttpRetry();

    }
    /////////////////////////////////////////////////初始化View///////////////////////////////////////////////////////////////////////////
    private void initHttpRetry() {
        HttpLoadStateUtil.getInstance().setNetRetryListener(this);
    }

    private void initTopBar() {
        mTvChooseTitleBar.setVisibility(View.VISIBLE);
        AllUtils.getInstance().setTextBold(mTvTitleTitleBar);
    }

    private void initSwiRefreshLayout() {
        mSrLayoutUsrVideoClassificationActivity.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        mSrLayoutUsrVideoClassificationActivity.setOnRefreshListener(this);
        mSrLayoutUsrVideoClassificationActivity.post(new Runnable() {
            @Override
            public void run() {
                mSrLayoutUsrVideoClassificationActivity.setRefreshing(true);
            }
        });//进入刷新
    }

    private void initView() {
        switch (mType) {
            case "0"://片单
                initForm();
                break;
            case "1"://影视
                initVideo();
                break;
            case "2"://历史记录
                initHisory();
                break;
        }
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerViewUserVideoClassificationActivity.setLayoutManager(mLayoutManger);
        mRecyclerViewUserVideoClassificationActivity.getItemAnimator().setChangeDuration(0);
        if (type_video_hisory.equals(mType)) {
            mRecyclerViewUserVideoClassificationActivity.setAdapter(mHistoryAdapter);
        } else {
            mRecyclerViewUserVideoClassificationActivity.setAdapter(mAdapter);
            mAdapter.setOnLoadMoreListener(this, mRecyclerViewUserVideoClassificationActivity);
        }

    }
    private void initHisory() {
        mHistoryAdapter = new RecyclerItemHistoryUserVideoClassificationAdapter(R.layout.recycler_item_history, mHistoryList);
        mHistoryAdapter.setContext(this);
        mHistoryAdapter.setEnableLoadMore(false);
        mHistoryAdapter.setHasStableIds(true);
        mHistoryAdapter.setEmptyView(HttpLoadStateUtil.getInstance().setContextAndInitView(this));
        mAdapter = mHistoryAdapter;
        LinearLayoutManager historyLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManger = historyLayoutManager;
        mHistoryAdapter.setOnDeleteChooseAllChangeListener(this);
        mHistoryAdapter.setLayoutManager(historyLayoutManager);
    }
    private void initForm() {
        mFormAdapter = new RecyclerItemFormUserVideoClassificationAdapter(R.layout.recycler_item_user_video_form_detail, mFormList);
        mFormAdapter.setContext(this);
        mFormAdapter.setEnableLoadMore(true);
        mFormAdapter.setHasStableIds(true);
        mFormAdapter.setEmptyView(HttpLoadStateUtil.getInstance().setContextAndInitView(this));
        mAdapter = mFormAdapter;
        LinearLayoutManager formLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManger = formLayoutManager;
        mFormAdapter.setOnDeleteChooseAllChangeListener(this);
        mFormAdapter.setLayoutManager(formLayoutManager);
    }
    private void initVideo() {
        mVideoAdapter = new RecyclerItemVideoUserVideoClassificationAdapter(R.layout.recycler_foot_item_detail_video_fragment, mVideoList);
        mVideoAdapter.setContext(this);
        mVideoAdapter.setEnableLoadMore(true);
        mVideoAdapter.setHasStableIds(true);
        mVideoAdapter.setEmptyView(HttpLoadStateUtil.getInstance().setContextAndInitView(this));
        mAdapter = mVideoAdapter;
        LinearLayoutManager videoLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManger = videoLayoutManager;
        mVideoAdapter.setOnDeleteChooseAllChangeListener(this);
        mVideoAdapter.setLayoutManager(videoLayoutManager);
    }
    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mType = intent.getStringExtra("type");
        }
    }

/////////////////////////////////////////////////*初始化View*///////////////////////////////////////////////////////////////////////////









/////////////////////////////////////////////////加载数据///////////////////////////////////////////////////////////////////////////
    private void initData(boolean isLoadMore) {
        switch (mType) {
            case "0"://片单
                initFormData(isLoadMore);
                mTitle = formTitle;
                break;
            case "1"://影视
                initVideoData(isLoadMore);
                mTitle = videoTitle;
                break;
            case "2"://历史记录
                initHisoryData();
                mTitle = hisoryTitle;
                break;
        }
        if (!TextUtils.isEmpty(mTitle)) {
            mTvTitleTitleBar.setText(mTitle + "");
        }
    }
    /**
     * 设置请求参数
     *
     * @param isLoadMore 是否是加载更多
     * @return
     */
    @NonNull
    private LinkedHashMap<String, String> setHtttpParamas(boolean isLoadMore) {
        LinkedHashMap<String, String> paramas = new LinkedHashMap<>();
        paramas.put("user_id", USER_ID + "");
        if (isLoadMore) {
            mCurrPage++;
        }
        paramas.put("page", mCurrPage + "");
        paramas.put("page_size", mPageSize + "");
        return paramas;
    }
    private void initHisoryData() {
        SqlUtils.getInstance().setOnControlSqlFinishListener(new OnControlSqlFinishListener() {
            @Override
            public void onQuerySqlFinishListener(Object obj) {
                mHistoryList = (List<UserVideoPlayHistoryBean>) obj;
                mHistoryAdapter = (RecyclerItemHistoryUserVideoClassificationAdapter) mAdapter;
                if (mHistoryList != null && mHistoryList.size() > 0) {
                    mHistoryAdapter.setNewData(mHistoryList);
                } else {
                    HttpLoadStateUtil.getInstance().loadSateChangeNoContent();
                }
                mSrLayoutUsrVideoClassificationActivity.post(new Runnable() {
                    @Override
                    public void run() {
                        mSrLayoutUsrVideoClassificationActivity.setRefreshing(false);
                    }
                });
            }
        });
        SqlUtils.getInstance().queryVideoPlayHistory2Show(this);
    }
    /**
     * 加载收藏的影视
     *
     * @param isLoadMore
     */
    private void initVideoData(final boolean isLoadMore) {
        LinkedHashMap<String, String> paramas = setHtttpParamas(isLoadMore);
        HttpManager.getInstance().getCallWithParamas(AppUrl.VIDEO_USER_VIDEO_VIDEO, paramas, new ServerCallBack() {
            @Override
            public void responseSucessful(String json) {
                loadFinished();
                UserVideoClassificationVideoBean videoList = HttpManager.getInstance().getGson().fromJson(json, UserVideoClassificationVideoBean.class);
                if (videoList != null) {
                    if (videoList.data != null && videoList.data.video != null && videoList.data.video.size() > 0) {
                        List<UserVideoClassificationVideoBean.DataBean.VideoBean> video = videoList.data.video;
                        mVideoAdapter = (RecyclerItemVideoUserVideoClassificationAdapter) mAdapter;
                        if (!isLoadMore) {
                            mVideoList = video;
                            mVideoAdapter.setNewData(mVideoList);
                            mTvChooseTitleBar.setVisibility(View.VISIBLE);
                        } else {
                            mVideoAdapter.addData(video);
                            mVideoAdapter.loadMoreComplete();
                        }
                    } else {
                        if (isLoadMore) {
                            mVideoAdapter.loadMoreEnd();
                        } else {
                            HttpLoadStateUtil.getInstance().loadSateChangeNoContent();
                        }
                    }
                } else {
                    HttpLoadStateUtil.getInstance().loadSateChangeNoContent();
                }
            }
            @Override
            public void responseClientFailure(String json, int code) {
                HttpLoadStateUtil.getInstance().loadSateChange(false);
                loadFinished();
                if (isLoadMore) {
                    mCurrPage--;
                }
                mAdapter.loadMoreFail();
            }
            @Override
            public void responseServerFailure(String json, int code) {
                HttpLoadStateUtil.getInstance().loadSateChange(false);
                loadFinished();
                mAdapter.loadMoreFail();
                if (isLoadMore) {
                    mCurrPage--;
                }
            }
            @Override
            public void netWorkFailure(String error) {
                HttpLoadStateUtil.getInstance().loadSateChange(true);
                loadFinished();
                mAdapter.loadMoreFail();
                if (isLoadMore) {
                    mCurrPage--;
                }
            }
        }, this);
    }
    /**
     * 加载收藏的片单
     *
     * @param isLoadMore
     */
    private void initFormData(final boolean isLoadMore) {
        LinkedHashMap<String, String> paramas = setHtttpParamas(isLoadMore);
        HttpManager.getInstance().getCallWithParamas(AppUrl.VIDEO_USER_VIDEO_FORM, paramas, new ServerCallBack() {
            @Override
            public void responseSucessful(String json) {
                loadFinished();
                UserVideoCassificationFormBean formList = HttpManager.getInstance().getGson().fromJson(json, UserVideoCassificationFormBean.class);
                if (formList != null) {
                    if (formList.data != null && formList.data.video_list != null && formList.data.video_list.size() > 0) {
                        List<UserVideoCassificationFormBean.DataBean.VideoListBean> video_list = formList.data.video_list;
                        mFormAdapter = (RecyclerItemFormUserVideoClassificationAdapter) mAdapter;
                        if (!isLoadMore) {
                            mFormList = video_list;
                            mFormAdapter.setNewData(mFormList);
                        } else {
                            mFormAdapter.addData(video_list);
                            mFormAdapter.loadMoreComplete();
                        }
                    } else {
                        if (isLoadMore) {
                            mFormAdapter.loadMoreEnd();
                        } else {
                            HttpLoadStateUtil.getInstance().loadSateChangeNoContent();
                        }
                    }
                } else {
                    HttpLoadStateUtil.getInstance().loadSateChangeNoContent();
                }
            }

            @Override
            public void responseClientFailure(String json, int code) {
                HttpLoadStateUtil.getInstance().loadSateChange(false);
                loadFinished();
                if (isLoadMore) {
                    mCurrPage--;
                }
                mAdapter.loadMoreFail();
            }
            @Override
            public void responseServerFailure(String json, int code) {
                HttpLoadStateUtil.getInstance().loadSateChange(false);
                loadFinished();
                mAdapter.loadMoreFail();
                if (isLoadMore) {
                    mCurrPage--;
                }
            }
            @Override
            public void netWorkFailure(String error) {
                HttpLoadStateUtil.getInstance().loadSateChange(true);
                loadFinished();
                mAdapter.loadMoreFail();
                if (isLoadMore) {
                    mCurrPage--;
                }
            }
        }, this);
    }
/////////////////////////////////////////////////*加载数据*///////////////////////////////////////////////////////////////////////////







    /////////////////////////////////////////////////下拉刷新与加载更多、网络重试///////////////////////////////////////////////////////////////////////////
    private void loadFinished() {
        isLoading = false;
        mSrLayoutUsrVideoClassificationActivity.setRefreshing(false);
    }
    @Override
    public void onRefresh() {
        if (mAdapter.isLoading()) {//正在加载更多，则不刷新
            mSrLayoutUsrVideoClassificationActivity.setRefreshing(false);
            AllUtils.showToast(this, "正在加载更多");
            return;
        }
        if (!isLoading) {
            isLoading = true;
            mCurrPage = 1;
            initData(false);
        }
    }
    @Override
    public void onLoadMoreRequested() {
        if (mSrLayoutUsrVideoClassificationActivity.isRefreshing()) {//正在刷新，则不加载更多
            mAdapter.loadMoreComplete();
            return;
        }
        initData(true);
    }
    @Override
    public void netRetry() {
        initData(true);
    }

/////////////////////////////////////////////////*下拉刷新与加载更多、网络重试*///////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////点击事件///////////////////////////////////////////////////////////////////////////
    @OnClick({R.id.iv_return_title_bar, R.id.tv_delete_title_bar, R.id.tv_choose_title_bar, R.id.tv_choose_all_title_bar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return_title_bar:
                finish();
                break;
            case R.id.tv_delete_title_bar:
                showRemoveDialog();
                break;
            case R.id.tv_choose_title_bar:
                clickChooseTitle();
                break;
            case R.id.tv_choose_all_title_bar:
                if ("全选".equals(mTvChooseAllTitleBar.getText())) {
                    mTvChooseAllTitleBar.setText("全不选");
                    chooseAll(true);
                } else {
                    mTvChooseAllTitleBar.setText("全选");
                    chooseAll(false);
                }
                break;
        }
    }
    /////////////////////////////////////////////////*点击事件*///////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////topbar操作删除、全选、取消///////////////////////////////////////////////////////////////////////////
    /**
     * 点击切换选择、取消，以此来决定是否展示条目删除按钮
     */
    private void clickChooseTitle() {
        if ("选择".equals(mTvChooseTitleBar.getText())) {
            mTvChooseTitleBar.setText("取消");
            mTvDeleteTitleBar.setVisibility(View.VISIBLE);
            mTvChooseAllTitleBar.setVisibility(View.VISIBLE);
            mTvChooseAllTitleBar.setText("全选");
            mSrLayoutUsrVideoClassificationActivity.setEnabled(false);
            mAdapter.setEnableLoadMore(false);
            chooseClick(true);
        } else {
            mTvChooseTitleBar.setText("选择");
            mSrLayoutUsrVideoClassificationActivity.setEnabled(true);
            if (!type_video_hisory.equals(mType)) {
                mAdapter.setEnableLoadMore(true);
            }
            mTvDeleteTitleBar.setVisibility(View.INVISIBLE);
            mTvChooseAllTitleBar.setVisibility(View.INVISIBLE);
            chooseClick(false);
        }
    }
    /**
     * 是否将条目进行全选
     *
     * @param chooseAll
     */
    private void chooseAll(boolean chooseAll) {
        switch (mType) {
            case "0"://片单
                formChooseDeleteAll(chooseAll);
                break;
            case "1"://影视
                videoChooseDeleteAll(chooseAll);
                break;
            case "2"://历史记录
                historyChooseDeleteAll(chooseAll);
                break;
        }
    }
    /**
     * 判断选择是否被点击，
     *
     * @param isChooseClick true显示条目的删除 false不显示
     */
    public void chooseClick(boolean isChooseClick) {
        switch (mType) {
            case "0"://片单
                mFormAdapter = (RecyclerItemFormUserVideoClassificationAdapter) mAdapter;
                //每次切换选择与取消要刷新bean不然会展示切换前的状态
                List<UserVideoCassificationFormBean.DataBean.VideoListBean> videoListBeanList = mFormAdapter.getData();
                if (videoListBeanList != null && videoListBeanList.size()>0) {
                    for (UserVideoCassificationFormBean.DataBean.VideoListBean videoListBean : videoListBeanList) {
                        videoListBean.isSelcted = false;
                        videoListBean.allChooseFalse = false;
                        videoListBean.allChooseTrue = false;
                    }
                }

                mFormAdapter.setShowDelete(isChooseClick);
                break;
            case "1"://影视
                mVideoAdapter = (RecyclerItemVideoUserVideoClassificationAdapter) mAdapter;
                List<UserVideoClassificationVideoBean.DataBean.VideoBean> videolist = mVideoAdapter.getData();
                if (mVideoList != null && mVideoList.size() > 0) {
                    for (UserVideoClassificationVideoBean.DataBean.VideoBean videoBean : mVideoList) {
                        videoBean.isSelcted = false;
                        videoBean.allChooseFalse = false;
                        videoBean.allChooseTrue = false;
                    }
                }
                mVideoAdapter.setShowDelete(isChooseClick);
                break;
            case "2"://历史记录
                mHistoryAdapter = (RecyclerItemHistoryUserVideoClassificationAdapter) mAdapter;
                mHistoryAdapter.setShowDelete(isChooseClick);
                break;
            default:
                break;
        }
    }
    /**影视记录是否被全选、全不选删除
     * @param chooseAll true 全选 false 全不选
     */
    private void historyChooseDeleteAll(boolean chooseAll) {
        if (mHistoryList != null && mHistoryList.size() > 0) {
            mHistoryAdapter.setChooseAll(chooseAll);
            for (UserVideoPlayHistoryBean userVideoPlayHistoryBean : mHistoryList) {
                if (chooseAll) {
                    userVideoPlayHistoryBean.allChooseTrue = false;
                    userVideoPlayHistoryBean.isSelcted = true;
                } else {
                    userVideoPlayHistoryBean.allChooseFalse = false;
                    userVideoPlayHistoryBean.isSelcted = false;
                }
            }
            mHistoryAdapter.notifyDataSetChanged();
        }

    }
    /**影视收藏是否被全选、全不选删除
     * @param chooseAll
     */
    private void videoChooseDeleteAll(boolean chooseAll) {
        if (mVideoList != null && mVideoList.size() > 0) {
            mVideoAdapter.setChooseAll(chooseAll);
            for (UserVideoClassificationVideoBean.DataBean.VideoBean videoBean : mVideoList) {
                if (chooseAll) {
                    videoBean.allChooseTrue = false;
                    videoBean.isSelcted = true;
                } else {
                    videoBean.allChooseFalse = false;
                    videoBean.isSelcted = false;
                }
            }
            mVideoAdapter.notifyDataSetChanged();
        }
    }
    /**片单收藏是否被全选、全不选删除
     * @param chooseAll
     */
    private void formChooseDeleteAll(boolean chooseAll) {
        if (mFormList != null && mFormList.size() > 0) {
            mFormAdapter.setChooseAll(chooseAll);
            for (UserVideoCassificationFormBean.DataBean.VideoListBean videoListBean : mFormList) {
                if (chooseAll) {
                    videoListBean.allChooseTrue = false;
                    videoListBean.isSelcted = true;
                } else {
                    videoListBean.allChooseFalse = false;
                    videoListBean.isSelcted = false;
                }
            }
            mFormAdapter.notifyDataSetChanged();
        }
    }
    //根据用户是否全选与全不选，来切换对应文字
    @Override
    public void showChooseAllTrueTitle() {
        mTvChooseAllTitleBar.setText("全选");
    }
    @Override
    public void showChooseAllFalseTitle() {
        mTvChooseAllTitleBar.setText("全不选");
    }
    /**
     * 如果收藏全部删除则隐藏topbar三个操作文字
     */
    private void hideTitleTextView() {
        mTvChooseAllTitleBar.setVisibility(View.INVISIBLE);
        mTvChooseTitleBar.setVisibility(View.INVISIBLE);
        mTvDeleteTitleBar.setVisibility(View.INVISIBLE);
        HttpLoadStateUtil.getInstance().loadSateChangeNoContent();
    }
    /////////////////////////////////////////////////*topbar操作删除、全选、取消*///////////////////////////////////////////////////////////////////////////



/////////////////////////////////////////////////删除收藏///////////////////////////////////////////////////////////////////////////
    /**
     * 请求服务器删除服务端收藏影视
     */
    public void removeVideoToServer() {
       HashMap<String, String> paramas = new HashMap<>();
        paramas.put("user_id", USER_ID + "");
        paramas.put("video_id", mRemoveVideoParamas + "");
        HttpManager.getInstance().postCall(AppUrl.VIDEO_USER_REMOVE_USER_COLLECTION_VIDEO,  new ServerCallBack() {
            @Override
            public void responseSucessful(String json) {
                removeVideoList();
                AllUtils.showToast(UserVideoClassificationActivity.this, delete_success);
            }
            @Override
            public void responseClientFailure(String json, int code) {
                AllUtils.showToast(UserVideoClassificationActivity.this, delete_fail);
            }
            @Override
            public void responseServerFailure(String json, int code) {
                AllUtils.showToast(UserVideoClassificationActivity.this, delete_fail);
            }
            @Override
            public void netWorkFailure(String error) {
                AllUtils.showToast(UserVideoClassificationActivity.this, delete_fail);
            }
        }, paramas,this);
    }
    /**
     * 请求服务器删除服务端收藏片单
     */
    public void removeFormToServer() {
        HashMap<String, String> paramas = new HashMap<>();
        paramas.put("user_id", USER_ID + "");
        paramas.put("list_id", mRemoveFormParamas + "");
        HttpManager.getInstance().postCall(AppUrl.VIDEO_USER_REMOVE_USER_COLLECTION_FORM,  new ServerCallBack() {
            @Override
            public void responseSucessful(String json) {
                removeFormList();
                AllUtils.showToast(UserVideoClassificationActivity.this, delete_success);
            }
            @Override
            public void responseClientFailure(String json, int code) {
                AllUtils.showToast(UserVideoClassificationActivity.this, delete_fail);
            }
            @Override
            public void responseServerFailure(String json, int code) {
                AllUtils.showToast(UserVideoClassificationActivity.this, delete_fail);
            }
            @Override
            public void netWorkFailure(String error) {
                AllUtils.showToast(UserVideoClassificationActivity.this, delete_fail);
            }
        }, paramas,this);
    }

    /**
     * 删除本地影视记录
     */
    public void removeHistoryList(){
        boolean deleteResult = false;
        if (mRemoveHisoryList != null && mRemoveHisoryList.size()>0 && mHistoryList!=null && mHistoryList.size()>0 && mHistoryList.size()>=mRemoveHisoryList.size()) {
            LogUtil.w("VIDEO 删除个数：", mRemoveHisoryList.size()+"   ，，，，，总个数"+mHistoryList.size());
            if ("全不选".equals(mTvChooseAllTitleBar.getText())) {
                for (UserVideoPlayHistoryBean userVideoPlayHistoryBean : mHistoryList) {
                    deleteResult = SqlUtils.getInstance().deleteVideoPlayHisory(userVideoPlayHistoryBean);
                }
                mHistoryList = new ArrayList<>();
                hideTitleTextView();
            }else {
                for (int i = 0; i < mRemoveHisoryList.size(); i++) {
                    int key = mRemoveHisoryList.get(i)-i;
                    UserVideoPlayHistoryBean bean = mHistoryList.get(key);
                    mHistoryList.remove(key);
                    deleteResult = SqlUtils.getInstance().deleteVideoPlayHisory(bean);
                }
            }
            if (deleteResult) {
                AllUtils.showToast(this,delete_success);
            }else {
                AllUtils.showToast(this,delete_fail);
            }
            if (mHistoryList.size()>0) {
                for (UserVideoPlayHistoryBean userVideoPlayHistoryBean : mHistoryList) {
                    userVideoPlayHistoryBean.isSelcted = false;
                    userVideoPlayHistoryBean.allChooseFalse = false;
                    userVideoPlayHistoryBean.allChooseTrue = false;
                }
            }
            mHistoryAdapter.setNewData(mHistoryList);
            LogUtil.w("VIDEO 删除个数：", mRemoveHisoryList.size()+"   ，，，，，总个数"+mHistoryList.size());
        }

    }
    /**
     * 删除用户要删除的影视
     */
    public void removeVideoList() {
        if (mRemoveVideoList != null && mRemoveVideoList.size()>0 && mVideoList !=null && mVideoList.size()>0 && mVideoList.size()>=mRemoveVideoList.size()) {
            LogUtil.w("VIDEO11111111", mVideoList.size()+"   ，，，，，"+mRemoveVideoList.size());
            if ("全不选".equals(mTvChooseAllTitleBar.getText())) {
                mVideoList = new ArrayList<>();
                hideTitleTextView();

            }else {
                for (int i = 0; i < mRemoveVideoList.size(); i++) {
                    int key = mRemoveVideoList.get(i);
                    mVideoList.remove(key-i);
                }
            }
            if (mVideoList.size()>0) {
                for (UserVideoClassificationVideoBean.DataBean.VideoBean videoBean : mVideoList) {
                    videoBean.allChooseFalse = false;
                    videoBean.allChooseTrue = false;
                    videoBean.isSelcted = false;
                }
            }
            mVideoAdapter.setNewData(mVideoList);


            LogUtil.w("VIDEO", mVideoList.size()+""+"     "+mRemoveVideoList.size());
        }
    }
    /**
     * 删除用户要删除的片单
     */
    public void removeFormList() {
        if (mRemoveFormList != null && mRemoveFormList.size()>0 && mFormList !=null && mFormList.size()>0 && mFormList.size()>=mRemoveFormList.size()) {
            LogUtil.w("VIDEO11111111", mFormList.size()+"   ，，，，，"+mRemoveFormList.size());
            if ("全不选".equals(mTvChooseAllTitleBar.getText())) {
                mFormList = new ArrayList<>();
                hideTitleTextView();
            }else {
                for (int i = 0; i < mRemoveFormList.size(); i++) {
                    int key = mRemoveFormList.get(i);
                    mFormList.remove(key-i);
                }
            }
            if (mFormList.size()>0) {
                for (UserVideoCassificationFormBean.DataBean.VideoListBean videoListBean : mFormList) {
                    videoListBean.isSelcted = false;
                    videoListBean.allChooseFalse = false;
                    videoListBean.allChooseTrue = false;
                }
            }
            mFormAdapter.setNewData(mFormList);

            LogUtil.w("VIDEO", mFormList.size()+""+"     "+mRemoveFormList.size());
        }
    }
    /**获取要删除的影视id
     * @return
     */
    public String getRemoveVideoParams() {
        StringBuilder sb = new StringBuilder();
        mRemoveVideoParamas = "";
        mRemoveVideoList = new ArrayList<>();
        if (mVideoList != null && mVideoList.size() > 0) {
            for (int i = 0; i < mVideoList.size(); i++) {
                UserVideoClassificationVideoBean.DataBean.VideoBean videoBean = mVideoList.get(i);
                if (videoBean.isSelcted) {
                    sb.append(videoBean.content_id + ",");
                    mRemoveVideoList.add(i);
                }
            }
            mRemoveVideoParamas = sb.toString();
            if (mRemoveVideoParamas.length()>0) {
                mRemoveVideoParamas = mRemoveVideoParamas.substring(0, mRemoveVideoParamas.length() - 1);
            }
        }
        return mRemoveVideoParamas;
    }

    /**获取要删除的影视记录List
     * @return
     */
    public List<Integer> getRemoveHistoryList(){
        mRemoveHisoryList = new ArrayList<>();
        if (mHistoryList != null && mHistoryList.size() > 0) {
            for (int i = 0; i < mHistoryList.size(); i++) {
                UserVideoPlayHistoryBean hisoryBean = mHistoryList.get(i);
                if (hisoryBean.isSelcted) {
                    mRemoveHisoryList.add(i);
                }
            }
           return mRemoveHisoryList;
        }
        return mRemoveHisoryList;
    }
    /**获取要删除片单id
     * @return
     */
    public String getRemoveFormParams() {
        StringBuilder sb = new StringBuilder();
        mRemoveFormParamas = "";
        mRemoveFormList = new ArrayList<>();
        if (mFormList != null && mFormList.size() > 0) {
            for (int i = 0; i < mFormList.size(); i++) {
                UserVideoCassificationFormBean.DataBean.VideoListBean videoListBean = mFormList.get(i);
                if (videoListBean.isSelcted) {
                    sb.append(videoListBean.content_id + ",");
                    mRemoveFormList.add(i);
                }
            }
            mRemoveFormParamas = sb.toString();
            if (mRemoveFormParamas.length()>0) {
                mRemoveFormParamas = mRemoveFormParamas.substring(0, mRemoveFormParamas.length() - 1);
            }
        }
        return mRemoveFormParamas;
    }
    /**
     * 是否弹出删除确认对话框
     */
    private void showRemoveDialog() {
        switch (mType) {
            case "0"://片单
                getRemoveFormParams();
                if (mRemoveFormList == null || mRemoveFormList.size()==0) {
                    AllUtils.showToast(this, mContent);
                    return;
                }else {
                    removeDialog();
                }
                break;
            case "1"://影视
                getRemoveVideoParams();
                if (mRemoveVideoList == null || mRemoveVideoList.size()==0) {
                    AllUtils.showToast(this,mContent);
                    return;
                }else {
                    removeDialog();
                }
                break;
            case "2"://历史记录
                getRemoveHistoryList();
                if (mRemoveHisoryList == null || mRemoveHisoryList.size()==0) {
                    AllUtils.showToast(this,mContent);
                    return;
                }else {
                    removeDialog();
                }
                break;
            default:
                break;
        }

    }
    /**
     * 确认删除对话框
     */
    private void removeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定删除所选收藏吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (mType) {
                    case "0"://片单
                        removeFormToServer();
                        break;
                    case "1"://影视
                        removeVideoToServer();
                        break;
                    case "2"://历史记录
                        removeHistoryList();
                        break;
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
/////////////////////////////////////////////////*删除收藏*///////////////////////////////////////////////////////////////////////////


}