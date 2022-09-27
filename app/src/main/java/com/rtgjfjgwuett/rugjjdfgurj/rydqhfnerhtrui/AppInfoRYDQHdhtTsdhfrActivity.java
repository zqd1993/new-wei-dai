package com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.rtgjfjgwuett.rugjjdfgurj.R;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrbase.BaseRYDQHdhtTsdhfrActivity;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.RYDQHdhtTsdhfrSharePreferencesUtil;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.StaticRYDQHdhtTsdhfrUtil;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.RYDQHdhtTsdhfrStatusBarUtil;

public class AppInfoRYDQHdhtTsdhfrActivity extends BaseRYDQHdhtTsdhfrActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_rydqh_fdhr_yrtehy;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (RYDQHdhtTsdhfrSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        RYDQHdhtTsdhfrStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticRYDQHdhtTsdhfrUtil.getAppVersion(this));
    }
}
