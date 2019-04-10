package com.gene.library.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaoLJ on 2018/7/18.
 * 控制多个Activity工具类
 */

public class ActivityUtil {
    private static final String TAG = "ActivityUtil";
    private static List<Activity> activities = new ArrayList<Activity>();

    public static void add(Activity activity) {
        activities.add(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            activity.finish();
        }
        activities.clear();
    }
}
