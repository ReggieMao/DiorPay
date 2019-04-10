package com.gene.diorpay.application;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.gene.library.base.BaseApp;
import com.gene.library.util.UserPreference;

import cn.jpush.android.api.JPushInterface;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by MaoLJ on 2018/7/18.
 * 应用
 */

public class MyApplication extends BaseApp {

    private static final String TAG = "MyApplication";
    private static MyApplication mApplication;
    private static Context sContext;

    // 为适配Android5.0以下而加入
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        
        UserPreference.sp_name = "ct_test";

        //Realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("DiorPay.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        //JPush
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        mApplication = this;
    }

    public static MyApplication getMyApplicationInstance() {
        return mApplication;
    }

}
