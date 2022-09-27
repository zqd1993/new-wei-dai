package com.rtyhdfgh.nrtdfgh.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rtyhdfgh.nrtdfgh.R;
import com.rtyhdfgh.nrtdfgh.util.CommonUtil;
import com.rtyhdfgh.nrtdfgh.util.MyPreferences;
import com.rtyhdfgh.nrtdfgh.util.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuanYuUsActivity extends AppCompatActivity {

    @BindView(R.id.version_code_tv)
    TextView appVersionInfoTv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_image)
    View backImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        if (MyPreferences.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_guanyu_us);
        ButterKnife.bind(this);
        titleTv.setText("关于我们");
        backImg.setOnClickListener(v -> finish());
        appVersionInfoTv.setText(("当前版本号：v" + CommonUtil.getVersion(this)));
    }
}
