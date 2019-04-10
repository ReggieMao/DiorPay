package com.gene.diorpay.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import com.gene.diorpay.R;
import com.gene.library.base.BaseActivity;
import com.gene.library.base.BaseApp;
import com.gene.library.util.Util;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by MaoLJ on 2018/7/19.
 * 登录注册页面
 */

public class StartActivity extends Base1Activity {

    private static final String TAG = "StartActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
    }

    @OnClick({R.id.tv_create, R.id.tv_login, R.id.tv_privacy})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_create:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.tv_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.tv_privacy:
                startActivity(new Intent(this, PrivacyPolicyActivity.class));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            List<Activity> activities = ((BaseApp) getApplication()).getActivities();
            for (Activity activity : activities) {
                activity.finish();
            }
        }
        return false;
    }

}
