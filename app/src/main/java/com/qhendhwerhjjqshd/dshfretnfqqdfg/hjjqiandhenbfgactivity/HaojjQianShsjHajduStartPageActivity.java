package com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgactivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qhendhwerhjjqshd.dshfretnfqqdfg.R;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfghttp.MainHaojjQianShsjHajduApi;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.CommonHaojjQianShsjHajduUtil;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.HaojjQianShsjHajduMyPreferences;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.StartPageHaojjQianShsjHajduDialog;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.HaojjQianShsjHajduStatusBarUtil;

public class HaojjQianShsjHajduStartPageActivity extends AppCompatActivity {

    private Bundle bundle;

    private boolean started = false, isResume = false;

    private String phone = "";

    private StartPageHaojjQianShsjHajduDialog startPageHaojjQianShsjHajduDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page_hao_jie_she_qtdhfery);
        HaojjQianShsjHajduStatusBarUtil.setTransparent(this, false);
        started = HaojjQianShsjHajduMyPreferences.getBool("started");
        phone = HaojjQianShsjHajduMyPreferences.getString("phone");
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
        startPageHaojjQianShsjHajduDialog = new StartPageHaojjQianShsjHajduDialog(this);
        startPageHaojjQianShsjHajduDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        startPageHaojjQianShsjHajduDialog.setOnListener(new StartPageHaojjQianShsjHajduDialog.OnListener() {
            @Override
            public void topBtnClicked() {
                HaojjQianShsjHajduMyPreferences.saveBool("started", true);
                startPageHaojjQianShsjHajduDialog.dismiss();
                CommonHaojjQianShsjHajduUtil.startActivity(HaojjQianShsjHajduStartPageActivity.this, LoginHaojjQianShsjHajduActivity.class, null);
            }

            @Override
            public void bottomBtnClicked() {
                finish();
            }

            @Override
            public void clickedZcxy() {
                bundle = new Bundle();
                bundle.putString("title", "注册协议");
                bundle.putString("url", MainHaojjQianShsjHajduApi.ZCXY);
                CommonHaojjQianShsjHajduUtil.startActivity(HaojjQianShsjHajduStartPageActivity.this, UserAgreementActivityHaojjQianShsjHajdu.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putString("title", "隐私协议");
                bundle.putString("url", MainHaojjQianShsjHajduApi.YSXY);
                CommonHaojjQianShsjHajduUtil.startActivity(HaojjQianShsjHajduStartPageActivity.this, UserAgreementActivityHaojjQianShsjHajdu.class, bundle);
            }
        });
        startPageHaojjQianShsjHajduDialog.show();
    }

    private void startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                CommonHaojjQianShsjHajduUtil.startActivity(this, HomePageHaojjQianShsjHajduActivity.class, null);
            } else {
                CommonHaojjQianShsjHajduUtil.startActivity(this, LoginHaojjQianShsjHajduActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }

}
