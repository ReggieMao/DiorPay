package com.gene.diorpay.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import com.gene.diorpay.R;
import com.gene.diorpay.view.GestureView;
import com.gene.library.base.BaseActivity;
import com.gene.library.base.BaseApp;
import com.gene.library.util.ActivityUtil;
import com.gene.library.util.ToastUtil;
import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;

/**
 * Created by MaoLJ on 2018/7/19.
 * 手势密码页面0
 */

public class GesturePwd0Activity extends BaseActivity {

    private static final String TAG = "GesturePwd0Activity";
    public static String FROM_WELCOME = "from_welcome";
    public static String FROM_HOME = "from_home";
    @Bind(R.id.img1) ImageView mImg1; @Bind(R.id.img2) ImageView mImg2; @Bind(R.id.img3) ImageView mImg3;
    @Bind(R.id.img4) ImageView mImg4; @Bind(R.id.img5) ImageView mImg5; @Bind(R.id.img6) ImageView mImg6;
    @Bind(R.id.img7) ImageView mImg7; @Bind(R.id.img8) ImageView mImg8; @Bind(R.id.img9) ImageView mImg9;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_tip)
    TextView mTvTip;
    @Bind(R.id.gesture_view)
    GestureView mGestureView;
    @Bind(R.id.et_transparent)
    EditText mEtTran;
    @Bind(R.id.img_back)
    RelativeLayout mImgBack;
    private List<ImageView> mImgList = new ArrayList<>();
    private TimeCount time;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gesture_pwd0;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
        if (!getIntent().getBooleanExtra(FROM_WELCOME, false)) {
            ActivityUtil.add(this);
            mImgBack.setVisibility(View.VISIBLE);
            mTvTitle.setText(getString(R.string.old_gesture_pwd));
        } else {
            mImgBack.setVisibility(View.GONE);
            mTvTitle.setText(getString(R.string.gesture_pwd));
        }
        mEtTran.setInputType(InputType.TYPE_NULL);
        time = new TimeCount(1000, 1000);
        mImgList.add(mImg1); mImgList.add(mImg2); mImgList.add(mImg3); mImgList.add(mImg4); mImgList.add(mImg5);
        mImgList.add(mImg6); mImgList.add(mImg7); mImgList.add(mImg8); mImgList.add(mImg9);
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
                    String oldPwd = "";
                    for (int number : numbers) {
                        oldPwd = oldPwd + number;
                    }
                    if (!oldPwd.equals(UserPreference.getString(UserPreference.GESTURE_PWD, ""))) {
                        if (UserPreference.getInt(UserPreference.PWD_ERR_COUNT, 4) != 0) {
                            ToastUtil.toast(GesturePwd0Activity.this, getString(R.string.toast_gesture_error) + " " + UserPreference.getInt(UserPreference.PWD_ERR_COUNT, 4) + " " + getString(R.string.toast_gesture_error1));
                            UserPreference.putInt(UserPreference.PWD_ERR_COUNT, (UserPreference.getInt(UserPreference.PWD_ERR_COUNT, 4) - 1));
                            mEtTran.setVisibility(View.VISIBLE);
                            time.start();
                        } else {
                            UserPreference.putString(UserPreference.SESSION_ID, "");
                            ToastUtil.toast(GesturePwd0Activity.this, getString(R.string.toast_gesture_error2));
                            startActivity(new Intent(GesturePwd0Activity.this, StartActivity.class));
                        }
                    } else {
                        UserPreference.putInt(UserPreference.PWD_ERR_COUNT, 4);
                        if (!getIntent().getBooleanExtra(FROM_WELCOME, false)) {
                            Intent intent = new Intent(GesturePwd0Activity.this, GesturePwd1Activity.class);
                            intent.putExtra(GesturePwd1Activity.FROM_SETTING, true);
                            startActivity(intent);
                        } else
                            if (getIntent().getBooleanExtra(FROM_HOME, false))
                                finish();
                            else
                                startActivity(new Intent(GesturePwd0Activity.this, MainActivity.class));
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

    @OnClick({R.id.img_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mGestureView.clean();
        for (int i = 0; i < mImgList.size(); i ++) {
            mImgList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_round_edit_gray));
        }
        mTvTip.setTextColor(getResources().getColor(R.color.textGray));
        mEtTran.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        time.cancel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getIntent().getBooleanExtra(FROM_WELCOME, false)) {
                List<Activity> activities = ((BaseApp) getApplication()).getActivities();
                for (Activity activity : activities) {
                    activity.finish();
                }
            } else
                finish();
        }
        return false;
    }

}
