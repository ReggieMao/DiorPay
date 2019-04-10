package com.gene.diorpay.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.library.api.DiorPayApi;
import com.gene.library.base.BaseActivity;
import com.gene.library.constant.Event;
import com.gene.library.util.ActivityUtil;
import com.gene.library.util.ToastUtil;
import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/7/19.
 * 修改密码页面2
 */

public class ChangePwd3Activity extends Base1Activity {

    private static final String TAG = "ChangePwd3Activity";
    public static String OLD_PWD = "old_pwd";
    public static String NEW_PWD = "new_pwd";
//    @Bind(R.id.ll_main)
//    LinearLayout mLlMain;
    @Bind(R.id.et_pwd)
    EditText mEtPwd;
    @Bind(R.id.tv_sure)
    TextView mTvSure;
    private boolean mOldRight = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_pwd3;
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

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.UPDATE_PWD, new Action1() {
            @Override
            public void call(Object o) {
                ToastUtil.toast(ChangePwd3Activity.this, getString(R.string.pwd_sure1));
                ActivityUtil.finishAll();
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
                    ToastUtil.toast(this, getString(R.string.pwd_tip3));
                    return;
                }
                if (!mEtPwd.getText().toString().equals(getIntent().getStringExtra(NEW_PWD))) {
                    ToastUtil.toast(this, getString(R.string.toast_sure_pwd));
                    return;
                }
                String sign = UserPreference.getString(UserPreference.SECRET, "") + "oldPassword=" + getIntent().getStringExtra(OLD_PWD) + "&password=" +
                        mEtPwd.getText().toString() + "&pwdType=2&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                sign = Util.encrypt(sign);
                DiorPayApi.getInstance().updateUserPwd(Util.getNowTime(), sign, UserPreference.getString(UserPreference.SESSION_ID, ""), getIntent().getStringExtra(OLD_PWD), mEtPwd.getText().toString());
                break;
        }
    }

}
