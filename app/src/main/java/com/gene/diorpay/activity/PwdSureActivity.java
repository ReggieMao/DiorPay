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
import com.gene.library.api.DiorPayApi;
import com.gene.library.base.BaseActivity;
import com.gene.library.constant.Constants;
import com.gene.library.constant.Event;
import com.gene.library.pojo.Register;
import com.gene.library.util.ToastUtil;
import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/7/19.
 * 数字密码确认页面
 */

public class PwdSureActivity extends Base1Activity {

    private static final String TAG = "PwdSureActivity";
    public static String NAME = "name";
    public static String MOBILE = "mobile";
    public static String CODE = "code";
    public static String PWD = "pwd";
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
        return R.layout.activity_pwd_sure;
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
        rxManage.on(Event.REGISTER, new Action1<Register>() {
            @Override
            public void call(Register o) {
                UserPreference.putString(UserPreference.SESSION_ID, o.getSessionId());
                UserPreference.putString(UserPreference.SECRET, o.getSecret());
                UserPreference.putString(UserPreference.ACCOUNT, getIntent().getStringExtra(MOBILE));
                ToastUtil.toast(PwdSureActivity.this, getString(R.string.toast_register_success));
                Intent intent = new Intent(PwdSureActivity.this, GesturePwd1Activity.class);
                intent.putExtra(GesturePwd1Activity.FROM_SETTING, false);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.img_back, R.id.tv_sure})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_sure:
                if (canNext) {
                    if (getIntent().getStringExtra(PWD).equals(mEtPwd.getText().toString())) {
                        String sign = Constants.SALT_CIPHER + "bindMobile=" + getIntent().getStringExtra(MOBILE) + "&nickName=" + getIntent().getStringExtra(NAME)
                                + "&passWord=" + mEtPwd.getText().toString() + "&payPassWord=" + mEtPwd.getText().toString() + "&submitTime=" + Util.getNowTime()
                                + "&verifyCode=" + getIntent().getStringExtra(CODE) + Constants.SALT_CIPHER;
                        sign = Util.encrypt(sign);
                        DiorPayApi.getInstance().register(getIntent().getStringExtra(MOBILE), mEtPwd.getText().toString(), mEtPwd.getText().toString(), sign,
                                Util.getNowTime(), getIntent().getStringExtra(NAME), getIntent().getStringExtra(CODE));
                    } else
                        ToastUtil.toast(this, getString(R.string.toast_sure_pwd));
                } else
                    ToastUtil.toast(this, getString(R.string.toast_pwd));
                break;
        }
    }

}
