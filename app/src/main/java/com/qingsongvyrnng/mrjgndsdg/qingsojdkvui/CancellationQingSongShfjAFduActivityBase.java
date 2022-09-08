package com.qingsongvyrnng.mrjgndsdg.qingsojdkvui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.qingsongvyrnng.mrjgndsdg.R;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvbase.BaseBaseQingSongShfjAFduActivity;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.RemindBaseQingSongShfjAFduDialog;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.BaseQingSongShfjAFduSharePreferencesUtil;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.BaseQingSongShfjAFduStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationQingSongShfjAFduActivityBase extends BaseBaseQingSongShfjAFduActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindBaseQingSongShfjAFduDialog mRemindBaseQingSongShfjAFduDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_qing_song_sh_udj;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindBaseQingSongShfjAFduDialog = new RemindBaseQingSongShfjAFduDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindBaseQingSongShfjAFduDialog.setBtnClickListener(new RemindBaseQingSongShfjAFduDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindBaseQingSongShfjAFduDialog.dismiss();
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
                    mRemindBaseQingSongShfjAFduDialog.dismiss();
                }
            });
            mRemindBaseQingSongShfjAFduDialog.show();
            mRemindBaseQingSongShfjAFduDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (BaseQingSongShfjAFduSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        BaseQingSongShfjAFduStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
