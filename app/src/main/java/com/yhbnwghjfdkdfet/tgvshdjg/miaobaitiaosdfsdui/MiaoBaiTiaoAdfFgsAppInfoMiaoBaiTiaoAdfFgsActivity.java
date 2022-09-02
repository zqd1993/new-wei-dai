package com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yhbnwghjfdkdfet.tgvshdjg.R;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdbase.BaseMiaoBaiTiaoAdfFgsActivity;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.SharePreferencesMiaoBaiTiaoAdfFgsUtil;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.StaticMiaoBaiTiaoAdfFgsUtil;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.MiaoBaiTiaoAdfFgsStatusBarUtil;

public class MiaoBaiTiaoAdfFgsAppInfoMiaoBaiTiaoAdfFgsActivity extends BaseMiaoBaiTiaoAdfFgsActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_miao_bai_tiao_sdf;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (SharePreferencesMiaoBaiTiaoAdfFgsUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        MiaoBaiTiaoAdfFgsStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticMiaoBaiTiaoAdfFgsUtil.getAppVersion(this));
    }
}
