package com.fghjtuytjuj.drtysghjertyh.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fjsdkqwj.pfdioewjnsd.R;

public class PromptDialog extends Dialog {
    TextView titleTv, promptTv, leftBtn, rightBtn;
    View btnLl;

    private BtnClickListener btnClickListener;
    private String titleStr, promptStr, leftStr, rightStr;

    public PromptDialog(@NonNull Context context, String titleStr, String promptStr, String leftStr, String rightStr) {
        super(context, R.style.tran_dialog);
        this.titleStr = titleStr;
        this.promptStr = promptStr;
        this.leftStr = leftStr;
        this.rightStr = rightStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prompt_dialog);
        titleTv = findViewById(R.id.title_tv);
        promptTv = findViewById(R.id.prompt_tv);
        leftBtn = findViewById(R.id.left_btn);
        rightBtn = findViewById(R.id.right_btn);
        titleTv.setText(titleStr);
        promptTv.setText(promptStr);
        leftBtn.setText(leftStr);
        rightBtn.setText(rightStr);
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
    }

    public void setBtnClickListener(BtnClickListener btnClickListener) {
        this.btnClickListener = btnClickListener;
    }

    public interface BtnClickListener {
        void leftClicked();

        void rightClicked();
    }
}
