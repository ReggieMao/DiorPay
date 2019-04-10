package com.gene.diorpay.activity;

import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import com.gene.diorpay.R;
import com.gene.library.base.BaseActivity;
import com.gene.library.util.Util;

/**
 * Created by MaoLJ on 2018/7/24.
 * 帮助中心页面
 */

public class HelpCenterActivity extends Base1Activity {

    private static final String TAG = "HelpCenterActivity";
    @Bind(R.id.img0) ImageView mImg0; @Bind(R.id.img1) ImageView mImg1; @Bind(R.id.img2) ImageView mImg2; @Bind(R.id.img3) ImageView mImg3;
    @Bind(R.id.img4) ImageView mImg4; @Bind(R.id.img5) ImageView mImg5; @Bind(R.id.img6) ImageView mImg6; @Bind(R.id.img7) ImageView mImg7;
    @Bind(R.id.img8) ImageView mImg8;
    @Bind(R.id.ll0) LinearLayout mLayout0; @Bind(R.id.ll1) LinearLayout mLayout1; @Bind(R.id.ll2) LinearLayout mLayout2; @Bind(R.id.ll3) LinearLayout mLayout3;
    @Bind(R.id.ll4) LinearLayout mLayout4; @Bind(R.id.ll5) LinearLayout mLayout5; @Bind(R.id.ll6) LinearLayout mLayout6; @Bind(R.id.ll7) LinearLayout mLayout7;
    @Bind(R.id.ll8) LinearLayout mLayout8;
    private boolean mLl0Show = false; private boolean mLl1Show = false; private boolean mLl2Show = false; private boolean mLl3Show = false;
    private boolean mLl4Show = false; private boolean mLl5Show = false; private boolean mLl6Show = false; private boolean mLl7Show = false;
    private boolean mLl8Show = false;
    @Bind(R.id.tv_answer)
    TextView mTvAnswer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_help_center;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
        mTvAnswer.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick({R.id.img_back, R.id.rl0, R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4, R.id.rl5, R.id.rl6, R.id.rl7, R.id.rl8})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rl0:
                if (!mLl0Show) {
                    mLayout0.setVisibility(View.VISIBLE);
                    mLl0Show = true;
                    mImg0.setImageResource(R.mipmap.go_b);
                } else {
                    mLayout0.setVisibility(View.GONE);
                    mLl0Show = false;
                    mImg0.setImageResource(R.mipmap.go);
                }
                break;
            case R.id.rl1:
                if (!mLl1Show) {
                    mLayout1.setVisibility(View.VISIBLE);
                    mLl1Show = true;
                    mImg1.setImageResource(R.mipmap.go_b);
                } else {
                    mLayout1.setVisibility(View.GONE);
                    mLl1Show = false;
                    mImg1.setImageResource(R.mipmap.go);
                }
                break;
            case R.id.rl2:
                if (!mLl2Show) {
                    mLayout2.setVisibility(View.VISIBLE);
                    mLl2Show = true;
                    mImg2.setImageResource(R.mipmap.go_b);
                } else {
                    mLayout2.setVisibility(View.GONE);
                    mLl2Show = false;
                    mImg2.setImageResource(R.mipmap.go);
                }
                break;
            case R.id.rl3:
                if (!mLl3Show) {
                    mLayout3.setVisibility(View.VISIBLE);
                    mLl3Show = true;
                    mImg3.setImageResource(R.mipmap.go_b);
                } else {
                    mLayout3.setVisibility(View.GONE);
                    mLl3Show = false;
                    mImg3.setImageResource(R.mipmap.go);
                }
                break;
            case R.id.rl4:
                if (!mLl4Show) {
                    mLayout4.setVisibility(View.VISIBLE);
                    mLl4Show = true;
                    mImg4.setImageResource(R.mipmap.go_b);
                } else {
                    mLayout4.setVisibility(View.GONE);
                    mLl4Show = false;
                    mImg4.setImageResource(R.mipmap.go);
                }
                break;
            case R.id.rl5:
                if (!mLl5Show) {
                    mLayout5.setVisibility(View.VISIBLE);
                    mLl5Show = true;
                    mImg5.setImageResource(R.mipmap.go_b);
                } else {
                    mLayout5.setVisibility(View.GONE);
                    mLl5Show = false;
                    mImg5.setImageResource(R.mipmap.go);
                }
                break;
            case R.id.rl6:
                if (!mLl6Show) {
                    mLayout6.setVisibility(View.VISIBLE);
                    mLl6Show = true;
                    mImg6.setImageResource(R.mipmap.go_b);
                } else {
                    mLayout6.setVisibility(View.GONE);
                    mLl6Show = false;
                    mImg6.setImageResource(R.mipmap.go);
                }
                break;
            case R.id.rl7:
                if (!mLl7Show) {
                    mLayout7.setVisibility(View.VISIBLE);
                    mLl7Show = true;
                    mImg7.setImageResource(R.mipmap.go_b);
                } else {
                    mLayout7.setVisibility(View.GONE);
                    mLl7Show = false;
                    mImg7.setImageResource(R.mipmap.go);
                }
                break;
            case R.id.rl8:
                if (!mLl8Show) {
                    mLayout8.setVisibility(View.VISIBLE);
                    mLl8Show = true;
                    mImg8.setImageResource(R.mipmap.go_b);
                } else {
                    mLayout8.setVisibility(View.GONE);
                    mLl8Show = false;
                    mImg8.setImageResource(R.mipmap.go);
                }
                break;
        }
    }

}
