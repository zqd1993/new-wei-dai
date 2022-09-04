package com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jinzhudfent.bbsdfwewfg.R;
import com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfbase.BaseJinZhuPigThdfgActivity;
import com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfutil.JinZhuPigThdfgSharePreferencesUtil;
import com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfutil.StaticJinZhuPigThdfgUtil;
import com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfutil.StatusJinZhuPigThdfgBarUtil;

public class AppInfoJinZhuPigThdfgActivity extends BaseJinZhuPigThdfgActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_jin_zhu_asf_pig;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (JinZhuPigThdfgSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusJinZhuPigThdfgBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticJinZhuPigThdfgUtil.getAppVersion(this));
    }
}
