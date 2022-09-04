package com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ruyidaivhernrf.etdfharefg.R;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfbase.BaseRuYiDaiKidunActivity;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfutil.RemindRuYiDaiKidunDialog;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfutil.RuYiDaiKidunSharePreferencesUtil;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfutil.RuYiDaiKidunStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationRuYiDaiKidunActivity extends BaseRuYiDaiKidunActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindRuYiDaiKidunDialog mRemindRuYiDaiKidunDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_ru_yi_dai_dfu_eng;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindRuYiDaiKidunDialog = new RemindRuYiDaiKidunDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindRuYiDaiKidunDialog.setBtnClickListener(new RemindRuYiDaiKidunDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindRuYiDaiKidunDialog.dismiss();
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
                    mRemindRuYiDaiKidunDialog.dismiss();
                }
            });
            mRemindRuYiDaiKidunDialog.show();
            mRemindRuYiDaiKidunDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (RuYiDaiKidunSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        RuYiDaiKidunStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
