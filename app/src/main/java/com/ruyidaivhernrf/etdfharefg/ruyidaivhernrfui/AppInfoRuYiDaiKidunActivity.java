package com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ruyidaivhernrf.etdfharefg.R;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfbase.BaseRuYiDaiKidunActivity;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfutil.RuYiDaiKidunSharePreferencesUtil;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfutil.StaticRuYiDaiKidunUtil;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfutil.RuYiDaiKidunStatusBarUtil;

public class AppInfoRuYiDaiKidunActivity extends BaseRuYiDaiKidunActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_ru_yi_dai_dfu_eng;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (RuYiDaiKidunSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        RuYiDaiKidunStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticRuYiDaiKidunUtil.getAppVersion(this));
    }
}
