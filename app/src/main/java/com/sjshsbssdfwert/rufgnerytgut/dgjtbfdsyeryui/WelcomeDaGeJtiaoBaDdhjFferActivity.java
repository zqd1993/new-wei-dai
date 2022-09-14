package com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.sjshsbssdfwert.rufgnerytgut.R;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryapi.DaGeJtiaoBaDdhjFferRetrofitManager;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerybase.BaseDaGeJtiaoBaDdhjFferActivity;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.DaGeJtiaoBaDdhjFferSharePreferencesUtil;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.StaticDaGeJtiaoBaDdhjFferUtil;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.DaGeJtiaoBaDdhjFferStatusBarUtil;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.DaGeJtiaoBaDdhjFferWelcomeRemindDialog;

public class WelcomeDaGeJtiaoBaDdhjFferActivity extends BaseDaGeJtiaoBaDdhjFferActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private DaGeJtiaoBaDdhjFferWelcomeRemindDialog daGeJtiaoBaDdhjFferWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_da_ge_jdf_yrjf_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        DaGeJtiaoBaDdhjFferStatusBarUtil.setTransparent(this, false);
        started = DaGeJtiaoBaDdhjFferSharePreferencesUtil.getBool("started");
        phone = DaGeJtiaoBaDdhjFferSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        daGeJtiaoBaDdhjFferWelcomeRemindDialog = new DaGeJtiaoBaDdhjFferWelcomeRemindDialog(this);
        daGeJtiaoBaDdhjFferWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        daGeJtiaoBaDdhjFferWelcomeRemindDialog.setOnListener(new DaGeJtiaoBaDdhjFferWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                DaGeJtiaoBaDdhjFferSharePreferencesUtil.saveBool("started", true);
                daGeJtiaoBaDdhjFferWelcomeRemindDialog.dismiss();
                StaticDaGeJtiaoBaDdhjFferUtil.startActivity(WelcomeDaGeJtiaoBaDdhjFferActivity.this, LoginDaGeJtiaoBaDdhjFferActivity.class, null);
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
                bundle.putString("url", DaGeJtiaoBaDdhjFferRetrofitManager.ZCXY);
                StaticDaGeJtiaoBaDdhjFferUtil.startActivity(WelcomeDaGeJtiaoBaDdhjFferActivity.this, UserYsxyDaGeJtiaoBaDdhjFferActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", DaGeJtiaoBaDdhjFferRetrofitManager.YSXY);
                StaticDaGeJtiaoBaDdhjFferUtil.startActivity(WelcomeDaGeJtiaoBaDdhjFferActivity.this, UserYsxyDaGeJtiaoBaDdhjFferActivity.class, bundle);
            }
        });
        daGeJtiaoBaDdhjFferWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticDaGeJtiaoBaDdhjFferUtil.startActivity(WelcomeDaGeJtiaoBaDdhjFferActivity.this, MainDaGeJtiaoBaDdhjFferActivity.class, null);
            } else {
                StaticDaGeJtiaoBaDdhjFferUtil.startActivity(WelcomeDaGeJtiaoBaDdhjFferActivity.this, LoginDaGeJtiaoBaDdhjFferActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
