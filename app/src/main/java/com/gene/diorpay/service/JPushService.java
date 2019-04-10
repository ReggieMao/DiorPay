package com.gene.diorpay.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.gene.diorpay.activity.NoticeInfoActivity;
import com.gene.diorpay.activity.TradeRecordActivity;
import com.gene.library.base.RxManage;
import com.gene.library.constant.Event;
import com.gene.library.pojo.RealmNotification;
import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import io.realm.Realm;

/**
 * Created by MaoLJ on 2018/8/1.
 * 极光推送的服务
 */

public class JPushService extends BroadcastReceiver {

    private static final String TAG = "JPushService";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        String mType = "";
        String mNoticeUrl = "";
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (!Util.isEmpty(extra) && !extra.equals("{}")) {
                try {
                    JSONObject object = new JSONObject(extra);
                    mType = object.getString("type");
                    if (mType.equals("公告")) {
                        new RxManage().post(Event.NOTICE_UNREAD, null);
                    } else if (mType.equals("转账")) {
                        Realm mRealm = Realm.getDefaultInstance();
                        RealmNotification realmNotification = new RealmNotification();
                        realmNotification.setTimeStamp(System.currentTimeMillis());
                        realmNotification.setCoinType(object.getString("userWalletType"));
                        realmNotification.setHasRead(false);
                        realmNotification.setOut(false);
                        realmNotification.setTime(Util.getNowTime());
                        realmNotification.setBalance(Double.parseDouble(object.getString("balance")));
                        realmNotification.setCoinName(object.getString("coinName"));
                        if (!Util.isEmpty(object.getString("fromAddress")))
                            realmNotification.setOutAddress(object.getString("fromAddress"));
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(realmNotification);
                            }
                        });
//                        UserPreference.putInt(UserPreference.TRADE_UNREAD, UserPreference.getInt(UserPreference.TRADE_UNREAD, 0) + 1);
//                        new RxManage().post(Event.TRADE_UNREAD, null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String mTitle = bundle.getString(JPushInterface.EXTRA_ALERT);
            if (!Util.isEmpty(extra) && !extra.equals("{}")) {
                try {
                    JSONObject object = new JSONObject(extra);
                    mType = object.getString("type");
                    if (mType.equals("公告")) {
                        mNoticeUrl = object.getString("url") + "&appSessionId=" + UserPreference.getString(UserPreference.SESSION_ID, "");
                        Intent intent1 = new Intent(context, NoticeInfoActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent1.putExtra(NoticeInfoActivity.CONTENT, mTitle);
                        intent1.putExtra(NoticeInfoActivity.URL, mNoticeUrl);
                        context.startActivity(intent1);
                    } else if (mType.equals("转账")) {
                        Intent intent2 = new Intent(context, TradeRecordActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent2.putExtra(TradeRecordActivity.NAME, object.getString("coinName"));
                        intent2.putExtra(TradeRecordActivity.FROM_ASSET, false);
                        intent2.putExtra(TradeRecordActivity.WALLET_TYPE, object.getString("userWalletType"));
                        intent2.putExtra(TradeRecordActivity.IS_OUT, false);
                        context.startActivity(intent2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.d(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

}
