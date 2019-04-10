package com.gene.diorpay.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.library.api.DiorPayApi;
import com.gene.library.base.BaseActivity;
import com.gene.library.constant.Event;
import com.gene.library.pojo.Balance;
import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/8/15.
 * 交易详情页面
 */

public class TradeRecordActivity extends Base1Activity {

    private static final String TAG = "TradeRecordActivity";
    public static String TRADE = "trade";
    public static String NAME = "name";
    public static String ADDRESS = "address";
    public static String WALLET_TYPE = "wallet_type";
    public static String IS_OUT = "is_out";
    public static String FROM_ASSET = "from_asset";
    @Bind(R.id.img_out_in)
    ImageView mImgOutIn;
    @Bind(R.id.tv_out_in)
    TextView mTvOutIn;
    @Bind(R.id.tv_count)
    TextView mTvCount;
    @Bind(R.id.tv_receivable)
    TextView mTvReceivable;
    @Bind(R.id.tv_transfer)
    TextView mTvTransfer;
    @Bind(R.id.tv_poundage)
    TextView mTvPoundage;
    @Bind(R.id.tv_height)
    TextView mTvHeight;
    @Bind(R.id.tv_number)
    TextView mTvNumber;
    @Bind(R.id.tv_type)
    TextView mTvType;
    @Bind(R.id.tv_confirm)
    TextView mTvConfirm;
    @Bind(R.id.tv_finish)
    LinearLayout mTvFinish;

    @Override
    public int getLayoutId() {
        return R.layout.activity_trade_record;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
        Balance balance;
        if (getIntent().getBooleanExtra(FROM_ASSET, false)) {
            balance = (Balance) getIntent().getSerializableExtra(TRADE);
            setInfo(balance, 0);
        } else {
            String sign = UserPreference.getString(UserPreference.SECRET, "") + "count=" + 1 + "&submitTime=" + Util.getNowTime() +
                    "&userWalletType=" + getIntent().getStringExtra(WALLET_TYPE) + UserPreference.getString(UserPreference.SECRET, "");
            sign = Util.encrypt(sign);
            DiorPayApi.getInstance().findBalanceList(1, UserPreference.getString(UserPreference.SESSION_ID, ""), sign, Util.getNowTime(), getIntent().getStringExtra(WALLET_TYPE), 1);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.FIND_BALANCE_LIST1, new Action1<List<Balance>>() {
            @Override
            public void call(List<Balance> o) {
                Balance balance = o.get(0);
                setInfo(balance, 1);
            }
        });
    }

    private void setInfo(Balance balance, int where) {
        if (where == 0) {
            if (balance.getSendAddress().equals(getIntent().getStringExtra(ADDRESS))) {
                mImgOutIn.setImageResource(R.mipmap.out);
                mTvOutIn.setText(getString(R.string.coin_out));
            } else {
                mImgOutIn.setImageResource(R.mipmap.in);
                mTvOutIn.setText(getString(R.string.coin_in));
            }
        } else {
            if (getIntent().getBooleanExtra(IS_OUT, false)) {
                mImgOutIn.setImageResource(R.mipmap.out);
                mTvOutIn.setText(getString(R.string.coin_out));
            } else {
                mImgOutIn.setImageResource(R.mipmap.in);
                mTvOutIn.setText(getString(R.string.coin_in));
            }
        }
        mTvCount.setText(Util.point(balance.getChangeValue(), 6) + " " + getIntent().getStringExtra(NAME));
        mTvReceivable.setText(balance.getReceiveAddress());
        mTvTransfer.setText(balance.getSendAddress());
        String name = getIntent().getStringExtra(NAME);
        if (name.equals("BZF") || name.equals("DOB"))
            name = "SAS";
        mTvPoundage.setText(Util.point(balance.getServiceFee(), 6) + name);
        mTvHeight.setText(balance.getBlockHeight() + "");
        mTvNumber.setText(balance.getTransationId());
        mTvType.setText(balance.getChangeAction());
        if (balance.getBlockHeight() == 0) {
            mTvConfirm.setVisibility(View.VISIBLE);
            mTvFinish.setVisibility(View.GONE);
        } else {
            mTvConfirm.setVisibility(View.GONE);
            mTvFinish.setVisibility(View.VISIBLE);
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

}
