package com.gene.library.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by MaoLJ on 2018/7/18.
 * 验证工具类
 */

public class CheckUtil {

    /**
     * 昵称限制性设置
     */
    public static void setNickname(EditText editText) {
        final int nameMaxLen = 20;
        // 昵称输入过滤器（限制中文最多输入10位，英文和其他字母最多输入20位）
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                int dindex = 0;
                int count = 0;

                while (count <= nameMaxLen && dindex < dest.length()) {
                    char c = dest.charAt(dindex++);
                    if (c < 128) {
                        count = count + 1;
                    } else {
                        count = count + 2;
                    }
                }

                if (count > nameMaxLen) {
                    return dest.subSequence(0, dindex - 1);
                }

                int sindex = 0;
                while (count <= nameMaxLen && sindex < source.length()) {
                    char c = source.charAt(sindex++);
                    if (c < 128) {
                        count = count + 1;
                    } else {
                        count = count + 2;
                    }
                }

                if (count > nameMaxLen) {
                    sindex --;
                }

                return source.subSequence(0, sindex);
            }
        };
        editText.setFilters(new InputFilter[] {filter});
    }

    /**
     * 验证是不是邮箱
     */
    public static boolean isMail(String mail) {
        return mail.trim().matches("^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
    }

    /**
     * 验证是不是手机号
     */
    public static boolean isMobile(String number) {
        String num = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return !TextUtils.isEmpty(number) && number.matches(num);
    }

}
