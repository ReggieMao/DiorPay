package com.gene.library.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by MaoLJ on 2018/7/18.
 * 加载图片工具类
 */

public class ImageLoadUtil {

    /**
     * 加载放在服务器上的图片
     */
    public static void loadServiceImg(ImageView v, String url, int defaultID) {
        RequestOptions options = new RequestOptions().placeholder(defaultID).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL);
        options.placeholder(defaultID);
        Glide.with(v.getContext()).load(url).apply(options).into(v);
    }

    /**
     * 加载放在服务器上的图片并处理成圆形
     */
    public static void loadServiceRoundImg(ImageView v, String url, int defaultID) {
        RequestOptions options = new RequestOptions().placeholder(defaultID).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL);
        options.placeholder(defaultID);
        options.transform(new CircleCrop());
        Glide.with(v.getContext()).load(url).apply(options).into(v);
    }

}
