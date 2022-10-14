package com.dgjadsie.jkermsd.youbeihwahsndactivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dgjadsie.jkermsd.R;
import com.dgjadsie.jkermsd.youbeihwahsndentity.BaseYouBeiHwHsajJsumEntity;
import com.dgjadsie.jkermsd.youbeihwahsndentity.ConfigYouBeiHwHsajJsumEntity;
import com.dgjadsie.jkermsd.youbeihwahsndhttp.MainYouBeiHwHsajJsumApi;
import com.dgjadsie.jkermsd.youbeihwahsndutil.MyYouBeiHwHsajJsumPreferences;
import com.dgjadsie.jkermsd.youbeihwahsndutil.StatusBarYouBeiHwHsajJsumUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class YouBeiHwHsajJsumUserAgreementActivity extends RxAppCompatActivity {

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.title_tv)
    TextView tvTitle;
    @BindView(R.id.back_image)
    ImageView backImage;

    private Bundle bundle;

    private String url, title;

    private WebSettings webSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5_you_bei_he_dje_yrhr);
        ButterKnife.bind(this);
        initH5();
    }

    private void initH5(){
        StatusBarYouBeiHwHsajJsumUtil.setTransparent(this, false);
        bundle = getIntent().getExtras();
        if (bundle.containsKey("title"))
            title = bundle.getString("title");
        if (bundle.containsKey("url"))
            url = bundle.getString("url");
        tvTitle.setText(title);
        backImage.setOnClickListener(v -> finish());
        webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //设置缓存
        webSettings.setJavaScriptEnabled(true);//设置能够解析Javascript
        webSettings.setDomStorageEnabled(true);//设置适应Html5 重点是这个设置
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
        MainYouBeiHwHsajJsumApi.getRetrofitManager().getApiService().getValue("VIDEOTAPE").enqueue(new Callback<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>>() {
            @Override
            public void onResponse(Call<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>> call, retrofit2.Response<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>> response) {
                if (response.body() == null){
                    return;
                }
                if (response.body() != null) {
                    MyYouBeiHwHsajJsumPreferences.saveBool("NO_RECORD", !response.body().getData().getVideoTape().equals("0"));
                    if (MyYouBeiHwHsajJsumPreferences.getBool("NO_RECORD")) {
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>> call, Throwable t) {

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
