package com.gene.library.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by MaoLJ on 2018/7/18.
 * 提示工具类
 */

public class ToastUtil {
    private static Toast toast = null;

    public static void toast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

}
