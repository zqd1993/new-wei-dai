package com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.ashqwhgqwbzxbsam.sdkkkksdfe.R;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertapi.RetrofitJiuJiHdfnfhGDhsdManager;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertbase.BaseJiuJiHdfnfhGDhsdActivity;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.JiuJiHdfnfhGDhsdSharePreferencesUtil;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.StaticJiuJiHdfnfhGDhsdUtil;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.JiuJiHdfnfhGDhsdStatusBarUtil;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.JiuJiHdfnfhGDhsdWelcomeRemindDialog;

public class WelcomeJiuJiHdfnfhGDhsdActivity extends BaseJiuJiHdfnfhGDhsdActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private JiuJiHdfnfhGDhsdWelcomeRemindDialog jiuJiHdfnfhGDhsdWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_jiu_ji_fdher_reytjyh_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        JiuJiHdfnfhGDhsdStatusBarUtil.setTransparent(this, false);
        started = JiuJiHdfnfhGDhsdSharePreferencesUtil.getBool("started");
        phone = JiuJiHdfnfhGDhsdSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        jiuJiHdfnfhGDhsdWelcomeRemindDialog = new JiuJiHdfnfhGDhsdWelcomeRemindDialog(this);
        jiuJiHdfnfhGDhsdWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        jiuJiHdfnfhGDhsdWelcomeRemindDialog.setOnListener(new JiuJiHdfnfhGDhsdWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                JiuJiHdfnfhGDhsdSharePreferencesUtil.saveBool("started", true);
                jiuJiHdfnfhGDhsdWelcomeRemindDialog.dismiss();
                StaticJiuJiHdfnfhGDhsdUtil.startActivity(WelcomeJiuJiHdfnfhGDhsdActivity.this, LoginJiuJiHdfnfhGDhsdActivity.class, null);
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
                bundle.putString("url", RetrofitJiuJiHdfnfhGDhsdManager.ZCXY);
                StaticJiuJiHdfnfhGDhsdUtil.startActivity(WelcomeJiuJiHdfnfhGDhsdActivity.this, UserYsxyJiuJiHdfnfhGDhsdActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RetrofitJiuJiHdfnfhGDhsdManager.YSXY);
                StaticJiuJiHdfnfhGDhsdUtil.startActivity(WelcomeJiuJiHdfnfhGDhsdActivity.this, UserYsxyJiuJiHdfnfhGDhsdActivity.class, bundle);
            }
        });
        jiuJiHdfnfhGDhsdWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticJiuJiHdfnfhGDhsdUtil.startActivity(WelcomeJiuJiHdfnfhGDhsdActivity.this, MaintJiuJiHdfnfhGDhsdActivity.class, null);
            } else {
                StaticJiuJiHdfnfhGDhsdUtil.startActivity(WelcomeJiuJiHdfnfhGDhsdActivity.this, LoginJiuJiHdfnfhGDhsdActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
