package com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyactivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.R;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyapi.MeiJFenQiDdfgjRfdfgRetrofitManager;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertybase.BaseMeiJFenQiDdfgjRfdfgActivity;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.MeiJFenQiDdfgjRfdfgSharePreferencesUtil;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.StaticMeiJFenQiDdfgjRfdfgUtil;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.MeiJFenQiDdfgjRfdfgStatusBarUtil;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.MeiJFenQiDdfgjRfdfgWelcomeRemindDialog;

public class WelcomeMeiJFenQiDdfgjRfdfgActivity extends BaseMeiJFenQiDdfgjRfdfgActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private MeiJFenQiDdfgjRfdfgWelcomeRemindDialog meiJFenQiDdfgjRfdfgWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mei_jie_sfgh_ewyfhg_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        MeiJFenQiDdfgjRfdfgStatusBarUtil.setTransparent(this, false);
        started = MeiJFenQiDdfgjRfdfgSharePreferencesUtil.getBool("started");
        phone = MeiJFenQiDdfgjRfdfgSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        meiJFenQiDdfgjRfdfgWelcomeRemindDialog = new MeiJFenQiDdfgjRfdfgWelcomeRemindDialog(this);
        meiJFenQiDdfgjRfdfgWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        meiJFenQiDdfgjRfdfgWelcomeRemindDialog.setOnListener(new MeiJFenQiDdfgjRfdfgWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                MeiJFenQiDdfgjRfdfgSharePreferencesUtil.saveBool("started", true);
                meiJFenQiDdfgjRfdfgWelcomeRemindDialog.dismiss();
                StaticMeiJFenQiDdfgjRfdfgUtil.startActivity(WelcomeMeiJFenQiDdfgjRfdfgActivity.this, LoginMeiJFenQiDdfgjRfdfgActivity.class, null);
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
                bundle.putString("url", MeiJFenQiDdfgjRfdfgRetrofitManager.ZCXY);
                StaticMeiJFenQiDdfgjRfdfgUtil.startActivity(WelcomeMeiJFenQiDdfgjRfdfgActivity.this, UserYsxyMeiJFenQiDdfgjRfdfgActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", MeiJFenQiDdfgjRfdfgRetrofitManager.YSXY);
                StaticMeiJFenQiDdfgjRfdfgUtil.startActivity(WelcomeMeiJFenQiDdfgjRfdfgActivity.this, UserYsxyMeiJFenQiDdfgjRfdfgActivity.class, bundle);
            }
        });
        meiJFenQiDdfgjRfdfgWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticMeiJFenQiDdfgjRfdfgUtil.startActivity(WelcomeMeiJFenQiDdfgjRfdfgActivity.this, MainMeiJFenQiDdfgjRfdfgActivity.class, null);
            } else {
                StaticMeiJFenQiDdfgjRfdfgUtil.startActivity(WelcomeMeiJFenQiDdfgjRfdfgActivity.this, LoginMeiJFenQiDdfgjRfdfgActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
