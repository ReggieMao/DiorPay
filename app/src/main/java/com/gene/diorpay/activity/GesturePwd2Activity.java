package com.gene.diorpay.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import com.gene.diorpay.R;
import com.gene.diorpay.view.GestureView;
import com.gene.library.base.BaseActivity;
import com.gene.library.util.ActivityUtil;
import com.gene.library.util.ToastUtil;
import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;

/**
 * Created by MaoLJ on 2018/7/19.
 * 手势密码页面2
 */

public class GesturePwd2Activity extends Base1Activity {

    private static final String TAG = "GesturePwd2Activity";
    public static String FROM_SETTING = "from_setting";
    public static String FIRST_PWD = "first_pwd";
    @Bind(R.id.img1) ImageView mImg1; @Bind(R.id.img2) ImageView mImg2; @Bind(R.id.img3) ImageView mImg3;
    @Bind(R.id.img4) ImageView mImg4; @Bind(R.id.img5) ImageView mImg5; @Bind(R.id.img6) ImageView mImg6;
    @Bind(R.id.img7) ImageView mImg7; @Bind(R.id.img8) ImageView mImg8; @Bind(R.id.img9) ImageView mImg9;
    @Bind(R.id.tv_tip)
    TextView mTvTip;
    @Bind(R.id.tv_jump)
    TextView mTvJump;
    @Bind(R.id.gesture_view)
    GestureView mGestureView;
    @Bind(R.id.ll_bottom)
    LinearLayout mLlBottom;
    @Bind(R.id.et_transparent)
    EditText mEtTran;
    private List<ImageView> mImgList = new ArrayList<>();
    private TimeCount time;
    private String mOldPwd = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_gesture_pwd2;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
        ActivityUtil.add(this);
        mEtTran.setInputType(InputType.TYPE_NULL);
        time = new TimeCount(1000, 1000);
        mImgList.add(mImg1); mImgList.add(mImg2); mImgList.add(mImg3); mImgList.add(mImg4); mImgList.add(mImg5);
        mImgList.add(mImg6); mImgList.add(mImg7); mImgList.add(mImg8); mImgList.add(mImg9);
        if (getIntent().getBooleanExtra(FROM_SETTING, false)) { // 从设置页面进入
            mLlBottom.setVisibility(View.GONE);
            mTvJump.setVisibility(View.GONE);
        } else { // 从注册页面进入
            mLlBottom.setVisibility(View.VISIBLE);
            mTvJump.setVisibility(View.VISIBLE);
        }

        mGestureView.setGestureCallback(new GestureView.GestureCallback() {
            @Override
            public void onNodeConnected(@NonNull int[] numbers) {
                switch (numbers[numbers.length - 1]) {
                    case 1:
                        mImg1.setBackground(getResources().getDrawable(R.drawable.bg_round_text_green));
                        break;
                    case 2:
                        mImg2.setBackground(getResources().getDrawable(R.drawable.bg_round_text_green));
                        break;
                    case 3:
                        mImg3.setBackground(getResources().getDrawable(R.drawable.bg_round_text_green));
                        break;
                    case 4:
                        mImg4.setBackground(getResources().getDrawable(R.drawable.bg_round_text_green));
                        break;
                    case 5:
                        mImg5.setBackground(getResources().getDrawable(R.drawable.bg_round_text_green));
                        break;
                    case 6:
                        mImg6.setBackground(getResources().getDrawable(R.drawable.bg_round_text_green));
                        break;
                    case 7:
                        mImg7.setBackground(getResources().getDrawable(R.drawable.bg_round_text_green));
                        break;
                    case 8:
                        mImg8.setBackground(getResources().getDrawable(R.drawable.bg_round_text_green));
                        break;
                    case 9:
                        mImg9.setBackground(getResources().getDrawable(R.drawable.bg_round_text_green));
                        break;
                }
            }

            @Override
            public void onGestureFinished(@NonNull int[] numbers) {
                if (numbers.length < 5) {
                    mTvTip.setTextColor(getResources().getColor(R.color.red));
                    for (int number : numbers) {
                        mImgList.get(number - 1).setBackground(getResources().getDrawable(R.drawable.bg_round_text_red1));
                    }
                    mEtTran.setVisibility(View.VISIBLE);
                    time.start();
                } else {
                    for (int number : numbers) {
                        mOldPwd = mOldPwd + number;
                    }
                }
            }
        });
    }

    private class TimeCount extends CountDownTimer {
        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            mGestureView.clean();
            for (int i = 0; i < mImgList.size(); i ++) {
                mImgList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_round_edit_gray));
            }
            mTvTip.setTextColor(getResources().getColor(R.color.textGray));
            mEtTran.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.img_back, R.id.tv_jump, R.id.tv_sure})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_jump:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_sure:
                if (!mOldPwd.equals(getIntent().getStringExtra(FIRST_PWD))) {
                    ToastUtil.toast(this, getString(R.string.toast_gesture_diff));
                    mOldPwd = "";
                    mEtTran.setVisibility(View.VISIBLE);
                    time.start();
                } else {
                    UserPreference.putString(UserPreference.GESTURE_PWD, mOldPwd);
                    ToastUtil.toast(this, getString(R.string.toast_gesture_success));
                    if (!getIntent().getBooleanExtra(FROM_SETTING, false)) // 从注册页面进来
                        startActivity(new Intent(this, MainActivity.class));
                    else // 从设置页面进来
                        ActivityUtil.finishAll();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        time.cancel();
    }

}
