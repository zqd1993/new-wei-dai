package com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.yamansdjfernt.yongqbdrgrth.R;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretapi.RongjieSfFgdfRetrofitManager;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretbase.BaseRongjieSfFgdfActivity;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.SharePreferencesUtilRongjieSfFgdf;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.StaticRongjieSfFgdfUtil;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.RongjieSfFgdfStatusBarUtil;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.WelcomeRemindDialogRongjieSfFgdf;

public class RongjieSfFgdfWelcomeRongjieSfFgdfActivity extends BaseRongjieSfFgdfActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private WelcomeRemindDialogRongjieSfFgdf welcomeRemindDialogRongjieSfFgdf;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_rong_jie_sdf_brty_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        RongjieSfFgdfStatusBarUtil.setTransparent(this, false);
        started = SharePreferencesUtilRongjieSfFgdf.getBool("started");
        phone = SharePreferencesUtilRongjieSfFgdf.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        welcomeRemindDialogRongjieSfFgdf = new WelcomeRemindDialogRongjieSfFgdf(this);
        welcomeRemindDialogRongjieSfFgdf.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        welcomeRemindDialogRongjieSfFgdf.setOnListener(new WelcomeRemindDialogRongjieSfFgdf.OnListener() {

            @Override
            public void topBtnClicked() {
                SharePreferencesUtilRongjieSfFgdf.saveBool("started", true);
                welcomeRemindDialogRongjieSfFgdf.dismiss();
                StaticRongjieSfFgdfUtil.startActivity(RongjieSfFgdfWelcomeRongjieSfFgdfActivity.this, RongjieSfFgdfLoginRongjieSfFgdfActivity.class, null);
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
                StaticRongjieSfFgdfUtil.startActivity(RongjieSfFgdfWelcomeRongjieSfFgdfActivity.this, RongjieSfFgdfUserYsxyRongjieSfFgdfActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RongjieSfFgdfRetrofitManager.YSXY);
                StaticRongjieSfFgdfUtil.startActivity(RongjieSfFgdfWelcomeRongjieSfFgdfActivity.this, RongjieSfFgdfUserYsxyRongjieSfFgdfActivity.class, bundle);
            }
        });
        welcomeRemindDialogRongjieSfFgdf.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticRongjieSfFgdfUtil.startActivity(RongjieSfFgdfWelcomeRongjieSfFgdfActivity.this, RongjieSfFgdfMainRongjieSfFgdfActivity.class, null);
            } else {
                StaticRongjieSfFgdfUtil.startActivity(RongjieSfFgdfWelcomeRongjieSfFgdfActivity.this, RongjieSfFgdfLoginRongjieSfFgdfActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
