package com.gene.library.view;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MaoLJ on 2018/7/18.
 * 通过未指定泛型T 创建的可适用于所有视图的ViewHolder
 */

public class CommonVH {
	/**一种比ArrayList效率更高的集合容器，用于存放View*/
	private SparseArray<View> mViews;
	/**安卓自带的机制所缓存的视图*/
	private View mConvertView;
	private int mPosition;
	
	public CommonVH(Context context, ViewGroup parent, int layoutId, int position) {
		mViews = new SparseArray<View>();
		mPosition = position;
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		mConvertView.setTag(this);
	}
	public static CommonVH get(Context context,View convertView, ViewGroup parent ,int layoutId, int position) {
		return new CommonVH(context, parent,layoutId, position);//新建一个ViewHolder进行缓存
	}
	
	/**
	 * 获取视图，并加入数据源
	 */
	public  <T extends View>T getView(int viewId){
		View view = mViews.get(viewId);
		if(view==null){
		    view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public View getConvertView() {
		return mConvertView;
	}

}
