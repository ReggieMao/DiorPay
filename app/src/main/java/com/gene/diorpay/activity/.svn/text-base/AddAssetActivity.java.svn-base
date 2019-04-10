package com.gene.diorpay.activity;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.diorpay.adapter.AddAssetAdapter;
import com.gene.library.api.DiorPayApi;
import com.gene.library.base.BaseActivity;
import com.gene.library.constant.Event;
import com.gene.library.pojo.Asset;
import com.gene.library.pojo.Coin;
import com.gene.library.pojo.RealmAsset;
import com.gene.library.pojo.RealmCoin;
import com.gene.library.pull.AutoPullAbleGridView;
import com.gene.library.util.ToastUtil;
import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/8/1.
 * 添加资产页面
 */

public class AddAssetActivity extends Base1Activity {

    private static final String TAG = "AddAssetActivity";
    @Bind(R.id.gv_asset_list)
    AutoPullAbleGridView mListView;
    @Bind(R.id.tv_no_data)
    TextView mTvNoData;
    @Bind(R.id.pb)
    ProgressBar mPb;
    private AddAssetAdapter mAdapter;
    private Realm mRealm = Realm.getDefaultInstance();

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_asset;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
        showOriginList();
        getAssetList();
    }

    @Override
    protected void initData() {
        super.initData();
        rxManage.on(Event.FIND_ALL_WALLET_LIST, new Action1<List<Coin>>() {
            @Override
            public void call(List<Coin> o) {
                RealmCoin coin = new RealmCoin();
                for (int i = 0; i < o.size(); i ++) {
                    coin.setCoinName(o.get(i).getCoinName());
                    coin.setAdd(o.get(i).isAdd());
                    coin.setShow(o.get(i).isShow());
                    mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealmOrUpdate(coin);
                        }
                    });
                }
                showAssetList(o);
            }
        });

        rxManage.on(Event.ADD_WALLET, new Action1<Asset>() {
            @Override
            public void call(Asset o) {
                ToastUtil.toast(AddAssetActivity.this, getString(R.string.toast_add_asset_success));
//                getAssetList();
            }
        });

        rxManage.on(Event.SET_WALLET_SHOW, new Action1() {
            @Override
            public void call(Object o) {
//                getAssetList();
            }
        });
    }

    private void showOriginList() {
        RealmResults<RealmCoin> realmCoins = mRealm.where(RealmCoin.class).findAll();
        List<Coin> mCoinsList = new ArrayList<>();
        for (int i = 0; i < realmCoins.size(); i ++) {
            Coin asset = new Coin();
            asset.setCoinName(realmCoins.get(i).getCoinName());
            asset.setAdd(realmCoins.get(i).isAdd());
            asset.setShow(realmCoins.get(i).isShow());
            mCoinsList.add(asset);
        }
        showAssetList(mCoinsList);
    }

    private void showAssetList(List<Coin> list) {
        mPb.setVisibility(View.GONE);
        if (null != list && list.size() > 0) {
            mTvNoData.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            if (null == mAdapter) {
                mAdapter = new AddAssetAdapter(this, list);
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

    private void getAssetList() {
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        DiorPayApi.getInstance().findAllWalletList(sign, Util.getNowTime(), UserPreference.getString(UserPreference.SESSION_ID, ""));
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
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

}
