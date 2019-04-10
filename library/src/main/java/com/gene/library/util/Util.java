package com.gene.library.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.Build;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import static java.lang.String.valueOf;

/**
 * Created by MaoLJ on 2018/7/18.
 * 工具类
 */

public class Util {

    public static final String TAG = "Util";

    /**
     * 判断一个字符串是否为空
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }

    /**
     * dp转像素
     */
    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 检测本地网络是否打开
     */
    public static boolean isNetworkOpen(Context ctx) {
        ConnectivityManager connManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo().isAvailable();
    }

    /**
     * 得到屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 软键盘挡住按钮处理
     */
    public static void addLayoutListener(View main, View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //1、获取main在窗体的可视区域
                main.getWindowVisibleDisplayFrame(rect);
                //2、获取main在窗体的不可视区域高度，在键盘没有弹起时，main.getRootView().getHeight()调节度应该和rect.bottom高度一样
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                int screenHeight = main.getRootView().getHeight();//屏幕高度
                //3、不可见区域大于屏幕本身高度的1/4：说明键盘弹起了
                if (mainInvisibleHeight > screenHeight / 4) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    // 4､获取Scroll的窗体坐标，算出main需要滚动的高度
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                    //5､让界面整体上移键盘的高度
                    main.scrollTo(0, srollHeight);
                } else {
                    //3、不可见区域小于屏幕高度1/4时,说明键盘隐藏了，把界面下移，移回到原有高度
                    main.scrollTo(0, 0);
                }
            }
        });
    }

    /**
     * 沉浸式状态栏
     */
    public static void immersiveStatus(Activity activity, boolean needBlack) {
        //判断版本是否支持沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (needBlack) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /**
     * 从appConfig.properties配置文件获取信息
     * type: 1表示测试版本，2表示正式版本
     */
    public static String getPropertiesURL(String s, int type) {
        String url = null;
        Properties pro = new Properties();
        try {
            if (type == 1) {
                pro.load(Util.class.getResourceAsStream("/assets/debugUrl.properties"));
            } else if (type == 2) {
                pro.load(Util.class.getResourceAsStream("/assets/releaseUrl.properties"));
            }
            url = pro.getProperty(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 使用OKHttp的post方法在上传文件的同时，也携带请求参数
     */
    public static Request postFile(Context context, String url, Map<String, Object> map, File file) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            requestBody.addFormDataPart("image_file", file.getName(), body);
        }
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        return new Request.Builder().url(url).post(requestBody.build()).tag(context).build();
    }

    /**
     * 获取软键盘高度
     */
    public static int getSoftInputHeight(Activity mContext) {
        Rect r = new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //获取屏幕的高度
        int screenHeight = mContext.getWindow().getDecorView().getRootView().getHeight();
        //计算软件盘的高度
        int softInputHeight = screenHeight - r.bottom;//r.bottom是从屏幕顶端开始算的,因此已经包含了状态栏高度
        if (Build.VERSION.SDK_INT >= 20) {
            softInputHeight = softInputHeight - getNavigationBarHeight(mContext);
        }
        Log.d(TAG, "getSoftInputHeight:" + softInputHeight);
        return softInputHeight;
    }

    /**
     * 底部虚拟按键栏的高度
     */
    private static int getNavigationBarHeight(Activity mContext) {
        Resources resources = mContext.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 获取当前时间
     */
    public static String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 设置语言
     */
    public static void setLanguage(Application application, boolean isEnglish) {
        Configuration configuration = application.getResources().getConfiguration();
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (isEnglish) {
            //设置英文
            configuration.locale = Locale.ENGLISH;
        } else {
            //设置中文
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        //更新配置
        application.getResources().updateConfiguration(configuration, displayMetrics);
    }

    /**
     * 限制最多输入小数点后6位
     */
    public static void setPoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 6) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 6 + 1);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                    }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 格式化double类型，小数点后保留size位(去尾法)
     */
    public static String point(double v, int size) {
        BigDecimal bg = new BigDecimal(v + "");
        String s = bg + "000000";
        String need = "";
        if (size == 6)
            need = s.substring(0, s.indexOf(".") + 7);
        else if (size == 2)
            need = s.substring(0, s.indexOf(".") + 3);
        return need;
    }

    /**
     * 格式化double类型，小数点后保留size位(四舍五入法)
     */
    public static String point1(double v, int size) {
        String formatStr = "0.";
        for (int i = 0; i < size; i++) {
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr).format(v);
    }

    public static String turnover(int i, String s) {
        String result = "";
        if (i == 1) {
            double d = Double.parseDouble(s) / 10000;
            result = Html.fromHtml("&yen;") + point(d, 2) + "万";
        } else {
            double d = Double.parseDouble(s) / 1000;
            result = " $ " + point1(d / Double.parseDouble(UserPreference.getString(UserPreference.EXCHANGE, "")), 2) + "K";
        }
        return result;
    }

    /**
     * MD5加密
     */
    public static String encrypt(String plaintext) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = plaintext.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 某个时间距离现在的时间差
     */
    public static String timeDifference(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date userDate;
        long userTime = 0;
        try {
            userDate = simpleDateFormat.parse(time);
            userTime = userDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date nowDate = new Date(System.currentTimeMillis());
        long nowTime = nowDate.getTime();
        long timeDiff = nowTime - userTime;
        if (timeDiff < 60 * 60 * 1000)
            return (timeDiff / (1000 * 60) + 1) + "分钟以前";
        else if (timeDiff >= 60 * 60 * 1000 && timeDiff < 24 * 60 * 60 * 1000)
            return (timeDiff / (1000 * 60 * 60) + 1) + "小时以前";
        else if (timeDiff >= 24 * 60 * 60 * 1000 && timeDiff < 240 * 60 * 60 * 1000)
            return (timeDiff / (1000 * 60 * 60 * 24) + 1) + "天以前";
        else
            return time.substring(5, 10);
    }

}
