package com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgactivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qhendhwerhjjqshd.dshfretnfqqdfg.R;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.CommonHaojjQianShsjHajduUtil;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.HaojjQianShsjHajduMyPreferences;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.HaojjQianShsjHajduStatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuanYuHaojjQianShsjHajduUsActivity extends AppCompatActivity {

    @BindView(R.id.version_code_tv)
    TextView appVersionInfoTv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_image)
    View backImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HaojjQianShsjHajduStatusBarUtil.setTransparent(this, false);
        if (HaojjQianShsjHajduMyPreferences.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_guanyu_us_hao_jie_she_qtdhfery);
        ButterKnife.bind(this);
        titleTv.setText("关于我们");
        backImg.setOnClickListener(v -> finish());
        appVersionInfoTv.setText(("当前版本号：v" + CommonHaojjQianShsjHajduUtil.getVersion(this)));
    }
}
