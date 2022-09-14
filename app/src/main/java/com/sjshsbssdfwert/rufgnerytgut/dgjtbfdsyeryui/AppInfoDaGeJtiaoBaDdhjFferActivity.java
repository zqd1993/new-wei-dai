package com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sjshsbssdfwert.rufgnerytgut.R;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerybase.BaseDaGeJtiaoBaDdhjFferActivity;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.DaGeJtiaoBaDdhjFferSharePreferencesUtil;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.StaticDaGeJtiaoBaDdhjFferUtil;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.DaGeJtiaoBaDdhjFferStatusBarUtil;

public class AppInfoDaGeJtiaoBaDdhjFferActivity extends BaseDaGeJtiaoBaDdhjFferActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_da_ge_jdf_yrjf;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (DaGeJtiaoBaDdhjFferSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        DaGeJtiaoBaDdhjFferStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticDaGeJtiaoBaDdhjFferUtil.getAppVersion(this));
    }
}
