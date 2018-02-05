package com.example.alan.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test);
//    WebView webView = (WebView) findViewById(R.id.webView_animation);
//        webView.loadUrl("https://baidu.com");
//        webView.setWebViewClient(new WebViewClient(){
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//
//
//        });






//        CollapsingToolbarLayout st = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//        ImageView iv = (ImageView) findViewById(R.id.iv);
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "蜗牛", Toast.LENGTH_SHORT).show();
////                finish();
//            }
//        });
//        st.setTitle("我爱你笨笨");
    }
}
