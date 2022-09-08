package com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.R;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvapi.BaseQuHuaDjdfuVdhrRetrofitManager;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvbase.BaseBaseQuHuaDjdfuVdhrActivity;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrSharePreferencesUtil;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.StaticBaseQuHuaDjdfuVdhrUtil;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrStatusBarUtil;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrWelcomeRemindDialog;

public class WelcomeQuHuaDjdfuVdhrActivityBase extends BaseBaseQuHuaDjdfuVdhrActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private BaseQuHuaDjdfuVdhrWelcomeRemindDialog baseQuHuaDjdfuVdhrWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome_qu_hua_hua_erf_engh;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        BaseQuHuaDjdfuVdhrStatusBarUtil.setTransparent(this, false);
        started = BaseQuHuaDjdfuVdhrSharePreferencesUtil.getBool("started");
        phone = BaseQuHuaDjdfuVdhrSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        baseQuHuaDjdfuVdhrWelcomeRemindDialog = new BaseQuHuaDjdfuVdhrWelcomeRemindDialog(this);
        baseQuHuaDjdfuVdhrWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        baseQuHuaDjdfuVdhrWelcomeRemindDialog.setOnListener(new BaseQuHuaDjdfuVdhrWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                BaseQuHuaDjdfuVdhrSharePreferencesUtil.saveBool("started", true);
                baseQuHuaDjdfuVdhrWelcomeRemindDialog.dismiss();
                StaticBaseQuHuaDjdfuVdhrUtil.startActivity(WelcomeQuHuaDjdfuVdhrActivityBase.this, LoginQuHuaDjdfuVdhrActivityBase.class, null);
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
                bundle.putString("url", BaseQuHuaDjdfuVdhrRetrofitManager.ZCXY);
                StaticBaseQuHuaDjdfuVdhrUtil.startActivity(WelcomeQuHuaDjdfuVdhrActivityBase.this, UserYsxyQuHuaDjdfuVdhrActivityBase.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", BaseQuHuaDjdfuVdhrRetrofitManager.YSXY);
                StaticBaseQuHuaDjdfuVdhrUtil.startActivity(WelcomeQuHuaDjdfuVdhrActivityBase.this, UserYsxyQuHuaDjdfuVdhrActivityBase.class, bundle);
            }
        });
        baseQuHuaDjdfuVdhrWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticBaseQuHuaDjdfuVdhrUtil.startActivity(WelcomeQuHuaDjdfuVdhrActivityBase.this, MainQuHuaDjdfuVdhrActivityBase.class, null);
            } else {
                StaticBaseQuHuaDjdfuVdhrUtil.startActivity(WelcomeQuHuaDjdfuVdhrActivityBase.this, LoginQuHuaDjdfuVdhrActivityBase.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
