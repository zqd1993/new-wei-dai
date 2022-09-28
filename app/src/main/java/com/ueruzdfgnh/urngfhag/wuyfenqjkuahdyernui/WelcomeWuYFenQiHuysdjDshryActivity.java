package com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.ueruzdfgnh.urngfhag.R;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernapi.RetrofitWuYFenQiHuysdjDshryManager;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernbase.BaseWuYFenQiHuysdjDshryActivity;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.WuYFenQiHuysdjDshrySharePreferencesUtil;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.StaticWuYFenQiHuysdjDshryUtil;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.WuYFenQiHuysdjDshryStatusBarUtil;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.WuYFenQiHuysdjDshryWelcomeRemindDialog;

public class WelcomeWuYFenQiHuysdjDshryActivity extends BaseWuYFenQiHuysdjDshryActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private WuYFenQiHuysdjDshryWelcomeRemindDialog wuYFenQiHuysdjDshryWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wu_yfen_qijai_dfjrt_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        WuYFenQiHuysdjDshryStatusBarUtil.setTransparent(this, false);
        started = WuYFenQiHuysdjDshrySharePreferencesUtil.getBool("started");
        phone = WuYFenQiHuysdjDshrySharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        wuYFenQiHuysdjDshryWelcomeRemindDialog = new WuYFenQiHuysdjDshryWelcomeRemindDialog(this);
        wuYFenQiHuysdjDshryWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        wuYFenQiHuysdjDshryWelcomeRemindDialog.setOnListener(new WuYFenQiHuysdjDshryWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                WuYFenQiHuysdjDshrySharePreferencesUtil.saveBool("started", true);
                wuYFenQiHuysdjDshryWelcomeRemindDialog.dismiss();
                StaticWuYFenQiHuysdjDshryUtil.startActivity(WelcomeWuYFenQiHuysdjDshryActivity.this, LoginWuYFenQiHuysdjDshryActivity.class, null);
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
                bundle.putString("url", RetrofitWuYFenQiHuysdjDshryManager.ZCXY);
                StaticWuYFenQiHuysdjDshryUtil.startActivity(WelcomeWuYFenQiHuysdjDshryActivity.this, UserYsxyWuYFenQiHuysdjDshryActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RetrofitWuYFenQiHuysdjDshryManager.YSXY);
                StaticWuYFenQiHuysdjDshryUtil.startActivity(WelcomeWuYFenQiHuysdjDshryActivity.this, UserYsxyWuYFenQiHuysdjDshryActivity.class, bundle);
            }
        });
        wuYFenQiHuysdjDshryWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticWuYFenQiHuysdjDshryUtil.startActivity(WelcomeWuYFenQiHuysdjDshryActivity.this, MaintWuYFenQiHuysdjDshryActivity.class, null);
            } else {
                StaticWuYFenQiHuysdjDshryUtil.startActivity(WelcomeWuYFenQiHuysdjDshryActivity.this, LoginWuYFenQiHuysdjDshryActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
