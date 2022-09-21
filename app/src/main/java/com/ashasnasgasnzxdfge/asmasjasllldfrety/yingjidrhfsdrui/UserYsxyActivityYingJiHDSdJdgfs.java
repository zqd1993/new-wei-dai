package com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ashasnasgasnzxdfge.asmasjasllldfrety.R;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrapi.YingJiHDSdJdgfsRetrofitManager;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrbase.YingJiHDSdJdgfsBaseActivity;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrbase.YingJiHDSdJdgfsObserverManager;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.YingJiHDSdJdgfsBaseModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.YingJiHDSdJdgfsConfigModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsSharePreferencesUtil;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsStatusBarUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserYsxyActivityYingJiHDSdJdgfs extends YingJiHDSdJdgfsBaseActivity {

    private View backImage;

    private WebView ysxyView;

    private Bundle bundle;

    private int tag;

    private String url;

    private WebSettings webSettings;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_ysxy_ying_ji_dh_jie_fuerty;
    }

    @Override
    public void initListener() {
        backImage.setOnClickListener(v -> finish());
    }

    @Override
    public void initData() {
        YingJiHDSdJdgfsStatusBarUtil.setTransparent(this, false);
        ysxyView = findViewById(R.id.ysxy_view);
        TextView tvTitle = findViewById(R.id.title_tv);
        backImage = findViewById(R.id.back_image);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (ysxyView.canGoBack()) {
                    ysxyView.goBack();
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
        if (ysxyView != null) ysxyView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getConfig();
        if (ysxyView != null) ysxyView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ysxyView != null) {
            ViewGroup parent = (ViewGroup) ysxyView.getParent();
            if (parent != null) {
                parent.removeView(ysxyView);
            }
            ysxyView.removeAllViews();
            ysxyView.destroy();
        }
    }

    private void getConfig() {
        Observable<YingJiHDSdJdgfsBaseModel<YingJiHDSdJdgfsConfigModel>> observable = YingJiHDSdJdgfsRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new YingJiHDSdJdgfsObserverManager<YingJiHDSdJdgfsBaseModel<YingJiHDSdJdgfsConfigModel>>() {
                    @Override
                    public void onSuccess(YingJiHDSdJdgfsBaseModel<YingJiHDSdJdgfsConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        YingJiHDSdJdgfsConfigModel configCaiJieTongYshVdjrytModel = model.getData();
                        if (configCaiJieTongYshVdjrytModel != null) {
                            YingJiHDSdJdgfsSharePreferencesUtil.saveBool("NO_RECORD", !configCaiJieTongYshVdjrytModel.getVideoTape().equals("0"));
                            if (YingJiHDSdJdgfsSharePreferencesUtil.getBool("NO_RECORD")) {
                                getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                            }
                        }
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }
}
