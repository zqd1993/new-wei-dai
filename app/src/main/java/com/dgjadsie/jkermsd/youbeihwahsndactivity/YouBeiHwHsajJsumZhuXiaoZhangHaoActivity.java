package com.dgjadsie.jkermsd.youbeihwahsndactivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dgjadsie.jkermsd.R;
import com.dgjadsie.jkermsd.youbeihwahsndutil.GeneralYouBeiHwHsajJsumDialog;
import com.dgjadsie.jkermsd.youbeihwahsndutil.MyYouBeiHwHsajJsumPreferences;
import com.dgjadsie.jkermsd.youbeihwahsndutil.StatusBarYouBeiHwHsajJsumUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YouBeiHwHsajJsumZhuXiaoZhangHaoActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_image)
    View backImg;
    @BindView(R.id.commit_btn)
    View commitBtn;

    private GeneralYouBeiHwHsajJsumDialog generalYouBeiHwHsajJsumDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarYouBeiHwHsajJsumUtil.setTransparent(this, false);
        if (MyYouBeiHwHsajJsumPreferences.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_you_bei_he_dje_yrhr_zhuxiao_zhanghao);
        ButterKnife.bind(this);
        titleTv.setText("注销账号");
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            generalYouBeiHwHsajJsumDialog = new GeneralYouBeiHwHsajJsumDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复");
            generalYouBeiHwHsajJsumDialog.setBtnClickListener(new GeneralYouBeiHwHsajJsumDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    Toast.makeText(YouBeiHwHsajJsumZhuXiaoZhangHaoActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    generalYouBeiHwHsajJsumDialog.dismiss();
                    finish();
                }

                @Override
                public void rightClicked() {
                    generalYouBeiHwHsajJsumDialog.dismiss();
                }
            });
            generalYouBeiHwHsajJsumDialog.show();
            generalYouBeiHwHsajJsumDialog.setBtnStr("注销账号", "取消");
        });
    }
}
