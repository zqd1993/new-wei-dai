package com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.dqlsdjdhwmg.fdhqwenhwmg.R;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwapi.MangGuoHwRetrofitManager;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwbase.BaseMangGuoHwActivity;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.MangGuoHwSharePreferencesUtils;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.StaticMangGuoHwUtil;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.StatusBarUtilMangGuoHw;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.MangGuoHwWelcomeRemindDialog;

public class WelcomeMangGuoHwActivity extends BaseMangGuoHwActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private MangGuoHwWelcomeRemindDialog mangGuoHwWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mang_guo_hw_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        StatusBarUtilMangGuoHw.setTransparent(this, false);
        started = MangGuoHwSharePreferencesUtils.getBool("started");
        phone = MangGuoHwSharePreferencesUtils.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        mangGuoHwWelcomeRemindDialog = new MangGuoHwWelcomeRemindDialog(this);
        mangGuoHwWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        mangGuoHwWelcomeRemindDialog.setOnListener(new MangGuoHwWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                MangGuoHwSharePreferencesUtils.saveBool("started", true);
                mangGuoHwWelcomeRemindDialog.dismiss();
                StaticMangGuoHwUtil.startActivity(WelcomeMangGuoHwActivity.this, LoginMangGuoHwActivity.class, null);
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
                bundle.putString("url", MangGuoHwRetrofitManager.ZCXY);
                StaticMangGuoHwUtil.startActivity(WelcomeMangGuoHwActivity.this, UserYsxyMangGuoHwActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", MangGuoHwRetrofitManager.YSXY);
                StaticMangGuoHwUtil.startActivity(WelcomeMangGuoHwActivity.this, UserYsxyMangGuoHwActivity.class, bundle);
            }
        });
        mangGuoHwWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticMangGuoHwUtil.startActivity(WelcomeMangGuoHwActivity.this, MainMangGuoHwActivity.class, null);
            } else {
                StaticMangGuoHwUtil.startActivity(WelcomeMangGuoHwActivity.this, LoginMangGuoHwActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
