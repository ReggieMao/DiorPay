package com.gene.diorpay.fragment;

import android.content.Intent;
import android.text.Html;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.diorpay.activity.AddAssetActivity;
import com.gene.diorpay.activity.Base1Activity;
import com.gene.diorpay.adapter.AssetAdapter;
import com.gene.library.api.DiorPayApi;
import com.gene.library.constant.Event;
import com.gene.library.pojo.Asset;
import com.gene.library.pojo.RealmAsset;
import com.gene.library.pojo.Wallet;
import com.gene.library.pull.AutoPullAbleGridView;
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
 * Created by MaoLJ on 2018/7/23.
 * 资产页面
 */

public class AssetFragment extends Base1Fragment {

    private static final String TAG = "AssetFragment";
    private Base1Activity mBaseActivity;
    @Bind(R.id.gv_asset_list)
    AutoPullAbleGridView mListView;
    @Bind(R.id.tv_no_data)
    TextView mTvNoData;
    @Bind(R.id.image0)
    ImageView mImage0;
    @Bind(R.id.image)
    ImageView mImage;
    @Bind(R.id.ll_start)
    LinearLayout mLlStart;
    @Bind(R.id.ll_end)
    RelativeLayout mLlEnd;
    @Bind(R.id.tv_my_asset)
    TextView mTvAsset;
    @Bind(R.id.tv_my_asset1)
    TextView mTvAsset1;
    @Bind(R.id.pb)
    ProgressBar mPb;
    private AssetAdapter mAdapter;
    private TextView mHeadTitle;
    private TextView mHeadAsset;
    private String mPersonAsset;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_asset;
    }

    @Override
    public void initView() {
        mBaseActivity = (Base1Activity) getActivity();
        mLlStart.setVisibility(View.VISIBLE);
        mLlEnd.setVisibility(View.GONE);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_asset_head, null);
        mHeadTitle = view.findViewById(R.id.tv_head_title);
        mHeadAsset = view.findViewById(R.id.tv_head_asset);
        mListView.addHeaderView(view);
        getAssetList();

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private SparseArray recordSp = new SparseArray(0);
            private int mCurrentFirstVisibleItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView arg0, int arg1, int i1, int i2) {
                mCurrentFirstVisibleItem = arg1;
                View firstView = arg0.getChildAt(0);
                if (null != firstView) {
                    ItemRecord itemRecord = (ItemRecord) recordSp.get(arg1);
                    if (null == itemRecord) {
                        itemRecord = new ItemRecord();
                    }
                    itemRecord.height = firstView.getHeight();
                    itemRecord.top = firstView.getTop();
                    recordSp.append(arg1, itemRecord);
                    int h = getScrollY();
                    if (h > 70 && h < 110) {
                        mHeadTitle.setText(getString(R.string.personal_asset));
                        mHeadAsset.setText(mPersonAsset);
                        mLlStart.setVisibility(View.GONE);
                        mLlEnd.setVisibility(View.GONE);
                        mImage0.setVisibility(View.GONE);
                        mImage.setVisibility(View.VISIBLE);
                    } else if (h > 110 || h == 110) {
                        mHeadTitle.setText("");
                        mHeadAsset.setText("");
                        mLlStart.setVisibility(View.GONE);
                        mLlEnd.setVisibility(View.VISIBLE);
                        mImage0.setVisibility(View.GONE);
                        mImage.setVisibility(View.VISIBLE);
                    } else {
                        mHeadTitle.setText("");
                        mHeadAsset.setText("");
                        mLlStart.setVisibility(View.VISIBLE);
                        mLlEnd.setVisibility(View.GONE);
                        mImage0.setVisibility(View.VISIBLE);
                        mImage.setVisibility(View.GONE);
                    }
                }
            }

            private int getScrollY() {
                int height = 0;
                for (int i = 0; i < mCurrentFirstVisibleItem; i++) {
                    ItemRecord itemRecord = (ItemRecord) recordSp.get(i);
                    height += itemRecord.height;
                }
                ItemRecord itemRecord = (ItemRecord) recordSp.get(mCurrentFirstVisibleItem);
                if (null == itemRecord) {
                    itemRecord = new ItemRecord();
                }
                return height - itemRecord.top;
            }

            class ItemRecord {
                int height = 0;
                int top = 0;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mBaseActivity.rxManage.on(Event.FIND_LIST, new Action1<Wallet>() {
            @Override
            public void call(Wallet o) {
                if (UserPreference.getInt(UserPreference.LANGUAGE, 1) == 1) {
                    mPersonAsset = Html.fromHtml("&yen;") + Util.point(o.getBalance(), 2);
                    mTvAsset.setText(Html.fromHtml("&yen;") + Util.point(o.getBalance(), 2));
                    mTvAsset1.setText(Html.fromHtml("&yen;") + Util.point(o.getBalance(), 2));
                } else {
                    mPersonAsset = "$ " + Util.point1(o.getBalance() / Double.parseDouble(UserPreference.getString(UserPreference.EXCHANGE, "")), 2);
                    mTvAsset.setText("$ " + Util.point1(o.getBalance() / Double.parseDouble(UserPreference.getString(UserPreference.EXCHANGE, "")), 2));
                    mTvAsset1.setText("$ " + Util.point1(o.getBalance() / Double.parseDouble(UserPreference.getString(UserPreference.EXCHANGE, "")), 2));
                }
                showAssetList(o.getList());
                setRealm(o.getList());
            }
        });
    }

    private void setRealm(List<Asset> assets) {
        Realm realm = Realm.getDefaultInstance();

        //测试用删除缓存
//        final RealmResults<RealmTrade> results = realm.where(RealmTrade.class).findAll();
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                results.deleteAllFromRealm();
//            }
//        });

        RealmAsset asset = new RealmAsset();
        RealmResults<RealmAsset> realmAssets = realm.where(RealmAsset.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmAssets.deleteAllFromRealm();
            }
        });
        for (int i = 0; i < assets.size(); i ++) {
            if (assets.get(i).isShow()) {
                asset.setCoinName(assets.get(i).getCoinName());
                asset.setAddress(assets.get(i).getUserWalletAddress());
                asset.setBalance(assets.get(i).getBalance());
                asset.setAvalBalance(assets.get(i).getAvalBalance());
                asset.setCoinType(assets.get(i).getUserWalletType());
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(asset);
                    }
                });
            }
        }
    }

    private void getAssetList() {
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        DiorPayApi.getInstance().findList(UserPreference.getString(UserPreference.SESSION_ID, ""), sign, Util.getNowTime());
    }

    private void showAssetList(List<Asset> list) {
        mPb.setVisibility(View.GONE);
        if (null == mAdapter) {
            mAdapter = new AssetAdapter(getActivity(), list);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(list);
        }
        if (null != list && list.size() > 0) {
            if (allRemove(list))
                mTvNoData.setVisibility(View.VISIBLE);
            else
                mTvNoData.setVisibility(View.GONE);
        } else {
            mTvNoData.setVisibility(View.VISIBLE);
        }
    }

    private boolean allRemove(List<Asset> list) {
        List<Boolean> booleanList = new ArrayList<>();
        for (int i = 0; i < list.size(); i ++) {
            if (list.get(i).isShow())
                booleanList.add(true);
            else
                booleanList.add(false);
        }
        return !booleanList.contains(true);
    }

    @OnClick({R.id.img_add})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_add:
                startActivity(new Intent(getActivity(), AddAssetActivity.class));
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getAssetList();
    }

}
