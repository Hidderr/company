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
import com.example.alan.myapplication.alan.adapter.vp.recycler.uservideo.RecyclerItemUserVideoAdapter;
import com.example.alan.myapplication.alan.bean.UserVideoBean;
import com.example.alan.myapplication.alan.bean.UserVideoPlayHistoryBean;
import com.example.alan.myapplication.alan.bean.UserVideoRootBean;
import com.example.alan.myapplication.alan.bean.VideoDetailBean;
import com.example.alan.myapplication.alan.constants.AppUrl;
import com.example.alan.myapplication.alan.http.HttpLoadStateUtil;
import com.example.alan.myapplication.alan.http.HttpManager;
import com.example.alan.myapplication.alan.http.ServerCallBack;
import com.example.alan.myapplication.alan.listener.OnControlSqlFinishListener;
import com.example.alan.myapplication.alan.utils.AllUtils;
import com.example.alan.myapplication.alan.utils.SqlUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alan on 2018/1/31.
 * 功能：我的影视
 */

public class UserVideoActivity extends AutoLayoutActivity implements SwipeRefreshLayout.OnRefreshListener ,View.OnClickListener{

    @Bind(R.id.recycler_view_user_activity)
    RecyclerView mRecyclerViewUserActivity;
    @Bind(R.id.sr_layout_user_activity)
    SwipeRefreshLayout mSrLayoutUserActivity;
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
     * 是否正在加载数据
     */
    private boolean isLoding;
    /**
     * 根Recycler的数据
     */
    private List<UserVideoRootBean.RooBean> mItemDataList = new ArrayList<>();
    private RecyclerItemUserVideoAdapter mRootRecyclerItemAdapter;
    /**
     * 用户ID，没登录传-1
     */
    private int userId = 13299;
    /**
     * 是否有观影历史
     */
    private int watch_history;
    public List<UserVideoPlayHistoryBean> mVideoHistoryList;
    public String mName = "收藏片单";
    public String mName1 = "收藏影视";
    public String mName2 = "观影历史";
    public String mName3 = "你或许喜欢";
    private String mTitle = "我的影视";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_user_video);
        ButterKnife.bind(this);
        initTopBar();
        initSwipRefreshLayout();
        testDataSql();
        initRecyclerView();
        LoadData(false);
    }

    private void initTopBar() {
        AllUtils.getInstance().setTextBold(mTvTitleTitleBar);
        mIvReturnTitleBar.setOnClickListener(this);
        mTvTitleTitleBar.setText(mTitle+"");
    }

    private void testDataSql() {
        String title = "电影";
        String image = "https://file03.xgimi.com/wpzs/201707/19/596ed5b33cfa675181.jpeg";
        String category = "电影";
        int duration = 3600;
        String year = "0";
        String playsource = "芒果Tv";
        String sourceicon = "https://file03.xgimi.com/wpzs/app/icon/lizhi.png";
        String video_id = "aff12451264b8192907bbdc9d68eac2a";

        for (int i = 0; i < 8; i++) {
            VideoDetailBean.DataBean data = new VideoDetailBean.DataBean();
            data.title = title;
            data.image = image;
            data.category = category;
            data.duration = duration;
            data.year = year + i;
            SqlUtils.getInstance().addPlayHistory2Sql(video_id + i, playsource, sourceicon, data, this);
        }
    }

    private void LoadData(final boolean isRefresh) {
        SqlUtils.getInstance().setOnControlSqlFinishListener(new OnControlSqlFinishListener() {
            @Override
            public void onQuerySqlFinishListener(Object obj) {
                mVideoHistoryList = (List<UserVideoPlayHistoryBean>) obj;
                if (mVideoHistoryList == null || mVideoHistoryList.size() == 0) {
                    watch_history = 2;
                    mVideoHistoryList = null;
                } else {
                    watch_history = 1;
                }
                loadDataFromServer(isRefresh);
            }
        });
        SqlUtils.getInstance().queryVideoPlayHistory2Show(this);
    }

    /**
     * 从服务器获取收藏影视和收藏片单
     *
     * @param isRefresh
     */
    private void loadDataFromServer(final boolean isRefresh) {
        LinkedHashMap<String, String> paramas = new LinkedHashMap<>();
        paramas.put("user_id", userId + "");
        paramas.put("watch_history", watch_history + "");
        HttpManager.getInstance().getCallWithParamas(AppUrl.VIDEO_USER_VIDEO, paramas, new ServerCallBack() {
            @Override
            public void responseSucessful(String json) {
                loadFinished();
                UserVideoBean userVideoBean = HttpManager.getInstance().getGson().fromJson(json, UserVideoBean.class);
                if (userVideoBean != null) {
                    layoutRecyclerByData(userVideoBean);
                } else {
                    HttpLoadStateUtil.getInstance().loadSateChangeNoContent();
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

    /**
     * 动态改变根RecyclerView的布局,以及刷新数据
     *
     * @param userVideoBean
     */
    private void layoutRecyclerByData(UserVideoBean userVideoBean) {
        if (userVideoBean.data != null) {
            mItemDataList = new ArrayList<>();
            UserVideoBean.DataBean data = userVideoBean.data;
            UserVideoBean.DataBean.CollectVideoListBean collect_video_list = data.collect_video_list;
            UserVideoBean.DataBean.CollectVideoBean collect_video = data.collect_video;
            List<UserVideoBean.DataBean.RecommendVideoBean> recommend_video = data.recommend_video;
            if (collect_video_list != null && collect_video_list.video_list != null && collect_video_list.video_list.size() > 0) {
                mItemDataList.add(new UserVideoRootBean.RooBean(mName));
            }
            if (collect_video_list != null && collect_video.collect != null && collect_video.collect.size() > 0) {
                mItemDataList.add(new UserVideoRootBean.RooBean(mName1));
            }
            if (mVideoHistoryList != null && mVideoHistoryList.size() > 0) {
                mItemDataList.add(new UserVideoRootBean.RooBean(mName2));
            }
            if (mVideoHistoryList == null && (collect_video == null || collect_video.collect == null || collect_video.collect.size() == 0)) {
                mItemDataList.add(new UserVideoRootBean.RooBean(mName3));
            }
            mRootRecyclerItemAdapter.setData(data, mVideoHistoryList);
            mRootRecyclerItemAdapter.setRootData(mItemDataList);
            mRootRecyclerItemAdapter.setNewData(mItemDataList);
            mRootRecyclerItemAdapter.notifyDataSetChanged();
        }
    }


    private void initRecyclerView() {
        mRootRecyclerItemAdapter = new RecyclerItemUserVideoAdapter(R.layout.a_recycler_item_root_video_fragment, mItemDataList);
        mRootRecyclerItemAdapter.setContext(this);
        mRootRecyclerItemAdapter.setEnableLoadMore(false);
        mRootRecyclerItemAdapter.setHasStableIds(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewUserActivity.setLayoutManager(linearLayoutManager1);
        mRecyclerViewUserActivity.getItemAnimator().setChangeDuration(0);
        mRecyclerViewUserActivity.setAdapter(mRootRecyclerItemAdapter);
        mRootRecyclerItemAdapter.setEmptyView(HttpLoadStateUtil.getInstance().setContextAndInitView(this));
    }


    private void initSwipRefreshLayout() {
        mSrLayoutUserActivity.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        mSrLayoutUserActivity.setOnRefreshListener(this);
        refresh();
    }

    private void refresh() {
        mSrLayoutUserActivity.post(new Runnable() {
            @Override
            public void run() {
                mSrLayoutUserActivity.setRefreshing(true);
            }
        });//进入刷新
    }


    @Override
    public void onRefresh() {
        if (!isLoding) {
            isLoding = true;
            LoadData(true);
        }
    }


    public void loadFinished() {
        isLoding = false;
        mSrLayoutUserActivity.setRefreshing(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        AllUtils.getInstance().hasStart = false;
        LoadData(true);//从收藏详情的Activity返回要刷新数据

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
