package com.example.alan.myapplication.alan.http;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    private HttpLoadStateUtil(){}
    private Context context ;
    public final  String TYPE_NO_NET="网络未连接,点击重试";
    public final  String TYPE_NO_Data="数据加载错误,点击重试";



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
        return mView;
    }

    private View initView(Context cxt) {
        mView = View.inflate(cxt, R.layout.http_load_state,null);
        mView.setVisibility(View.VISIBLE);
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
        return mView;
    }

    /**更改加载状态
     * @param noNet
     */
    public void loadSateChange(boolean noNet){
        if (mTvLoadState != null) {
            if (noNet) {
                mTvLoadState.setText(TYPE_NO_NET);
            }else {
                mTvLoadState.setText(TYPE_NO_Data);
            }
        }
    }

    /**
     * 有数据隐藏布局
     */
    public void setLayoutGone(){
        if (mView != null) {
            mView.setVisibility(View.GONE);
        }
    }


    /**设置点击重试按钮回调
     * @param netRetryListener
     */
    public void setNetRetryListener(NetRetryListener netRetryListener){
        this.netRetryListener = netRetryListener;

    }

    /**
     * 点击重新加载
     */
    public interface NetRetryListener{
        public void netRetry();
    }

}
