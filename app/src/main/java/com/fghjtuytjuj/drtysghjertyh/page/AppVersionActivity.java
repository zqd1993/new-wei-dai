package com.fghjtuytjuj.drtysghjertyh.page;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fghjtuytjuj.drtysghjertyh.common.SharePreferencesUtil;
import com.fghjtuytjuj.drtysghjertyh.common.StaticCommon;
import com.fghjtuytjuj.drtysghjertyh.common.StatusBarUtil;
import com.fjsdkqwj.pfdioewjnsd.R;

public class AppVersionActivity extends AppCompatActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_version);
        if (SharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticCommon.getAppVersion(this));
        backImg.setOnClickListener(v ->{
            finish();
        });
    }
}
