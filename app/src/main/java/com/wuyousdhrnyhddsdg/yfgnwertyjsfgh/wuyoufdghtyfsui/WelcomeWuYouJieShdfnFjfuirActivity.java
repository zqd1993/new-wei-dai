package com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.R;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsapi.RetrofitWuYouJieShdfnFjfuirManager;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsbase.BaseWuYouJieShdfnFjfuirActivity;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.WuYouJieShdfnFjfuirSharePreferencesUtil;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.StaticWuYouJieShdfnFjfuirUtil;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.WuYouJieShdfnFjfuirStatusBarUtil;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.WuYouJieShdfnFjfuirWelcomeRemindDialog;

public class WelcomeWuYouJieShdfnFjfuirActivity extends BaseWuYouJieShdfnFjfuirActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private WuYouJieShdfnFjfuirWelcomeRemindDialog wuYouJieShdfnFjfuirWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wu_you_jie_jdf_eryj_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        WuYouJieShdfnFjfuirStatusBarUtil.setTransparent(this, false);
        started = WuYouJieShdfnFjfuirSharePreferencesUtil.getBool("started");
        phone = WuYouJieShdfnFjfuirSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        wuYouJieShdfnFjfuirWelcomeRemindDialog = new WuYouJieShdfnFjfuirWelcomeRemindDialog(this);
        wuYouJieShdfnFjfuirWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        wuYouJieShdfnFjfuirWelcomeRemindDialog.setOnListener(new WuYouJieShdfnFjfuirWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                WuYouJieShdfnFjfuirSharePreferencesUtil.saveBool("started", true);
                wuYouJieShdfnFjfuirWelcomeRemindDialog.dismiss();
                StaticWuYouJieShdfnFjfuirUtil.startActivity(WelcomeWuYouJieShdfnFjfuirActivity.this, LoginWuYouJieShdfnFjfuirActivity.class, null);
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
                bundle.putString("url", RetrofitWuYouJieShdfnFjfuirManager.ZCXY);
                StaticWuYouJieShdfnFjfuirUtil.startActivity(WelcomeWuYouJieShdfnFjfuirActivity.this, UserYsxyWuYouJieShdfnFjfuirActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RetrofitWuYouJieShdfnFjfuirManager.YSXY);
                StaticWuYouJieShdfnFjfuirUtil.startActivity(WelcomeWuYouJieShdfnFjfuirActivity.this, UserYsxyWuYouJieShdfnFjfuirActivity.class, bundle);
            }
        });
        wuYouJieShdfnFjfuirWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticWuYouJieShdfnFjfuirUtil.startActivity(WelcomeWuYouJieShdfnFjfuirActivity.this, MaintWuYouJieShdfnFjfuirActivity.class, null);
            } else {
                StaticWuYouJieShdfnFjfuirUtil.startActivity(WelcomeWuYouJieShdfnFjfuirActivity.this, LoginWuYouJieShdfnFjfuirActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
