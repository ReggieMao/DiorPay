package com.gene.diorpay.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gene.diorpay.R;
import com.gene.diorpay.adapter.FragmentAdapter;
import com.gene.diorpay.application.MyApplication;
import com.gene.diorpay.fragment.AssetFragment;
import com.gene.diorpay.fragment.Base1Fragment;
import com.gene.diorpay.fragment.MarketFragment;
import com.gene.diorpay.fragment.MineFragment;
import com.gene.diorpay.fragment.TradeFragment;
import com.gene.diorpay.view.ArcMenuView;
import com.gene.library.api.DiorPayApi;
import com.gene.library.base.BaseApp;
import com.gene.library.constant.ConstantCode;
import com.gene.library.constant.Event;
import com.gene.library.pojo.Income;
import com.gene.library.util.DialogUtil;
import com.gene.library.util.ToastUtil;
import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;
import com.gene.library.view.SwipeViewPager;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/7/18.
 * 主页面
 */

public class MainActivity extends Base1Activity implements ArcMenuView.onMenuItemClickListner, ArcMenuView.onCenterClickListner {

    private static final String TAG = "MainActivity";
    private int REQUEST_CODE_SCAN = 1001;
    private ArcMenuView mMenuView;
    @Bind(R.id.center)
    View mView;
    @Bind(R.id.view_pager)
    SwipeViewPager mSwipeViewPager;
    @Bind(R.id.radio_group)
    RadioGroup mRadioGroup;
    @Bind(R.id.et_transparent)
    EditText mEtTran;
    @Bind(R.id.rb_asset)
    RadioButton mRadioButton1;
    @Bind(R.id.rb_trade)
    RadioButton mRadioButton2;
    @Bind(R.id.rb_market)
    RadioButton mRadioButton3;
    @Bind(R.id.rb_mine)
    RadioButton mRadioButton4;
    private boolean mIsOpen = true;
    private Base1Fragment mBaseFragment;
    private TradeFragment mTradeFragment;
    private MarketFragment mMarketFragment;
    private MineFragment mMineFragment;
    private String[] mAll;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
        UserPreference.putInt(UserPreference.PWD_ERR_COUNT, 4);
        if (UserPreference.getInt(UserPreference.LANGUAGE, 1) == 1) {
            Util.setLanguage(MyApplication.getMyApplicationInstance(), false);
            mRadioButton1.setText(getString(R.string.asset));
            mRadioButton2.setText(getString(R.string.trade));
            mRadioButton3.setText(getString(R.string.market));
            mRadioButton4.setText(getString(R.string.mine));
        } else {
            Util.setLanguage(MyApplication.getMyApplicationInstance(), true);
            mRadioButton1.setText(getString(R.string.asset1));
            mRadioButton2.setText(getString(R.string.trade1));
            mRadioButton3.setText(getString(R.string.market1));
            mRadioButton4.setText(getString(R.string.mine1));
        }
        mEtTran.setInputType(InputType.TYPE_NULL);
        mMenuView = mView.findViewById(R.id.arcMenu);
        mMenuView.setOnMenuItemClickListner(this);
        mMenuView.setOnCenterClickListner(this);

        AssetFragment mAssetFragment = new AssetFragment();
        mTradeFragment = new TradeFragment();
        mMarketFragment = new MarketFragment();
        mMineFragment = new MineFragment();
        mSwipeViewPager.setSwipeEnable(false);
        FragmentAdapter mAdapter = new FragmentAdapter(getSupportFragmentManager());
        mAdapter.addFragment(mAssetFragment);
        mAdapter.addFragment(mTradeFragment);
        mAdapter.addFragment(mMarketFragment);
        mAdapter.addFragment(mMineFragment);
        mSwipeViewPager.setAdapter(mAdapter);
        mRadioGroup.setOnCheckedChangeListener(radioGroupListener);
        ((RadioButton) mRadioGroup.getChildAt(0)).setChecked(true);

        String sign = UserPreference.getString(UserPreference.SECRET, "") + "audienceId=" + JPushInterface.getRegistrationID(MainActivity.this) + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        DiorPayApi.getInstance().registerId(Util.getNowTime(), sign, UserPreference.getString(UserPreference.SESSION_ID, ""), JPushInterface.getRegistrationID(MainActivity.this));

        getUsdToCny();
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.RE_LOGIN, new Action1() {
            @Override
            public void call(Object o) {
                UserPreference.putString(UserPreference.GESTURE_PWD, "");
                UserPreference.putString(UserPreference.SESSION_ID, "");
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });

        rxManage.on(Event.REGISTER_ID, new Action1() {
            @Override
            public void call(Object o) {
                Log.d(TAG, "极光推送RegisterId上传成功！");
                if (Util.isEmpty(UserPreference.getString(UserPreference.GESTURE_PWD, ""))) {
                    DialogUtil.gestureDialog(MainActivity.this, new DialogUtil.OnResultListener4() {
                        @Override
                        public void select1() {
                            Intent intent = new Intent(MainActivity.this, GesturePwd1Activity.class);
                            intent.putExtra(GesturePwd1Activity.FROM_SETTING, true);
                            startActivity(intent);
                        }

                        @Override
                        public void select2() {

                        }
                    });
                }
            }
        });

        rxManage.on(Event.GET_INCOME1, new Action1<Income>() {
            @Override
            public void call(Income o) {
                Intent intent = new Intent(MainActivity.this, SendAssetActivity.class);
                intent.putExtra(SendAssetActivity.COIN_NAME, mAll[0]);
                intent.putExtra(SendAssetActivity.USER_WALLET_TYPE, mAll[1]);
                intent.putExtra(SendAssetActivity.USER_WALLET_ADDRESS, mAll[2]);
                intent.putExtra(SendAssetActivity.COIN_COUNT, Util.point(o.getAvalBalance(), 6));
                startActivity(intent);
            }
        });

        rxManage.on(Event.USD_TO_CNY, new Action1<Double>() {
            @Override
            public void call(Double o) {
                UserPreference.putString(UserPreference.EXCHANGE, Util.point(o, 2));
            }
        });
    }

    private void getUsdToCny() {
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        DiorPayApi.getInstance().usdToCny(Util.getNowTime(), sign, UserPreference.getString(UserPreference.SESSION_ID, ""));
    }

    @Override
    public void onClick(View childView, int position) {
        switch (childView.getTag().toString()) {
            case "collect":
                if (!mIsOpen) {
                    mIsOpen = true;
                    startActivity(new Intent(this, CollectCoinActivity.class));
                }
                mEtTran.setVisibility(View.GONE);
                break;
            case "sweep":
                if (!mIsOpen) {
                    mIsOpen = true;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // 先判断是否有权限
                        if (AndPermission.hasPermission(MainActivity.this, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            // 有权限
                            Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                            startActivityForResult(intent, REQUEST_CODE_SCAN);
                        } else {
                            // 申请权限
                            AndPermission.with(MainActivity.this).requestCode(ConstantCode.REQUEST_CODE_OF_CAMERA).callback(MainActivity.this)
                                    .permission(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE).start();
                        }
                    } else {
                        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_SCAN);
                    }
                }
                mEtTran.setVisibility(View.GONE);
                break;
            case "send":
                if (!mIsOpen) {
                    mIsOpen = true;
                    startActivity(new Intent(this, SendCoinActivity.class));
                }
                mEtTran.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onClick(boolean isOpen) {
        mIsOpen = isOpen;
        if (!isOpen)
            mEtTran.setVisibility(View.VISIBLE);
        else
            mEtTran.setVisibility(View.GONE);
    }

    RadioGroup.OnCheckedChangeListener radioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_asset: // 资产
                    if (null != mSwipeViewPager) {
                        mSwipeViewPager.setCurrentItem(0, false);
                    }
                    break;
                case R.id.rb_trade: // 行情
                    if (null != mSwipeViewPager) {
                        mSwipeViewPager.setCurrentItem(1, false);
                    }
                    mBaseFragment = mTradeFragment;
                    break;
                case R.id.rb_market: // 资讯
                    if (null != mSwipeViewPager) {
                        mSwipeViewPager.setCurrentItem(2, false);
                    }
                    mBaseFragment = mMarketFragment;
                    break;
                case R.id.rb_mine: // 我的
                    if (null != mSwipeViewPager) {
                        mSwipeViewPager.setCurrentItem(3, false);
                    }
                    mBaseFragment = mMineFragment;
                    break;
            }
            if (mBaseFragment != null) {
                mBaseFragment.onChangeFragment(checkedId);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                String[] all = content.split(" ");
                if (all.length == 3) {
                    mAll = all;
                    String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + "&userWalletType=" + all[1]
                            + UserPreference.getString(UserPreference.SECRET, "");
                    sign = Util.encrypt(sign);
                    DiorPayApi.getInstance().getIncome(1, UserPreference.getString(UserPreference.SESSION_ID, ""), sign, Util.getNowTime(), all[1]);
                } else
                    ToastUtil.toast(MainActivity.this, getString(R.string.code_not_right));
            }
        }
    }

    @PermissionYes(ConstantCode.REQUEST_CODE_OF_CAMERA)
    private void getCameraYes(List<String> grantedPermissions) {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @PermissionNo(ConstantCode.REQUEST_CODE_OF_CAMERA)
    private void getCameraNo(List<String> deniedPermissions) {
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            AndPermission.defaultSettingDialog(this, ConstantCode.REQUEST_CODE_OF_CAMERA).show();
        }
    }

    @OnClick({R.id.et_transparent})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.et_transparent:
                if (!mIsOpen) {
                    mMenuView.toggleMenu(300);
                }
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mEtTran.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!mIsOpen) {
                mMenuView.toggleMenu(300);
            } else {
                List<Activity> activities = ((BaseApp) getApplication()).getActivities();
                for (Activity activity : activities) {
                    activity.finish();
                }
            }
        }
        return false;
    }

}
