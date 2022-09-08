package com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.R;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvbase.BaseBaseQuHuaDjdfuVdhrActivity;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.RemindBaseQuHuaDjdfuVdhrDialog;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrSharePreferencesUtil;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationQuHuaDjdfuVdhrActivityBase extends BaseBaseQuHuaDjdfuVdhrActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindBaseQuHuaDjdfuVdhrDialog mRemindBaseQuHuaDjdfuVdhrDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_qu_hua_hua_erf_engh;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindBaseQuHuaDjdfuVdhrDialog = new RemindBaseQuHuaDjdfuVdhrDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindBaseQuHuaDjdfuVdhrDialog.setBtnClickListener(new RemindBaseQuHuaDjdfuVdhrDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindBaseQuHuaDjdfuVdhrDialog.dismiss();
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
                    mRemindBaseQuHuaDjdfuVdhrDialog.dismiss();
                }
            });
            mRemindBaseQuHuaDjdfuVdhrDialog.show();
            mRemindBaseQuHuaDjdfuVdhrDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (BaseQuHuaDjdfuVdhrSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        BaseQuHuaDjdfuVdhrStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
