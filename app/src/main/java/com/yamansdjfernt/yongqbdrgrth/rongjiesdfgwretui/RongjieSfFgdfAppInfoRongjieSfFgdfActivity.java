package com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yamansdjfernt.yongqbdrgrth.R;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretbase.BaseRongjieSfFgdfActivity;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.SharePreferencesUtilRongjieSfFgdf;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.StaticRongjieSfFgdfUtil;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.RongjieSfFgdfStatusBarUtil;

public class RongjieSfFgdfAppInfoRongjieSfFgdfActivity extends BaseRongjieSfFgdfActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_rong_jie_sdf_brty;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (SharePreferencesUtilRongjieSfFgdf.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        RongjieSfFgdfStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticRongjieSfFgdfUtil.getAppVersion(this));
    }
}
