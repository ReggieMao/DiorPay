package com.gene.diorpay.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.diorpay.activity.TradeRecordActivity;
import com.gene.library.pojo.Balance;
import com.gene.library.util.Util;
import com.gene.library.view.CommonVH;

import java.util.List;

/**
 * Created by MaoLJ on 2018/7/23.
 * 交易记录适配器
 */

public class TransactionAdapter extends CommonListViewAdapter<Balance> {

    private static final String TAG = "TransactionAdapter";
    private Activity mActivity;
    private String mCoinName;
    private String mAddress;
    public TransactionAdapter(Activity activity, List<Balance> datas, String coinName, String address) {
        super(activity, datas);
        this.mActivity = activity;
        this.mCoinName = coinName;
        this.mAddress = address;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_transaction, position);

        LinearLayout layout = holder.getView(R.id.layout);
        View view = holder.getView(R.id.view);
        TextView outIn = holder.getView(R.id.tv_out_in);
        TextView time = holder.getView(R.id.tv_time);
        TextView count = holder.getView(R.id.tv_count);
        TextView remain = holder.getView(R.id.tv_remain);

        Balance balance = mDatas.get(position);

        if (balance.getSendAddress().equals(mAddress)) {
            view.setBackgroundColor(mActivity.getResources().getColor(R.color.green));
            outIn.setText(mActivity.getString(R.string.out) + mCoinName);
            count.setText("-" + Util.point(balance.getChangeValue(), 6));
        } else {
            view.setBackgroundColor(mActivity.getResources().getColor(R.color.textBlue));
            outIn.setText(mActivity.getString(R.string.in) + mCoinName);
            count.setText("+" + Util.point(balance.getChangeValue(), 6));
        }
        time.setText(balance.getCreateDate());
//        remain.setText(mActivity.getString(R.string.remain) + Util.point(balance.));
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, TradeRecordActivity.class);
                intent.putExtra(TradeRecordActivity.TRADE, balance);
                intent.putExtra(TradeRecordActivity.NAME, mCoinName);
                intent.putExtra(TradeRecordActivity.ADDRESS, mAddress);
                intent.putExtra(TradeRecordActivity.FROM_ASSET, true);
                mActivity.startActivity(intent);
            }
        });

        return holder.getConvertView();
    }

}
