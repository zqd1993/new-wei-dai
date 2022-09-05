package com.youjiegherh.pocketqwrh.youjiewetdfhui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.youjiegherh.pocketqwrh.R;
import com.youjiegherh.pocketqwrh.youjiewetdfhapi.NewCodeXiaoNiuKuaiRetrofitManager;
import com.youjiegherh.pocketqwrh.youjiewetdfhbase.BaseNewCodeXiaoNiuKuaiActivity;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.YouJieSDjdfiSharePreferencesUtil;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.StaticYouJieSDjdfiUtil;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.YouJieSDjdfiStatusBarUtil;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.YouJieSDjdfiWelcomeRemindDialog;

public class WelcomeYouJieSDjdfiActivity extends BaseNewCodeXiaoNiuKuaiActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private YouJieSDjdfiWelcomeRemindDialog youJieSDjdfiWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome_you_jie_iejbvr;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        YouJieSDjdfiStatusBarUtil.setTransparent(this, false);
        started = YouJieSDjdfiSharePreferencesUtil.getBool("started");
        phone = YouJieSDjdfiSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        youJieSDjdfiWelcomeRemindDialog = new YouJieSDjdfiWelcomeRemindDialog(this);
        youJieSDjdfiWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        youJieSDjdfiWelcomeRemindDialog.setOnListener(new YouJieSDjdfiWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                YouJieSDjdfiSharePreferencesUtil.saveBool("started", true);
                youJieSDjdfiWelcomeRemindDialog.dismiss();
                StaticYouJieSDjdfiUtil.startActivity(WelcomeYouJieSDjdfiActivity.this, LoginYouJieSDjdfiActivity.class, null);
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
                StaticYouJieSDjdfiUtil.startActivity(WelcomeYouJieSDjdfiActivity.this, YouJieSDjdfiUserYsxyActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", NewCodeXiaoNiuKuaiRetrofitManager.YSXY);
                StaticYouJieSDjdfiUtil.startActivity(WelcomeYouJieSDjdfiActivity.this, YouJieSDjdfiUserYsxyActivity.class, bundle);
            }
        });
        youJieSDjdfiWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticYouJieSDjdfiUtil.startActivity(WelcomeYouJieSDjdfiActivity.this, MainYouJieSDjdfiActivity.class, null);
            } else {
                StaticYouJieSDjdfiUtil.startActivity(WelcomeYouJieSDjdfiActivity.this, LoginYouJieSDjdfiActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
