package com.gene.library.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.gene.library.base.BaseApp;

/**
 * Created by MaoLJ on 2018/7/18.
 * 本地保存
 */

public class UserPreference {

    public static final String SESSION_ID = "session_id";
    public static final String SECRET = "secret";
    public static final String GESTURE_PWD = "gesture_pwd";
    public static final String PWD_ERR_COUNT = "pwd_err_count";
    public static final String ACCOUNT = "account";
    public static final String LANGUAGE = "language";
    public static final String SHOULD_PWD = "should_pwd";
//    public static final String TRADE_UNREAD = "trade_unread";
    public static final String EXCHANGE = "exchange";

    public static String sp_name;

    private static SharedPreferences getSharePreferences() {
        return BaseApp.getAppContext().getSharedPreferences(sp_name, Context.MODE_PRIVATE);
    }

    public static void putInt(String key, int value) {
        getSharePreferences().edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int value){
        return getSharePreferences().getInt(key,value);
    }

    public static void putString(String key, String value) {
        getSharePreferences().edit().putString(key, value).apply();
    }

    public static String getString(String key, String def) {
        return getSharePreferences().getString(key, def);
    }

}
