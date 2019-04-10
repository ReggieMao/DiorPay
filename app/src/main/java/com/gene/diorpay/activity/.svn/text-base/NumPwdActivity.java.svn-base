package com.gene.diorpay.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import com.gene.diorpay.R;
import com.gene.library.base.BaseActivity;
import com.gene.library.util.ToastUtil;
import com.gene.library.util.Util;

/**
 * Created by MaoLJ on 2018/7/19.
 * 数字密码页面
 */

public class NumPwdActivity extends Base1Activity {

    private static final String TAG = "NumPwdActivity";
    public static String NAME = "name";
    public static String MOBILE = "mobile";
    public static String CODE = "code";
//    @Bind(R.id.ll_main)
//    LinearLayout mLlMain;
    @Bind(R.id.ll_three_point)
    LinearLayout mLlThreePoint;
    @Bind(R.id.et_pwd)
    EditText mEtPwd;
    @Bind(R.id.tv_sure)
    TextView mTvSure;
    private boolean canNext = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_num_pwd;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
//        Util.addLayoutListener(mLlMain, mTvSure);
        mEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    mTvSure.setBackground(getResources().getDrawable(R.drawable.bg_round_text_blue));
                    canNext = true;
                } else {
                    mTvSure.setBackground(getResources().getDrawable(R.drawable.bg_round_text_gray1));
                    canNext = false;
                }
            }
        });
        mEtPwd.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                final int screenHeight = getWindow().getDecorView().getRootView().getHeight();
                final int heightDifference = screenHeight - rect.bottom;
                boolean visible = heightDifference > screenHeight / 3;
                if (!visible) {
                    mLlThreePoint.setVisibility(View.VISIBLE);
                } else {
                    mLlThreePoint.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @OnClick({R.id.img_back, R.id.tv_sure})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_sure:
                if (canNext) {
                    Intent intent = new Intent(this, PwdSureActivity.class);
                    intent.putExtra(PwdSureActivity.NAME, getIntent().getStringExtra(NAME));
                    intent.putExtra(PwdSureActivity.MOBILE, getIntent().getStringExtra(MOBILE));
                    intent.putExtra(PwdSureActivity.CODE, getIntent().getStringExtra(CODE));
                    intent.putExtra(PwdSureActivity.PWD, mEtPwd.getText().toString());
                    startActivity(intent);
                } else
                    ToastUtil.toast(this, getString(R.string.toast_pwd));
                break;
        }
    }

}
