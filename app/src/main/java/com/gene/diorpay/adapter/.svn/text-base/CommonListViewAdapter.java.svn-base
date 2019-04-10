package com.gene.diorpay.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by MaoLJ on 2018/7/23.
 * 单列通用适配器
 */

public abstract class CommonListViewAdapter<T> extends BaseAdapter {

	private static final String TAG = "CommonListViewAdapter";
	protected Activity mActivity;
	protected List<T> mDatas;
	protected LayoutInflater mInflater;
	
	public CommonListViewAdapter(Activity activity, List<T> datas) {
		super();
		this.mActivity = activity;
		mInflater = LayoutInflater.from(activity);
		this.mDatas = datas;
	}

	public void addItems(List<T> datas){
		if (datas == null) {
			return ;
		}
		if (mDatas == null) {
			mDatas = datas;
		} else {
			mDatas.addAll(datas);
		}
		notifyDataSetChanged();
	}
	
	public void setItems(List<T> datas){
		mDatas = datas;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if (mDatas == null) {
			return 0;
		}
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		if (mDatas == null) {
			return null;
		}
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);

}
