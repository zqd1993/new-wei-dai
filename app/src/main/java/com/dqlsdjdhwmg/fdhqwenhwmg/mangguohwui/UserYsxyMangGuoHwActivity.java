package com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui;

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

import com.dqlsdjdhwmg.fdhqwenhwmg.R;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwapi.MangGuoHwRetrofitManager;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwbase.BaseMangGuoHwActivity;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwbase.ObserverMangGuoHwManager;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.BaseMangGuoHwModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.MangGuoHwConfigModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.MangGuoHwSharePreferencesUtils;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.StatusBarUtilMangGuoHw;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserYsxyMangGuoHwActivity extends BaseMangGuoHwActivity {

    private View backImage;

    private WebView ysxyView;

    private Bundle bundle;

    private int tag;

    private String url;

    private WebSettings webSettings;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mang_guo_hw_user_ysxy;
    }

    @Override
    public void initListener() {
        backImage.setOnClickListener(v -> finish());
    }

    @Override
    public void initData() {
        StatusBarUtilMangGuoHw.setTransparent(this, false);
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
        Observable<BaseMangGuoHwModel<MangGuoHwConfigModel>> observable = MangGuoHwRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverMangGuoHwManager<BaseMangGuoHwModel<MangGuoHwConfigModel>>() {
                    @Override
                    public void onSuccess(BaseMangGuoHwModel<MangGuoHwConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        MangGuoHwConfigModel mangGuoHwConfigModel = model.getData();
                        if (mangGuoHwConfigModel != null) {
                            MangGuoHwSharePreferencesUtils.saveBool("NO_RECORD", !mangGuoHwConfigModel.getVideoTape().equals("0"));
                            if (MangGuoHwSharePreferencesUtils.getBool("NO_RECORD")) {
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
