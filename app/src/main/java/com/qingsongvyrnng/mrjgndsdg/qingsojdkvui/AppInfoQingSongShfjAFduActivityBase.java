package com.qingsongvyrnng.mrjgndsdg.qingsojdkvui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.qingsongvyrnng.mrjgndsdg.R;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvbase.BaseBaseQingSongShfjAFduActivity;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.BaseQingSongShfjAFduSharePreferencesUtil;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.StaticBaseQingSongShfjAFduUtil;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.BaseQingSongShfjAFduStatusBarUtil;

public class AppInfoQingSongShfjAFduActivityBase extends BaseBaseQingSongShfjAFduActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_qing_song_sh_udj;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (BaseQingSongShfjAFduSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        BaseQingSongShfjAFduStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticBaseQingSongShfjAFduUtil.getAppVersion(this));
    }
}
