package com.gene.diorpay.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gene.diorpay.view.HorizontalListView;
import com.gene.library.base.BaseActivity;
import com.gene.library.pojo.RealmAsset;
import com.gene.library.util.Util;
import com.google.zxing.WriterException;
import com.yzq.zxinglibrary.encode.CodeCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import com.gene.diorpay.R;
import com.gene.diorpay.adapter.CoinAdapter;
import com.gene.library.pojo.Asset;
import com.gene.library.util.ToastUtil;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by MaoLJ on 2018/7/20.
 * 收币页面
 */

public class CollectCoinActivity extends Base1Activity {

    private static final String TAG = "SendCoinActivity";
    @Bind(R.id.list_view)
    HorizontalListView mListView;
    @Bind(R.id.img_logo)
    ImageView mImgLogo;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    @Bind(R.id.img_code)
    ImageView mImgCode;
    private List<Asset> mAssetList;
    private CoinAdapter mAdapter;
    private int mCurrentItem = 0;
    private Realm mRealm = Realm.getDefaultInstance();

    @Override
    public int getLayoutId() {
        return R.layout.activity_collect_coin;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
        getCoinList();
    }

    @Override
    protected void initData() {
        super.initData();

    }

    private void getCoinList() {
        RealmResults<RealmAsset> realmAssets = mRealm.where(RealmAsset.class).findAll();
        mAssetList = new ArrayList<>();
        for (int i = 0; i < realmAssets.size(); i ++) {
            Asset asset = new Asset();
            asset.setCoinName(realmAssets.get(i).getCoinName());
            asset.setUserWalletAddress(realmAssets.get(i).getAddress());
            asset.setUserWalletType(realmAssets.get(i).getCoinType());
            mAssetList.add(asset);
        }
        showCoinList(mAssetList);
        if (mAssetList.size() > 0)
            showCoinInfo(0);
        else
            ToastUtil.toast(this, getString(R.string.add_wallet));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentItem = position;
                mAdapter.setPosition(mCurrentItem);
                mAdapter.notifyDataSetChanged();
                showCoinInfo(mCurrentItem);
            }
        });
    }

    private void showCoinInfo(int position) {
        switch (mAssetList.get(position).getCoinName()) {
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
        mTvName.setText(mAssetList.get(position).getCoinName() + getString(R.string.address0));
        mTvAddress.setText(mAssetList.get(position).getUserWalletAddress());
        String mCoinType = mAssetList.get(position).getUserWalletType();
        Bitmap bitmap = null;
        try {
            bitmap = CodeCreator.createQRCode(mAssetList.get(position).getCoinName() + " " + mCoinType + " " + mTvAddress.getText().toString(), 400, 400, null);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        mImgCode.setImageBitmap(bitmap);
    }

    private void showCoinList(List<Asset> list) {
        if (null != list && list.size() > 0) {
            if (null == mAdapter) {
                mAdapter = new CoinAdapter(this, list);
                mListView.setAdapter(mAdapter);
            } else {
                mAdapter.setItems(list);
            }
        }
    }

    @OnClick({R.id.img_back, R.id.tv_copy})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_copy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(mTvAddress.getText().toString());
                ToastUtil.toast(this, getString(R.string.toast_copy_success));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

}
