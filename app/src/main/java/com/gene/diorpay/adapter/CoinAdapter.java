package com.gene.diorpay.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.library.pojo.Asset;
import com.gene.library.view.CommonVH;

import java.util.List;

/**
 * Created by MaoLJ on 2018/7/23.
 * 币种适配器
 */

public class CoinAdapter extends CommonListViewAdapter<Asset> {

    private static final String TAG = "CoinAdapter";
    private Activity mActivity;
    private int mPosition;

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public CoinAdapter(Activity activity, List<Asset> datas) {
        super(activity, datas);
        this.mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_coin, position);

        RelativeLayout layout = holder.getView(R.id.layout);
        ImageView logo = holder.getView(R.id.img_logo);
        TextView name = holder.getView(R.id.tv_name);

        Asset coin = mDatas.get(position);

        if (position == getPosition())
            layout.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_round_edit_white1));
        else
            layout.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_round_edit_gray5));
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
        name.setText(coin.getCoinName());

        return holder.getConvertView();
    }

}
