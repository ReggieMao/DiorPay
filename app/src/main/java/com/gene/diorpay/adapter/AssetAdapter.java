package com.gene.diorpay.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.gene.diorpay.R;
import com.gene.diorpay.activity.AssetInfoActivity;
import com.gene.library.pojo.Asset;
import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;
import com.gene.library.view.CommonVH;

/**
 * Created by MaoLJ on 2018/7/23.
 * 资产适配器
 */

public class AssetAdapter extends CommonListViewAdapter<Asset> {

    private static final String TAG = "AssetAdapter";
    private Activity mActivity;
    public AssetAdapter(Activity activity, List<Asset> datas) {
        super(activity, datas);
        this.mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_asset, position);

        LinearLayout layout = holder.getView(R.id.layout);
        TextView name = holder.getView(R.id.tv_name);
        TextView count = holder.getView(R.id.tv_count);
//        ImageView imgUpDown = holder.getView(R.id.img_up_down);
//        TextView tvUpDown = holder.getView(R.id.tv_up_down);
        TextView rmb = holder.getView(R.id.tv_rmb);
        ImageView logo = holder.getView(R.id.img_logo);

        Asset asset = mDatas.get(position);

        if (asset.isShow())
            layout.setVisibility(View.VISIBLE);
        else
            layout.setVisibility(View.GONE);
        name.setText(asset.getCoinName());
        count.setText(Util.point(asset.getBalance(), 6));
        if (UserPreference.getInt(UserPreference.LANGUAGE, 1) == 1)
            rmb.setText("≈ " + Html.fromHtml("&yen;") + Util.point(asset.getRmbBalance(), 2));
        else
            rmb.setText("≈ $ " + Util.point1(asset.getRmbBalance() / Double.parseDouble(UserPreference.getString(UserPreference.EXCHANGE, "")), 2));
        switch (asset.getCoinName()) {
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
//        if (!Util.isEmpty(asset.getCoinUpDownNum())) {
//            if (!asset.getCoinUpDownNum().startsWith("-")) {
//                tvUpDown.setText("+ " + Util.point(Double.parseDouble(asset.getCoinUpDownNum().substring(0, asset.getCoinUpDownNum().length() - 1)), 2) + "%");
//                tvUpDown.setTextColor(mActivity.getResources().getColor(R.color.red));
//                imgUpDown.setImageResource(R.mipmap.red_up);
//            } else {
//                tvUpDown.setText(Util.point(Double.parseDouble(asset.getCoinUpDownNum().substring(0, asset.getCoinUpDownNum().length() - 1)), 2) + "%");
//                tvUpDown.setTextColor(mActivity.getResources().getColor(R.color.deep_green));
//                imgUpDown.setImageResource(R.mipmap.green_down);
//            }
//        } else {
//            tvUpDown.setText("0.00%");
//            tvUpDown.setTextColor(mActivity.getResources().getColor(R.color.gray666));
//            imgUpDown.setImageResource(R.mipmap.flat);
//        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, AssetInfoActivity.class);
                intent.putExtra(AssetInfoActivity.USER_WALLET_TYPE, asset.getUserWalletType());
                intent.putExtra(AssetInfoActivity.USER_WALLET_ADDRESS, asset.getUserWalletAddress());
                intent.putExtra(AssetInfoActivity.COIN_RMB, rmb.getText().toString());
                intent.putExtra(AssetInfoActivity.COIN_NAME, asset.getCoinName());
                intent.putExtra(AssetInfoActivity.COIN_BALANCE, Util.point(asset.getBalance(), 6));
                mActivity.startActivity(intent);
            }
        });

        return holder.getConvertView();
    }

}
