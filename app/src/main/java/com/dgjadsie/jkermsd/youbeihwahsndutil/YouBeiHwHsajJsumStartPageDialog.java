package com.dgjadsie.jkermsd.youbeihwahsndutil;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.dgjadsie.jkermsd.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YouBeiHwHsajJsumStartPageDialog extends Dialog {
    @BindView(R.id.top_btn)
    TextView topBtn;
    @BindView(R.id.bottom_btn)
    TextView bottomBtn;
    @BindView(R.id.content_tv)
    TextView contentTv;

    private OnListener onListener;
    private Context context;

    public YouBeiHwHsajJsumStartPageDialog(@NonNull Context context) {
        super(context, R.style.tran_dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context, R.layout.dialog_start_page_you_bei_he_dje_yrhr, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setCanceledOnTouchOutside(false);
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
        String[] text = {
                "为了保障软件服务的安全、运营的质量及效率，我们会收集您的设备信息和服务日志，具体内容请参照隐私条款；" +
                        "为了预防恶意程序，确保运营质量及效率，我们会收集安装的应用信息或正在进行的进程信息。" +
                        "如果未经您的授权，我们不会使用您的个人信息用于您未授权的其他途径或目的。\n\n" +
                        "我们非常重视对您个人信息的保护，您需要同意",
                "《用户注册协议》",
                "和",
                "《隐私政策》",
                "，才能继续使用我们的服务。",
        };
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < text.length; i++) {
            stringBuffer.append(text[i]);
        }
        contentTv.setText(stringBuffer.toString());
        SpannableString spannableString = new SpannableString(contentTv.getText().toString().trim());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                if (onListener != null) {
                    onListener.clickedZcxy();
                }
            }
        };
        if (text.length > 2) {
            int startNum = spannableString.length() - text[4].length() - text[3].length() - text[2].length() - text[1].length();
            int endNum = spannableString.length() - text[4].length() - text[3].length() - text[2].length();
            spannableString.setSpan(clickableSpan, startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#DEAD27")), startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (text.length > 4) {
            ClickableSpan clickableSpan1 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    if (onListener != null) {
                        onListener.clickedYsxy();
                    }
                }
            };
            int startNum = spannableString.length() - text[4].length() - text[3].length();
            int endNum = spannableString.length() - text[4].length();
            spannableString.setSpan(clickableSpan1, startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#DEAD27")), startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        contentTv.setText(spannableString);
        contentTv.setMovementMethod(LinkMovementMethod.getInstance());
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
