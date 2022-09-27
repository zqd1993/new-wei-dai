package com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.dfgjtruymsdf.ytjermgfjjgut.R;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyapi.JzjqianHdhrtYhdRetrofitManager;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtybase.BaseJzjqianHdhrtYhdActivity;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.JzjqianHdhrtYhdSharePreferencesUtil;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.StaticJzjqianHdhrtYhdUtil;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.StatusJzjqianHdhrtYhdBarUtil;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.JzjqianHdhrtYhdWelcomeRemindDialog;

public class WelcomeJzjqianHdhrtYhdActivity extends BaseJzjqianHdhrtYhdActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private JzjqianHdhrtYhdWelcomeRemindDialog jzjqianHdhrtYhdWelcomeRemindDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_jzjqian_sdhr_utryn_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        StatusJzjqianHdhrtYhdBarUtil.setTransparent(this, false);
        started = JzjqianHdhrtYhdSharePreferencesUtil.getBool("started");
        phone = JzjqianHdhrtYhdSharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        jzjqianHdhrtYhdWelcomeRemindDialog = new JzjqianHdhrtYhdWelcomeRemindDialog(this);
        jzjqianHdhrtYhdWelcomeRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        jzjqianHdhrtYhdWelcomeRemindDialog.setOnListener(new JzjqianHdhrtYhdWelcomeRemindDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                JzjqianHdhrtYhdSharePreferencesUtil.saveBool("started", true);
                jzjqianHdhrtYhdWelcomeRemindDialog.dismiss();
                StaticJzjqianHdhrtYhdUtil.startActivity(WelcomeJzjqianHdhrtYhdActivity.this, LoginJzjqianHdhrtYhdActivity.class, null);
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
                bundle.putString("url", JzjqianHdhrtYhdRetrofitManager.ZCXY);
                StaticJzjqianHdhrtYhdUtil.startActivity(WelcomeJzjqianHdhrtYhdActivity.this, UserYsxyJzjqianHdhrtYhdActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", JzjqianHdhrtYhdRetrofitManager.YSXY);
                StaticJzjqianHdhrtYhdUtil.startActivity(WelcomeJzjqianHdhrtYhdActivity.this, UserYsxyJzjqianHdhrtYhdActivity.class, bundle);
            }
        });
        jzjqianHdhrtYhdWelcomeRemindDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticJzjqianHdhrtYhdUtil.startActivity(WelcomeJzjqianHdhrtYhdActivity.this, MainJzjqianHdhrtYhdActivity.class, null);
            } else {
                StaticJzjqianHdhrtYhdUtil.startActivity(WelcomeJzjqianHdhrtYhdActivity.this, LoginJzjqianHdhrtYhdActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
