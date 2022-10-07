package com.fghjtuytjuj.drtysghjertyh.page;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fghjtuytjuj.drtysghjertyh.common.SharePreferencesUtil;
import com.fghjtuytjuj.drtysghjertyh.common.StatusBarUtil;
import com.fghjtuytjuj.drtysghjertyh.view.PromptDialog;
import com.fjsdkqwj.pfdioewjnsd.R;

public class CloseAccountActivity extends AppCompatActivity {

    private TextView titleTv, commitBtn;
    private View backImg;

    private PromptDialog promptDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_close_account);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("注销账号");
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            promptDialog = new PromptDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", "取消", "注销账号");
            promptDialog.setBtnClickListener(new PromptDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    promptDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    Toast.makeText(CloseAccountActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    promptDialog.dismiss();
                    finish();
                }
            });
            promptDialog.show();
        });
    }
}
