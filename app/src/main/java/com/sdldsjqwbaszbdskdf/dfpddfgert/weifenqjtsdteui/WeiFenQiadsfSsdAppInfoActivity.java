package com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sdldsjqwbaszbdskdf.dfpddfgert.R;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtebase.BaseRongjieSfFgdfActivity;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.SharePreferencesUtilWeiFenQiadsfSsd;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.StaticWeiFenQiadsfSsdUtil;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.WeiFenQiadsfSsdStatusBarUtil;

public class WeiFenQiadsfSsdAppInfoActivity extends BaseRongjieSfFgdfActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_wei_fen_qi_dfger_vjkrt;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (SharePreferencesUtilWeiFenQiadsfSsd.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        WeiFenQiadsfSsdStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticWeiFenQiadsfSsdUtil.getAppVersion(this));
    }
}
