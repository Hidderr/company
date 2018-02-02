package com.example.alan.myapplication.alan.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.adapter.vp.ViewPagerHeaderVideoFragmentAdapter;
import com.example.alan.myapplication.alan.adapter.vp.recycler.RecyclerFooterItemAdapter;
import com.example.alan.myapplication.alan.adapter.vp.recycler.RootRecyclerItemAdapter;
import com.example.alan.myapplication.alan.bean.VideoFragmentBannerBean;
import com.example.alan.myapplication.alan.bean.VideoFragmentFooterBean;
import com.example.alan.myapplication.alan.bean.VideoFragmentProjectBean;
import com.example.alan.myapplication.alan.constants.AppUrl;
import com.example.alan.myapplication.alan.gimi.ToastUtil;
import com.example.alan.myapplication.alan.http.HttpLoadStateUtil;
import com.example.alan.myapplication.alan.http.HttpManager;
import com.example.alan.myapplication.alan.http.ServerCallBack;
import com.example.alan.myapplication.alan.ui.UserVideoActivity;
import com.example.alan.myapplication.alan.ui.VideoClassificationActivity;
import com.example.alan.myapplication.alan.utils.AllUtils;
import com.example.alan.myapplication.alan.view.InterceptViewPager;
import com.example.alan.myapplication.alan.view.vp.ScaleInTransformer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.alan.myapplication.R.id.iv_my_movie_header_video_fragment;
import static com.example.alan.myapplication.alan.global.GlobalApplication.context;

//import com.zhy.magicviewpager.transformer.ScaleInTransformer;


/**
 * Created by Alan on 2018/1/20.
 * 影视
 */

public class VideoFragment extends ABaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    @Bind(R.id.recycler_view_video_fragment)
    RecyclerView mRecyclerViewVideoFragment;
    @Bind(R.id.sr_layout_video_fragment)
    SwipeRefreshLayout mSrLayoutVideoFragment;
    /**
     * 是否正在加载数据
     */
    public boolean isLoading = false;
    /**
     * 当前需要加载的口味研究所页数
     */
    public int mCurrentPage = 1;

    /**
     * RecyclerView中间条目的数据
     */
    public List<VideoFragmentProjectBean.DataBean.SubjectsBean> mItemDataList = new ArrayList<>();
    /**
     * RecyclerVie footer条目的数据
     */
    public List<VideoFragmentFooterBean.DataBean.IndividualityBean> mFooterDataList = new ArrayList<>();
    /**
     * RecyclerVie header条目的数据
     */
    public List<VideoFragmentBannerBean.DataBean.BannerListBean> banner_list = new ArrayList<>();

    /**
     * VidoFragment根RecyclerView的Adapter
     */
    public RootRecyclerItemAdapter mRootRecyclerItemAdapter;
    public RecyclerFooterItemAdapter mRecyclerFooterItemAdapter;
    public ViewPagerHeaderVideoFragmentAdapter mViewPagerHeaderVideoFragmentAdapter;
    public View mFooterView;
    public View mHeaderView;
    public InterceptViewPager mHeaderVp;
//    @Bind(R.id.iv_load_state)
//    ImageView mIvLoadState;
//    @Bind(R.id.tv_des_load_state)
//    TextView mTvDesLoadState;
//    @Bind(R.id.rl_root_load_state)
//    RelativeLayout mRlRootLoadState;

    public final  String TYPE_NO_NET="网络未连接,点击重试";
    public final  String TYPE_NO_Data="数据加载错误,点击重试";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mFragmentContext).inflate(R.layout.fragment_video, null);
        ButterKnife.bind(this, view);
        initView();
        loadData();
        return view;
    }

    private void initView() {
        initLoadSateView();
        initRecyclerViewHeader();
        initRecyclerViewfooter();

        initSwiRefreshLayout();
        initRecyclerView();


    }

    private void initLoadSateView() {
        HttpLoadStateUtil.getInstance().setNetRetryListener(new HttpLoadStateUtil.NetRetryListener() {
            @Override
            public void netRetry() {
                loadData();//点击重试加载布局
            }
        });
    }


    private void initRecyclerViewfooter() {
        mFooterView = LayoutInflater.from(mFragmentContext).inflate(R.layout.fragment_video_footer, null);
        RecyclerView footerRecyclerView = (RecyclerView) mFooterView.findViewById(R.id.recyclerview_foot_video_fragment);
        mRecyclerFooterItemAdapter = new RecyclerFooterItemAdapter(mFooterDataList);
        mRecyclerFooterItemAdapter.setContext(mFragmentContext);
        mRecyclerFooterItemAdapter.setEnableLoadMore(true);
        mRecyclerFooterItemAdapter.setHasStableIds(true);
        LinearLayoutManager linearLayoutManagerFooter = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        footerRecyclerView.setLayoutManager(linearLayoutManagerFooter);
        footerRecyclerView.getItemAnimator().setChangeDuration(0);
        footerRecyclerView.setAdapter(mRecyclerFooterItemAdapter);
        mRecyclerFooterItemAdapter.setOnLoadMoreListener(this, footerRecyclerView);
        mRecyclerFooterItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(mFragmentContext, position + "口味研究所", Toast.LENGTH_SHORT).show();
                if (mFooterDataList != null && mFooterDataList.size()>0) {
                   VideoFragmentFooterBean.DataBean.IndividualityBean bean =  mFooterDataList.get(position);
                   String contentId =  bean.content_id;
                    if (2!=bean.type) {
                        AllUtils.getInstance().startVideoDetailActivity(mFragmentContext,contentId);
                    }

                }
            }
        });
    }

    private void initRecyclerViewHeader() {
        mHeaderView = LayoutInflater.from(mFragmentContext).inflate(R.layout.fragment_video_header, null);
        mHeaderVp = (InterceptViewPager) mHeaderView.findViewById(R.id.vp_header_video_fragment);
        mHeaderVp.setPageMargin(18);//设置page间间距，自行根据需求设置
        mHeaderVp.setOffscreenPageLimit(3);//>=3
        mHeaderVp.setPageTransformer(true, new ScaleInTransformer());

        ImageView headerIvMyMovie = (ImageView) mHeaderView.findViewById(iv_my_movie_header_video_fragment);
        ImageView headerIvSeeLive = (ImageView) mHeaderView.findViewById(R.id.iv_see_live_header_video_fragment);
        ImageView headerIvFindMovie = (ImageView) mHeaderView.findViewById(R.id.iv_find_movie_header_video_fragment);
        headerIvMyMovie.setOnClickListener(this);
        headerIvSeeLive.setOnClickListener(this);
        headerIvFindMovie.setOnClickListener(this);


    }

    private void initRecyclerView() {
        mRootRecyclerItemAdapter = new RootRecyclerItemAdapter(R.layout.recycler_item_root_video_fragment, mItemDataList);
        mRootRecyclerItemAdapter.setContext(mFragmentContext);
        mRootRecyclerItemAdapter.setEnableLoadMore(false);
        mRootRecyclerItemAdapter.setHasStableIds(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewVideoFragment.setLayoutManager(linearLayoutManager1);
        mRecyclerViewVideoFragment.getItemAnimator().setChangeDuration(0);
        mRecyclerViewVideoFragment.setAdapter(mRootRecyclerItemAdapter);
        View view = HttpLoadStateUtil.getInstance().setContextAndInitView(mFragmentContext);
        mRootRecyclerItemAdapter.setEmptyView(view);
        mRootRecyclerItemAdapter.setHeaderViewAsFlow(true);
        mRootRecyclerItemAdapter.addHeaderView(mHeaderView);
        mRootRecyclerItemAdapter.addFooterView(mFooterView);
        mRootRecyclerItemAdapter.setHeaderFooterEmpty(true,true);

//        mRootRecyclerItemAdapter.setFooterViewAsFlow(true);
    }

    private void initSwiRefreshLayout() {
        mSrLayoutVideoFragment.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        mSrLayoutVideoFragment.setOnRefreshListener(this);
        mSrLayoutVideoFragment.post(new Runnable() {
            @Override
            public void run() {
                mSrLayoutVideoFragment.setRefreshing(true);
            }
        });//进入刷新

    }

    private void loadData() {
        loadHeaderData();//加载RecyclerView的头布局数据
        loadItemData(false);//加载RecyclerView的中间条目数据
        loadFooterData(false);//加载RecyclerView的尾布局数据

    }

    private void loadDataRefreshing() {
        loadHeaderData();//加载RecyclerView的头布局数据
        loadItemData(true);//加载RecyclerView的中间条目数据
        loadFooterData(false);//加载RecyclerView的尾布局数据

    }



    private void loadFooterData(final boolean loadMore) {
        LinkedHashMap<String, String> paramas = new LinkedHashMap<>();
        if (loadMore) {
            paramas.put("page", ++mCurrentPage + "");
        } else {
            paramas.put("page", 1 + "");
        }
        paramas.put("user_id", "8888");
        paramas.put("mac_id", "8888");
        HttpManager.getInstance().getCallWithParamas(AppUrl.HOME_PAGE_INDIVIDUALITY, paramas, new ServerCallBack() {
            @Override
            public void responseSucessful(String json) {
                VideoFragmentFooterBean videoFragmentFooterBean = HttpManager.getInstance().getGson().fromJson(json, VideoFragmentFooterBean.class);
                if (videoFragmentFooterBean != null) {
                    if (videoFragmentFooterBean.data != null) {
                        List<VideoFragmentFooterBean.DataBean.IndividualityBean> footerData = videoFragmentFooterBean.data.individuality;
                        if (footerData != null) {
                            if (footerData.size() > 0) {
                                if (loadMore) {
                                    mFooterDataList.addAll(footerData);
                                    mRecyclerFooterItemAdapter.addData(footerData);
                                    mRecyclerFooterItemAdapter.loadMoreComplete();
                                } else {
                                    mFooterDataList = footerData;
                                    mRecyclerFooterItemAdapter.setNewData(footerData);
                                }

                            } else {
                                if (loadMore) {
                                    mCurrentPage--;
                                    ToastUtil.getToast("没有更多数据", mFragmentContext);
                                    mRecyclerFooterItemAdapter.loadMoreEnd();
                                }
                            }
                        }

                    }
                }
                allCallFinished();

            }

            @Override
            public void responseClientFailure(String json, int code) {
                loadMoreFailFinish(loadMore);
                allCallFinished();
            }

            @Override
            public void responseServerFailure(String json, int code) {
                loadMoreFailFinish(loadMore);
                allCallFinished();
            }

            @Override
            public void netWorkFailure(String error) {
                loadMoreFailFinish(loadMore);
                allCallFinished();
            }
        }, this);
    }

    private void loadMoreFailFinish(boolean loadMore) {
        if (loadMore) {
            mCurrentPage--;
        }
        mRecyclerFooterItemAdapter.loadMoreFail();
    }

    private void loadItemData(final boolean isRefresh) {
        HttpManager.getInstance().getCall(AppUrl.HOMEPAGE_SUBJECT, new ServerCallBack() {
            @Override
            public void responseSucessful(String json) {

                VideoFragmentProjectBean videoFragmentProjectBean = HttpManager.getInstance().getGson().fromJson(json, VideoFragmentProjectBean.class);
                if (videoFragmentProjectBean != null) {
                    if (videoFragmentProjectBean.data != null) {
                        List<VideoFragmentProjectBean.DataBean.SubjectsBean> subjects = videoFragmentProjectBean.data.subjects;
                        if (subjects != null) {
                            if (subjects.size() > 0) {
                                mRootRecyclerItemAdapter.setNewData(subjects);
                            }else {
                                if (!isRefresh) {
                                    HttpLoadStateUtil.getInstance().loadSateChangeNoContent();
                                }

                            }
                        }
                    }
                }
                allCallFinished();
            }

            @Override
            public void responseClientFailure(String json, int code) {
                allCallFinished();
                HttpLoadStateUtil.getInstance().loadSateChange(false);
            }

            @Override
            public void responseServerFailure(String json, int code) {
                allCallFinished();
                HttpLoadStateUtil.getInstance().loadSateChange(false);
            }

            @Override
            public void netWorkFailure(String error) {
                allCallFinished();
                HttpLoadStateUtil.getInstance().loadSateChange(true);
            }
        }, this);

    }

    private void loadHeaderData() {
        HttpManager.getInstance().getCall(AppUrl.HOMEPAGE_BANNER, new ServerCallBack() {
            @Override
            public void responseSucessful(String json) {

                VideoFragmentBannerBean videoFragmentBannerBean = HttpManager.getInstance().getGson().fromJson(json, VideoFragmentBannerBean.class);
                if (videoFragmentBannerBean != null) {
                    if (videoFragmentBannerBean.data != null) {
                        List<VideoFragmentBannerBean.DataBean.BannerListBean> banner_list = videoFragmentBannerBean.data.banner_list;
                        if (banner_list != null) {
                            if (banner_list.size() > 0) {
                                mViewPagerHeaderVideoFragmentAdapter = new ViewPagerHeaderVideoFragmentAdapter(banner_list, mFragmentContext);

                                mHeaderVp.setAdapter(mViewPagerHeaderVideoFragmentAdapter);
                            }
                        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {//SwiprRefreshLayout正在刷新
        if (mRecyclerFooterItemAdapter.isLoading()) {//正在加载更多，则不刷新
            mSrLayoutVideoFragment.setRefreshing(false);
            AllUtils.showToast(mFragmentContext,"正在加载更多");
            return;
        }
        if (!isLoading) {
            isLoading = true;
            mCurrentPage =1;
            loadDataRefreshing();
        }
    }

    @Override
    public void onLoadMoreRequested() {//RecyclerView的footer加载更多
        if (mSrLayoutVideoFragment.isRefreshing()) {//正在刷新，则不加载更多
            mRecyclerFooterItemAdapter.loadMoreComplete();
            return;
        }
        loadFooterData(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_my_movie_header_video_fragment://我的影视
                AllUtils.getInstance().startActivity(mFragmentContext, UserVideoActivity.class);
                break;

            case R.id.iv_see_live_header_video_fragment://直播
                break;

            case R.id.iv_find_movie_header_video_fragment://找影视
                AllUtils.getInstance().startActivity(mFragmentContext, VideoClassificationActivity.class);
                break;
            case R.id.rl_root_load_state://重新加载
                loadData();
                break;
        }
    }




    /**
     * 每当一个请求完成则加1，所有请求完成结束刷新按钮
     */
    public synchronized void allCallFinished() {
        CALL_NUMBER++;
//        Log.w("HTTP111","             "+CALL_NUMBER);
        if (CALL_NUMBER >= 3) {
            CALL_NUMBER=0;
            mSrLayoutVideoFragment.post(new Runnable() {
                @Override
                public void run() {
                    if (mSrLayoutVideoFragment.isEnabled() && mSrLayoutVideoFragment.isRefreshing()) {
                        mSrLayoutVideoFragment.setRefreshing(false);
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
