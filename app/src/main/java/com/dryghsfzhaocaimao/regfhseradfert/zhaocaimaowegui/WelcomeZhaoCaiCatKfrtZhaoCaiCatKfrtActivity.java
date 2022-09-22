package com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.dryghsfzhaocaimao.regfhseradfert.R;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegapi.RetrofitZhaoCaiCatKfrtManager;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegbase.BaseZhaoCaiCatKfrtActivity;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.ZhaoCaiCatKfrtSharePreferencesUtil;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.StaticZhaoCaiCatKfrtUtil;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.ZhaoCaiCatKfrtStatusBarUtil;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.ZhaoCaiCatKfrtWelcomeRemindDialog;

public class WelcomeZhaoCaiCatKfrtZhaoCaiCatKfrtActivity extends BaseZhaoCaiCatKfrtActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private ZhaoCaiCatKfrtWelcomeRemindDialog zhaoCaiCatKfrtWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhao_cai_mao_dfget_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        ZhaoCaiCatKfrtStatusBarUtil.setTransparent(this, false);
        started = ZhaoCaiCatKfrtSharePreferencesUtil.getBool("started");
        phone = ZhaoCaiCatKfrtSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        zhaoCaiCatKfrtWelcomeRemindDialog = new ZhaoCaiCatKfrtWelcomeRemindDialog(this);
        zhaoCaiCatKfrtWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        zhaoCaiCatKfrtWelcomeRemindDialog.setOnListener(new ZhaoCaiCatKfrtWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                ZhaoCaiCatKfrtSharePreferencesUtil.saveBool("started", true);
                zhaoCaiCatKfrtWelcomeRemindDialog.dismiss();
                StaticZhaoCaiCatKfrtUtil.startActivity(WelcomeZhaoCaiCatKfrtZhaoCaiCatKfrtActivity.this, LoginZhaoCaiCatKfrtZhaoCaiCatKfrtActivity.class, null);
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
                bundle.putString("url", RetrofitZhaoCaiCatKfrtManager.ZCXY);
                StaticZhaoCaiCatKfrtUtil.startActivity(WelcomeZhaoCaiCatKfrtZhaoCaiCatKfrtActivity.this, ZhaoCaiCatKfrtUserYsxyZhaoCaiCatKfrtActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RetrofitZhaoCaiCatKfrtManager.YSXY);
                StaticZhaoCaiCatKfrtUtil.startActivity(WelcomeZhaoCaiCatKfrtZhaoCaiCatKfrtActivity.this, ZhaoCaiCatKfrtUserYsxyZhaoCaiCatKfrtActivity.class, bundle);
            }
        });
        zhaoCaiCatKfrtWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticZhaoCaiCatKfrtUtil.startActivity(WelcomeZhaoCaiCatKfrtZhaoCaiCatKfrtActivity.this, MainZhaoCaiCatKfrtZhaoCaiCatKfrtActivity.class, null);
            } else {
                StaticZhaoCaiCatKfrtUtil.startActivity(WelcomeZhaoCaiCatKfrtZhaoCaiCatKfrtActivity.this, LoginZhaoCaiCatKfrtZhaoCaiCatKfrtActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
