package com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.tyfdgeruyfgh.ernhfygwsert.R;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuapi.RetrofitZCMdhdTshdfManager;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyubase.BaseZCMdhdTshdfActivity;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ZCMdhdTshdfSharePreferencesUtil;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.StaticZCMdhdTshdfUtil;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ZCMdhdTshdfStatusBarUtil;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ZCMdhdTshdfWelcomeRemindDialog;

public class WelcomeZCMdhdTshdfActivity extends BaseZCMdhdTshdfActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private ZCMdhdTshdfWelcomeRemindDialog ZCMdhdTshdfWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zcm_fhgetr_tqttry_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        ZCMdhdTshdfStatusBarUtil.setTransparent(this, false);
        started = ZCMdhdTshdfSharePreferencesUtil.getBool("started");
        phone = ZCMdhdTshdfSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        ZCMdhdTshdfWelcomeRemindDialog = new ZCMdhdTshdfWelcomeRemindDialog(this);
        ZCMdhdTshdfWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        ZCMdhdTshdfWelcomeRemindDialog.setOnListener(new ZCMdhdTshdfWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                ZCMdhdTshdfSharePreferencesUtil.saveBool("started", true);
                ZCMdhdTshdfWelcomeRemindDialog.dismiss();
                StaticZCMdhdTshdfUtil.startActivity(WelcomeZCMdhdTshdfActivity.this, LoginZCMdhdTshdfActivity.class, null);
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
                bundle.putString("url", RetrofitZCMdhdTshdfManager.ZCXY);
                StaticZCMdhdTshdfUtil.startActivity(WelcomeZCMdhdTshdfActivity.this, UserYsxyZCMdhdTshdfActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RetrofitZCMdhdTshdfManager.YSXY);
                StaticZCMdhdTshdfUtil.startActivity(WelcomeZCMdhdTshdfActivity.this, UserYsxyZCMdhdTshdfActivity.class, bundle);
            }
        });
        ZCMdhdTshdfWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticZCMdhdTshdfUtil.startActivity(WelcomeZCMdhdTshdfActivity.this, MainZCMdhdTshdfActivity.class, null);
            } else {
                StaticZCMdhdTshdfUtil.startActivity(WelcomeZCMdhdTshdfActivity.this, LoginZCMdhdTshdfActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
