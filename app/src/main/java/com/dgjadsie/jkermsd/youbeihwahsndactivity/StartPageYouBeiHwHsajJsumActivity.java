package com.dgjadsie.jkermsd.youbeihwahsndactivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dgjadsie.jkermsd.R;
import com.dgjadsie.jkermsd.youbeihwahsndhttp.MainYouBeiHwHsajJsumApi;
import com.dgjadsie.jkermsd.youbeihwahsndutil.CommonYouBeiHwHsajJsumUtil;
import com.dgjadsie.jkermsd.youbeihwahsndutil.MyYouBeiHwHsajJsumPreferences;
import com.dgjadsie.jkermsd.youbeihwahsndutil.YouBeiHwHsajJsumStartPageDialog;
import com.dgjadsie.jkermsd.youbeihwahsndutil.StatusBarYouBeiHwHsajJsumUtil;

public class StartPageYouBeiHwHsajJsumActivity extends AppCompatActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private YouBeiHwHsajJsumStartPageDialog youBeiHwHsajJsumStartPageDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page_you_bei_he_dje_yrhr);
        StatusBarYouBeiHwHsajJsumUtil.setTransparent(this, false);
        started = MyYouBeiHwHsajJsumPreferences.getBool("started");
        phone = MyYouBeiHwHsajJsumPreferences.getString("phone");
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
        youBeiHwHsajJsumStartPageDialog = new YouBeiHwHsajJsumStartPageDialog(this);
        youBeiHwHsajJsumStartPageDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        youBeiHwHsajJsumStartPageDialog.setOnListener(new YouBeiHwHsajJsumStartPageDialog.OnListener() {
            @Override
            public void topBtnClicked() {
                MyYouBeiHwHsajJsumPreferences.saveBool("started", true);
                youBeiHwHsajJsumStartPageDialog.dismiss();
                CommonYouBeiHwHsajJsumUtil.startActivity(StartPageYouBeiHwHsajJsumActivity.this, LoginYouBeiHwHsajJsumActivity.class, null);
            }

            @Override
            public void bottomBtnClicked() {
                finish();
            }

            @Override
            public void clickedZcxy() {
                bundle = new Bundle();
                bundle.putString("title", "注册协议");
                bundle.putString("url", MainYouBeiHwHsajJsumApi.ZCXY);
                CommonYouBeiHwHsajJsumUtil.startActivity(StartPageYouBeiHwHsajJsumActivity.this, YouBeiHwHsajJsumUserAgreementActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putString("title", "隐私协议");
                bundle.putString("url", MainYouBeiHwHsajJsumApi.YSXY);
                CommonYouBeiHwHsajJsumUtil.startActivity(StartPageYouBeiHwHsajJsumActivity.this, YouBeiHwHsajJsumUserAgreementActivity.class, bundle);
            }
        });
        youBeiHwHsajJsumStartPageDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                CommonYouBeiHwHsajJsumUtil.startActivity(this, YouBeiHwHsajJsumHomePageActivity.class, null);
            } else {
                CommonYouBeiHwHsajJsumUtil.startActivity(this, LoginYouBeiHwHsajJsumActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }

}
