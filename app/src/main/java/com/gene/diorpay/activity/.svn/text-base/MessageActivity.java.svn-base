package com.gene.diorpay.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.diorpay.adapter.NoticeAdapter;
import com.gene.diorpay.adapter.NotificationAdapter;
import com.gene.library.api.DiorPayApi;
import com.gene.library.base.BaseActivity;
import com.gene.library.constant.Event;
import com.gene.library.pojo.Market;
import com.gene.library.pojo.Notice;
import com.gene.library.pojo.Notification;
import com.gene.library.pojo.RealmNotification;
import com.gene.library.pull.AutoPullAbleGridView;
import com.gene.library.util.ToastUtil;
import com.gene.library.util.UserPreference;
import com.gene.library.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.functions.Action1;

/**
 * Created by MaoLJ on 2018/8/6.
 * 消息中心页面
 */

public class MessageActivity extends Base1Activity implements View.OnClickListener {

    private static final String TAG = "MessageActivity";
    private ArrayList<View> mPageList;
    @Bind(R.id.view_pager)
    ViewPager mViewPage;
    @Bind(R.id.view1)
    View mLine1;
    @Bind(R.id.view2)
    View mLine2;
    @Bind(R.id.rl_notification)
    RelativeLayout mRlNotification;
    @Bind(R.id.rl_message)
    RelativeLayout mRlMessage;
//    @Bind(R.id.tv_count1)
//    TextView mTvCount1;
    @Bind(R.id.tv_count2)
    TextView mTvCount2;
    private View mView1;
    private View mView2;
    private AutoPullAbleGridView mListView1;
    private AutoPullAbleGridView mListView2;
    private TextView mTvNoData1;
    private TextView mTvNoData2;
    private NotificationAdapter mAdapter1;
    private NoticeAdapter mAdapter2;
    private List<Notice> mNotices;
    private Realm mRealm = Realm.getDefaultInstance();

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
        LayoutInflater inflater = getLayoutInflater();
        mView1 = inflater.inflate(R.layout.view_notification, null);
        mView2 = inflater.inflate(R.layout.view_message, null);
        notificationView();
        messageView();

        mRlNotification.setOnClickListener(this);
        mRlMessage.setOnClickListener(this);

        mPageList = new ArrayList<View>();
        mPageList.add(mView1);
        mPageList.add(mView2);

        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mPageList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mPageList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mPageList.get(position));
                return mPageList.get(position);
            }
        };

        //绑定适配器
        mViewPage.setAdapter(pagerAdapter);
        //设置viewPager的初始界面为第1个界面
        mViewPage.setCurrentItem(0);
        //添加切换界面的监听器
        mViewPage.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    @Override
    protected void initData() {
        super.initData();
//        rxManage.on(Event.TRADE_UNREAD, new Action1() {
//            @Override
//            public void call(Object o) {
//                mTvCount1.setVisibility(View.VISIBLE);
//                mTvCount1.setText((Integer.parseInt(mTvCount1.getText().toString()) + 1) + "");
//                getNotificationList();
//            }
//        });

        rxManage.on(Event.NOTICE_UNREAD, new Action1() {
            @Override
            public void call(Object o) {
                getMessageList();
            }
        });

        rxManage.on(Event.FIND_NEWS_LIST1, new Action1<Market>() {
            @Override
            public void call(Market o) {
                mNotices = o.getNoticeList();
                if (o.getUnreadNum() == 0)
                    mTvCount2.setVisibility(View.GONE);
                else {
                    mTvCount2.setVisibility(View.VISIBLE);
                    mTvCount2.setText(o.getUnreadNum() + "");
                }
            }
        });

        rxManage.on(Event.MARK_ALL_READ, new Action1() {
            @Override
            public void call(Object o) {
                ToastUtil.toast(MessageActivity.this, getString(R.string.read_finish));
//                UserPreference.putInt(UserPreference.TRADE_UNREAD, 0);
//                mTvCount1.setVisibility(View.GONE);
                mTvCount2.setVisibility(View.GONE);
            }
        });
    }

    private void notificationView() {
        mListView1 = mView1.findViewById(R.id.gv_notification_list);
        mTvNoData1 = mView1.findViewById(R.id.tv_no_data);
        getNotificationList();
    }

    private void messageView() {
        mListView2 = mView2.findViewById(R.id.gv_message_list);
        mTvNoData2 = mView2.findViewById(R.id.tv_no_data);
        getMessageList();
    }

    private void getNotificationList() {
        List<Notification> noticeList = new ArrayList<>();
        RealmResults<RealmNotification> realmNotifications = mRealm.where(RealmNotification.class).findAll();
        int unReadCount = 0;
        for (int i = 0; i < realmNotifications.size(); i ++) {
            Notification notification = new Notification();
            notification.setTimeStamp(realmNotifications.get(i).getTimeStamp());
            notification.setTime(realmNotifications.get(i).getTime());
            notification.setOut(realmNotifications.get(i).isOut());
            notification.setAddress(Util.isEmpty(realmNotifications.get(i).getInAddress()) ? realmNotifications.get(i).getOutAddress() : realmNotifications.get(i).getInAddress());
            notification.setCount(realmNotifications.get(i).getBalance());
            notification.setCoinName(realmNotifications.get(i).getCoinName());
            notification.setWalletType(realmNotifications.get(i).getCoinType());
            notification.setHasRead(realmNotifications.get(i).isHasRead());
            if (!realmNotifications.get(i).isHasRead())
                unReadCount = unReadCount + 1;
            noticeList.add(notification);
        }
//        UserPreference.putInt(UserPreference.TRADE_UNREAD, unReadCount);
        showNotificationList(noticeList);
    }

    private void getMessageList() {
        String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
        sign = Util.encrypt(sign);
        DiorPayApi.getInstance().findNewList(1, Util.getNowTime(), sign, UserPreference.getString(UserPreference.SESSION_ID, ""));
    }

    private void showNotificationList(List<Notification> list) {
        if (null != list && list.size() > 0) {
            mTvNoData1.setVisibility(View.GONE);
            mListView1.setVisibility(View.VISIBLE);
            if (null == mAdapter1) {
                mAdapter1 = new NotificationAdapter(this, list);
                mListView1.setAdapter(mAdapter1);
            } else {
                mAdapter1.setItems(list);
            }
        } else {
            mTvNoData1.setVisibility(View.VISIBLE);
            mListView1.setVisibility(View.GONE);
            if (null != mAdapter1) mAdapter1.setItems(list);
        }
    }

    private void showMessageList(List<Notice> list) {
        if (null != list && list.size() > 0) {
            mTvNoData2.setVisibility(View.GONE);
            mListView2.setVisibility(View.VISIBLE);
            if (null == mAdapter2) {
                mAdapter2 = new NoticeAdapter(this, list);
                mListView2.setAdapter(mAdapter2);
            } else {
                mAdapter2.setItems(list);
            }
        } else {
            mTvNoData2.setVisibility(View.VISIBLE);
            mListView2.setVisibility(View.GONE);
            if (null != mAdapter2) mAdapter2.setItems(list);
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int index) {
            if (index == 1)
                showMessageList(mNotices);
            lineShow(index);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void lineShow(int index) {
        if (index == 0) {
            mLine1.setVisibility(View.VISIBLE);
            mLine2.setVisibility(View.GONE);
        } else if (index == 1) {
            mLine1.setVisibility(View.GONE);
            mLine2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_notification:
                mViewPage.setCurrentItem(0);
                lineShow(0);
                break;
            case R.id.rl_message:
                mViewPage.setCurrentItem(1);
                lineShow(1);
                break;
        }
    }

    @OnClick({R.id.img_back, R.id.tv_all_read})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_all_read:
                String sign = UserPreference.getString(UserPreference.SECRET, "") + "submitTime=" + Util.getNowTime() + UserPreference.getString(UserPreference.SECRET, "");
                sign = Util.encrypt(sign);
                DiorPayApi.getInstance().markAllRead(Util.getNowTime(), sign, UserPreference.getString(UserPreference.SESSION_ID, ""));
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        messageView();
//        if (UserPreference.getInt(UserPreference.TRADE_UNREAD, 0) == 0)
//            mTvCount1.setVisibility(View.GONE);
//        else
//            mTvCount1.setText(UserPreference.getInt(UserPreference.TRADE_UNREAD, 0) + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

}
