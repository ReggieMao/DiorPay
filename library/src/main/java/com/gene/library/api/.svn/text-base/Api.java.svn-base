package com.gene.library.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.gene.library.constant.Constants;
import com.gene.library.base.BaseApp;
import com.gene.library.util.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by MaoLJ on 2018/7/18.
 * 接口类
 */

public class Api {

    private static final String TAG = "Api";
    private static final int DEFAULT_TIMEOUT = 120 * 1000;
    private Retrofit retrofit;
    public ApiService service;

    // 构造方法私有
    private Api() {
        // 配置 API 相关缓存存取的位置（文件对象）
        File cacheFile = new File(BaseApp.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(Constants.LOG_DEBUG ? HttpLoggingInterceptor.Level.BODY  : HttpLoggingInterceptor.Level.NONE);

        // 创建 OkHttpClient 对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpHeaderInterceptor(BaseApp.getAppContext()))
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor(BaseApp.getAppContext()))
                .cache(cache)
                .build();

        // 创建 retrofit 对象
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build();
        service = retrofit.create(ApiService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final Api INSTANCE = new Api();
    }

    //获取单例
    public static Api getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 为 Http Request 添加 header 的 Interceptor
     */
    private class HttpHeaderInterceptor implements Interceptor {

//        String deviceId = UserPreference.getString(UserPreference.DEVICE_ID, "");
        String jssionid = "";
        String thinkjs = "";
        Context context;

        HttpHeaderInterceptor(Context context) {
            super();
            this.context = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            SharedPreferences sharedPreferences1 = context.getSharedPreferences("cookie_jssionid", Context.MODE_PRIVATE);
            Observable.just(sharedPreferences1.getString("cookie_jssionid", ""))
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String cookie) {
                            jssionid = cookie;
                        }
                    });

            SharedPreferences sharedPreferences2 = context.getSharedPreferences("cookie_thinkjs", Context.MODE_PRIVATE);
            Observable.just(sharedPreferences2.getString("cookie_thinkjs", ""))
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String cookie) {
                            thinkjs = cookie;
                        }
                    });

            return chain.proceed(chain.request()
                    .newBuilder()
                    .addHeader("platform", "ANDROID")
//                    .addHeader("deviceId", deviceId)
                    .addHeader("Cookie", jssionid + thinkjs)
                    .addHeader("cache-control", "max-age=0")
                    .addHeader("accept-language", "zh-CN,zh;q=0.8")
                    .build());
        }
    }

    /**
     * 缓存 Interceptor，用于检查网络情况，及设置数据的缓存
     */
    private class HttpCacheInterceptor implements Interceptor {

        Context context;

        HttpCacheInterceptor(Context context) {
            super();
            this.context = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            // 判断网络是否连接，如果网络正常，那么直接从网络上获取数据；如果没有网络，那么强制从缓存里获取数据
            if (!NetWorkUtil.isNetConnected(BaseApp.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d(TAG, "no network");
            }

            Response originalResponse = chain.proceed(request);
            // 这里获取请求返回的cookie
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                final StringBuffer cookieBuffer = new StringBuffer();
                Observable.from(originalResponse.headers("Set-Cookie"))
                        .map(new Func1<String, String>() {
                            @Override
                            public String call(String s) {
                                String[] cookieArray = s.split(";");
                                return cookieArray[0];
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String cookie) {
                                cookieBuffer.append(cookie).append(";");
                            }
                        });
                if (cookieBuffer.toString().indexOf(";") + 1 != cookieBuffer.toString().length()) {
                    SharedPreferences sharedPreferences1 = context.getSharedPreferences("cookie_jssionid", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                    editor1.putString("cookie_jssionid", cookieBuffer.toString().substring(0, cookieBuffer.toString().indexOf(";") + 1));
                    editor1.commit();

                    SharedPreferences sharedPreferences2 = context.getSharedPreferences("cookie_thinkjs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                    editor2.putString("cookie_thinkjs", cookieBuffer.toString().substring(cookieBuffer.toString().indexOf(";") + 1));
                    editor2.commit();
                } else {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("cookie_jssionid", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("cookie_jssionid", cookieBuffer.toString());
                    editor.commit();
                }
            }

            if (NetWorkUtil.isNetConnected(BaseApp.getAppContext())) {
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200") // max-stale 缓存的有效时间
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }

}