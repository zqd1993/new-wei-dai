package com.geihuawefvjelkfu.qwersdbn.ncopgeinihuaui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.geihuawefvjelkfu.qwersdbn.R;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuabase.BaseNcOpGeiNiHuaActivity;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil.NcOpGeiNiHuaSharePreferencesUtil;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil.NcOpGeiNiHuaStaticUtil;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil.NcOpGeiNiHuaStatusBarUtil;

public class NcOpGeiNiHuaAppInfoNcOpGeiNiHuaActivity extends BaseNcOpGeiNiHuaActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (NcOpGeiNiHuaSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        NcOpGeiNiHuaStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + NcOpGeiNiHuaStaticUtil.getAppVersion(this));
    }
}
