package com.gene.diorpay.activity;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.library.base.BaseActivity;
import com.gene.library.util.Util;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by MaoLJ on 2018/8/6.
 * 公告详情页面
 */

public class NoticeInfoActivity extends Base1Activity {

    private static final String TAG = "NoticeInfoActivity";
    public static String CONTENT = "content";
    public static String URL = "url";
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.web)
    WebView mWebView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_notice_info;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
        mTvTitle.setText(getIntent().getStringExtra(CONTENT));
        String url = getIntent().getStringExtra(URL);
        mWebView.loadUrl(url);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @OnClick({R.id.img_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

}
