package com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnactivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.urhdnsertjg.gjuerjfhf.R;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.JZhuJsiQIajsdnCommonUtil;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.MyJZhuJsiQIajsdnPreferences;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.JZhuJsiQIajsdnStatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JZhuJsiQIajsdnGuanYuUsActivity extends AppCompatActivity {

    @BindView(R.id.version_code_tv)
    TextView appVersionInfoTv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_image)
    View backImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JZhuJsiQIajsdnStatusBarUtil.setTransparent(this, false);
        if (MyJZhuJsiQIajsdnPreferences.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_guanyu_us_jin_zhu_jqi_djrufn_dfke);
        ButterKnife.bind(this);
        titleTv.setText("关于我们");
        backImg.setOnClickListener(v -> finish());
        appVersionInfoTv.setText(("当前版本号：v" + JZhuJsiQIajsdnCommonUtil.getVersion(this)));
    }
}
