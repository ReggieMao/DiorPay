package com.gene.diorpay.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.library.base.BaseActivity;
import com.gene.library.util.Util;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by MaoLJ on 2018/8/15.
 * 版本日志页面
 */

public class VersionLogActivity extends Base1Activity {

    private static final String TAG = "VersionLogActivity";
    @Bind(R.id.tv_1)
    TextView mTv1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_version_log;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
        mTv1.setText(getString(R.string.version) + "V1.1.0" + getString(R.string.record));
    }

    @OnClick({R.id.img_back, R.id.rl_1})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_1:
                Intent intent = new Intent(this, LogInfoActivity.class);
                intent.putExtra(LogInfoActivity.TITLE, mTv1.getText().toString());
                intent.putExtra(LogInfoActivity.TIME, "2018-08-15");
                intent.putExtra(LogInfoActivity.CONTENT, "1.更新APP bug\n2.更新APP的错误。\n3.修复登录问题。");
                startActivity(intent);
                break;
        }
    }

}
