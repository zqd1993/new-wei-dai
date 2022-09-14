package com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sjshsbssdfwert.rufgnerytgut.R;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerybase.BaseDaGeJtiaoBaDdhjFferActivity;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.RemindDaGeJtiaoBaDdhjFferDialog;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.DaGeJtiaoBaDdhjFferSharePreferencesUtil;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.DaGeJtiaoBaDdhjFferStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationDaGeJtiaoBaDdhjFferActivity extends BaseDaGeJtiaoBaDdhjFferActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindDaGeJtiaoBaDdhjFferDialog mRemindDaGeJtiaoBaDdhjFferDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_da_ge_jdf_yrjf;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindDaGeJtiaoBaDdhjFferDialog = new RemindDaGeJtiaoBaDdhjFferDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindDaGeJtiaoBaDdhjFferDialog.setBtnClickListener(new RemindDaGeJtiaoBaDdhjFferDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindDaGeJtiaoBaDdhjFferDialog.dismiss();
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
                    mRemindDaGeJtiaoBaDdhjFferDialog.dismiss();
                }
            });
            mRemindDaGeJtiaoBaDdhjFferDialog.show();
            mRemindDaGeJtiaoBaDdhjFferDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (DaGeJtiaoBaDdhjFferSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        DaGeJtiaoBaDdhjFferStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
