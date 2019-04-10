package com.gene.diorpay.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gene.diorpay.activity.Base1Activity;
import com.gene.library.R;
import com.gene.library.base.BaseActivity;
import com.gene.library.base.BaseModel;
import com.gene.library.base.BasePresenter;
import com.gene.library.constant.Event;
import com.gene.library.exception.ApiException;
import com.gene.library.svprogresshud.SVProgressHUD;

import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/7/18.
 * Fragment父类
 */

public abstract class Base1Fragment<T extends BasePresenter, E extends BaseModel>extends Fragment {

    private static final String TAG = "BaseFragment";
    public T mPresenter;
    public E mModel;

    public final static int F_DISSMIS_DIALOG = 11111;
    public final static int F_SHOW_DIALOG = 22222;
    private Base1Activity mBaseActivity;
    /** 加载*/
    public SVProgressHUD mSVProgressHUD;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
        initData();
        initEvent();
    }

    protected void initEvent() {
        /**
         * 注册通用的异常简易处理事件
         * 放在 MainActivity 是为了让整个异常处理事件只有一个接收者，通过 MainActivity 再将
         * 具体的 UI 响应转给系统最上层的 Activity 处理
         */
        mBaseActivity = (Base1Activity) getActivity();
        mBaseActivity.rxManage.on(Event.SIMPLE_EXCEPTION_HANDLE, new Action1<ApiException>() {
            @Override
            public void call(ApiException e) {
                dissmisDialog();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(this.getLayoutId(), container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
        ButterKnife.unbind(this);
    }
    public abstract int getLayoutId();

    public abstract void initView();

    protected void initData() {

    }

    private Handler mFatherHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case F_DISSMIS_DIALOG:
                    if (null != mSVProgressHUD )
                        mSVProgressHUD.dismissImmediately();
                        mSVProgressHUD = null;
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
        mSVProgressHUD = new SVProgressHUD(getActivity());
        mSVProgressHUD.showWithStatus(getString(R.string.sending),SVProgressHUD.SVProgressHUDMaskType.None);
        mFatherHandler.sendEmptyMessage(F_SHOW_DIALOG);
    }

    public void dissmisDialog() {
        mFatherHandler.sendEmptyMessage(F_DISSMIS_DIALOG);
    }

    /**
     * 改变fragment的时候调用
     */
    public void onChangeFragment(int position) {}
}
