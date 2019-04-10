package com.gene.library.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaoLJ on 2018/7/18.
 * App父类
 */

public class BaseApp extends Application {

    private static final String TAG = "BaseApp";
    private static BaseApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static Context getAppContext() {
        return mApp;
    }

    public static Resources getAppResources() {
        return mApp.getResources();
    }

    private List<Activity> activities = new ArrayList<Activity>();

    public List<Activity> getActivities(){
        return activities;
    }

}
