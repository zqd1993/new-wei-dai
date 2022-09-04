package com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhaocaimaowerdfgf.nheradfert.R;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegbase.BaseZhaoCaiCatKfrtActivity;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegutil.ZhaoCaiCatKfrtSharePreferencesUtil;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegutil.StaticZhaoCaiCatKfrtUtil;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegutil.ZhaoCaiCatKfrtStatusBarUtil;

public class ZhaoCaiCatKfrtAppInfoZhaoCaiCatKfrtActivity extends BaseZhaoCaiCatKfrtActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_zhao_cai_mao_dfget;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (ZhaoCaiCatKfrtSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        ZhaoCaiCatKfrtStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticZhaoCaiCatKfrtUtil.getAppVersion(this));
    }
}
