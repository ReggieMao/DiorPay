package com.gene.diorpay.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.library.api.DiorPayApi;
import com.gene.library.base.BaseActivity;
import com.gene.library.constant.ConstantCode;
import com.gene.library.constant.Event;
import com.gene.library.pojo.RealmNotification;
import com.gene.library.util.DialogUtil;
import com.gene.library.util.ToastUtil;
import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;
import com.jakewharton.rxbinding.view.RxView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import io.realm.Realm;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/7/23.
 * 发送资产页面
 */

public class SendAssetActivity extends Base1Activity {

    private static final String TAG = "SendAssetActivity";
    private int REQUEST_CODE_SCAN = 1001;
    public static String COIN_NAME = "coin_name";
    public static String COIN_COUNT = "coin_count";
    public static String USER_WALLET_TYPE = "user_wallet_type";
    public static String USER_WALLET_ADDRESS = "user_wallet_address";
    @Bind(R.id.tv_title)
    TextView mTvTile;
    @Bind(R.id.img_logo)
    ImageView mImgLogo;
    @Bind(R.id.ll_main)
    LinearLayout mLlMain;
    @Bind(R.id.et_address)
    EditText mEtAddress;
    @Bind(R.id.et_count)
    EditText mEtCount;
    @Bind(R.id.tv_type)
    TextView mTvType;
    @Bind(R.id.tv_can_pay)
    TextView mTvCanPay;
    @Bind(R.id.tv_miner)
    TextView mTvMiner;
    @Bind(R.id.img_go)
    ImageView mImgGo;
    @Bind(R.id.tv_send)
    TextView mTvSend;
    private int mNowSelect = 2;
    private int mFeeType;
    private Realm mRealm = Realm.getDefaultInstance();

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_asset;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
        Util.addLayoutListener(mLlMain, mTvSend);
        RxView.clicks(mTvSend).throttleFirst(2, TimeUnit.SECONDS);
        mTvTile.setText(getResources().getString(R.string.send) + getIntent().getStringExtra(COIN_NAME));
        switch (getIntent().getStringExtra(COIN_NAME)) {
            case "BTC":
                mImgLogo.setImageResource(R.mipmap.coin_btc);
                mTvType.setText(getString(R.string.ordinary));
                break;
            case "ETH":
                mImgLogo.setImageResource(R.mipmap.coin_eth);
                mTvMiner.setText("0.000200ETH");
                mTvType.setText(getString(R.string.ordinary));
                break;
            case "SAS":
                mFeeType = 1;
                mImgGo.setVisibility(View.GONE);
                mTvType.setText("SAS/KB");
                mTvMiner.setText("0.004000");
                mImgLogo.setImageResource(R.mipmap.coin_sas);
                break;
            case "BZF":
                mImgLogo.setImageResource(R.mipmap.coin_bzf);
                mTvMiner.setText("0.150000SAS");
                mTvType.setText(getString(R.string.ordinary));
                break;
            case "DOB":
                mImgLogo.setImageResource(R.mipmap.coin_dob);
                mTvMiner.setText("0.150000SAS");
                mTvType.setText(getString(R.string.ordinary));
                break;
        }
        mTvCanPay.setText(getString(R.string.group) + getIntent().getStringExtra(COIN_COUNT) + " " + getIntent().getStringExtra(COIN_NAME));
        Util.setPoint(mEtCount);
        mEtAddress.setText(getIntent().getStringExtra(USER_WALLET_ADDRESS));
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.TRANSFER_TO_OTHERS, new Action1() {
            @Override
            public void call(Object o) {
                ToastUtil.toast(SendAssetActivity.this, getString(R.string.toast_transform_success));
                RealmNotification realmNotification = new RealmNotification();
                realmNotification.setTimeStamp(System.currentTimeMillis());
                realmNotification.setHasRead(false);
                realmNotification.setCoinType(getIntent().getStringExtra(USER_WALLET_TYPE));
                realmNotification.setOut(true);
                realmNotification.setTime(Util.getNowTime());
                realmNotification.setBalance(Double.parseDouble(mEtCount.getText().toString()));
                realmNotification.setCoinName(getIntent().getStringExtra(COIN_NAME));
                realmNotification.setInAddress(mEtAddress.getText().toString());
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(realmNotification);
                    }
                });
                finish();
            }
        });
    }

    @OnClick({R.id.img_back, R.id.tv_send, R.id.et_address, R.id.ll_miner, R.id.img_sweep, R.id.tv_all})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_send:
                if (Util.isEmpty(mEtAddress.getText().toString())) {
                    ToastUtil.toast(this, getString(R.string.toast_input_address));
                    return;
                }
                if (Util.isEmpty(mEtCount.getText().toString())) {
                    ToastUtil.toast(this, getString(R.string.toast_input_count));
                    return;
                }
                if (Double.parseDouble(mEtCount.getText().toString()) == 0) {
                    ToastUtil.toast(this, getString(R.string.toast_input_count0));
                    return;
                }
                if (Double.parseDouble(mEtCount.getText().toString()) > Double.parseDouble(getIntent().getStringExtra(COIN_COUNT))) {
                    ToastUtil.toast(this, getString(R.string.toast_input_count1));
                    return;
                }
                DialogUtil.transactionDialog(this, new DialogUtil.OnResultListener2() {
                    @Override
                    public void onOk(String... params) {
                        String sign = UserPreference.getString(UserPreference.SECRET, "") + "feeType=" + mFeeType + "&outAddress=" + mEtAddress.getText().toString() + "&payPassword=" +
                                Util.encrypt(params[0]) + "&submitTime=" + Util.getNowTime() + "&transferAmount=" + mEtCount.getText().toString() +
                                "&userWalletType=" + getIntent().getStringExtra(USER_WALLET_TYPE) + UserPreference.getString(UserPreference.SECRET, "");
                        sign = Util.encrypt(sign);
                        DiorPayApi.getInstance().transferToOthers(0, Util.getNowTime(), sign, UserPreference.getString(UserPreference.SESSION_ID, ""),
                                getIntent().getStringExtra(USER_WALLET_TYPE), mEtAddress.getText().toString(), mEtCount.getText().toString(), Util.encrypt(params[0]), mFeeType);
                    }
                });
                break;
            case R.id.et_address:
                mEtAddress.setCursorVisible(true);
                break;
            case R.id.ll_miner:
                if (!getIntent().getStringExtra(COIN_NAME).equals("SAS")) {
                    DialogUtil.minerFeeDialog(this, mNowSelect, new DialogUtil.OnResultListener1() {
                        @Override
                        public void select1() {
                            mNowSelect = 1;
                            mFeeType = 3;
                            mTvType.setText(getString(R.string.first));
                            if (getIntent().getStringExtra(COIN_NAME).equals("ETH"))
                                mTvMiner.setText("0.000300ETH");
                            else
                                mTvMiner.setText("0.200000SAS");
                        }

                        @Override
                        public void select2() {
                            mNowSelect = 2;
                            mFeeType = 2;
                            mTvType.setText(getString(R.string.ordinary));
                            if (getIntent().getStringExtra(COIN_NAME).equals("ETH"))
                                mTvMiner.setText("0.000200ETH");
                            else
                                mTvMiner.setText("0.150000SAS");
                        }

                        @Override
                        public void select3() {
                            mNowSelect = 3;
                            mFeeType = 1;
                            mTvType.setText(getString(R.string.economics));
                            if (getIntent().getStringExtra(COIN_NAME).equals("ETH"))
                                mTvMiner.setText("0.000100ETH");
                            else
                                mTvMiner.setText("0.100000SAS");
                        }
                    });
                }
                break;
            case R.id.img_sweep:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 先判断是否有权限
                    if (AndPermission.hasPermission(SendAssetActivity.this, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // 有权限
                        Intent intent1 = new Intent(SendAssetActivity.this, CaptureActivity.class);
                        startActivityForResult(intent1, REQUEST_CODE_SCAN);
                    } else {
                        // 申请权限
                        AndPermission.with(SendAssetActivity.this).requestCode(ConstantCode.REQUEST_CODE_OF_CAMERA).callback(SendAssetActivity.this)
                                .permission(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE).start();
                    }
                } else {
                    Intent intent1 = new Intent(SendAssetActivity.this, CaptureActivity.class);
                    startActivityForResult(intent1, REQUEST_CODE_SCAN);
                }
                break;
            case R.id.tv_all:
                mEtCount.setText(getIntent().getStringExtra(COIN_COUNT));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                String[] all = content.split(" ");
                if (all.length == 3)
                    mEtAddress.setText(all[all.length - 1]);
                else
                    ToastUtil.toast(SendAssetActivity.this, getString(R.string.code_not_right));
            }
        }
    }

    @PermissionYes(ConstantCode.REQUEST_CODE_OF_CAMERA)
    private void getCameraYes(List<String> grantedPermissions) {
        Intent intent = new Intent(SendAssetActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @PermissionNo(ConstantCode.REQUEST_CODE_OF_CAMERA)
    private void getCameraNo(List<String> deniedPermissions) {
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            AndPermission.defaultSettingDialog(this, ConstantCode.REQUEST_CODE_OF_CAMERA).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

}
