package com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ashasnasgasnzxdfge.asmasjasllldfrety.R;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrbase.YingJiHDSdJdgfsBaseActivity;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsSharePreferencesUtil;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.StaticCYingJiHDSdJdgfsUtil;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsStatusBarUtil;

public class AppInfoActivityYingJiHDSdJdgfs extends YingJiHDSdJdgfsBaseActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_ying_ji_dh_jie_fuerty;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (YingJiHDSdJdgfsSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        YingJiHDSdJdgfsStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticCYingJiHDSdJdgfsUtil.getAppVersion(this));
    }
}
