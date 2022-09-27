package com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.tyfdgeruyfgh.ernhfygwsert.R;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyubase.BaseZCMdhdTshdfActivity;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ZCMdhdTshdfSharePreferencesUtil;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.StaticZCMdhdTshdfUtil;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ZCMdhdTshdfStatusBarUtil;

public class AppInfoZCMdhdTshdfActivity extends BaseZCMdhdTshdfActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_zcm_fhgetr_tqttry;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (ZCMdhdTshdfSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        ZCMdhdTshdfStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticZCMdhdTshdfUtil.getAppVersion(this));
    }
}
