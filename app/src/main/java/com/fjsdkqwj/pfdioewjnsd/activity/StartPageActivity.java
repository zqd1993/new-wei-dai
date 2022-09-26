package com.fjsdkqwj.pfdioewjnsd.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fjsdkqwj.pfdioewjnsd.R;
import com.fjsdkqwj.pfdioewjnsd.util.CommonUtil;
import com.fjsdkqwj.pfdioewjnsd.util.MyPreferences;
import com.fjsdkqwj.pfdioewjnsd.util.StartPageDialog;
import com.fjsdkqwj.pfdioewjnsd.util.StatusBarUtil;

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

            }

            @Override
            public void bottomBtnClicked() {

            }

            @Override
            public void clickedZcxy() {

            }

            @Override
            public void clickedYsxy() {

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
