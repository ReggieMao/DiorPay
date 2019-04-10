package com.gene.library.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by MaoLJ on 2018/7/18.
 *
 */

public class SwipeViewPager extends ViewPager {
    private boolean swipeEnable = true;

    public SwipeViewPager(Context context) {
        super(context);
    }

    public SwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isSwipeEnable() {
        return swipeEnable;
    }

    public void setSwipeEnable(boolean swipeEnable) {
        this.swipeEnable = swipeEnable;
    }

    // 1.禁掉viewpager左右滑动事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isSwipeEnable() && super.onTouchEvent(event);
    }

    // 2.禁掉viewpager左右滑动事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isSwipeEnable() && super.onInterceptTouchEvent(event);
    }
}
