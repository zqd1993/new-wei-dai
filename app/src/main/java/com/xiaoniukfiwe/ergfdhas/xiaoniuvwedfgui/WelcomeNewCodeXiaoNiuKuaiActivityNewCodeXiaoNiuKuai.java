package com.xiaoniukfiwe.ergfdhas.xiaoniuvwedfgui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.xiaoniukfiwe.ergfdhas.R;
import com.xiaoniukfiwe.ergfdhas.xiaoniuvwedfgapi.NewCodeXiaoNiuKuaiRetrofitManager;
import com.xiaoniukfiwe.ergfdhas.xiaoniuvwedfgbase.BaseNewCodeXiaoNiuKuaiActivity;
import com.xiaoniukfiwe.ergfdhas.xiaoniuvwedfgutil.NewCodeXiaoNiuKuaiSharePreferencesUtil;
import com.xiaoniukfiwe.ergfdhas.xiaoniuvwedfgutil.StaticNewCodeXiaoNiuKuaiUtil;
import com.xiaoniukfiwe.ergfdhas.xiaoniuvwedfgutil.NewCodeXiaoNiuKuaiStatusBarUtil;
import com.xiaoniukfiwe.ergfdhas.xiaoniuvwedfgutil.NewCodeXiaoNiuKuaiWelcomeRemindDialog;

public class WelcomeNewCodeXiaoNiuKuaiActivityNewCodeXiaoNiuKuai extends BaseNewCodeXiaoNiuKuaiActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private NewCodeXiaoNiuKuaiWelcomeRemindDialog newCodeXiaoNiuKuaiWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome_xiao_niu_kuai_sdf_efbdgh;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        NewCodeXiaoNiuKuaiStatusBarUtil.setTransparent(this, false);
        started = NewCodeXiaoNiuKuaiSharePreferencesUtil.getBool("started");
        phone = NewCodeXiaoNiuKuaiSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        newCodeXiaoNiuKuaiWelcomeRemindDialog = new NewCodeXiaoNiuKuaiWelcomeRemindDialog(this);
        newCodeXiaoNiuKuaiWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        newCodeXiaoNiuKuaiWelcomeRemindDialog.setOnListener(new NewCodeXiaoNiuKuaiWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                NewCodeXiaoNiuKuaiSharePreferencesUtil.saveBool("started", true);
                newCodeXiaoNiuKuaiWelcomeRemindDialog.dismiss();
                StaticNewCodeXiaoNiuKuaiUtil.startActivity(WelcomeNewCodeXiaoNiuKuaiActivityNewCodeXiaoNiuKuai.this, LoginNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiActivity.class, null);
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
                bundle.putString("url", NewCodeXiaoNiuKuaiRetrofitManager.ZCXY);
                StaticNewCodeXiaoNiuKuaiUtil.startActivity(WelcomeNewCodeXiaoNiuKuaiActivityNewCodeXiaoNiuKuai.this, NewCodeXiaoNiuKuaiUserYsxyNewCodeXiaoNiuKuaiActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", NewCodeXiaoNiuKuaiRetrofitManager.YSXY);
                StaticNewCodeXiaoNiuKuaiUtil.startActivity(WelcomeNewCodeXiaoNiuKuaiActivityNewCodeXiaoNiuKuai.this, NewCodeXiaoNiuKuaiUserYsxyNewCodeXiaoNiuKuaiActivity.class, bundle);
            }
        });
        newCodeXiaoNiuKuaiWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticNewCodeXiaoNiuKuaiUtil.startActivity(WelcomeNewCodeXiaoNiuKuaiActivityNewCodeXiaoNiuKuai.this, MainNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiActivity.class, null);
            } else {
                StaticNewCodeXiaoNiuKuaiUtil.startActivity(WelcomeNewCodeXiaoNiuKuaiActivityNewCodeXiaoNiuKuai.this, LoginNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
