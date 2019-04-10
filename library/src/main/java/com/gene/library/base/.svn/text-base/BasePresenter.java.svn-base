package com.gene.library.base;

import android.content.Context;

/**
 * Created by MaoLJ on 2018/7/18.
 *
 */

public abstract class BasePresenter<E, T> {

    private static final String TAG = "BasePresenter";
    public Context context;
    public E mModel;
    public T mView;
    public RxManage mRxManage = new RxManage();

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public abstract void onStart();

    public void onDestroy() {
        mRxManage.clearAndRemoveRxbusEvents();
    }
}
