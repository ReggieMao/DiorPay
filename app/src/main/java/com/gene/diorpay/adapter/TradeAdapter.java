package com.gene.diorpay.adapter;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;

import java.util.List;

import com.gene.diorpay.R;
import com.gene.library.pojo.Trade;
import com.gene.library.view.CommonVH;

/**
 * Created by MaoLJ on 2018/7/23.
 * 交易适配器
 */

public class TradeAdapter extends CommonListViewAdapter<Trade> {

    private static final String TAG = "TradeAdapter";
    private Activity mActivity;
    public TradeAdapter(Activity activity, List<Trade> datas) {
        super(activity, datas);
        this.mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_trade, position);

        TextView name = holder.getView(R.id.tv_name);
        TextView money = holder.getView(R.id.tv_money);
        TextView upDown = holder.getView(R.id.tv_up_down);
        TextView volume = holder.getView(R.id.tv_volume);

        Trade trade = mDatas.get(position);

        name.setText(trade.getName().contains("-") ? trade.getName().substring(0, trade.getName().indexOf("-")) : trade.getName());
        if (UserPreference.getInt(UserPreference.LANGUAGE, 1) == 1) {
            money.setText(Html.fromHtml("&yen;") + Util.point(trade.getPrice_cny(), 2));
            volume.setText(mActivity.getString(R.string.volume) + Util.turnover(1, trade.getDeal_24H()));
        } else {
            money.setText("$ " + Util.point1(trade.getPrice_cny() / Double.parseDouble(UserPreference.getString(UserPreference.EXCHANGE, "")), 2));
            volume.setText(mActivity.getString(R.string.volume) + Util.turnover(2, trade.getDeal_24H()));
        }
        if (!Util.isEmpty(upDown.getText().toString()) || Double.parseDouble(trade.getChg_24H().substring(0, trade.getChg_24H().length() - 1)) != 0) {
            if (trade.getChg_24H().startsWith("-")) {
                upDown.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.bg_round_text_red));
                upDown.setText(Util.point(Double.parseDouble(trade.getChg_24H().substring(0, trade.getChg_24H().length() - 1)), 2) + "%");
            } else {
                upDown.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.bg_round_text_deep_green));
                upDown.setText("+" + Util.point(Double.parseDouble(trade.getChg_24H().substring(0, trade.getChg_24H().length() - 1)), 2) + "%");
            }
        } else {
            upDown.setText("0.00%");
            upDown.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.bg_round_text_gray3));
        }

        return holder.getConvertView();
    }

}
