package com.gene.diorpay.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.diorpay.adapter.TransactionAdapter;
import com.gene.library.api.DiorPayApi;
import com.gene.library.base.BaseActivity;
import com.gene.library.constant.ConstantCode;
import com.gene.library.constant.Event;
import com.gene.library.pojo.Balance;
import com.gene.library.pojo.Income;
import com.gene.library.pojo.RealmTrade;
import com.gene.library.pull.AutoPullAbleGridView;
import com.gene.library.pull.AutoPullToRefreshLayout;
import com.gene.library.util.ToastUtil;
import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/7/23.
 * 资产详情页面
 */

public class AssetInfoActivity extends Base1Activity {

    private static final String TAG = "AssetInfoActivity";
    private int REQUEST_CODE_SCAN = 1001;
    public static String COIN_NAME = "coin_name";
    public static String COIN_RMB = "coin_rmb";
    public static String COIN_BALANCE = "coin_balance";
    public static String USER_WALLET_TYPE = "user_wallet_type";
    public static String USER_WALLET_ADDRESS = "user_wallet_address";
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.img_logo)
    ImageView mImgLogo;
    @Bind(R.id.tv_rmb)
    TextView mTvRmb;
    @Bind(R.id.pb)
    ProgressBar mPb;
    @Bind(R.id.gv_transaction_list)
    AutoPullAbleGridView mListView;
    @Bind(R.id.refresh_view)
    AutoPullToRefreshLayout mPullLayout;
    @Bind(R.id.tv_no_data)
    TextView mTvNoData;
    private TransactionAdapter mAdapter;
    private String mAddress = "";
    private String mCount;
    private boolean mFlag = false;
    private boolean mDownFlag = false;
    private int mCurrentPage = 0;
    private String[] mAll;
    private int mOriginSize;
    private int mNowSize;
    private Realm mRealm = Realm.getDefaultInstance();

    @Override
    public int getLayoutId() {
        return R.layout.activity_asset_info;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + "&userWalletType=" + getIntent().getStringExtra(USER_WALLET_TYPE)
                + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        DiorPayApi.getInstance().getIncome(0, UserPreference.getString(UserPreference.SESSION_ID, ""), sign, Util.getNowTime(), getIntent().getStringExtra(USER_WALLET_TYPE));
        mTvTitle.setText(getIntent().getStringExtra(COIN_NAME) + getResources().getString(R.string.asset));
        switch (getIntent().getStringExtra(COIN_NAME)) {
            case "BTC":
                mImgLogo.setImageResource(R.mipmap.coin_btc);
                break;
            case "ETH":
                mImgLogo.setImageResource(R.mipmap.coin_eth);
                break;
            case "SAS":
                mImgLogo.setImageResource(R.mipmap.coin_sas);
                break;
            case "BZF":
                mImgLogo.setImageResource(R.mipmap.coin_bzf);
                break;
            case "DOB":
                mImgLogo.setImageResource(R.mipmap.coin_dob);
                break;
        }
        mTvRmb.setText(getIntent().getStringExtra(COIN_RMB));
        mTvName.setText(getIntent().getStringExtra(COIN_BALANCE) + " " + getIntent().getStringExtra(COIN_NAME));
        mPullLayout.setOnRefreshListener(listener);
//        mListView.setOnLoadListener(autoListener);
        mListView.finishLoading(AutoPullAbleGridView.INIT);
        mTvNoData.setVisibility(View.GONE);
        getRealm();
        getTransactionList(mCurrentPage, 0);
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.GET_INCOME, new Action1<Income>() {
            @Override
            public void call(Income o) {
                mAddress = Util.isEmpty(o.getUserWalletAddress()) ? getIntent().getStringExtra(USER_WALLET_ADDRESS) : o.getUserWalletAddress();
                mCount = Util.point(o.getAvalBalance(), 6);
                mTvName.setText(Util.point(o.getBalance(), 6) + " " + getIntent().getStringExtra(COIN_NAME));
            }
        });

        rxManage.on(Event.FIND_BALANCE_LIST, new Action1<List<Balance>>() {
            @Override
            public void call(List<Balance> o) {
                mOriginSize = o.size();
                showTransactionList(o, mCurrentPage, mFlag);
                if (mCurrentPage == 0) {
                    setRealm(o);
                }
            }
        });

        rxManage.on(Event.FIND_BALANCE_LIST2, new Action1<List<Balance>>() {
            @Override
            public void call(List<Balance> o) {
                mNowSize = o.size();
                if (mNowSize != mOriginSize) {//数据有更新
                    showTransactionList(o, mCurrentPage, mFlag);
                    RealmResults<RealmTrade> results = mRealm.where(RealmTrade.class).equalTo("nameAddress", getIntent().getStringExtra(COIN_NAME) + "-" + (Util.isEmpty(mAddress) ? getIntent().getStringExtra(USER_WALLET_ADDRESS) : mAddress)).findAll();
                    mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            results.deleteAllFromRealm();
                        }
                    });
                    if (mCurrentPage == 0) {
                        setRealm(o);
                    }
                }
            }
        });

        rxManage.on(Event.GET_INCOME2, new Action1<Income>() {
            @Override
            public void call(Income o) {
                Intent intent = new Intent(AssetInfoActivity.this, SendAssetActivity.class);
                intent.putExtra(SendAssetActivity.COIN_NAME, mAll[0]);
                intent.putExtra(SendAssetActivity.USER_WALLET_TYPE, mAll[1]);
                intent.putExtra(SendAssetActivity.USER_WALLET_ADDRESS, mAll[2]);
                intent.putExtra(SendAssetActivity.COIN_COUNT, Util.point(o.getAvalBalance(), 6));
                startActivity(intent);
            }
        });
    }

    private void getRealm() {
        RealmResults<RealmTrade> realmTrades = mRealm.where(RealmTrade.class).equalTo("nameAddress", getIntent().getStringExtra(COIN_NAME) + "-" + (Util.isEmpty(mAddress) ? getIntent().getStringExtra(USER_WALLET_ADDRESS) : mAddress)).findAll();
        if (realmTrades.size() > 0) {
            List<Balance> balances = new ArrayList<>();
            for (int i = 0; i < realmTrades.size(); i ++) {
                Balance balance = new Balance();
                balance.setBeforeChangeBalance(realmTrades.get(i).getBeforeChangeBalance());
                balance.setBlockConformations(realmTrades.get(i).getBlockConformations());
                balance.setBlockDate(realmTrades.get(i).getBlockDate());
                balance.setBlockHeight(realmTrades.get(i).getBlockHeight());
                balance.setBookCode(realmTrades.get(i).getBookCode());
                balance.setChangeAction(realmTrades.get(i).getChangeAction());
                balance.setChangeValue(realmTrades.get(i).getChangeValue());
                balance.setContractAddress(realmTrades.get(i).getContractAddress());
                balance.setCreateDate(realmTrades.get(i).getCreateDate());
                balance.setId(realmTrades.get(i).getId());
                balance.setReceiveAddress(realmTrades.get(i).getReceiveAddress());
                balance.setRemark(realmTrades.get(i).getRemark());
                balance.setSendAddress(realmTrades.get(i).getSendAddress());
                balance.setServiceFee(realmTrades.get(i).getServiceFee());
                balance.setStatus(realmTrades.get(i).getStatus());
                balance.setTransationId(realmTrades.get(i).getTransationId());
                balance.setUpdateDate(realmTrades.get(i).getUpdateDate());
                balance.setUserId(realmTrades.get(i).getUserId());
                balance.setIndex(realmTrades.get(i).getIndex());
                balances.add(balance);
            }
            showTransactionList(balances, 0, false);
        }
    }

    private void setRealm(List<Balance> balances) {
        RealmTrade trade = new RealmTrade();
        for (int i = 0; i < balances.size(); i ++) {
            trade.setNameAddress(getIntent().getStringExtra(COIN_NAME) + "-" + (Util.isEmpty(mAddress) ? getIntent().getStringExtra(USER_WALLET_ADDRESS) : mAddress));
            trade.setBeforeChangeBalance(balances.get(i).getBeforeChangeBalance());
            trade.setBlockConformations(balances.get(i).getBlockConformations());
            trade.setBlockDate(balances.get(i).getBlockDate());
            trade.setBlockHeight(balances.get(i).getBlockHeight());
            trade.setBookCode(balances.get(i).getBookCode());
            trade.setChangeAction(balances.get(i).getChangeAction());
            trade.setChangeValue(balances.get(i).getChangeValue());
            trade.setContractAddress(balances.get(i).getContractAddress());
            trade.setCreateDate(balances.get(i).getCreateDate());
            trade.setId(balances.get(i).getId());
            trade.setReceiveAddress(balances.get(i).getReceiveAddress());
            trade.setRemark(balances.get(i).getRemark());
            trade.setSendAddress(balances.get(i).getSendAddress());
            trade.setServiceFee(balances.get(i).getServiceFee());
            trade.setStatus(balances.get(i).getStatus());
            trade.setTransationId(balances.get(i).getTransationId());
            trade.setIndex(balances.get(i).getTransationId() + "-" + balances.get(i).getCreateDate());
            trade.setUpdateDate(balances.get(i).getUpdateDate());
            trade.setUserId(balances.get(i).getUserId());
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(trade);
                }
            });
        }
    }

    private void getTransactionList(int page, int where) {
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "count=" + page + "&submitTime=" + Util.getNowTime() +
                "&userWalletType=" + getIntent().getStringExtra(USER_WALLET_TYPE) + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        DiorPayApi.getInstance().findBalanceList(where, UserPreference.getString(UserPreference.SESSION_ID, ""), sign, Util.getNowTime(), getIntent().getStringExtra(USER_WALLET_TYPE), page);
    }

    private void showTransactionList(List<Balance> list, int page, boolean flag) {
        mPb.setVisibility(View.GONE);
        if (mDownFlag) {
            mDownFlag = false;
            mPullLayout.refreshFinish(AutoPullToRefreshLayout.SUCCEED);
        }
        if (null != list && list.size() > 0) {
            mTvNoData.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            mListView.setLoadDataFlag(true);
            mListView.finishLoading(AutoPullAbleGridView.INIT);
            if (null == mAdapter) {
                mAdapter = new TransactionAdapter(this, list, getIntent().getStringExtra(COIN_NAME), Util.isEmpty(mAddress) ? getIntent().getStringExtra(USER_WALLET_ADDRESS) : mAddress);
                mListView.setAdapter(mAdapter);
            } else {
                if (page == 0) {
                    mAdapter.setItems(list);
                } else {
                    mAdapter.addItems(list);
                }
            }
        } else {
            mListView.setLoadDataFlag(false);
            mListView.finishLoading(AutoPullAbleGridView.FINISH);
            if (flag) {
                if (page == 0) {
                    mTvNoData.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                }
            } else {
                if (null != mAdapter) mAdapter.setItems(list);
                if (page == 0) {
                    mTvNoData.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                }
            }
        }
    }

    private AutoPullToRefreshLayout.OnRefreshListener listener = new AutoPullToRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh(AutoPullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mDownFlag = true;
                    mCurrentPage = 0;
                    getTransactionList(mCurrentPage, 0);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }
    };

//    AutoPullAbleGridView.OnLoadListener autoListener = new AutoPullAbleGridView.OnLoadListener() {
//        @Override
//        public void onLoad(AutoPullAbleGridView pullAbleListView) {
//            // 上拉加载
//            new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    mFlag = true;
//                    mCurrentPage ++;
//                    getTransactionList(mCurrentPage, 0);
//                }
//            }.sendEmptyMessageDelayed(0, 2000);
//        }
//    };

    @OnClick({R.id.img_back, R.id.rl_collect, R.id.rl_send, R.id.img_sweep})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_collect:
                Intent intent0 = new Intent(this, CollectAssetActivity.class);
                intent0.putExtra(CollectAssetActivity.COIN_NAME, getIntent().getStringExtra(COIN_NAME));
                intent0.putExtra(CollectAssetActivity.COIN_ADDRESS, Util.isEmpty(mAddress) ? getIntent().getStringExtra(USER_WALLET_ADDRESS) : mAddress);
                intent0.putExtra(CollectAssetActivity.COIN_TYPE, getIntent().getStringExtra(USER_WALLET_TYPE));
                startActivity(intent0);
                break;
            case R.id.rl_send:
                Intent intent = new Intent(this, SendAssetActivity.class);
                intent.putExtra(SendAssetActivity.COIN_NAME, getIntent().getStringExtra(COIN_NAME));
                intent.putExtra(SendAssetActivity.COIN_COUNT, mCount);
                intent.putExtra(SendAssetActivity.USER_WALLET_TYPE, getIntent().getStringExtra(USER_WALLET_TYPE));
                startActivity(intent);
                break;
            case R.id.img_sweep:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 先判断是否有权限
                    if (AndPermission.hasPermission(AssetInfoActivity.this, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // 有权限
                        Intent intent1 = new Intent(AssetInfoActivity.this, CaptureActivity.class);
                        startActivityForResult(intent1, REQUEST_CODE_SCAN);
                    } else {
                        // 申请权限
                        AndPermission.with(AssetInfoActivity.this).requestCode(ConstantCode.REQUEST_CODE_OF_CAMERA).callback(AssetInfoActivity.this)
                                .permission(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE).start();
                    }
                } else {
                    Intent intent1 = new Intent(AssetInfoActivity.this, CaptureActivity.class);
                    startActivityForResult(intent1, REQUEST_CODE_SCAN);
                }
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
                if (all.length == 3) {
                    mAll = all;
                    String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + "&userWalletType=" + all[1]
                            + UserPreference.getString(UserPreference.SECRET, "");
                    sign = Util.encrypt(sign);
                    DiorPayApi.getInstance().getIncome(2, UserPreference.getString(UserPreference.SESSION_ID, ""), sign, Util.getNowTime(), all[1]);
                } else
                    ToastUtil.toast(AssetInfoActivity.this, getString(R.string.code_not_right));
            }
        }
    }

    @PermissionYes(ConstantCode.REQUEST_CODE_OF_CAMERA)
    private void getCameraYes(List<String> grantedPermissions) {
        Intent intent = new Intent(AssetInfoActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @PermissionNo(ConstantCode.REQUEST_CODE_OF_CAMERA)
    private void getCameraNo(List<String> deniedPermissions) {
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            AndPermission.defaultSettingDialog(this, ConstantCode.REQUEST_CODE_OF_CAMERA).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + "&userWalletType=" + getIntent().getStringExtra(USER_WALLET_TYPE)
                + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        DiorPayApi.getInstance().getIncome(0, UserPreference.getString(UserPreference.SESSION_ID, ""), sign, Util.getNowTime(), getIntent().getStringExtra(USER_WALLET_TYPE));
        getTransactionList(mCurrentPage, 2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

}
