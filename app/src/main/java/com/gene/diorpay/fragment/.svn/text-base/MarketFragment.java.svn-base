package com.gene.diorpay.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.diorpay.activity.Base1Activity;
import com.gene.diorpay.activity.HotActivity;
import com.gene.diorpay.activity.NoticeActivity;
import com.gene.diorpay.adapter.HotAdapter;
import com.gene.diorpay.adapter.NewsAdapter;
import com.gene.diorpay.view.HorizontalListView;
import com.gene.library.api.DiorPayApi;
import com.gene.library.constant.Event;
import com.gene.library.pojo.Hot;
import com.gene.library.pojo.Market;
import com.gene.library.pojo.News;
import com.gene.library.pojo.Notice;
import com.gene.library.pull.AutoPullAbleGridView;
import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/7/23.
 * 资讯页面
 */

public class MarketFragment extends Base1Fragment {

    private static final String TAG = "MarketFragment";
    private Base1Activity mBaseActivity;
    private int mPosition = -1;
    private View mView;
    @Bind(R.id.gv_news_list)
    AutoPullAbleGridView mListView;
    @Bind(R.id.tv_no_data)
    TextView mTvNoData;
    @Bind(R.id.tv_notice)
    TextView mTvNotice;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.tv_unread)
    TextView mTvUnread;
    private NewsAdapter mAdapter;
    private HotAdapter mHotAdapter;
    private HorizontalListView mListHot;
    private List<Notice> mNoticeList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_market;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onChangeFragment(int position) {
        super.onChangeFragment(position);
        if (mPosition == -1) {
            mPosition = position;
            initViewDelayedLoading();
        }
    }

    private void initViewDelayedLoading() {
        mBaseActivity = (Base1Activity) getActivity();
        mView = LayoutInflater.from(getActivity()).inflate(R.layout.view_market_head, null);
        mListHot = mView.findViewById(R.id.list_view);
        getNewsList();
        initDataDelayedLoading();
    }

    private void initDataDelayedLoading() {
        mBaseActivity.rxManage.on(Event.NOTICE_UNREAD, new Action1() {
            @Override
            public void call(Object o) {
                getNewsList();
            }
        });

        mBaseActivity.rxManage.on(Event.FIND_NEWS_LIST, new Action1<Market>() {
            @Override
            public void call(Market o) {
                mTvNotice.setText(o.getNoticeList().get(0).getNoticeTitle());
                mTvTime.setText(o.getNoticeList().get(0).getBeginTime());
                if (o.getUnreadNum() != 0) {
                    mTvUnread.setVisibility(View.VISIBLE);
                    mTvUnread.setText(o.getUnreadNum() + "");
                } else
                    mTvUnread.setVisibility(View.GONE);
                showHotList(o.getHotList());
                mListHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), HotActivity.class);
                        intent.putExtra(HotActivity.URL, o.getHotList().get(position).getNoticeUrl());
                        getActivity().startActivity(intent);
                    }
                });
                if (mListView.getHeaderViewsCount() == 0)
                    mListView.addHeaderView(mView);
                showNewsList(o.getNewsList());
                mNoticeList = o.getNoticeList();
            }
        });
    }

    private void getNewsList() {
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        DiorPayApi.getInstance().findNewList(0, Util.getNowTime(), sign, UserPreference.getString(UserPreference.SESSION_ID, ""));
    }

    private void showHotList(List<Hot> list) {
        if (null != list && list.size() > 0) {
            if (null == mHotAdapter) {
                mHotAdapter = new HotAdapter(getActivity(), list);
                mListHot.setAdapter(mHotAdapter);
            } else {
                mHotAdapter.setItems(list);
            }
        }
    }

    private void showNewsList(List<News> list) {
        if (null == mAdapter) {
            mAdapter = new NewsAdapter(getActivity(), list);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(list);
        }
        if (null != list && list.size() > 0) {
            mTvNoData.setVisibility(View.GONE);
        } else {
            mTvNoData.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.layout})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.layout:
                Intent intent = new Intent(getActivity(), NoticeActivity.class);
                intent.putExtra(NoticeActivity.NOTICE_LIST, (Serializable) mNoticeList);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getNewsList();
    }

}
