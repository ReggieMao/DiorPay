package com.gene.diorpay.activity;

import android.view.View;

import com.gene.library.base.BaseActivity;
import com.gene.library.util.Util;

import butterknife.OnClick;
import com.gene.diorpay.R;

/**
 * Created by MaoLJ on 2018/8/6.
 * 使用协议页面
 */

public class AgreementActivity extends Base1Activity {

    private static final String TAG = "AgreementActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_agreement;
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
