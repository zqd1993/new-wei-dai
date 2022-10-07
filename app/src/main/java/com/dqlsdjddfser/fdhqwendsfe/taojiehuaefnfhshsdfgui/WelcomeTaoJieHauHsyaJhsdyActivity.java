package com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.dqlsdjddfser.fdhqwendsfe.R;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgapi.TaoJieHauHsyaJhsdyRetrofitManager;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgbase.BaseTaoJieHauHsyaJhsdyActivity;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.SharePreferencesUtilTaoJieHauHsyaJhsdy;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.StaticTaoJieHauHsyaJhsdyUtil;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.TaoJieHauHsyaJhsdyStatusBarUtil;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.WelcomeRemindDialogTaoJieHauHsyaJhsdy;

public class WelcomeTaoJieHauHsyaJhsdyActivity extends BaseTaoJieHauHsyaJhsdyActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private WelcomeRemindDialogTaoJieHauHsyaJhsdy welcomeRemindDialogTaoJieHauHsyaJhsdy;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tao_jie_hua_djheru_fhentj_welcome;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        TaoJieHauHsyaJhsdyStatusBarUtil.setTransparent(this, false);
        started = SharePreferencesUtilTaoJieHauHsyaJhsdy.getBool("started");
        phone = SharePreferencesUtilTaoJieHauHsyaJhsdy.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        welcomeRemindDialogTaoJieHauHsyaJhsdy = new WelcomeRemindDialogTaoJieHauHsyaJhsdy(this);
        welcomeRemindDialogTaoJieHauHsyaJhsdy.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        welcomeRemindDialogTaoJieHauHsyaJhsdy.setOnListener(new WelcomeRemindDialogTaoJieHauHsyaJhsdy.OnListener() {

            @Override
            public void topBtnClicked() {
                SharePreferencesUtilTaoJieHauHsyaJhsdy.saveBool("started", true);
                welcomeRemindDialogTaoJieHauHsyaJhsdy.dismiss();
                StaticTaoJieHauHsyaJhsdyUtil.startActivity(WelcomeTaoJieHauHsyaJhsdyActivity.this, LoginTaoJieHauHsyaJhsdyActivity.class, null);
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
                bundle.putString("url", TaoJieHauHsyaJhsdyRetrofitManager.ZCXY);
                StaticTaoJieHauHsyaJhsdyUtil.startActivity(WelcomeTaoJieHauHsyaJhsdyActivity.this, TaoJieHauHsyaJhsdyUserYsxyActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", TaoJieHauHsyaJhsdyRetrofitManager.YSXY);
                StaticTaoJieHauHsyaJhsdyUtil.startActivity(WelcomeTaoJieHauHsyaJhsdyActivity.this, TaoJieHauHsyaJhsdyUserYsxyActivity.class, bundle);
            }
        });
        welcomeRemindDialogTaoJieHauHsyaJhsdy.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticTaoJieHauHsyaJhsdyUtil.startActivity(WelcomeTaoJieHauHsyaJhsdyActivity.this, MainTaoJieHauHsyaJhsdyActivity.class, null);
            } else {
                StaticTaoJieHauHsyaJhsdyUtil.startActivity(WelcomeTaoJieHauHsyaJhsdyActivity.this, LoginTaoJieHauHsyaJhsdyActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
