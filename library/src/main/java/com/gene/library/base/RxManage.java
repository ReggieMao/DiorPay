package com.gene.library.base;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by MaoLJ on 2018/7/18.
 * 用于管理RxBus的事件和RxJava相关代码的生命周期处理
 */

public class RxManage {

    private static final String TAG = "RxManage";
    public RxBus mRxBus = RxBus.getInstance();
    private Map<String, Observable<?>> mObservables = new HashMap<>();// 管理观察者
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();// 管理订阅者

    public void on(String eventName, Action1 action1) {
        Observable<?> observable = mRxBus.register(eventName);
        mObservables.put(eventName, observable);
        mCompositeSubscription.add(observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, (e) -> e.printStackTrace()));
    }

    public void add(Subscription m) {
        mCompositeSubscription.add(m);
    }

    public void clear() {
        mCompositeSubscription.clear();
    }

    public void clearAndRemoveRxbusEvents() {
        clear();
        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet()) {
            mRxBus.unregister(entry.getKey(), entry.getValue());// 移除观察
        }
    }

    public void post(Object tag, Object content) {
        mRxBus.post(tag, content);
    }
}
