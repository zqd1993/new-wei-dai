package com.fjsdkqwj.pfdioewjnsd.ui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.fjsdkqwj.pfdioewjnsd.R;
import com.fjsdkqwj.pfdioewjnsd.base.BaseActivity;
import com.fjsdkqwj.pfdioewjnsd.util.RemindDialog;
import com.fjsdkqwj.pfdioewjnsd.util.SharePreferencesUtil;
import com.fjsdkqwj.pfdioewjnsd.util.StatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationActivity extends BaseActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindDialog mRemindDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindDialog = new RemindDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindDialog.setBtnClickListener(new RemindDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    rotateLoading.start();
                    loadingFl.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingFl.setVisibility(View.GONE);
                            rotateLoading.stop();
                            finish();
                        }
                    }, 2000);
                    mRemindDialog.dismiss();
                }
            });
            mRemindDialog.show();
            mRemindDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (SharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
