package com.gene.diorpay.fragment;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.diorpay.activity.Base1Activity;
import com.gene.diorpay.adapter.TradeAdapter;
import com.gene.library.api.DiorPayApi;
import com.gene.library.constant.Event;
import com.gene.library.pojo.Trade;
import com.gene.library.pull.AutoPullAbleGridView;
import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;

import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/7/23.
 * 交易页面
 */

public class TradeFragment extends Base1Fragment {

    private static final String TAG = "TradeFragment";
    private Base1Activity mBaseActivity;
    private int mPosition = -1;
    @Bind(R.id.gv_trade_list)
    AutoPullAbleGridView mListView;
    @Bind(R.id.tv_no_data)
    TextView mTvNoData;
    @Bind(R.id.pb)
    ProgressBar mPb;
    private TradeAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_trade;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onChangeFragment(int position) {
        super.onChangeFragment(position);
        if (mPosition == -1) {
            mPosition = position;
            initViewDelayedLoading();
        }
    }

    private void initViewDelayedLoading() {
        mBaseActivity = (Base1Activity) getActivity();
        getTradeList();
        initDataDelayedLoading();
    }

    private void initDataDelayedLoading() {
        mBaseActivity.rxManage.on(Event.GET_TRADE_INFO, new Action1<List<Trade>>() {
            @Override
            public void call(List<Trade> o) {
                showTradeList(o);
            }
        });
    }

    private void getTradeList() {
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        DiorPayApi.getInstance().getTradeInfo(Util.getNowTime(), sign, UserPreference.getString(UserPreference.SESSION_ID, ""));
    }

    private void showTradeList(List<Trade> list) {
        mPb.setVisibility(View.GONE);
        if (null != list && list.size() > 0) {
            mTvNoData.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            if (null == mAdapter) {
                mAdapter = new TradeAdapter(getActivity(), list);
                mListView.setAdapter(mAdapter);
            } else {
                mAdapter.setItems(list);
            }
        } else {
            mTvNoData.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            if (null != mAdapter) mAdapter.setItems(list);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getTradeList();
    }

}
