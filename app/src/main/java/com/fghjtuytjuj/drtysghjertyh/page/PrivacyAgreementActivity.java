package com.fghjtuytjuj.drtysghjertyh.page;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.fghjtuytjuj.drtysghjertyh.bean.BaseModel;
import com.fghjtuytjuj.drtysghjertyh.bean.ConfigBean;
import com.fghjtuytjuj.drtysghjertyh.common.SharePreferencesUtil;
import com.fghjtuytjuj.drtysghjertyh.common.StatusBarUtil;
import com.fghjtuytjuj.drtysghjertyh.net.NetApi;
import com.fjsdkqwj.pfdioewjnsd.R;
import com.trello.rxlifecycle2.components.RxActivity;

import retrofit2.Call;
import retrofit2.Callback;

public class PrivacyAgreementActivity extends RxActivity{

    private View backImage;

    private WebView webView;

    private Bundle bundle;

    private int type;

    private String url;

    private WebSettings webSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_privacy_agreement);
        webView = findViewById(R.id.ysxy_view);
        TextView tvTitle = findViewById(R.id.title_tv);
        backImage = findViewById(R.id.back_image);
        bundle = getIntent().getExtras();
        if (bundle.containsKey("type"))
            type = bundle.getInt("type");
        if (bundle.containsKey("url"))
            url = bundle.getString("url");
        if (type == 1)
            tvTitle.setText(getResources().getString(R.string.privacy_policy));
        else
            tvTitle.setText(getResources().getString(R.string.user_service_agreement));
        webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setTextZoom(100);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getConfigValue();
        if (webView != null) webView.onResume();
    }

    private void getConfigValue(){
        NetApi.getNetApi().getNetInterface().getValue("VIDEOTAPE").enqueue(new Callback<BaseModel<ConfigBean>>() {
            @Override
            public void onResponse(Call<BaseModel<ConfigBean>> call, retrofit2.Response<BaseModel<ConfigBean>> response) {
                if (response.body() == null){
                    return;
                }
                if (response.body() != null) {
                    SharePreferencesUtil.saveBool("NO_RECORD", !response.body().getData().getVideoTape().equals("0"));
                    if (SharePreferencesUtil.getBool("NO_RECORD")) {
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ConfigBean>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.destroy();
        }
    }
}
