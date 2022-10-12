package com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.R;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvapi.BaseQuHuaDjdfuVdhrRetrofitManager;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvbase.BaseBaseQuHuaDjdfuVdhrActivity;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvbase.BaseQuHuaDjdfuVdhrObserverManager;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.BaseQuHuaDjdfuVdhrModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.BaseQuHuaDjdfuVdhrConfigModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrDownloadApkUtil;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrSharePreferencesUtil;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrStatusBarUtil;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.EasyPermissions;

public class GoodsDetailsQuHuaDjdfuVdhrActivityBase extends BaseBaseQuHuaDjdfuVdhrActivity implements EasyPermissions.PermissionCallbacks{

    protected static final int RC_PERM = 123;

    private View backImage;

    private WebView webView;

    private Bundle bundle;

    private String url, title, filePath, apkUrl = "";

    private WebSettings webSettings;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_ysxy_qu_hua_hua_erf_engh;
    }

    @Override
    public void initListener() {
        backImage.setOnClickListener(v -> finish());
    }

    @Override
    public void initData() {
        BaseQuHuaDjdfuVdhrStatusBarUtil.setTransparent(this, false);
        TextView tvTitle = findViewById(R.id.title_tv);
        backImage = findViewById(R.id.back_image);
        webView = findViewById(R.id.ysxy_view);
        bundle = getIntent().getExtras();
        if (bundle.containsKey("url"))
            url = bundle.getString("url");
        if (bundle.containsKey("title"))
            title = bundle.getString("title");
        tvTitle.setText(title);
        webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //设置缓存
        webSettings.setJavaScriptEnabled(true);//设置能够解析Javascript
        webSettings.setDomStorageEnabled(true);//设置适应Html5 重点是这个设置
        webSettings.setTextZoom(100);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            apkUrl = url;
            checkPermission();
        });
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
        getConfig();
        if (webView != null) webView.onResume();
    }

    private void getConfig() {
        Observable<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrConfigModel>> observable = BaseQuHuaDjdfuVdhrRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQuHuaDjdfuVdhrObserverManager<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrConfigModel>>() {
                    @Override
                    public void onSuccess(BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        BaseQuHuaDjdfuVdhrConfigModel baseQuHuaDjdfuVdhrConfigModel = model.getData();
                        if (baseQuHuaDjdfuVdhrConfigModel != null) {
                            BaseQuHuaDjdfuVdhrSharePreferencesUtil.saveBool("NO_RECORD", !baseQuHuaDjdfuVdhrConfigModel.getVideoTape().equals("0"));
                            if (BaseQuHuaDjdfuVdhrSharePreferencesUtil.getBool("NO_RECORD")) {
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

    /**
     * 未知来源安装权限申请回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1001 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 未知来源安装应用权限开启
            boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (haveInstallPermission) {
                installApk();
            }
        }
    }

    /**
     * 安装最新Apk
     */
    private void installApk() {
        File file = new File(filePath);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String packageName = getApplicationContext().getPackageName();
                String authority = new StringBuilder(packageName).append(".provider").toString();
                Uri uri = FileProvider.getUriForFile(this, authority, file);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
            } else {
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
            }
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String setFilePath() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy()) {
            return this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/apk";
        }
        String packageName = getApplicationContext().getPackageName();
        return filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + packageName;
    }

    public void downFile(String url) {
        ProgressDialog progressDialog = new ProgressDialog(GoodsDetailsQuHuaDjdfuVdhrActivityBase.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("下载中...");
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.show();
        progressDialog.setCancelable(false);
        String apkName[] = url.split("/");
        BaseQuHuaDjdfuVdhrDownloadApkUtil.get().download(url, setFilePath(), apkName[apkName.length - 1], new BaseQuHuaDjdfuVdhrDownloadApkUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                filePath = file.getPath();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    // Android8.0之前，直接安装Apk
                    installApk();
                    return;
                }
                boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
                if (!haveInstallPermission) {
                    // 权限没有打开则提示用户去手动打开
                    Uri packageURI = Uri.parse("package:" + getPackageName());
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                    startActivityForResult(intent, 1001);
                } else {
                    installApk();
                }

            }

            @Override
            public void onDownloading(int progress) {
                progressDialog.setProgress(progress);
            }

            @Override
            public void onDownloadFailed(Exception e) {
                //下载异常进行相关提示操作

            }
        });
    }

    private void checkPermission() {
        String[] per = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, per)) {
            downFile(apkUrl);
        } else {
            EasyPermissions.requestPermissions(this, "需要允许读写内存卡权限",
                    RC_PERM, per);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        downFile(apkUrl);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(apkUrl));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
