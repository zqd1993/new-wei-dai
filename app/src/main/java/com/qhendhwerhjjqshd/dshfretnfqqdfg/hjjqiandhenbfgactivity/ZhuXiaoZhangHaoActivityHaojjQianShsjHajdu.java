package com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgactivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qhendhwerhjjqshd.dshfretnfqqdfg.R;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.GeneralHaojjQianShsjHajduDialog;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.HaojjQianShsjHajduMyPreferences;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.HaojjQianShsjHajduStatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhuXiaoZhangHaoActivityHaojjQianShsjHajdu extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_image)
    View backImg;
    @BindView(R.id.commit_btn)
    View commitBtn;

    private GeneralHaojjQianShsjHajduDialog generalHaojjQianShsjHajduDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HaojjQianShsjHajduStatusBarUtil.setTransparent(this, false);
        if (HaojjQianShsjHajduMyPreferences.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_zhuxiao_zhanghao_hao_jie_she_qtdhfery);
        ButterKnife.bind(this);
        titleTv.setText("注销账号");
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            generalHaojjQianShsjHajduDialog = new GeneralHaojjQianShsjHajduDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复");
            generalHaojjQianShsjHajduDialog.setBtnClickListener(new GeneralHaojjQianShsjHajduDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    Toast.makeText(ZhuXiaoZhangHaoActivityHaojjQianShsjHajdu.this, "提交成功", Toast.LENGTH_SHORT).show();
                    generalHaojjQianShsjHajduDialog.dismiss();
                    finish();
                }

                @Override
                public void rightClicked() {
                    generalHaojjQianShsjHajduDialog.dismiss();
                }
            });
            generalHaojjQianShsjHajduDialog.show();
            generalHaojjQianShsjHajduDialog.setBtnStr("注销账号", "取消");
        });
    }
}
