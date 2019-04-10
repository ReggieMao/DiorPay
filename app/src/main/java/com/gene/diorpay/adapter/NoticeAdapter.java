package com.gene.diorpay.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gene.diorpay.R;
import com.gene.diorpay.activity.NoticeInfoActivity;
import com.gene.library.pojo.Notice;
import com.gene.library.view.CommonVH;

import java.util.List;

/**
 * Created by MaoLJ on 2018/7/23.
 * 公告适配器
 */

public class NoticeAdapter extends CommonListViewAdapter<Notice> {

    private static final String TAG = "NoticeAdapter";
    private Activity mActivity;
    public NoticeAdapter(Activity activity, List<Notice> datas) {
        super(activity, datas);
        this.mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_notice, position);

        LinearLayout layout = holder.getView(R.id.layout);
        TextView content = holder.getView(R.id.tv_content);
        TextView time = holder.getView(R.id.tv_time);

        Notice notice = mDatas.get(position);

        content.setText(notice.getNoticeTitle());
        time.setText(notice.getBeginTime());
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, NoticeInfoActivity.class);
                intent.putExtra(NoticeInfoActivity.CONTENT, notice.getNoticeTitle());
                intent.putExtra(NoticeInfoActivity.URL, notice.getNoticeUrl());
                mActivity.startActivity(intent);
            }
        });

        return holder.getConvertView();
    }

}
