package com.rtyhdfgh.nrtdfgh.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rtyhdfgh.nrtdfgh.R;
import com.rtyhdfgh.nrtdfgh.util.GeneralDialog;
import com.rtyhdfgh.nrtdfgh.util.MyPreferences;
import com.rtyhdfgh.nrtdfgh.util.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhuXiaoZhangHaoActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_image)
    View backImg;
    @BindView(R.id.commit_btn)
    View commitBtn;

    private GeneralDialog generalDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        if (MyPreferences.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_zhuxiao_zhanghao);
        ButterKnife.bind(this);
        titleTv.setText("注销账号");
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            generalDialog = new GeneralDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复");
            generalDialog.setBtnClickListener(new GeneralDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    Toast.makeText(ZhuXiaoZhangHaoActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    generalDialog.dismiss();
                    finish();
                }

                @Override
                public void rightClicked() {
                    generalDialog.dismiss();
                }
            });
            generalDialog.show();
            generalDialog.setBtnStr("注销账号", "取消");
        });
    }
}
