package com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnactivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.urhdnsertjg.gjuerjfhf.R;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnhttp.JZhuJsiQIajsdnMainApi;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.JZhuJsiQIajsdnCommonUtil;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.JZhuJsiQIajsdnStartPageDialog;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.MyJZhuJsiQIajsdnPreferences;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.JZhuJsiQIajsdnStatusBarUtil;

public class StartPageJZhuJsiQIajsdnActivity extends AppCompatActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private JZhuJsiQIajsdnStartPageDialog JZhuJsiQIajsdnStartPageDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jin_zhu_jqi_djrufn_dfke_start_page);
        JZhuJsiQIajsdnStatusBarUtil.setTransparent(this, false);
        started = MyJZhuJsiQIajsdnPreferences.getBool("started");
        phone = MyJZhuJsiQIajsdnPreferences.getString("phone");
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
        JZhuJsiQIajsdnStartPageDialog = new JZhuJsiQIajsdnStartPageDialog(this);
        JZhuJsiQIajsdnStartPageDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        JZhuJsiQIajsdnStartPageDialog.setOnListener(new JZhuJsiQIajsdnStartPageDialog.OnListener() {
            @Override
            public void topBtnClicked() {
                MyJZhuJsiQIajsdnPreferences.saveBool("started", true);
                JZhuJsiQIajsdnStartPageDialog.dismiss();
                JZhuJsiQIajsdnCommonUtil.startActivity(StartPageJZhuJsiQIajsdnActivity.this, LoginJZhuJsiQIajsdnActivity.class, null);
            }

            @Override
            public void bottomBtnClicked() {
                finish();
            }

            @Override
            public void clickedZcxy() {
                bundle = new Bundle();
                bundle.putString("title", "注册协议");
                bundle.putString("url", JZhuJsiQIajsdnMainApi.ZCXY);
                JZhuJsiQIajsdnCommonUtil.startActivity(StartPageJZhuJsiQIajsdnActivity.this, JZhuJsiQIajsdnUserAgreementActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putString("title", "隐私协议");
                bundle.putString("url", JZhuJsiQIajsdnMainApi.YSXY);
                JZhuJsiQIajsdnCommonUtil.startActivity(StartPageJZhuJsiQIajsdnActivity.this, JZhuJsiQIajsdnUserAgreementActivity.class, bundle);
            }
        });
        JZhuJsiQIajsdnStartPageDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                JZhuJsiQIajsdnCommonUtil.startActivity(this, HomePageJZhuJsiQIajsdnActivity.class, null);
            } else {
                JZhuJsiQIajsdnCommonUtil.startActivity(this, LoginJZhuJsiQIajsdnActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }

}
