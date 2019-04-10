package com.gene.library.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import com.gene.library.R;

/**
 * Created by MaoLJ on 2018/7/18.
 * 提示框工具类
 */

public class DialogUtil {

    private static final String TAG = "DialogUtil";

    /**
     * 回调方法的接口0
     */
    public interface OnResultListener0 {
        public void onOK();
    }

    /**
     * 回调方法的接口1
     */
    public interface OnResultListener1 {
        public void select1();

        public void select2();

        public void select3();
    }

    /**
     * 回调方法的接口2
     */
    public interface OnResultListener2 {
        public void onOk(String... params);
    }

    /**
     * 回调方法的接口3
     */
    public interface OnResultListener3 {
        public void onDelete();
    }

    /**
     * 回调方法的接口4
     */
    public interface OnResultListener4 {
        public void select1();

        public void select2();
    }

    /**
     * 交易提示
     */
    public static void transactionDialog(Context context, final OnResultListener2 onResListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_transaction, null);
        final PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
        LinearLayout sub = (LinearLayout) view.findViewById(R.id.sub_layout);
        EditText pwd = (EditText) view.findViewById(R.id.et_pwd);
        RelativeLayout close = (RelativeLayout) view.findViewById(R.id.img_close);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) pwd.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(pwd, 0);
            }
        }, 200);

        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    String input = pwd.getText().toString();
                    onResListener.onOk(new String[]{input});
                    mPopupWindow.dismiss();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click sub layout");
            }
        });
    }

    /**
     * 选择矿工费提示
     */
    public static void minerFeeDialog(Context context, int nowSelect, final OnResultListener1 onResListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_miner_fee, null);
        final PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView dismiss = (TextView) view.findViewById(R.id.tv_dismiss);
        RelativeLayout layout1 = (RelativeLayout) view.findViewById(R.id.layout1);
        RelativeLayout layout2 = (RelativeLayout) view.findViewById(R.id.layout2);
        RelativeLayout layout3 = (RelativeLayout) view.findViewById(R.id.layout3);
        ImageView img1 = (ImageView) view.findViewById(R.id.img1);
        ImageView img2 = (ImageView) view.findViewById(R.id.img2);
        ImageView img3 = (ImageView) view.findViewById(R.id.img3);

        switch (nowSelect) {
            case 1:
                img1.setVisibility(View.VISIBLE);
                img2.setVisibility(View.GONE);
                img3.setVisibility(View.GONE);
                break;
            case 2:
                img1.setVisibility(View.GONE);
                img2.setVisibility(View.VISIBLE);
                img3.setVisibility(View.GONE);
                break;
            case 3:
                img1.setVisibility(View.GONE);
                img2.setVisibility(View.GONE);
                img3.setVisibility(View.VISIBLE);
                break;
        }
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.select1();
                mPopupWindow.dismiss();
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.select2();
                mPopupWindow.dismiss();
            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.select3();
                mPopupWindow.dismiss();
            }
        });
    }

    /**
     * 选择语言提示
     */
    public static void languageDialog(Context context, int nowSelect, final OnResultListener4 onResListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_language, null);
        final PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView dismiss = (TextView) view.findViewById(R.id.tv_dismiss);
        RelativeLayout layout1 = (RelativeLayout) view.findViewById(R.id.layout1);
        RelativeLayout layout2 = (RelativeLayout) view.findViewById(R.id.layout2);
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        ImageView img1 = (ImageView) view.findViewById(R.id.img1);
        ImageView img2 = (ImageView) view.findViewById(R.id.img2);
        TextView tv1 = (TextView) view.findViewById(R.id.tv1);
        TextView tv2 = (TextView) view.findViewById(R.id.tv2);

        title.setText(context.getString(R.string.select_language));
        tv1.setText(context.getString(R.string.chinese));
        tv2.setText(context.getString(R.string.english));

        switch (nowSelect) {
            case 1:
                img1.setVisibility(View.VISIBLE);
                img2.setVisibility(View.GONE);
                break;
            case 2:
                img1.setVisibility(View.GONE);
                img2.setVisibility(View.VISIBLE);
                break;
        }
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.select1();
                mPopupWindow.dismiss();
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.select2();
                mPopupWindow.dismiss();
            }
        });
    }

    /**
     * 设置头像
     */
    public static void avatarDialog(Context context, final OnResultListener4 onResListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_avatar, null);
        final PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView dismiss = (TextView) view.findViewById(R.id.tv_dismiss);
        TextView take = (TextView) view.findViewById(R.id.tv_take);
        TextView from = (TextView) view.findViewById(R.id.tv_from);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPreference.putInt(UserPreference.SHOULD_PWD, 1);
                onResListener.select1();
                mPopupWindow.dismiss();
            }
        });
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPreference.putInt(UserPreference.SHOULD_PWD, 1);
                onResListener.select2();
                mPopupWindow.dismiss();
            }
        });
    }

    /**
     * 退出登录提示
     */
    public static void logoutDialog(Context context, final OnResultListener4 onResListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_logout, null);
        final PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView sure = (TextView) view.findViewById(R.id.tv_sure);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.select1();
                mPopupWindow.dismiss();
            }
        });
    }

    /**
     * 设置手势密码提示
     */
    public static void gestureDialog(Context context, final OnResultListener4 onResListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_to_set, null);
        final PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView sure = (TextView) view.findViewById(R.id.tv_sure);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResListener.select1();
                mPopupWindow.dismiss();
            }
        });
    }

}
