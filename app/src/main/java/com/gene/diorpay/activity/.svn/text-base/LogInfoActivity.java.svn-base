package com.gene.diorpay.activity;

import android.view.View;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.library.base.BaseActivity;
import com.gene.library.util.Util;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by MaoLJ on 2018/8/15.
 * 日志详情页面
 */

public class LogInfoActivity extends Base1Activity {

    private static final String TAG = "LogInfoActivity";
    public static String TITLE = "title";
    public static String TIME = "time";
    public static String CONTENT = "content";
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.tv_content)
    TextView mTvContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_log_info;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
        mTvTitle.setText(getIntent().getStringExtra(TITLE));
        mTvTime.setText(getIntent().getStringExtra(TIME));
        mTvContent.setText(getIntent().getStringExtra(CONTENT));
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
