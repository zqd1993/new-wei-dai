package com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.R;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyapi.CaiJieTongYshVdjrytRetrofitManager;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhybase.CaiJieTongYshVdjrytBaseActivity;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.CaiJieTongYshVdjrytSharePreferencesUtil;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.StaticCaiJieTongYshVdjrytUtil;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.CaiJieTongYshVdjrytStatusBarUtil;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.CaiJieTongYshVdjrytWelcomeRemindDialog;

public class WelcomeActivityCaiJieTongYshVdjryt extends CaiJieTongYshVdjrytBaseActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private CaiJieTongYshVdjrytWelcomeRemindDialog caiJieTongYshVdjrytWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cai_jie_tong_drt_etfnh_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        CaiJieTongYshVdjrytStatusBarUtil.setTransparent(this, false);
        started = CaiJieTongYshVdjrytSharePreferencesUtil.getBool("started");
        phone = CaiJieTongYshVdjrytSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        caiJieTongYshVdjrytWelcomeRemindDialog = new CaiJieTongYshVdjrytWelcomeRemindDialog(this);
        caiJieTongYshVdjrytWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        caiJieTongYshVdjrytWelcomeRemindDialog.setOnListener(new CaiJieTongYshVdjrytWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                CaiJieTongYshVdjrytSharePreferencesUtil.saveBool("started", true);
                caiJieTongYshVdjrytWelcomeRemindDialog.dismiss();
                StaticCaiJieTongYshVdjrytUtil.startActivity(WelcomeActivityCaiJieTongYshVdjryt.this, LoginActivityCaiJieTongYshVdjryt.class, null);
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
                bundle.putString("url", CaiJieTongYshVdjrytRetrofitManager.ZCXY);
                StaticCaiJieTongYshVdjrytUtil.startActivity(WelcomeActivityCaiJieTongYshVdjryt.this, UserYsxyActivityCaiJieTongYshVdjryt.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", CaiJieTongYshVdjrytRetrofitManager.YSXY);
                StaticCaiJieTongYshVdjrytUtil.startActivity(WelcomeActivityCaiJieTongYshVdjryt.this, UserYsxyActivityCaiJieTongYshVdjryt.class, bundle);
            }
        });
        caiJieTongYshVdjrytWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticCaiJieTongYshVdjrytUtil.startActivity(WelcomeActivityCaiJieTongYshVdjryt.this, MainActivityCaiJieTongYshVdjryt.class, null);
            } else {
                StaticCaiJieTongYshVdjrytUtil.startActivity(WelcomeActivityCaiJieTongYshVdjryt.this, LoginActivityCaiJieTongYshVdjryt.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
