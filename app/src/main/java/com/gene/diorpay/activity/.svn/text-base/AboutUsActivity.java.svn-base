package com.gene.diorpay.activity;

import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import com.gene.diorpay.R;
import com.gene.library.base.BaseActivity;
import com.gene.library.util.Util;

/**
 * Created by MaoLJ on 2018/7/24.
 * 关于我们页面
 */

public class AboutUsActivity extends Base1Activity {

    private static final String TAG = "AboutUsActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
    }

    @OnClick({R.id.img_back, R.id.rl_agreement, R.id.rl_version, R.id.rl_privacy})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_agreement:
                startActivity(new Intent(this, AgreementActivity.class));
                break;
            case R.id.rl_version:
                startActivity(new Intent(this, VersionLogActivity.class));
                break;
            case R.id.rl_privacy:
                startActivity(new Intent(this, PrivacyPolicyActivity.class));
                break;
        }
    }

}
