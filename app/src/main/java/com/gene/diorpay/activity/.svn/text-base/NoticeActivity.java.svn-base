package com.gene.diorpay.activity;

import android.view.View;
import android.widget.TextView;

import com.gene.library.base.BaseActivity;
import com.gene.library.pojo.Notice;
import com.gene.library.pull.AutoPullAbleGridView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import com.gene.diorpay.R;
import com.gene.diorpay.adapter.NoticeAdapter;
import com.gene.library.util.Util;

/**
 * Created by MaoLJ on 2018/7/24.
 * 公告页面
 */

public class NoticeActivity extends Base1Activity {

    private static final String TAG = "NoticeActivity";
    public static String NOTICE_LIST= "notice_list";
    @Bind(R.id.gv_notice_list)
    AutoPullAbleGridView mListView;
    @Bind(R.id.tv_no_data)
    TextView mTvNoData;
    private NoticeAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_notice;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
        showNoticeList((List<Notice>) getIntent().getSerializableExtra(NOTICE_LIST));
    }

    private void showNoticeList(List<Notice> list) {
        if (null != list && list.size() > 0) {
            mTvNoData.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            if (null == mAdapter) {
                mAdapter = new NoticeAdapter(this, list);
                mListView.setAdapter(mAdapter);
            } else {
                mAdapter.setItems(list);
            }
        } else {
            mTvNoData.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            if (null != mAdapter) mAdapter.setItems(list);
        }
    }

    @OnClick({R.id.img_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

}
