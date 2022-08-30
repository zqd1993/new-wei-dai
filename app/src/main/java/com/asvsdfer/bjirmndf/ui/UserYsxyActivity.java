package com.asvsdfer.bjirmndf.ui;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.asvsdfer.bjirmndf.R;
import com.asvsdfer.bjirmndf.base.BaseActivity;
import com.asvsdfer.bjirmndf.util.StatusBarUtil;

public class UserYsxyActivity extends BaseActivity {

    private WebView ysxyView;

    private Bundle bundle;

    private int tag;

    private String url;

    private WebSettings webSettings;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_ysxy;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        StatusBarUtil.setTransparent(this, false);
        ysxyView = findViewById(R.id.ysxy_view);
        TextView tvTitle = findViewById(R.id.title_tv);
        bundle = getIntent().getExtras();
        if (bundle.containsKey("tag"))
            tag = bundle.getInt("tag");
        if (bundle.containsKey("url"))
            url = bundle.getString("url");
        if (tag == 1)
            tvTitle.setText(getResources().getString(R.string.privacy_policy));
        else
            tvTitle.setText(getResources().getString(R.string.user_service_agreement));
        webSettings = ysxyView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //设置缓存
        webSettings.setJavaScriptEnabled(true);//设置能够解析Javascript
        webSettings.setDomStorageEnabled(true);//设置适应Html5 重点是这个设置
        webSettings.setTextZoom(100);
        ysxyView.setWebViewClient(new WebViewClient());
        ysxyView.loadUrl(url);
    }
}
