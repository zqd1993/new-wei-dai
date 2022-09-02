package com.xbk1jk.zldkbk.zhulihuavrsdtrui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.xbk1jk.zldkbk.R;
import com.xbk1jk.zldkbk.zhulihuavrsdtrapi.ZhuLiDaiKuanHuadewgRetrofitManager;
import com.xbk1jk.zldkbk.zhulihuavrsdtrbase.BaseZhuLiDaiKuanHuadewgFgsActivity;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.SharePreferencesZhuLiDaiKuanHuadewgUtil;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.StaticZhuLiDaiKuanHuadewgUtil;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.ZhuLiDaiKuanHuadewgStatusBarUtil;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.ZhuLiDaiKuanHuadewgWelcomeRemindDialog;

public class WelcomeZhuLiDaiKuanHuadewgActivity extends BaseZhuLiDaiKuanHuadewgFgsActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private ZhuLiDaiKuanHuadewgWelcomeRemindDialog zhuLiDaiKuanHuadewgWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhu_li_dai_kuan_hua_setg_sert_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        ZhuLiDaiKuanHuadewgStatusBarUtil.setTransparent(this, false);
        started = SharePreferencesZhuLiDaiKuanHuadewgUtil.getBool("started");
        phone = SharePreferencesZhuLiDaiKuanHuadewgUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        zhuLiDaiKuanHuadewgWelcomeRemindDialog = new ZhuLiDaiKuanHuadewgWelcomeRemindDialog(this);
        zhuLiDaiKuanHuadewgWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        zhuLiDaiKuanHuadewgWelcomeRemindDialog.setOnListener(new ZhuLiDaiKuanHuadewgWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                SharePreferencesZhuLiDaiKuanHuadewgUtil.saveBool("started", true);
                zhuLiDaiKuanHuadewgWelcomeRemindDialog.dismiss();
                StaticZhuLiDaiKuanHuadewgUtil.startActivity(WelcomeZhuLiDaiKuanHuadewgActivity.this, LoginZhuLiDaiKuanHuadewgActivity.class, null);
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
                bundle.putString("url", ZhuLiDaiKuanHuadewgRetrofitManager.ZCXY);
                StaticZhuLiDaiKuanHuadewgUtil.startActivity(WelcomeZhuLiDaiKuanHuadewgActivity.this, ZhuLiDaiKuanHuadewgUserYsxyActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ZhuLiDaiKuanHuadewgRetrofitManager.YSXY);
                StaticZhuLiDaiKuanHuadewgUtil.startActivity(WelcomeZhuLiDaiKuanHuadewgActivity.this, ZhuLiDaiKuanHuadewgUserYsxyActivity.class, bundle);
            }
        });
        zhuLiDaiKuanHuadewgWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticZhuLiDaiKuanHuadewgUtil.startActivity(WelcomeZhuLiDaiKuanHuadewgActivity.this, MainZhuLiDaiKuanHuadewgActivity.class, null);
            } else {
                StaticZhuLiDaiKuanHuadewgUtil.startActivity(WelcomeZhuLiDaiKuanHuadewgActivity.this, LoginZhuLiDaiKuanHuadewgActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
