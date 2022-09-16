package com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.R;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhybase.CaiJieTongYshVdjrytBaseActivity;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.CaiJieTongYshVdjrytSharePreferencesUtil;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.StaticCaiJieTongYshVdjrytUtil;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.CaiJieTongYshVdjrytStatusBarUtil;

public class AppInfoActivityCaiJieTongYshVdjryt extends CaiJieTongYshVdjrytBaseActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_cai_jie_tong_drt_etfnh;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (CaiJieTongYshVdjrytSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        CaiJieTongYshVdjrytStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticCaiJieTongYshVdjrytUtil.getAppVersion(this));
    }
}
