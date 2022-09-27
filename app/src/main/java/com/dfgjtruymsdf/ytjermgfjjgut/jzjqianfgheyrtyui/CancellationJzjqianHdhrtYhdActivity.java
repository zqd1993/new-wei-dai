package com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.dfgjtruymsdf.ytjermgfjjgut.R;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtybase.BaseJzjqianHdhrtYhdActivity;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.RemindJzjqianHdhrtYhdDialog;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.JzjqianHdhrtYhdSharePreferencesUtil;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.StatusJzjqianHdhrtYhdBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationJzjqianHdhrtYhdActivity extends BaseJzjqianHdhrtYhdActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindJzjqianHdhrtYhdDialog mRemindJzjqianHdhrtYhdDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_jzjqian_sdhr_utryn;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindJzjqianHdhrtYhdDialog = new RemindJzjqianHdhrtYhdDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindJzjqianHdhrtYhdDialog.setBtnClickListener(new RemindJzjqianHdhrtYhdDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindJzjqianHdhrtYhdDialog.dismiss();
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
                    mRemindJzjqianHdhrtYhdDialog.dismiss();
                }
            });
            mRemindJzjqianHdhrtYhdDialog.show();
            mRemindJzjqianHdhrtYhdDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (JzjqianHdhrtYhdSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusJzjqianHdhrtYhdBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
