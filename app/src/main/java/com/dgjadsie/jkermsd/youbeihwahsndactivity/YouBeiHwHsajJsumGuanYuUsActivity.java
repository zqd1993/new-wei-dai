package com.dgjadsie.jkermsd.youbeihwahsndactivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dgjadsie.jkermsd.R;
import com.dgjadsie.jkermsd.youbeihwahsndutil.CommonYouBeiHwHsajJsumUtil;
import com.dgjadsie.jkermsd.youbeihwahsndutil.MyYouBeiHwHsajJsumPreferences;
import com.dgjadsie.jkermsd.youbeihwahsndutil.StatusBarYouBeiHwHsajJsumUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YouBeiHwHsajJsumGuanYuUsActivity extends AppCompatActivity {

    @BindView(R.id.version_code_tv)
    TextView appVersionInfoTv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_image)
    View backImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarYouBeiHwHsajJsumUtil.setTransparent(this, false);
        if (MyYouBeiHwHsajJsumPreferences.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_guanyu_us_you_bei_he_dje_yrhr);
        ButterKnife.bind(this);
        titleTv.setText("关于我们");
        backImg.setOnClickListener(v -> finish());
        appVersionInfoTv.setText(("当前版本号：v" + CommonYouBeiHwHsajJsumUtil.getVersion(this)));
    }
}
