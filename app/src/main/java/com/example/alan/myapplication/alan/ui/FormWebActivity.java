package com.example.alan.myapplication.alan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.jsclass.Collection;
import com.example.alan.myapplication.alan.utils.AllUtils;
import com.just.agentweb.AgentWeb;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alan on 2018/2/3.
 * 功能：加载 表单详情
 */

public class FormWebActivity extends AutoLayoutActivity implements View.OnClickListener {
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
//    @Bind(R.id.button)
//    Button mButton;
//    @Bind(R.id.button2)
//    Button mButton2;
//    @Bind(R.id.button3)
//    Button mButton3;
    private AgentWeb mAgentWeb;
    private String mTitle="片单详情";
    /**
     * 片单ID
     */
    private String list_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_form_web);
        ButterKnife.bind(this);
        initIntent();
        initTopBar();
        initAgentWeb();


    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            list_id = intent.getStringExtra("list_id");
        }
    }

    private void initTopBar() {
        AllUtils.getInstance().setTextBold(mTvTitleTitleBar);
        mIvReturnTitleBar.setOnClickListener(this);
        mTvTitleTitleBar.setText(mTitle+"");
    }

    private void initAgentWeb() {
        mAgentWeb = AgentWeb.with(this)//传入Activity or Fragment
                .setAgentWebParent(mLlRootFormWebActivity, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator()// 使用默认进度条
                .createAgentWeb()//
                .ready()
                .go("file:///android_asset/a_test.html");
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android",new Collection(list_id));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_return_title_bar:
                finish();
            break;
//            case :
//                break;
//
//             case :
//                 break;
//
//             case :
//                 break;
            default:
                break;
        }
    }
}
