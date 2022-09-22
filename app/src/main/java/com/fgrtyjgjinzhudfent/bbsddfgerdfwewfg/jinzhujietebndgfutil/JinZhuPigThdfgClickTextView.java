package com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.List;

public class JinZhuPigThdfgClickTextView extends androidx.appcompat.widget.AppCompatTextView {

    private List<SpanModel> models;
    private SpanClickListener listener;

    public JinZhuPigThdfgClickTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public static class SpanModel {

        private String str;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }

    public static class ClickSpanModel extends SpanModel {

        public ClickSpanModel() {
        }
    }

    private SpannableString getClickableSpan(int i, ClickSpanModel spanModel, String colorValue) {
        SpannableString spannableString = new SpannableString(spanModel.getStr());
        int start = 0;
        int end = spannableString.length();
        spannableString.setSpan(new SampleClickableSpan(i), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(colorValue)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new NoUnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public void setText(List<SpanModel> models, SpanClickListener listener, String colorValue) {
        this.setClickable(true);
        this.models = models;
        this.listener = listener;
        this.setMovementMethod(LinkMovementMethod.getInstance());
        for (int i = 0; i < models.size(); i++) {
            SpanModel baseSpanModel = models.get(i);
            SpannableString spannableString;
            if (baseSpanModel instanceof ClickSpanModel) {
                spannableString = getClickableSpan(i, (ClickSpanModel) baseSpanModel, colorValue);
            } else {
                spannableString = new SpannableString(baseSpanModel.getStr());
            }
            if (i == 0) {
                this.setText(spannableString);
            } else {
                this.append(spannableString);
            }
        }
    }

    class SampleClickableSpan extends ClickableSpan implements OnClickListener {
        private int position;

        public SampleClickableSpan(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.OnClickListener(position);
            }
//            setText(models, listener);
        }
    }

    public class NoUnderlineSpan extends UnderlineSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }
    }

    public interface SpanClickListener {
        void OnClickListener(int position);
    }

}
