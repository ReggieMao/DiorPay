package com.gene.library.pull;

import android.widget.AbsListView;

/**
 * Created by MaoLJ on 2018/7/18.
 * 列表滚动监听
 */

public class ListViewScrollListener implements AbsListView.OnScrollListener {

    /** 当前滚动数目*/
    private int mScrollNum;
    /** 列表的总数目*/
    private int mTotalScrollNum;
    /** 列表滚动是停止了还是在继续滚动*/
    private boolean mIsScrollStatus;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: //空闲状态
                onStopScroll(false,mScrollNum,mTotalScrollNum);
                mIsScrollStatus = false;
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING: // 滚动状态
                onScrollState(true);
                mIsScrollStatus = true;
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://触摸后滚动
                onScrollState(true);
                mIsScrollStatus = true;
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mScrollNum = firstVisibleItem + visibleItemCount - 1;
        mTotalScrollNum = totalItemCount - 1;
        onScrollList(view, mScrollNum, mTotalScrollNum, mIsScrollStatus);
    }

    /**
     * 滚动空闲状态
     */
    public void onStopScroll(boolean isScroll, int scrollNum, int totalScrollNum){}

    /**
     * 滚动中
     */
    public void onScrollState(boolean isScroll){}

    /**
     * 滚动就会监听
     */
    public void onScrollList(AbsListView view, int scrollNum, int totalScrollNum, boolean isScrollStatus){}

}
