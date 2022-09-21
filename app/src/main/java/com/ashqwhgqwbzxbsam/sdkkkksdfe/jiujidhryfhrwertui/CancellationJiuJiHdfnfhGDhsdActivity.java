package com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ashqwhgqwbzxbsam.sdkkkksdfe.R;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertbase.BaseJiuJiHdfnfhGDhsdActivity;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.RemindJiuJiHdfnfhGDhsdDialog;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.JiuJiHdfnfhGDhsdSharePreferencesUtil;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.JiuJiHdfnfhGDhsdStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationJiuJiHdfnfhGDhsdActivity extends BaseJiuJiHdfnfhGDhsdActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindJiuJiHdfnfhGDhsdDialog mRemindJiuJiHdfnfhGDhsdDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation__jiu_ji_fdher_reytjyh;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindJiuJiHdfnfhGDhsdDialog = new RemindJiuJiHdfnfhGDhsdDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindJiuJiHdfnfhGDhsdDialog.setBtnClickListener(new RemindJiuJiHdfnfhGDhsdDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindJiuJiHdfnfhGDhsdDialog.dismiss();
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
                    mRemindJiuJiHdfnfhGDhsdDialog.dismiss();
                }
            });
            mRemindJiuJiHdfnfhGDhsdDialog.show();
            mRemindJiuJiHdfnfhGDhsdDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (JiuJiHdfnfhGDhsdSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        JiuJiHdfnfhGDhsdStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
