package com.gene.diorpay.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.gene.diorpay.R;
import com.gene.diorpay.activity.NewsActivity;
import com.gene.library.pojo.News;
import com.gene.library.util.ImageLoadUtil;
import com.gene.library.view.CommonVH;

/**
 * Created by MaoLJ on 2018/7/23.
 * 新闻适配器
 */

public class NewsAdapter extends CommonListViewAdapter<News> {

    private static final String TAG = "NewsAdapter";
    private Activity mActivity;
    public NewsAdapter(Activity activity, List<News> datas) {
        super(activity, datas);
        this.mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH holder = CommonVH.get(mActivity, convertView, parent, R.layout.item_news, position);

        LinearLayout layout = holder.getView(R.id.layout);
        TextView title = holder.getView(R.id.tv_title);
        TextView time = holder.getView(R.id.tv_time);
        ImageView logo = holder.getView(R.id.img_logo);

        News news = mDatas.get(position);

        title.setText(news.getNoticeTitle());
        time.setText(news.getBeginTime());
        ImageLoadUtil.loadServiceImg(logo, news.getPageImgPath(), 0);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, NewsActivity.class);
                intent.putExtra(NewsActivity.URL, news.getNoticeUrl());
                mActivity.startActivity(intent);
            }
        });

        return holder.getConvertView();
    }

}
