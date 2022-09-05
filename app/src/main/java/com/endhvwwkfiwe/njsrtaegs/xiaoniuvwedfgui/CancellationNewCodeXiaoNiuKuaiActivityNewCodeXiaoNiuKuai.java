package com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.endhvwwkfiwe.njsrtaegs.R;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgbase.BaseNewCodeXiaoNiuKuaiActivity;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgutil.RemindNewCodeXiaoNiuKuaiDialog;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgutil.NewCodeXiaoNiuKuaiSharePreferencesUtil;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgutil.NewCodeXiaoNiuKuaiStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationNewCodeXiaoNiuKuaiActivityNewCodeXiaoNiuKuai extends BaseNewCodeXiaoNiuKuaiActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindNewCodeXiaoNiuKuaiDialog mRemindNewCodeXiaoNiuKuaiDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_xiao_niu_kuai_sdf_efbdgh;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindNewCodeXiaoNiuKuaiDialog = new RemindNewCodeXiaoNiuKuaiDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindNewCodeXiaoNiuKuaiDialog.setBtnClickListener(new RemindNewCodeXiaoNiuKuaiDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindNewCodeXiaoNiuKuaiDialog.dismiss();
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
                    mRemindNewCodeXiaoNiuKuaiDialog.dismiss();
                }
            });
            mRemindNewCodeXiaoNiuKuaiDialog.show();
            mRemindNewCodeXiaoNiuKuaiDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (NewCodeXiaoNiuKuaiSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        NewCodeXiaoNiuKuaiStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
