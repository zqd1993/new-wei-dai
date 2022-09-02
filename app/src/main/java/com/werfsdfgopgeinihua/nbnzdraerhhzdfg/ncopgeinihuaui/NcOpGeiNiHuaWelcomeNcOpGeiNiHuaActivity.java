package com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.R;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaapi.NcOpGeiNiHuaRetrofitManager;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuabase.BaseNcOpGeiNiHuaActivity;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaSharePreferencesUtil;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaStaticUtil;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaStatusBarUtil;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaWelcomeRemindDialog;

public class NcOpGeiNiHuaWelcomeNcOpGeiNiHuaActivity extends BaseNcOpGeiNiHuaActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private NcOpGeiNiHuaWelcomeRemindDialog ncOpGeiNiHuaWelcomeRemindDialog;

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
        NcOpGeiNiHuaStatusBarUtil.setTransparent(this, false);
        started = NcOpGeiNiHuaSharePreferencesUtil.getBool("started");
        phone = NcOpGeiNiHuaSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        ncOpGeiNiHuaWelcomeRemindDialog = new NcOpGeiNiHuaWelcomeRemindDialog(this);
        ncOpGeiNiHuaWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        ncOpGeiNiHuaWelcomeRemindDialog.setOnListener(new NcOpGeiNiHuaWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                NcOpGeiNiHuaSharePreferencesUtil.saveBool("started", true);
                ncOpGeiNiHuaWelcomeRemindDialog.dismiss();
                NcOpGeiNiHuaStaticUtil.startActivity(NcOpGeiNiHuaWelcomeNcOpGeiNiHuaActivity.this, NcOpGeiNiHuaLoginNcOpGeiNiHuaActivity.class, null);
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
                bundle.putString("url", NcOpGeiNiHuaRetrofitManager.ZCXY);
                NcOpGeiNiHuaStaticUtil.startActivity(NcOpGeiNiHuaWelcomeNcOpGeiNiHuaActivity.this, NcOpGeiNiHuaUserYsxyNcOpGeiNiHuaActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", NcOpGeiNiHuaRetrofitManager.YSXY);
                NcOpGeiNiHuaStaticUtil.startActivity(NcOpGeiNiHuaWelcomeNcOpGeiNiHuaActivity.this, NcOpGeiNiHuaUserYsxyNcOpGeiNiHuaActivity.class, bundle);
            }
        });
        ncOpGeiNiHuaWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                NcOpGeiNiHuaStaticUtil.startActivity(NcOpGeiNiHuaWelcomeNcOpGeiNiHuaActivity.this, NcOpGeiNiHuaMainNcOpGeiNiHuaActivity.class, null);
            } else {
                NcOpGeiNiHuaStaticUtil.startActivity(NcOpGeiNiHuaWelcomeNcOpGeiNiHuaActivity.this, NcOpGeiNiHuaLoginNcOpGeiNiHuaActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
