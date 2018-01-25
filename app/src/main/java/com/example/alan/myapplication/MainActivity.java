package com.example.alan.myapplication;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_bar);
        CollapsingToolbarLayout st = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "蜗牛", Toast.LENGTH_SHORT).show();
//                finish();
            }
        });
        st.setTitle("我爱你笨笨");
    }
}