package com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.dfjsdndsuweq.sfdkdfmsz.R;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefapi.BaseTaoFenQiDfREvfRetrofitManager;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefbase.BaseBaseTaoFenQiDfREvfActivity;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.BaseTaoFenQiDfREvfSharePreferencesUtil;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.StaticBaseTaoFenQiDfREvfUtil;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.BaseTaoFenQiDfREvfStatusBarUtil;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.BaseTaoFenQiDfREvfWelcomeRemindDialog;

public class WelcomeTaoFenQiDfREvfActivityBase extends BaseBaseTaoFenQiDfREvfActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private BaseTaoFenQiDfREvfWelcomeRemindDialog baseTaoFenQiDfREvfWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome_tao_fen_qi_rtgr_vbd;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        BaseTaoFenQiDfREvfStatusBarUtil.setTransparent(this, false);
        started = BaseTaoFenQiDfREvfSharePreferencesUtil.getBool("started");
        phone = BaseTaoFenQiDfREvfSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        baseTaoFenQiDfREvfWelcomeRemindDialog = new BaseTaoFenQiDfREvfWelcomeRemindDialog(this);
        baseTaoFenQiDfREvfWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        baseTaoFenQiDfREvfWelcomeRemindDialog.setOnListener(new BaseTaoFenQiDfREvfWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                BaseTaoFenQiDfREvfSharePreferencesUtil.saveBool("started", true);
                baseTaoFenQiDfREvfWelcomeRemindDialog.dismiss();
                StaticBaseTaoFenQiDfREvfUtil.startActivity(WelcomeTaoFenQiDfREvfActivityBase.this, LoginTaoFenQiDfREvfActivityBase.class, null);
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
                bundle.putString("url", BaseTaoFenQiDfREvfRetrofitManager.ZCXY);
                StaticBaseTaoFenQiDfREvfUtil.startActivity(WelcomeTaoFenQiDfREvfActivityBase.this, UserYsxyTaoFenQiDfREvfActivityBase.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", BaseTaoFenQiDfREvfRetrofitManager.YSXY);
                StaticBaseTaoFenQiDfREvfUtil.startActivity(WelcomeTaoFenQiDfREvfActivityBase.this, UserYsxyTaoFenQiDfREvfActivityBase.class, bundle);
            }
        });
        baseTaoFenQiDfREvfWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticBaseTaoFenQiDfREvfUtil.startActivity(WelcomeTaoFenQiDfREvfActivityBase.this, MainTaoFenQiDfREvfActivityBase.class, null);
            } else {
                StaticBaseTaoFenQiDfREvfUtil.startActivity(WelcomeTaoFenQiDfREvfActivityBase.this, LoginTaoFenQiDfREvfActivityBase.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
