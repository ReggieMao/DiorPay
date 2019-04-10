package com.gene.diorpay.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gene.diorpay.activity.TradeRecordActivity;
import com.gene.library.pojo.Notification;

import java.util.List;

import com.gene.diorpay.R;
import com.gene.library.pojo.RealmNotification;
import com.gene.library.util.Util;
import com.gene.library.view.CommonVH;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by MaoLJ on 2018/7/23.
 * 转账通知适配器
 */

public class NotificationAdapter extends CommonListViewAdapter<Notification> {

    private static final String TAG = "NotificationAdapter";
    private Activity mActivity;
    private Realm mRealm = Realm.getDefaultInstance();
    public NotificationAdapter(Activity activity, List<Notification> datas) {
        super(activity, datas);
        this.mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_notification, position);

        TextView address = holder.getView(R.id.tv_address);
        TextView time1 = holder.getView(R.id.tv_time1);
        TextView time2 = holder.getView(R.id.tv_time2);
        TextView coin = holder.getView(R.id.tv_coin);
        ImageView outIn = holder.getView(R.id.img_out_in);
        LinearLayout layout = holder.getView(R.id.layout);

        Notification notice = mDatas.get(position);

        address.setText(notice.getAddress());
        time1.setText(notice.getTime());
        time2.setText(notice.getTime());
        coin.setText(Util.point(notice.getCount(), 6) + " " + notice.getCoinName());
        if (notice.isOut()) {
            coin.setTextColor(mActivity.getResources().getColor(R.color.c45));
            outIn.setImageResource(R.mipmap.out);
        } else {
            coin.setTextColor(mActivity.getResources().getColor(R.color.textBlue));
            outIn.setImageResource(R.mipmap.in);
        }

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!notice.isHasRead()) {
                    mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RealmResults<RealmNotification> list = mRealm.where(RealmNotification.class).equalTo("timeStamp", notice.getTimeStamp()).findAll();
                            for (RealmNotification realmNotification : list) {
                                realmNotification.setHasRead(true);
                            }
                        }
                    });
//                    UserPreference.putInt(UserPreference.TRADE_UNREAD, (UserPreference.getInt(UserPreference.TRADE_UNREAD, 0)) - 1);
                }
                Intent intent = new Intent(mActivity, TradeRecordActivity.class);
                intent.putExtra(TradeRecordActivity.NAME, notice.getCoinName());
                intent.putExtra(TradeRecordActivity.FROM_ASSET, false);
                intent.putExtra(TradeRecordActivity.WALLET_TYPE, notice.getWalletType());
                intent.putExtra(TradeRecordActivity.IS_OUT, notice.isOut());
                mActivity.startActivity(intent);
            }
        });

        return holder.getConvertView();
    }

}
