package com.example.alan.myapplication.alan.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.alan.myapplication.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;

/**
 * Created by Alan on 2018/1/29.
 * 功能：影视筛选
 */

public class VideoScreeningActivity extends AutoLayoutActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_screening);
        ButterKnife.bind(this);
        initIntent();

    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            int type = intent.getIntExtra("type",-1);
        }
    }
}
