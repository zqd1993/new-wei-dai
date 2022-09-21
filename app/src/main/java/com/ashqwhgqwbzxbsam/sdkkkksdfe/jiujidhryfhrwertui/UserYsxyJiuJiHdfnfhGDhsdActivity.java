package com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui;

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

import com.ashqwhgqwbzxbsam.sdkkkksdfe.R;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertapi.RetrofitJiuJiHdfnfhGDhsdManager;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertbase.BaseJiuJiHdfnfhGDhsdActivity;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertbase.JiuJiHdfnfhGDhsdObserverManager;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertmodel.JiuJiHdfnfhGDhsdBaseModel;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertmodel.ConfigJiuJiHdfnfhGDhsdModel;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.JiuJiHdfnfhGDhsdSharePreferencesUtil;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.JiuJiHdfnfhGDhsdStatusBarUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserYsxyJiuJiHdfnfhGDhsdActivity extends BaseJiuJiHdfnfhGDhsdActivity {

    private View backImage;

    private WebView ysxyView;

    private Bundle bundle;

    private int tag;

    private String url;

    private WebSettings webSettings;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_ysxy_jiu_ji_fdher_reytjyh;
    }

    @Override
    public void initListener() {
        backImage.setOnClickListener(v -> finish());
    }

    @Override
    public void initData() {
        JiuJiHdfnfhGDhsdStatusBarUtil.setTransparent(this, false);
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
        Observable<JiuJiHdfnfhGDhsdBaseModel<ConfigJiuJiHdfnfhGDhsdModel>> observable = RetrofitJiuJiHdfnfhGDhsdManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JiuJiHdfnfhGDhsdObserverManager<JiuJiHdfnfhGDhsdBaseModel<ConfigJiuJiHdfnfhGDhsdModel>>() {
                    @Override
                    public void onSuccess(JiuJiHdfnfhGDhsdBaseModel<ConfigJiuJiHdfnfhGDhsdModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigJiuJiHdfnfhGDhsdModel configJiuJiHdfnfhGDhsdModel = model.getData();
                        if (configJiuJiHdfnfhGDhsdModel != null) {
                            JiuJiHdfnfhGDhsdSharePreferencesUtil.saveBool("NO_RECORD", !configJiuJiHdfnfhGDhsdModel.getVideoTape().equals("0"));
                            if (JiuJiHdfnfhGDhsdSharePreferencesUtil.getBool("NO_RECORD")) {
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
