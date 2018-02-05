package com.example.alan.myapplication.alan.http;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.utils.AllUtils;

/**
 * Created by Alan on 2018/1/26.
 * 功能：
 */

public class HttpLoadStateUtil {

    public View mView;
    public ImageView mIvLoadState;
    public TextView mTvLoadState;
    private NetRetryListener netRetryListener;
    private TextView mTvLoadingLoadState;
    private LinearLayout mLlErrorLoadState;

    private HttpLoadStateUtil(){}
    private Context context ;
    public final  String TYPE_NO_NET="网络未连接,点击重试";
    public final  String TYPE_NO_Data="数据加载错误或者暂无数据,点击重试";
    public final  String TYPE_NO_Content="暂无数据,点击重试";



    public static HttpLoadStateUtil mInstance;

    public static  HttpLoadStateUtil getInstance(){
        if (mInstance == null)
        {
            synchronized (HttpLoadStateUtil.class)
            {
                if (mInstance == null)
                {
                    mInstance = new HttpLoadStateUtil();
                }
            }
        }
        return mInstance;
    }


    public View setContextAndInitView(Context cxt){
        this.context = cxt;
        if (mView == null) {
           return initView(context);
        }
        if (mView.getParent() != null) {
         ViewGroup v =    (ViewGroup) mView.getParent();
            v.removeView(mView);
        }
        setLoadingLayoutVisible();//重新初始化显示加载布局
        return mView;
    }

    private View initView(Context cxt) {
        mView = View.inflate(cxt, R.layout.a_http_load_state,null);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (netRetryListener != null && AllUtils.getInstance().isNetworkConnected()) {
                    netRetryListener.netRetry();
                }

            }
        });
        mIvLoadState = (ImageView) mView.findViewById(R.id.iv_load_state);
        mTvLoadState = (TextView) mView.findViewById(R.id.tv_des_load_state);
        mTvLoadingLoadState = (TextView) mView.findViewById(R.id.tv_loading_load_state);
        mLlErrorLoadState = (LinearLayout) mView.findViewById(R.id.ll_error_load_state);
        return mView;
    }

    /**更改加载状态
     * @param noNet
     */
    public void loadSateChange(boolean noNet){
        setLoadingLayoutGone();
        if (mTvLoadState != null) {
            if (noNet) {
                mTvLoadState.setText(TYPE_NO_NET);
            }else {
                mTvLoadState.setText(TYPE_NO_Data);
            }
        }
    }

    /**暂无数据状态
     */
    public void loadSateChangeNoContent(){
        setLoadingLayoutGone();
        if (mTvLoadState != null)
                mTvLoadState.setText(TYPE_NO_Content);
    }

    /**
     * 隐藏正在加载布局，显示错误布局
     */
    public void setLoadingLayoutGone(){
        if (mTvLoadingLoadState != null) {
            mTvLoadingLoadState.setVisibility(View.GONE);
            mLlErrorLoadState.setVisibility(View.VISIBLE);
        }
    }


    /**设置点击重试按钮回调
     * @param netRetryListener
     */
    public void setNetRetryListener(NetRetryListener netRetryListener){
        this.netRetryListener = netRetryListener;

    }

    public void setLoadingLayoutVisible(){
        if (mTvLoadingLoadState != null) {
            mTvLoadingLoadState.setVisibility(View.VISIBLE);
            mLlErrorLoadState.setVisibility(View.GONE);
        }
    }

    /**
     * 点击重新加载
     */
    public interface NetRetryListener{
        public void netRetry();
    }

}
