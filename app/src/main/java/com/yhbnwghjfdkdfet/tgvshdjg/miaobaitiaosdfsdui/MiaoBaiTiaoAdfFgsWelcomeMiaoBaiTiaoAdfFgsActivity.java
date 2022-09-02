package com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.yhbnwghjfdkdfet.tgvshdjg.R;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdapi.MiaoBaiTiaoAdfFgsRetrofitManager;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdbase.BaseMiaoBaiTiaoAdfFgsActivity;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.SharePreferencesMiaoBaiTiaoAdfFgsUtil;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.StaticMiaoBaiTiaoAdfFgsUtil;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.MiaoBaiTiaoAdfFgsStatusBarUtil;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.MiaoBaiTiaoAdfFgsWelcomeRemindDialog;

public class MiaoBaiTiaoAdfFgsWelcomeMiaoBaiTiaoAdfFgsActivity extends BaseMiaoBaiTiaoAdfFgsActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private MiaoBaiTiaoAdfFgsWelcomeRemindDialog miaoBaiTiaoAdfFgsWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_miao_bai_tiao_sdf_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        MiaoBaiTiaoAdfFgsStatusBarUtil.setTransparent(this, false);
        started = SharePreferencesMiaoBaiTiaoAdfFgsUtil.getBool("started");
        phone = SharePreferencesMiaoBaiTiaoAdfFgsUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        miaoBaiTiaoAdfFgsWelcomeRemindDialog = new MiaoBaiTiaoAdfFgsWelcomeRemindDialog(this);
        miaoBaiTiaoAdfFgsWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        miaoBaiTiaoAdfFgsWelcomeRemindDialog.setOnListener(new MiaoBaiTiaoAdfFgsWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                SharePreferencesMiaoBaiTiaoAdfFgsUtil.saveBool("started", true);
                miaoBaiTiaoAdfFgsWelcomeRemindDialog.dismiss();
                StaticMiaoBaiTiaoAdfFgsUtil.startActivity(MiaoBaiTiaoAdfFgsWelcomeMiaoBaiTiaoAdfFgsActivity.this, LoginMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity.class, null);
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                finish();
            }

            @Override
            public void clickedZcxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", MiaoBaiTiaoAdfFgsRetrofitManager.ZCXY);
                StaticMiaoBaiTiaoAdfFgsUtil.startActivity(MiaoBaiTiaoAdfFgsWelcomeMiaoBaiTiaoAdfFgsActivity.this, MiaoBaiTiaoAdfFgsUserYsxyMiaoBaiTiaoAdfFgsActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", MiaoBaiTiaoAdfFgsRetrofitManager.YSXY);
                StaticMiaoBaiTiaoAdfFgsUtil.startActivity(MiaoBaiTiaoAdfFgsWelcomeMiaoBaiTiaoAdfFgsActivity.this, MiaoBaiTiaoAdfFgsUserYsxyMiaoBaiTiaoAdfFgsActivity.class, bundle);
            }
        });
        miaoBaiTiaoAdfFgsWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticMiaoBaiTiaoAdfFgsUtil.startActivity(MiaoBaiTiaoAdfFgsWelcomeMiaoBaiTiaoAdfFgsActivity.this, MainMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity.class, null);
            } else {
                StaticMiaoBaiTiaoAdfFgsUtil.startActivity(MiaoBaiTiaoAdfFgsWelcomeMiaoBaiTiaoAdfFgsActivity.this, LoginMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
