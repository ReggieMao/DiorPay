package com.gene.diorpay.view;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gene.library.base.BaseViewHolder;
import com.gene.library.base.Layout;

import butterknife.Bind;
import com.gene.diorpay.R;

/**
 * Created by MaoLJ on 2018/7/18.
 * 默认底部视图
 */

public class DefaultFooterVH extends BaseViewHolder<Object> {

    private static final String TAG = "DefaultFooterVH";
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.tv_state)
    TextView tvState;

    @Layout(R.layout.view_default_load_more)
    public DefaultFooterVH(View view) {
        super(view);
    }

    @Override
    public void onBindViewHolder(View view, Object o) {
        boolean isLoading = (o != null);
        tvState.setText(isLoading ? "正在加载" : "没有更多");
        progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }


}