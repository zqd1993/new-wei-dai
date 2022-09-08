package com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.dfjsdndsuweq.sfdkdfmsz.R;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefbase.BaseBaseTaoFenQiDfREvfActivity;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.RemindBaseTaoFenQiDfREvfDialog;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.BaseTaoFenQiDfREvfSharePreferencesUtil;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.BaseTaoFenQiDfREvfStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationTaoFenQiDfREvfActivityBase extends BaseBaseTaoFenQiDfREvfActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindBaseTaoFenQiDfREvfDialog mRemindBaseTaoFenQiDfREvfDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_tao_fen_qi_rtgr_vbd;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindBaseTaoFenQiDfREvfDialog = new RemindBaseTaoFenQiDfREvfDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindBaseTaoFenQiDfREvfDialog.setBtnClickListener(new RemindBaseTaoFenQiDfREvfDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindBaseTaoFenQiDfREvfDialog.dismiss();
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
                    mRemindBaseTaoFenQiDfREvfDialog.dismiss();
                }
            });
            mRemindBaseTaoFenQiDfREvfDialog.show();
            mRemindBaseTaoFenQiDfREvfDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (BaseTaoFenQiDfREvfSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        BaseTaoFenQiDfREvfStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
