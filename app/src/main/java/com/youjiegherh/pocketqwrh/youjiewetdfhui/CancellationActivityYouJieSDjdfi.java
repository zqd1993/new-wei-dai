package com.youjiegherh.pocketqwrh.youjiewetdfhui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.youjiegherh.pocketqwrh.R;
import com.youjiegherh.pocketqwrh.youjiewetdfhbase.BaseNewCodeXiaoNiuKuaiActivity;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.RemindYouJieSDjdfiDialog;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.YouJieSDjdfiSharePreferencesUtil;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.YouJieSDjdfiStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationActivityYouJieSDjdfi extends BaseNewCodeXiaoNiuKuaiActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindYouJieSDjdfiDialog mRemindYouJieSDjdfiDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_you_jie_iejbvr;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindYouJieSDjdfiDialog = new RemindYouJieSDjdfiDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindYouJieSDjdfiDialog.setBtnClickListener(new RemindYouJieSDjdfiDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindYouJieSDjdfiDialog.dismiss();
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
                    mRemindYouJieSDjdfiDialog.dismiss();
                }
            });
            mRemindYouJieSDjdfiDialog.show();
            mRemindYouJieSDjdfiDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (YouJieSDjdfiSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        YouJieSDjdfiStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
