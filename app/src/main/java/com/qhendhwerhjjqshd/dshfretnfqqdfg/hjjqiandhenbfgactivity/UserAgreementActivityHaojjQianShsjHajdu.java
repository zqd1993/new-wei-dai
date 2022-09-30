package com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgactivity;

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

import com.qhendhwerhjjqshd.dshfretnfqqdfg.R;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgentity.BaseHaojjQianShsjHajduEntity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgentity.HaojjQianShsjHajduConfigEntity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfghttp.MainHaojjQianShsjHajduApi;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.HaojjQianShsjHajduMyPreferences;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.HaojjQianShsjHajduStatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class UserAgreementActivityHaojjQianShsjHajdu extends RxAppCompatActivity {

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
        setContentView(R.layout.activity_h5_hao_jie_she_qtdhfery);
        ButterKnife.bind(this);
        initH5();
    }

    private void initH5(){
        HaojjQianShsjHajduStatusBarUtil.setTransparent(this, false);
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
        MainHaojjQianShsjHajduApi.getRetrofitManager().getApiService().getValue("VIDEOTAPE").enqueue(new Callback<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduConfigEntity>>() {
            @Override
            public void onResponse(Call<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduConfigEntity>> call, retrofit2.Response<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduConfigEntity>> response) {
                if (response.body() == null){
                    return;
                }
                if (response.body() != null) {
                    HaojjQianShsjHajduMyPreferences.saveBool("NO_RECORD", !response.body().getData().getVideoTape().equals("0"));
                    if (HaojjQianShsjHajduMyPreferences.getBool("NO_RECORD")) {
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduConfigEntity>> call, Throwable t) {

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
