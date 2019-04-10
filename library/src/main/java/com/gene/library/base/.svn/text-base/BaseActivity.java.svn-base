package com.gene.library.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gene.library.constant.Event;
import com.gene.library.exception.ApiException;
import com.gene.library.exception.ExceptionHandler;
import com.gene.library.svprogresshud.SVProgressHUD;
import com.gene.library.util.AndroidWorkaround;
import com.gene.library.R;

import java.util.List;

import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/7/18.
 * Activity父类
 */

public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    public T mPresenter;
    public E mModel;
    public Context mContext;
    public RxManage rxManage;

    public final static int F_DISSMIS_DIALOG = 11111;
    public final static int F_SHOW_DIALOG = 22222;
    /** 加载*/
    public SVProgressHUD mSVProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApp) getApplication()).getActivities().add(this);
        //适配原生、华为系统手机虚拟键遮挡tab的问题
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }
        setContentView(this.getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        rxManage = new RxManage();
        this.dealSavedInstanceState(savedInstanceState);
        this.initView();
        this.initData();
        this.initEvent();
        if (mPresenter != null)
            mPresenter.setVM(this, mModel);
    }

    protected void initData() {
    }

    protected void initEvent() {
        /**
         * 注册通用的异常简易处理事件
         * 放在 MainActivity 是为了让整个异常处理事件只有一个接收者，通过 MainActivity 再将
         * 具体的 UI 响应转给系统最上层的 Activity 处理
         */
        rxManage.on(Event.SIMPLE_EXCEPTION_HANDLE, new Action1<ApiException>() {
            @Override
            public void call(ApiException e) {
                dissmisDialog();
                List acitivities = ((BaseApp)getApplication()).getActivities();
                Log.d(TAG, ((Activity)BaseActivity.this).getClass().getSimpleName() + " is trying to triger simple exception handler");
                // ExceptionHandler.TRIGER_INTERVAL 秒内不允许重复执行异常处理：
                // currentTime 可以被异步获取多次，但是只有一个调用可以进入同步块进行异常处理，
                // 当一次异常处理完成时，待在同步块外面的调用再进入同步块时，由于时间仍然是之前
                // 获取到的时间（getmLastHandlerStartTime），因此不会触发再次的
                // 异常处理
                long currentTime = System.currentTimeMillis();
                // 通过同步块的方式，限制调用次数
                synchronized (this) {
                    if (currentTime - ExceptionHandler.getmLastHandlerStartTime() > ExceptionHandler.TRIGER_INTERVAL) {
                        // 最上层的 activity 触发异常提示事件
                        Activity topActivity = null;
                        if (acitivities.size() > 0)
                            topActivity = (Activity) acitivities.get(acitivities.size() - 1);
                        else
                            return;

                        // 如果最上层的 activity 正好处于destroy阶段，那么再选下一个activity触发异常处理
                        if (!topActivity.isFinishing()) {
                            ExceptionHandler.handleException((Context) topActivity, e);
                        } else {
                            if (acitivities.size() > 1) {
                                ExceptionHandler.handleException((Context) (Activity) acitivities.get(acitivities.size() - 2), e);
                            }
                        }
                    } else {
                        Log.d(TAG, "Exception has handled by other activity. ");
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void dealSavedInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String activityName = getClass().getSimpleName();
        ((BaseApp)getApplication()).getActivities().remove(this);
        Log.d("MainActivity", activityName + " is onDestroy");
        if (mPresenter != null)
            mPresenter.onDestroy();
        ButterKnife.unbind(this);
        rxManage.clearAndRemoveRxbusEvents();
    }

    public abstract int getLayoutId();

    public abstract void initView();

    /**
     * Description: 父类共用
     */
    private Handler mFatherHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case F_DISSMIS_DIALOG:
                    if (mSVProgressHUD != null) {
                        mSVProgressHUD.dismissImmediately();
                        mSVProgressHUD = null;
                    }
                    break;
                case F_SHOW_DIALOG:
                    if (null != mSVProgressHUD)
                        mSVProgressHUD.isShowing();
                    break;
                default:
                    break;
            }
        }
    };

    public void showDialog() {
        mSVProgressHUD = new SVProgressHUD(this);
        mSVProgressHUD.showWithStatus(getString(R.string.progresshud_loading), SVProgressHUD.SVProgressHUDMaskType.None);
        mFatherHandler.sendEmptyMessage(F_SHOW_DIALOG);
    }

    public void dissmisDialog() {
        mFatherHandler.sendEmptyMessage(F_DISSMIS_DIALOG);
    }

}
