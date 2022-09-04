package com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegutil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zhaocaimaowerdfgf.nheradfert.R;

public class ZhaoCaiCatKfrtWelcomeRemindDialog extends Dialog {

    ZhaoCaiCatKfrtClickTextView content_tv;
    TextView topBtn;
    TextView bottomBtn;

    private OnListener onListener;

    public ZhaoCaiCatKfrtWelcomeRemindDialog(@NonNull Context context) {
        super(context, R.style.tran_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_welcome_remind_zhao_cai_mao_dfget);
        setCanceledOnTouchOutside(false);
        content_tv = findViewById(R.id.content_tv);
        topBtn = findViewById(R.id.top_btn);
        bottomBtn = findViewById(R.id.bottom_btn);
        topBtn.setOnClickListener(v -> {
            if (onListener != null) {
                onListener.topBtnClicked();
            }
        });

        bottomBtn.setOnClickListener(v -> {
            if (onListener != null) {
                onListener.bottomBtnClicked();
            }
        });

        content_tv.setText(StaticZhaoCaiCatKfrtUtil.createSpanTexts(), position -> {
            switch (position) {
                case 1:
                    if (onListener != null) {
                        onListener.clickedZcxy();
                    }
                    break;
                default:
                    if (onListener != null) {
                        onListener.clickedYsxy();
                    }
                    break;
            }
        }, "#EA2623");

    }

    @Override
    public void show() {
        super.show();
    }

    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }

    public interface OnListener {
        void topBtnClicked();

        void bottomBtnClicked();

        void clickedZcxy();

        void clickedYsxy();
    }

}
