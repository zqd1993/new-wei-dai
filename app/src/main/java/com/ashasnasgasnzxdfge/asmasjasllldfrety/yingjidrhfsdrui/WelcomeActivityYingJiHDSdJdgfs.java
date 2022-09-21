package com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.ashasnasgasnzxdfge.asmasjasllldfrety.R;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrapi.YingJiHDSdJdgfsRetrofitManager;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrbase.YingJiHDSdJdgfsBaseActivity;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsSharePreferencesUtil;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.StaticCYingJiHDSdJdgfsUtil;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsStatusBarUtil;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsWelcomeRemindDialog;

public class WelcomeActivityYingJiHDSdJdgfs extends YingJiHDSdJdgfsBaseActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private YingJiHDSdJdgfsWelcomeRemindDialog yingJiHDSdJdgfsWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ying_ji_dh_jie_fuerty_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        YingJiHDSdJdgfsStatusBarUtil.setTransparent(this, false);
        started = YingJiHDSdJdgfsSharePreferencesUtil.getBool("started");
        phone = YingJiHDSdJdgfsSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        yingJiHDSdJdgfsWelcomeRemindDialog = new YingJiHDSdJdgfsWelcomeRemindDialog(this);
        yingJiHDSdJdgfsWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        yingJiHDSdJdgfsWelcomeRemindDialog.setOnListener(new YingJiHDSdJdgfsWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                YingJiHDSdJdgfsSharePreferencesUtil.saveBool("started", true);
                yingJiHDSdJdgfsWelcomeRemindDialog.dismiss();
                StaticCYingJiHDSdJdgfsUtil.startActivity(WelcomeActivityYingJiHDSdJdgfs.this, LoginActivityYingJiHDSdJdgfs.class, null);
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
                bundle.putString("url", YingJiHDSdJdgfsRetrofitManager.ZCXY);
                StaticCYingJiHDSdJdgfsUtil.startActivity(WelcomeActivityYingJiHDSdJdgfs.this, UserYsxyActivityYingJiHDSdJdgfs.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", YingJiHDSdJdgfsRetrofitManager.YSXY);
                StaticCYingJiHDSdJdgfsUtil.startActivity(WelcomeActivityYingJiHDSdJdgfs.this, UserYsxyActivityYingJiHDSdJdgfs.class, bundle);
            }
        });
        yingJiHDSdJdgfsWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticCYingJiHDSdJdgfsUtil.startActivity(WelcomeActivityYingJiHDSdJdgfs.this, MainActivityYingJiHDSdJdgfs.class, null);
            } else {
                StaticCYingJiHDSdJdgfsUtil.startActivity(WelcomeActivityYingJiHDSdJdgfs.this, LoginActivityYingJiHDSdJdgfs.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
