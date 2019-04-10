package com.gene.diorpay.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gene.library.base.BaseActivity;
import com.gene.library.util.Util;
import com.google.zxing.WriterException;
import com.yzq.zxinglibrary.encode.CodeCreator;

import butterknife.Bind;
import butterknife.OnClick;
import com.gene.diorpay.R;
import com.gene.library.util.ToastUtil;

/**
 * Created by MaoLJ on 2018/7/20.
 * 收资产页面
 */

public class CollectAssetActivity extends Base1Activity {

    private static final String TAG = "CollectAssetActivity";
    public static String COIN_NAME = "coin_name";
    public static String COIN_ADDRESS = "coin_address";
    public static String COIN_TYPE = "coin_type";
    @Bind(R.id.img_logo)
    ImageView mImgLogo;
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.img_code)
    ImageView mImgCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_collect_asset;
    }

    @Override
    public void initView() {
        Util.immersiveStatus(this, true);
        switch (getIntent().getStringExtra(COIN_NAME)) {
            case "BTC":
                mImgLogo.setImageResource(R.mipmap.coin_btc);
                break;
            case "ETH":
                mImgLogo.setImageResource(R.mipmap.coin_eth);
                break;
            case "SAS":
                mImgLogo.setImageResource(R.mipmap.coin_sas);
                break;
            case "BZF":
                mImgLogo.setImageResource(R.mipmap.coin_bzf);
                break;
            case "DOB":
                mImgLogo.setImageResource(R.mipmap.coin_dob);
                break;
        }
        mTvName.setText(getIntent().getStringExtra(COIN_NAME) + getString(R.string.address0));
        mTvAddress.setText(getIntent().getStringExtra(COIN_ADDRESS));

        Bitmap bitmap = null;
        try {
            bitmap = CodeCreator.createQRCode(getIntent().getStringExtra(COIN_NAME) + " " + getIntent().getStringExtra(COIN_TYPE) + " " + mTvAddress.getText().toString(), 400, 400, null);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        mImgCode.setImageBitmap(bitmap);
    }

    @OnClick({R.id.img_back, R.id.tv_copy})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_copy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(mTvAddress.getText().toString());
                ToastUtil.toast(this, getString(R.string.toast_copy_success));
                break;
        }
    }

}
