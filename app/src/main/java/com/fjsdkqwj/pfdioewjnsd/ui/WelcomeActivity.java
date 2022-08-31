package com.fjsdkqwj.pfdioewjnsd.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.fjsdkqwj.pfdioewjnsd.R;
import com.fjsdkqwj.pfdioewjnsd.api.RetrofitManager;
import com.fjsdkqwj.pfdioewjnsd.base.BaseActivity;
import com.fjsdkqwj.pfdioewjnsd.util.SharePreferencesUtil;
import com.fjsdkqwj.pfdioewjnsd.util.StaticUtil;
import com.fjsdkqwj.pfdioewjnsd.util.StatusBarUtil;
import com.fjsdkqwj.pfdioewjnsd.util.WelcomeRemindDialog;

public class WelcomeActivity extends BaseActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private WelcomeRemindDialog welcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        StatusBarUtil.setTransparent(this, false);
        started = SharePreferencesUtil.getBool("started");
        phone = SharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        welcomeRemindDialog = new WelcomeRemindDialog(this);
        welcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        welcomeRemindDialog.setOnListener(new WelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                SharePreferencesUtil.saveBool("started", true);
                welcomeRemindDialog.dismiss();
                StaticUtil.startActivity(WelcomeActivity.this, LoginActivity.class, null);
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
                bundle.putString("url", RetrofitManager.ZCXY);
                StaticUtil.startActivity(WelcomeActivity.this, UserYsxyActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RetrofitManager.YSXY);
                StaticUtil.startActivity(WelcomeActivity.this, UserYsxyActivity.class, bundle);
            }
        });
        welcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticUtil.startActivity(WelcomeActivity.this, MainActivity.class, null);
            } else {
                StaticUtil.startActivity(WelcomeActivity.this, LoginActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
