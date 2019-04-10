package com.gene.diorpay.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.library.base.BaseActivity;
import com.gene.library.util.ActivityUtil;
import com.gene.library.util.ToastUtil;
import com.gene.library.util.Util;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by MaoLJ on 2018/7/19.
 * 修改密码页面1
 */

public class ChangePwd1Activity extends Base1Activity {

    private static final String TAG = "ChangePwd1Activity";
//    @Bind(R.id.ll_main)
//    LinearLayout mLlMain;
    @Bind(R.id.et_pwd)
    EditText mEtPwd;
    @Bind(R.id.tv_sure)
    TextView mTvSure;
    private boolean mOldRight = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_pwd1;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
//        Util.addLayoutListener(mLlMain, mTvSure);
        ActivityUtil.add(this);
        mEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOldRight = s.length() == 6;
                nextBack();
            }
        });
    }

    private void nextBack() {
        if (mOldRight)
            mTvSure.setBackground(getResources().getDrawable(R.drawable.bg_round_text_blue));
        else
            mTvSure.setBackground(getResources().getDrawable(R.drawable.bg_round_text_gray1));
    }

    @OnClick({R.id.img_back, R.id.tv_sure})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_sure:
                if (mEtPwd.getText().length() != 6) {
                    ToastUtil.toast(this, getString(R.string.pwd_tip1));
                    return;
                }
                Intent intent = new Intent(this, ChangePwd2Activity.class);
                intent.putExtra(ChangePwd2Activity.OLD_PWD, mEtPwd.getText().toString());
                startActivity(intent);
                break;
        }
    }

}
