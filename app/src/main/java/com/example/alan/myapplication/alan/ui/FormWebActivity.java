package com.example.alan.myapplication.alan.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.gimi.LogUtil;
import com.example.alan.myapplication.alan.utils.AllUtils;
import com.just.agentweb.AgentWeb;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alan on 2018/2/3.
 * 功能：
 */

public class FormWebActivity extends AutoLayoutActivity {
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
    @Bind(R.id.ll_root_form_web_activity)
    LinearLayout mLlRootFormWebActivity;
    @Bind(R.id.button)
    Button mButton;
    @Bind(R.id.button2)
    Button mButton2;
    @Bind(R.id.button3)
    Button mButton3;
    private AgentWeb mAgentWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_web);
        ButterKnife.bind(this);
        LogUtil.w("ACTIVITY", "JJJJJJJJJJJJJJ");
        mAgentWeb = AgentWeb.with(this)//传入Activity or Fragment
                .setAgentWebParent(mLlRootFormWebActivity, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator()// 使用默认进度条
                .createAgentWeb()//
                .ready()
                .go("file:///android_asset/test.html");




    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.button, R.id.button2, R.id.button3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                mAgentWeb.getJsAccessEntrace().quickCallJs("callNativeApp", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        AllUtils.showToast(FormWebActivity.this,value);
                    }
                });
                break;
            case R.id.button2:
                mAgentWeb.getJsAccessEntrace().quickCallJs("callNativeApp2", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        AllUtils.showToast(FormWebActivity.this,value);
                    }
                });
                break;
            case R.id.button3:
                mAgentWeb.getJsAccessEntrace().quickCallJs("callNativeApp3", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        AllUtils.showToast(FormWebActivity.this,value);
                    }
                });
                break;
        }
    }
}
