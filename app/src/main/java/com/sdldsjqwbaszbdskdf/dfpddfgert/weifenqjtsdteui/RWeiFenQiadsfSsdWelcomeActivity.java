package com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.sdldsjqwbaszbdskdf.dfpddfgert.R;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteapi.RongjieSfFgdfRetrofitManager;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtebase.BaseRongjieSfFgdfActivity;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.SharePreferencesUtilWeiFenQiadsfSsd;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.StaticWeiFenQiadsfSsdUtil;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.WeiFenQiadsfSsdStatusBarUtil;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.WelcomeRemindDialog;

public class RWeiFenQiadsfSsdWelcomeActivity extends BaseRongjieSfFgdfActivity {

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
        return R.layout.activity_wei_fen_qi_dfger_vjkrt_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        WeiFenQiadsfSsdStatusBarUtil.setTransparent(this, false);
        started = SharePreferencesUtilWeiFenQiadsfSsd.getBool("started");
        phone = SharePreferencesUtilWeiFenQiadsfSsd.getString("phone");
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
                SharePreferencesUtilWeiFenQiadsfSsd.saveBool("started", true);
                welcomeRemindDialog.dismiss();
                StaticWeiFenQiadsfSsdUtil.startActivity(RWeiFenQiadsfSsdWelcomeActivity.this, WeiFenQiadsfSsdLoginActivity.class, null);
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
                bundle.putString("url", RongjieSfFgdfRetrofitManager.ZCXY);
                StaticWeiFenQiadsfSsdUtil.startActivity(RWeiFenQiadsfSsdWelcomeActivity.this, WeiFenQiadsfSsdUserYsxyActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RongjieSfFgdfRetrofitManager.YSXY);
                StaticWeiFenQiadsfSsdUtil.startActivity(RWeiFenQiadsfSsdWelcomeActivity.this, WeiFenQiadsfSsdUserYsxyActivity.class, bundle);
            }
        });
        welcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticWeiFenQiadsfSsdUtil.startActivity(RWeiFenQiadsfSsdWelcomeActivity.this, WeiFenQiadsfSsdMainWeiFenQiadsfSsdActivity.class, null);
            } else {
                StaticWeiFenQiadsfSsdUtil.startActivity(RWeiFenQiadsfSsdWelcomeActivity.this, WeiFenQiadsfSsdLoginActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
