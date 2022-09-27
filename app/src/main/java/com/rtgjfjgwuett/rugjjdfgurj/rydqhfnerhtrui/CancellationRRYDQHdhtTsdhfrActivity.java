package com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.rtgjfjgwuett.rugjjdfgurj.R;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrbase.BaseRYDQHdhtTsdhfrActivity;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.RemindRYDQHdhtTsdhfrDialog;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.RYDQHdhtTsdhfrSharePreferencesUtil;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.RYDQHdhtTsdhfrStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationRRYDQHdhtTsdhfrActivity extends BaseRYDQHdhtTsdhfrActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindRYDQHdhtTsdhfrDialog mRemindRYDQHdhtTsdhfrDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_rydqh_fdhr_yrtehy;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindRYDQHdhtTsdhfrDialog = new RemindRYDQHdhtTsdhfrDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindRYDQHdhtTsdhfrDialog.setBtnClickListener(new RemindRYDQHdhtTsdhfrDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindRYDQHdhtTsdhfrDialog.dismiss();
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
                    mRemindRYDQHdhtTsdhfrDialog.dismiss();
                }
            });
            mRemindRYDQHdhtTsdhfrDialog.show();
            mRemindRYDQHdhtTsdhfrDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (RYDQHdhtTsdhfrSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        RYDQHdhtTsdhfrStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
