package com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.rtgjfjgwuett.rugjjdfgurj.R;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrapi.RYDQHdhtTsdhfrRetrofitManager;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrbase.BaseRYDQHdhtTsdhfrActivity;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.RYDQHdhtTsdhfrSharePreferencesUtil;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.StaticRYDQHdhtTsdhfrUtil;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.RYDQHdhtTsdhfrStatusBarUtil;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.RYDQHdhtTsdhfrWelcomeRemindDialog;

public class WelcomeRYDQHdhtTsdhfrActivity extends BaseRYDQHdhtTsdhfrActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private RYDQHdhtTsdhfrWelcomeRemindDialog RYDQHdhtTsdhfrWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome_rydqh_fdhr_yrtehy;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        RYDQHdhtTsdhfrStatusBarUtil.setTransparent(this, false);
        started = RYDQHdhtTsdhfrSharePreferencesUtil.getBool("started");
        phone = RYDQHdhtTsdhfrSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        RYDQHdhtTsdhfrWelcomeRemindDialog = new RYDQHdhtTsdhfrWelcomeRemindDialog(this);
        RYDQHdhtTsdhfrWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        RYDQHdhtTsdhfrWelcomeRemindDialog.setOnListener(new RYDQHdhtTsdhfrWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                RYDQHdhtTsdhfrSharePreferencesUtil.saveBool("started", true);
                RYDQHdhtTsdhfrWelcomeRemindDialog.dismiss();
                StaticRYDQHdhtTsdhfrUtil.startActivity(WelcomeRYDQHdhtTsdhfrActivity.this, LoginRYDQHdhtTsdhfrActivity.class, null);
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
                bundle.putString("url", RYDQHdhtTsdhfrRetrofitManager.ZCXY);
                StaticRYDQHdhtTsdhfrUtil.startActivity(WelcomeRYDQHdhtTsdhfrActivity.this, UserYsxyRYDQHdhtTsdhfrActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RYDQHdhtTsdhfrRetrofitManager.YSXY);
                StaticRYDQHdhtTsdhfrUtil.startActivity(WelcomeRYDQHdhtTsdhfrActivity.this, UserYsxyRYDQHdhtTsdhfrActivity.class, bundle);
            }
        });
        RYDQHdhtTsdhfrWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticRYDQHdhtTsdhfrUtil.startActivity(WelcomeRYDQHdhtTsdhfrActivity.this, MainRYDQHdhtTsdhfrActivity.class, null);
            } else {
                StaticRYDQHdhtTsdhfrUtil.startActivity(WelcomeRYDQHdhtTsdhfrActivity.this, LoginRYDQHdhtTsdhfrActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
