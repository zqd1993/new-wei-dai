package com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.R;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfapi.JinZhuPigThdfgRetrofitManager;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfbase.BaseJinZhuPigThdfgActivity;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.JinZhuPigThdfgSharePreferencesUtil;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.StaticJinZhuPigThdfgUtil;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.StatusJinZhuPigThdfgBarUtil;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.JinZhuPigThdfgWelcomeRemindDialog;

public class WelcomeJinZhuPigThdfgActivity extends BaseJinZhuPigThdfgActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private JinZhuPigThdfgWelcomeRemindDialog jinZhuPigThdfgWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_jin_zhu_asf_pig_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        StatusJinZhuPigThdfgBarUtil.setTransparent(this, false);
        started = JinZhuPigThdfgSharePreferencesUtil.getBool("started");
        phone = JinZhuPigThdfgSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        jinZhuPigThdfgWelcomeRemindDialog = new JinZhuPigThdfgWelcomeRemindDialog(this);
        jinZhuPigThdfgWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        jinZhuPigThdfgWelcomeRemindDialog.setOnListener(new JinZhuPigThdfgWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                JinZhuPigThdfgSharePreferencesUtil.saveBool("started", true);
                jinZhuPigThdfgWelcomeRemindDialog.dismiss();
                StaticJinZhuPigThdfgUtil.startActivity(WelcomeJinZhuPigThdfgActivity.this, LoginJinZhuPigThdfgActivity.class, null);
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
                bundle.putString("url", JinZhuPigThdfgRetrofitManager.ZCXY);
                StaticJinZhuPigThdfgUtil.startActivity(WelcomeJinZhuPigThdfgActivity.this, UserYsxyJinZhuPigThdfgActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", JinZhuPigThdfgRetrofitManager.YSXY);
                StaticJinZhuPigThdfgUtil.startActivity(WelcomeJinZhuPigThdfgActivity.this, UserYsxyJinZhuPigThdfgActivity.class, bundle);
            }
        });
        jinZhuPigThdfgWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticJinZhuPigThdfgUtil.startActivity(WelcomeJinZhuPigThdfgActivity.this, MainJinZhuPigThdfgActivity.class, null);
            } else {
                StaticJinZhuPigThdfgUtil.startActivity(WelcomeJinZhuPigThdfgActivity.this, LoginJinZhuPigThdfgActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
