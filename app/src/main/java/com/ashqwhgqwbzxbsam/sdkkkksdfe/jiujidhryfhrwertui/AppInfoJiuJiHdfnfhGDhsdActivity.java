package com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ashqwhgqwbzxbsam.sdkkkksdfe.R;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertbase.BaseJiuJiHdfnfhGDhsdActivity;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.JiuJiHdfnfhGDhsdSharePreferencesUtil;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.StaticJiuJiHdfnfhGDhsdUtil;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.JiuJiHdfnfhGDhsdStatusBarUtil;

public class AppInfoJiuJiHdfnfhGDhsdActivity extends BaseJiuJiHdfnfhGDhsdActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_jiu_ji_fdher_reytjyh;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (JiuJiHdfnfhGDhsdSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        JiuJiHdfnfhGDhsdStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticJiuJiHdfnfhGDhsdUtil.getAppVersion(this));
    }
}
