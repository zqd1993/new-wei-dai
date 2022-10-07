package com.fghjtuytjuj.drtysghjertyh.page;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fghjtuytjuj.drtysghjertyh.common.SharePreferencesUtil;
import com.fghjtuytjuj.drtysghjertyh.common.StaticCommon;
import com.fghjtuytjuj.drtysghjertyh.common.StatusBarUtil;
import com.fghjtuytjuj.drtysghjertyh.net.NetApi;
import com.fghjtuytjuj.drtysghjertyh.view.TransitionDialog;
import com.fjsdkqwj.pfdioewjnsd.R;

public class TransitionActivity extends AppCompatActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private TransitionDialog transitionDialog;

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_transition);
        started = SharePreferencesUtil.getBool("started");
        phone = SharePreferencesUtil.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    private void showDialog() {
        transitionDialog = new TransitionDialog(this);
        transitionDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        transitionDialog.setOnListener(new TransitionDialog.OnListener() {

            @Override
            public void agreeBtnClicked() {
                SharePreferencesUtil.saveBool("started", true);
                transitionDialog.dismiss();
                StaticCommon.startActivity(TransitionActivity.this, RegisterActivity.class, null);
                finish();
            }

            @Override
            public void disagreeBtnClicked() {
                finish();
            }

            @Override
            public void clickedZcxy() {
                bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putString("url", NetApi.REGISTRATION_AGREEMENT);
                StaticCommon.startActivity(TransitionActivity.this, PrivacyAgreementActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putInt("type", 2);
                bundle.putString("url", NetApi.PRIVACY_AGREEMENT);
                StaticCommon.startActivity(TransitionActivity.this, PrivacyAgreementActivity.class, bundle);
            }
        });
        transitionDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticCommon.startActivity(TransitionActivity.this, WorkActivity.class, null);
            } else {
                StaticCommon.startActivity(TransitionActivity.this, RegisterActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
