package com.youjiegherh.pocketqwrh.youjiewetdfhutil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.youjiegherh.pocketqwrh.R;

public class RemindYouJieSDjdfiDialog extends Dialog {

    TextView titleTv, contentTv, leftBtn, rightBtn, sureBtn;
    View btnLl, oneBtnLl;

    private BtnClickListener btnClickListener;
    private String titleStr, contentStr;
    private boolean showOneBtn;

    public RemindYouJieSDjdfiDialog(@NonNull Context context, String titleStr, String contentStr, boolean showOneBtn) {
        super(context, R.style.tran_dialog);
        this.titleStr = titleStr;
        this.contentStr = contentStr;
        this.showOneBtn = showOneBtn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_you_jie_iejbvr_remind);
        titleTv = findViewById(R.id.title_tv);
        contentTv = findViewById(R.id.content_tv);
        leftBtn = findViewById(R.id.left_btn);
        rightBtn = findViewById(R.id.right_btn);
        sureBtn = findViewById(R.id.sure_btn);
        btnLl = findViewById(R.id.btn_ll);
        oneBtnLl = findViewById(R.id.one_btn_ll);
        titleTv.setText(titleStr);
        contentTv.setText(contentStr);

        if (showOneBtn) {
            btnLl.setVisibility(View.GONE);
            oneBtnLl.setVisibility(View.VISIBLE);
        }
        leftBtn.setOnClickListener(v -> {
            if (btnClickListener != null) {
                btnClickListener.leftClicked();
            }
        });
        rightBtn.setOnClickListener(v -> {
            if (btnClickListener != null) {
                btnClickListener.rightClicked();
            }
        });
        sureBtn.setOnClickListener(v -> {
            dismiss();
        });
    }

    public void setBtnStr(String leftStr, String rightStr) {
        leftBtn.setText(leftStr);
        rightBtn.setText(rightStr);
    }

    public void setBtnClickListener(BtnClickListener btnClickListener) {
        this.btnClickListener = btnClickListener;
    }

    public interface BtnClickListener {
        void leftClicked();

        void rightClicked();
    }
}
