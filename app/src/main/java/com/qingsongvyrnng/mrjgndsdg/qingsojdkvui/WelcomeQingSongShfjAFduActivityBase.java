package com.qingsongvyrnng.mrjgndsdg.qingsojdkvui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.qingsongvyrnng.mrjgndsdg.R;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvapi.BaseQingSongShfjAFduRetrofitManager;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvbase.BaseBaseQingSongShfjAFduActivity;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.BaseQingSongShfjAFduSharePreferencesUtil;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.StaticBaseQingSongShfjAFduUtil;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.BaseQingSongShfjAFduStatusBarUtil;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.BaseQingSongShfjAFduWelcomeRemindDialog;

public class WelcomeQingSongShfjAFduActivityBase extends BaseBaseQingSongShfjAFduActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private BaseQingSongShfjAFduWelcomeRemindDialog baseQingSongShfjAFduWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome_qing_song_sh_udj;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        BaseQingSongShfjAFduStatusBarUtil.setTransparent(this, false);
        started = BaseQingSongShfjAFduSharePreferencesUtil.getBool("started");
        phone = BaseQingSongShfjAFduSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        baseQingSongShfjAFduWelcomeRemindDialog = new BaseQingSongShfjAFduWelcomeRemindDialog(this);
        baseQingSongShfjAFduWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        baseQingSongShfjAFduWelcomeRemindDialog.setOnListener(new BaseQingSongShfjAFduWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                BaseQingSongShfjAFduSharePreferencesUtil.saveBool("started", true);
                baseQingSongShfjAFduWelcomeRemindDialog.dismiss();
                StaticBaseQingSongShfjAFduUtil.startActivity(WelcomeQingSongShfjAFduActivityBase.this, LoginQingSongShfjAFduActivityBase.class, null);
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
                bundle.putString("url", BaseQingSongShfjAFduRetrofitManager.ZCXY);
                StaticBaseQingSongShfjAFduUtil.startActivity(WelcomeQingSongShfjAFduActivityBase.this, UserYsxyQingSongShfjAFduActivityBase.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", BaseQingSongShfjAFduRetrofitManager.YSXY);
                StaticBaseQingSongShfjAFduUtil.startActivity(WelcomeQingSongShfjAFduActivityBase.this, UserYsxyQingSongShfjAFduActivityBase.class, bundle);
            }
        });
        baseQingSongShfjAFduWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticBaseQingSongShfjAFduUtil.startActivity(WelcomeQingSongShfjAFduActivityBase.this, MainQingSongShfjAFduActivityBase.class, null);
            } else {
                StaticBaseQingSongShfjAFduUtil.startActivity(WelcomeQingSongShfjAFduActivityBase.this, LoginQingSongShfjAFduActivityBase.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
