package com.gene.library.pull;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by MaoLJ on 2018/7/18.
 * 可下拉列表视图(ListView)
 */

public class AutoPullAbleListView extends ListView implements AutoPullAble {

    private ImageView mLoadingView;
    private AnimationDrawable mLoadAnim;
    /** 初始化状态*/
    public static final int INIT = 0;
    /** 正在加载*/
    public static final int LOADING = 1;
    /** 完成加载*/
    public static final int FINISH = 2;
    /** 上拉监听*/
    private OnLoadListener mOnLoadListener;
    /** 记录当前的状态*/
    private int state = INIT;
    /** 是否可以上拉*/
    private boolean mCanLoad = true;
    /** 判断是否能下拉*/
    private boolean mPullDownFlag = true;
    /** 判断是否需要加载*/
    private boolean mIsLoadFlag = true;
    /** 加载完成的文字*/
    private String mFinishText;
    /** 滑动监听*/
    private AutoPullScrollListener mAutoPullScrollListener;

    /** 加载完成后开发者可以自己写提示文字*/
    public void setFinishText(String finishText) {
        this.mFinishText = finishText;
    }
    /** 设置是否可以上拉*/
    public void setPullDownFlag(boolean flag) {
        this.mPullDownFlag = flag;
    }
    /** 提供给使用者自己判断是否要加载*/
    public void setLoadDataFlag(boolean isLoadFlag) {
        this.mIsLoadFlag = isLoadFlag;
    }

    public AutoPullAbleListView(Context context) {
        super(context);
        init(context);
    }

    public AutoPullAbleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoPullAbleListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(com.gene.library.R.layout.view_auto_load_more, null);
        mLoadingView = (ImageView) view.findViewById(com.gene.library.R.id.loading_icon);
        mLoadingView.setBackgroundResource(com.gene.library.R.drawable.loading_anim);
        mLoadAnim = (AnimationDrawable) mLoadingView.getBackground();
        this.setOnScrollListener(onListViewScrollListener);
    }

    /**
     * ListView的滑动监听
     */
    private ListViewScrollListener onListViewScrollListener = new ListViewScrollListener() {
        @Override
        public void onStopScroll(boolean isScroll, int scrollNum, int totalScrollNum) {
            super.onStopScroll(isScroll, scrollNum, totalScrollNum);
            if (scrollNum == totalScrollNum) {
                checkLoad();
            }
            if (mAutoPullScrollListener != null) mAutoPullScrollListener.onStopScroll(isScroll, scrollNum, totalScrollNum);
        }

        @Override
        public void onScrollList(AbsListView view, int scrollNum, int totalScrollNum, boolean isScrollStatus) {
            super.onScrollList(view, scrollNum, totalScrollNum, isScrollStatus);
            if (mAutoPullScrollListener != null) mAutoPullScrollListener.onScrollList(view, scrollNum, totalScrollNum, isScrollStatus);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: // 按下的时候禁止自动加载
                mCanLoad = false;
                break;
            case MotionEvent.ACTION_UP:  // 松开手判断是否自动加载
                mCanLoad = true;
                checkLoad();
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 判断是否满足自动加载条件
     */
    private void checkLoad() {
        if (reachBottom() && mOnLoadListener != null && state != LOADING && mCanLoad && mIsLoadFlag) {
            mOnLoadListener.onLoad(this);
            changeState(LOADING);
        }
    }

    public void changeState(int state) {
        this.state = state;
        switch (state) {
            case INIT:
                mLoadAnim.stop();
                mLoadingView.setVisibility(View.INVISIBLE);
                break;
            case LOADING:
                mLoadAnim.start();
                mLoadingView.setVisibility(View.VISIBLE);
                break;
            case FINISH:
                mLoadAnim.stop();
                mLoadingView.setVisibility(View.INVISIBLE);
                if (mFinishText == null) mFinishText = "";
                break;
        }
    }

    /**
     * 完成加载
     */
    public void finishLoading(int status) {
        changeState(status);
    }

    @Override
    public boolean canPullDown() {
        if (getCount() == 0) {
            return mPullDownFlag; // 没有item的时候也可以下拉刷新
        } else if (mPullDownFlag && getFirstVisiblePosition() == 0 && getChildAt(0) != null && getChildAt(0).getTop() >= 0) {
            return mPullDownFlag; // 滑到ListView的顶部了
        } else {
            return false;
        }
    }

    /**
     * 下拉
     */
    public void setOnLoadListener(OnLoadListener listener) {
        this.mOnLoadListener = listener;
    }

    /**
     * 滑动监听
     */
    public void setOnLoadScrollListener(AutoPullScrollListener autoPullScrollListener) {
        this.mAutoPullScrollListener = autoPullScrollListener;
    }

    public boolean reachBottom() {
        if (getCount() == 0) {  // 没有item的时候也可以上拉加载
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1)) { // 滑到底部了
            View childAt = getChildAt(getLastVisiblePosition() - getFirstVisiblePosition());
            if (childAt != null && childAt.getTop() < getMeasuredHeight()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 上拉加载
     */
    public interface OnLoadListener {
        public void onLoad(AutoPullAbleListView pullAbleListView);
    }

}
