package com.example.alan.myapplication.alan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.recycler.OnRecyclerViewItemListener;
import com.example.alan.myapplication.alan.adapter.vp.recycler.RecyclerItemVideoAreaScreeningHeaderAdapter;
import com.example.alan.myapplication.alan.adapter.vp.recycler.RecyclerItemVideoScreeningResultActivityAdapter;
import com.example.alan.myapplication.alan.adapter.vp.recycler.RecyclerItemVideoTypeScreeningHeaderAdapter;
import com.example.alan.myapplication.alan.adapter.vp.recycler.RecyclerItemVideoYearScreeningHeaderAdapter;
import com.example.alan.myapplication.alan.bean.JsonConvertUtils;
import com.example.alan.myapplication.alan.bean.VideoScreeningActivityHeaderBean;
import com.example.alan.myapplication.alan.bean.VideoScreeningResultBean;
import com.example.alan.myapplication.alan.constants.AppUrl;
import com.example.alan.myapplication.alan.http.HttpLoadStateUtil;
import com.example.alan.myapplication.alan.http.HttpManager;
import com.example.alan.myapplication.alan.http.ServerCallBack;
import com.example.alan.myapplication.alan.utils.AllUtils;
import com.example.alan.myapplication.alan.view.DividerItemDecoration;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.alan.myapplication.alan.global.GlobalApplication.context;

/**
 * Created by Alan on 2018/1/29.
 * 功能：影视筛选
 */

public class VideoScreeningActivity extends AutoLayoutActivity implements  BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener {

    public RecyclerView mTypeRecycler;
    public RecyclerView mYearRecycler;
    public RecyclerView mAreaRecycler;
    @Bind(R.id.recyclerview_video_screening_activity)
    RecyclerView mRecyclerviewVideoScreeningActivity;
    @Bind(R.id.sr_layout_video_screening_activity)
    SwipeRefreshLayout mSrLayoutVideoScreeningActivity;
    private List<VideoScreeningActivityHeaderBean.DataBean.TypeBean> mTypeBeanList;
    private List<VideoScreeningActivityHeaderBean.DataBean.YearBean> mYearBeanList;
    private List<VideoScreeningActivityHeaderBean.DataBean.AreaBean> mAreaBeanList;
    public RecyclerView.SmoothScroller mSmoothScroller;
    private List<VideoScreeningResultBean.DataBean> mItemDataBeanList;
    public View mHeaderView;

    //筛选条件
    public int mType;
    private int mCurrPage=1;
    private String mPageSize="21";
    private String mKind="";
    private String mArea="";
    private String mYear="";
    /**
     * 是否正在加载数据
     */
    public boolean isLoading = false;
    public RecyclerItemVideoScreeningResultActivityAdapter mItemAdapter;
    public RecyclerItemVideoAreaScreeningHeaderAdapter mAreaAdpater;
    public RecyclerItemVideoYearScreeningHeaderAdapter mYearAdpater;
    public RecyclerItemVideoTypeScreeningHeaderAdapter mTypeAdpater;
    public String mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_screening);
        ButterKnife.bind(this);
        initIntent();
        initSwiRefreshLayout();
        initHeader();
        initItem();
        loadData(false);

    }

    private void loadData(boolean loadMore) {
        loadHeadData();
        loadItemData(loadMore);
    }



    private void loadItemData(final boolean loadMore) {
        LinkedHashMap<String, String> paramas = new LinkedHashMap<>();
        paramas.put("category", mTitle+"");
        if (loadMore) {
            mCurrPage++;
        }
        paramas.put("page",mCurrPage +"");
        paramas.put("page_size",mPageSize +"");
        paramas.put("kind",mKind +"");
        paramas.put("area",mArea +"");
        paramas.put("year",mYear +"");
        HttpManager.getInstance().getCallWithParamas(AppUrl.VIDEO_CLASSIFICATION_RESULT, paramas, new ServerCallBack() {
            /**
             * @param json
             */
            @Override
            public void responseSucessful(String json) {
                VideoScreeningResultBean resultBean = JsonConvertUtils.AESjson2Class(json,VideoScreeningResultBean.class);
                if (resultBean != null) {
                    if (resultBean.data != null && resultBean.data.size()>0) {
                        List<VideoScreeningResultBean.DataBean> data = resultBean.data;
                        if (loadMore) {
                            mItemAdapter.addData(data);
                        }else {
                            mItemAdapter.setNewData(data);
                        }
                        mItemAdapter.loadMoreComplete();
                    }else {
                        HttpLoadStateUtil.getInstance().loadSateChange(false);
                        if (loadMore) {
                            mItemAdapter.loadMoreEnd();
                        }
                    }
                }
                allCallFinished();
            }

            @Override
            public void responseClientFailure(String json, int code) {
                allCallFinished();
                loadFinishAndChangeLoadState(false,loadMore);
            }

            @Override
            public void responseServerFailure(String json, int code) {
                allCallFinished();
                loadFinishAndChangeLoadState(false,loadMore);
            }

            @Override
            public void netWorkFailure(String error) {
                allCallFinished();
                loadFinishAndChangeLoadState(true,loadMore);
            }
        }, this);
    }



    private void loadHeadData() {
        LinkedHashMap<String, String> paramas = new LinkedHashMap<>();
        paramas.put("category_id",mType +"");
        HttpManager.getInstance().getCallWithParamas(AppUrl.VIDEO_CLASSIFICATION_CONDITION_GET, paramas, new ServerCallBack() {
            @Override
            public void responseSucessful(String json) {
                VideoScreeningActivityHeaderBean headerBean = JsonConvertUtils.AESjson2Class(json,VideoScreeningActivityHeaderBean.class);
                        if (headerBean != null) {
                            if (headerBean.data != null) {
                                VideoScreeningActivityHeaderBean.DataBean dataBean = headerBean.data;
                                List<VideoScreeningActivityHeaderBean.DataBean.TypeBean> type =dataBean.type;
                                if (type != null && type.size()>0) {
                                    VideoScreeningActivityHeaderBean.DataBean.TypeBean typeBean =  new VideoScreeningActivityHeaderBean.DataBean.TypeBean();
                                    typeBean.setName("全部");
                                    type.add(0,typeBean);
                                    mTypeAdpater.setNewData(type);
                                }else {
                                    mTypeRecycler.setVisibility(View.GONE);
                                }

                                List<VideoScreeningActivityHeaderBean.DataBean.YearBean> year=dataBean.year;
                                if (year != null && year.size()>0) {
                                    VideoScreeningActivityHeaderBean.DataBean.YearBean yearBean =  new VideoScreeningActivityHeaderBean.DataBean.YearBean();
                                    yearBean.setName("全部");
                                    year.add(0,yearBean);
                                    mYearAdpater.setNewData(year);
                                }else {
                                    mYearRecycler.setVisibility(View.GONE);
                                }

                              List<VideoScreeningActivityHeaderBean.DataBean.AreaBean> area=dataBean.area;
                                if (area != null && area.size()>0) {
                                    VideoScreeningActivityHeaderBean.DataBean.AreaBean areaBean =  new VideoScreeningActivityHeaderBean.DataBean.AreaBean();
                                    areaBean.setName("全部");
                                    area.add(0,areaBean);
                                    mAreaAdpater.setNewData(area);
                                }else {
                                    mAreaRecycler.setVisibility(View.GONE);
                                }

                        }else {

                            }
                }
                allCallFinished();
            }

            @Override
            public void responseClientFailure(String json, int code) {
                allCallFinished();
            }

            @Override
            public void responseServerFailure(String json, int code) {
                allCallFinished();
            }

            @Override
            public void netWorkFailure(String error) {
                allCallFinished();
            }
        }, this);
    }


    private void initSwiRefreshLayout() {
        mSrLayoutVideoScreeningActivity.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        mSrLayoutVideoScreeningActivity.setOnRefreshListener(this);
        mSrLayoutVideoScreeningActivity.post(new Runnable() {
            @Override
            public void run() {
                mSrLayoutVideoScreeningActivity.setRefreshing(true);
            }
        });//进入刷新

    }


    private void initItem() {
        mItemAdapter = new RecyclerItemVideoScreeningResultActivityAdapter(R.layout.recycler_item_video_screening, mItemDataBeanList);
        mItemAdapter.setContext(this);
        mItemAdapter.setEnableLoadMore(true);
        mItemAdapter.setHasStableIds(true);
        mItemAdapter.addHeaderView(mHeaderView);
        mItemAdapter.setHeaderAndEmpty(true);
        mItemAdapter.setEmptyView(HttpLoadStateUtil.getInstance().setContextAndInitView(this));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerviewVideoScreeningActivity.setLayoutManager(gridLayoutManager);
        mRecyclerviewVideoScreeningActivity.getItemAnimator().setChangeDuration(0);
        mRecyclerviewVideoScreeningActivity.addItemDecoration(new DividerItemDecoration(this,10,0xffffffff));
        mRecyclerviewVideoScreeningActivity.setAdapter(mItemAdapter);
        mItemAdapter.setOnLoadMoreListener(this, mRecyclerviewVideoScreeningActivity);
        
    }
    
    


    /**
     * 初始化筛选条件
     */
    private void initHeader() {
        initHeaderView();
        initType();
        initYear();
        initArea();
    }

    private void initHeaderView() {
        mHeaderView = View.inflate(this, R.layout.activity_video_screening_header, null);
        mTypeRecycler = (RecyclerView) mHeaderView.findViewById(R.id.recyclerview1_recycler_item_header_video_screening_activity);
        mYearRecycler = (RecyclerView) mHeaderView.findViewById(R.id.recyclerview2_recycler_item_header_video_screening_activity);
        mAreaRecycler = (RecyclerView) mHeaderView.findViewById(R.id.recyclerview3_recycler_item_header_video_screening_activity);

    }


    private void initArea() {
        mAreaAdpater = new RecyclerItemVideoAreaScreeningHeaderAdapter(R.layout.recycler_header_item_video_screening_activity, mAreaBeanList);
        mAreaAdpater.setContext(context);
        mAreaAdpater.setEnableLoadMore(false);
        mAreaAdpater.setHasStableIds(true);

        final LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mAreaRecycler.setLayoutManager(linearLayoutManager3);
        mAreaRecycler.getItemAnimator().setChangeDuration(0);
        mAreaRecycler.setAdapter(mAreaAdpater);
        final RecyclerView.SmoothScroller areaScroller = new LinearSmoothScroller(context) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        mAreaAdpater.setOnRecyclerViewItemListener(new OnRecyclerViewItemListener() {
            @Override
            public void OnRecyclerViewItemListener(int position,String condition) {
                if (linearLayoutManager3.findLastVisibleItemPosition() == position) {
                    areaScroller.setTargetPosition(position);
                    linearLayoutManager3.startSmoothScroll(areaScroller);
                }
                mArea = position==0?"":condition;
                loadItemData(false);
            }
        });
    }


    private void initYear() {
        mYearAdpater = new RecyclerItemVideoYearScreeningHeaderAdapter(R.layout.recycler_header_item_video_screening_activity, mYearBeanList);
        mYearAdpater.setContext(context);
        mYearAdpater.setEnableLoadMore(false);
        mYearAdpater.setHasStableIds(true);

        final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mYearRecycler.setLayoutManager(linearLayoutManager2);
        mYearRecycler.getItemAnimator().setChangeDuration(0);
        mYearRecycler.setAdapter(mYearAdpater);

        final RecyclerView.SmoothScroller yearScroller = new LinearSmoothScroller(context) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        mYearAdpater.setOnRecyclerViewItemListener(new OnRecyclerViewItemListener() {
            @Override
            public void OnRecyclerViewItemListener(int position,String condition) {
                if (linearLayoutManager2.findLastVisibleItemPosition() == position) {
                    yearScroller.setTargetPosition(position);
                    linearLayoutManager2.startSmoothScroll(yearScroller);
                }

                mYear = position==0?"":condition;
                loadItemData(false);
            }
        });
    }


    private void initType() {
        mTypeAdpater = new RecyclerItemVideoTypeScreeningHeaderAdapter(R.layout.recycler_header_item_video_screening_activity, mTypeBeanList);
        mTypeAdpater.setContext(context);
        mTypeAdpater.setEnableLoadMore(false);
        mTypeAdpater.setHasStableIds(true);

        final LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mTypeRecycler.setLayoutManager(linearLayoutManager1);
        mTypeRecycler.getItemAnimator().setChangeDuration(0);
        mTypeRecycler.setAdapter(mTypeAdpater);

        final RecyclerView.SmoothScroller typeScroller = new LinearSmoothScroller(context) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        mTypeAdpater.setOnRecyclerViewItemListener(new OnRecyclerViewItemListener() {
            @Override
            public void OnRecyclerViewItemListener(int position,String condition) {
                if (linearLayoutManager1.findLastVisibleItemPosition() == position) {
                    typeScroller.setTargetPosition(position);
                    linearLayoutManager1.startSmoothScroll(typeScroller);
                }

                mKind = position==0?"":condition;
                loadItemData(false);
            }
        });

    }


    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra("title");
            mType = intent.getIntExtra("type", -1);
        }
    }

    @Override
    public void onRefresh() {
        if (mItemAdapter.isLoading()) {//正在加载更多，则不刷新
            mSrLayoutVideoScreeningActivity.setRefreshing(false);
            AllUtils.showToast(this,"正在加载更多");
            return;
        }
        if (!isLoading) {
            isLoading = true;
            mCurrPage=1;
            loadData(false);
        }
    }

    @Override
    public void onLoadMoreRequested() {

        if (mSrLayoutVideoScreeningActivity.isRefreshing()) {//正在刷新，则不加载更多
            mItemAdapter.loadMoreComplete();
            return;
        }
        loadItemData(true);
    }

    public void loadFinished(){
        isLoading = false;
        mSrLayoutVideoScreeningActivity.setRefreshing(false);
    }

    /**更改加载页数并且更新加载状态
     * @param noNet 是否是网络断开
     */
    public void loadFinishAndChangeLoadState(boolean noNet,boolean isMore){
        HttpLoadStateUtil.getInstance().loadSateChange(noNet);
        mItemAdapter.loadMoreFail();
        if (isMore) {
            mCurrPage--;
        }

    }


    /**
     * 每当一个请求完成则加1，所有请求完成结束刷新按钮
     */
    public synchronized void allCallFinished() {
        CALL_NUMBER++;
//        Log.w("HTTP111","             "+CALL_NUMBER);
        if (CALL_NUMBER >= 2) {
            CALL_NUMBER=0;
            mSrLayoutVideoScreeningActivity.post(new Runnable() {
                @Override
                public void run() {
                    if (mSrLayoutVideoScreeningActivity.isEnabled() && mSrLayoutVideoScreeningActivity.isRefreshing()) {
                        mSrLayoutVideoScreeningActivity.setRefreshing(false);
                        isLoading = false;
//                        Log.w("HTTP","             "+CALL_NUMBER);
                        CALL_NUMBER=0;
                    }
                }
            });

        }
    }

    /**
     * 请求完成个数
     */
    int CALL_NUMBER = 0;


}
