package com.gene.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by MaoLJ on 2018/7/18.
 *
 */

public class CommentListView extends ListView {

    public CommentListView(Context context) {
        super(context, null);
    }

    public CommentListView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, measureSpec);
    }

}
