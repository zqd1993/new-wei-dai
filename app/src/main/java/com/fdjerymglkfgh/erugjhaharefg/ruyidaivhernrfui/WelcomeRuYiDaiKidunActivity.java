package com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.fdjerymglkfgh.erugjhaharefg.R;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfapi.RuYiDaiKidunRetrofitManager;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfbase.BaseRuYiDaiKidunActivity;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfutil.RuYiDaiKidunSharePreferencesUtil;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfutil.StaticRuYiDaiKidunUtil;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfutil.RuYiDaiKidunStatusBarUtil;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfutil.RuYiDaiKidunWelcomeRemindDialog;

public class WelcomeRuYiDaiKidunActivity extends BaseRuYiDaiKidunActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private RuYiDaiKidunWelcomeRemindDialog ruYiDaiKidunWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome_ru_yi_dai_dfu_eng;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        RuYiDaiKidunStatusBarUtil.setTransparent(this, false);
        started = RuYiDaiKidunSharePreferencesUtil.getBool("started");
        phone = RuYiDaiKidunSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        ruYiDaiKidunWelcomeRemindDialog = new RuYiDaiKidunWelcomeRemindDialog(this);
        ruYiDaiKidunWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        ruYiDaiKidunWelcomeRemindDialog.setOnListener(new RuYiDaiKidunWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                RuYiDaiKidunSharePreferencesUtil.saveBool("started", true);
                ruYiDaiKidunWelcomeRemindDialog.dismiss();
                StaticRuYiDaiKidunUtil.startActivity(WelcomeRuYiDaiKidunActivity.this, LoginRuYiDaiKidunActivity.class, null);
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
                bundle.putString("url", RuYiDaiKidunRetrofitManager.ZCXY);
                StaticRuYiDaiKidunUtil.startActivity(WelcomeRuYiDaiKidunActivity.this, UserYsxyRuYiDaiKidunActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RuYiDaiKidunRetrofitManager.YSXY);
                StaticRuYiDaiKidunUtil.startActivity(WelcomeRuYiDaiKidunActivity.this, UserYsxyRuYiDaiKidunActivity.class, bundle);
            }
        });
        ruYiDaiKidunWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticRuYiDaiKidunUtil.startActivity(WelcomeRuYiDaiKidunActivity.this, MainRuYiDaiKidunActivity.class, null);
            } else {
                StaticRuYiDaiKidunUtil.startActivity(WelcomeRuYiDaiKidunActivity.this, LoginRuYiDaiKidunActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
