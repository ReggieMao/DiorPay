package com.gene.library.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by MaoLJ on 2018/7/18.
 *
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private static final String TAG = "BaseViewHolder";

    public BaseViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }

    /**
     * 绑定ViewHolder
     */
    public abstract void onBindViewHolder(View view, T obj);

}
