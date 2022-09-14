package com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.R;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsbase.BaseWuYouJieShdfnFjfuirActivity;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.RemindWuYouJieShdfnFjfuirDialog;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.WuYouJieShdfnFjfuirSharePreferencesUtil;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.WuYouJieShdfnFjfuirStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationWuYouJieShdfnFjfuirActivity extends BaseWuYouJieShdfnFjfuirActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindWuYouJieShdfnFjfuirDialog mRemindWuYouJieShdfnFjfuirDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_wu_you_jie_jdf_eryj;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindWuYouJieShdfnFjfuirDialog = new RemindWuYouJieShdfnFjfuirDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindWuYouJieShdfnFjfuirDialog.setBtnClickListener(new RemindWuYouJieShdfnFjfuirDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindWuYouJieShdfnFjfuirDialog.dismiss();
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
                    mRemindWuYouJieShdfnFjfuirDialog.dismiss();
                }
            });
            mRemindWuYouJieShdfnFjfuirDialog.show();
            mRemindWuYouJieShdfnFjfuirDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (WuYouJieShdfnFjfuirSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        WuYouJieShdfnFjfuirStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
