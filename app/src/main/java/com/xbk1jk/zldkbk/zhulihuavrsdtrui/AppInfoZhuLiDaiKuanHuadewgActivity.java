package com.xbk1jk.zldkbk.zhulihuavrsdtrui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.xbk1jk.zldkbk.R;
import com.xbk1jk.zldkbk.zhulihuavrsdtrbase.BaseZhuLiDaiKuanHuadewgFgsActivity;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.SharePreferencesZhuLiDaiKuanHuadewgUtil;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.StaticZhuLiDaiKuanHuadewgUtil;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.ZhuLiDaiKuanHuadewgStatusBarUtil;

public class AppInfoZhuLiDaiKuanHuadewgActivity extends BaseZhuLiDaiKuanHuadewgFgsActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_zhu_li_dai_kuan_hua_setg_sert;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (SharePreferencesZhuLiDaiKuanHuadewgUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        ZhuLiDaiKuanHuadewgStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticZhuLiDaiKuanHuadewgUtil.getAppVersion(this));
    }
}
