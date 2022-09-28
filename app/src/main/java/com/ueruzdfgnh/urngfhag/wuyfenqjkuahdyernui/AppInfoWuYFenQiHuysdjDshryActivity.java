package com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ueruzdfgnh.urngfhag.R;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernbase.BaseWuYFenQiHuysdjDshryActivity;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.WuYFenQiHuysdjDshrySharePreferencesUtil;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.StaticWuYFenQiHuysdjDshryUtil;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.WuYFenQiHuysdjDshryStatusBarUtil;

public class AppInfoWuYFenQiHuysdjDshryActivity extends BaseWuYFenQiHuysdjDshryActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_wu_yfen_qijai_dfjrt;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (WuYFenQiHuysdjDshrySharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        WuYFenQiHuysdjDshryStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticWuYFenQiHuysdjDshryUtil.getAppVersion(this));
    }
}
