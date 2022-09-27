package com.rtyhdfgh.nrtdfgh.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rtyhdfgh.nrtdfgh.R;
import com.rtyhdfgh.nrtdfgh.http.MainApi;
import com.rtyhdfgh.nrtdfgh.util.CommonUtil;
import com.rtyhdfgh.nrtdfgh.util.MyPreferences;
import com.rtyhdfgh.nrtdfgh.util.StartPageDialog;
import com.rtyhdfgh.nrtdfgh.util.StatusBarUtil;

public class StartPageActivity extends AppCompatActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private StartPageDialog startPageDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        StatusBarUtil.setTransparent(this, false);
        started = MyPreferences.getBool("started");
        phone = MyPreferences.getString("phone");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 500);
    }

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    private void showDialog(){
        startPageDialog = new StartPageDialog(this);
        startPageDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        startPageDialog.setOnListener(new StartPageDialog.OnListener() {
            @Override
            public void topBtnClicked() {
                MyPreferences.saveBool("started", true);
                startPageDialog.dismiss();
                CommonUtil.startActivity(StartPageActivity.this, LoginActivity.class, null);
            }

            @Override
            public void bottomBtnClicked() {
                finish();
            }

            @Override
            public void clickedZcxy() {
                bundle = new Bundle();
                bundle.putString("title", "注册协议");
                bundle.putString("url", MainApi.ZCXY);
                CommonUtil.startActivity(StartPageActivity.this, UserAgreementActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putString("title", "隐私协议");
                bundle.putString("url", MainApi.YSXY);
                CommonUtil.startActivity(StartPageActivity.this, UserAgreementActivity.class, bundle);
            }
        });
        startPageDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                CommonUtil.startActivity(this, HomePageActivity.class, null);
            } else {
                CommonUtil.startActivity(this, LoginActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }

}
