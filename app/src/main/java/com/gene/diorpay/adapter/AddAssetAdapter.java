package com.gene.diorpay.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.library.api.DiorPayApi;
import com.gene.library.pojo.Coin;
import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;
import com.gene.library.view.CommonVH;

import java.util.List;

/**
 * Created by MaoLJ on 2018/7/23.
 * 添加资产适配器
 */

public class AddAssetAdapter extends CommonListViewAdapter<Coin> {

    private static final String TAG = "AddAssetAdapter";
    private Activity mActivity;
    public AddAssetAdapter(Activity activity, List<Coin> datas) {
        super(activity, datas);
        this.mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_add_asset, position);

        LinearLayout layout = holder.getView(R.id.layout);
        TextView name = holder.getView(R.id.tv_name);
        ImageView logo = holder.getView(R.id.img_logo);
        ImageView select = holder.getView(R.id.img_select);

        Coin coin = mDatas.get(position);

        name.setText(coin.getCoinName());
        if (coin.isAdd()) { // 已添加过
            if (coin.isShow())
                select.setImageResource(R.mipmap.choose);
            else {
                select.setImageResource(0);
                select.setBackgroundResource(R.drawable.bg_round_text_gray2);
            }
        } else // 未添加过
            select.setBackgroundResource(R.drawable.bg_round_text_gray2);
        switch (coin.getCoinName()) {
            case "BTC":
                logo.setImageResource(R.mipmap.coin_btc);
                break;
            case "ETH":
                logo.setImageResource(R.mipmap.coin_eth);
                break;
            case "SAS":
                logo.setImageResource(R.mipmap.coin_sas);
                break;
            case "BZF":
                logo.setImageResource(R.mipmap.coin_bzf);
                break;
            case "DOB":
                logo.setImageResource(R.mipmap.coin_dob);
                break;
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coin.isAdd()) {
                    if (coin.isShow()) {
                        select.setImageResource(0);
                        select.setBackgroundResource(R.drawable.bg_round_text_gray2);
                        coin.setShow(false);
                        String sign = UserPreference.getString(UserPreference.SECRET, "") + "coinType=" + coin.getUserWalletType() + "&isShow=false" + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                        sign = Util.encrypt(sign);
                        DiorPayApi.getInstance().setWalletShow(Util.getNowTime(), sign, UserPreference.getString(UserPreference.SESSION_ID, ""), false, coin.getUserWalletType());
                    } else {
                        select.setImageResource(R.mipmap.choose);
                        coin.setShow(true);
                        String sign = UserPreference.getString(UserPreference.SECRET, "") + "coinType=" + coin.getUserWalletType() + "&isShow=true" + "&submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                        sign = Util.encrypt(sign);
                        DiorPayApi.getInstance().setWalletShow(Util.getNowTime(), sign, UserPreference.getString(UserPreference.SESSION_ID, ""), true, coin.getUserWalletType());
                    }
                } else {
                    select.setImageResource(R.mipmap.choose);
                    coin.setAdd(true);
                    String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + "&userWalletType=" +
                            coin.getUserWalletType() + UserPreference.getString(UserPreference.SECRET, "");
                    sign = Util.encrypt(sign);
                    DiorPayApi.getInstance().addWallet(UserPreference.getString(UserPreference.SESSION_ID, ""), sign, Util.getNowTime(), coin.getUserWalletType());
                }
            }
        });

        return holder.getConvertView();
    }

}
