package com.gene.diorpay.activity;

import android.view.View;

import com.gene.diorpay.R;
import com.gene.library.base.BaseActivity;
import com.gene.library.util.Util;

import butterknife.OnClick;

/**
 * Created by MaoLJ on 2018/8/6.
 * 隐私条款页面
 */

public class PrivacyPolicyActivity extends Base1Activity {

    private static final String TAG = "PrivacyPolicyActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_privacy_policy;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);

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
