package com.dgjadsie.jkermsd.youbeihwahsndactivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.dgjadsie.jkermsd.R;
import com.dgjadsie.jkermsd.youbeihwahsndentity.BaseYouBeiHwHsajJsumEntity;
import com.dgjadsie.jkermsd.youbeihwahsndentity.ConfigYouBeiHwHsajJsumEntity;
import com.dgjadsie.jkermsd.youbeihwahsndhttp.MainYouBeiHwHsajJsumApi;
import com.dgjadsie.jkermsd.youbeihwahsndutil.DownloadYouBeiHwHsajJsumUtil;
import com.dgjadsie.jkermsd.youbeihwahsndutil.MyYouBeiHwHsajJsumPreferences;
import com.dgjadsie.jkermsd.youbeihwahsndutil.StatusBarYouBeiHwHsajJsumUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;

public class YouBeiHwHsajJsumShowGoogsInfoActivity extends RxAppCompatActivity implements EasyPermissions.PermissionCallbacks{

    protected static final int RC_PERM = 123;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.title_tv)
    TextView tvTitle;
    @BindView(R.id.back_image)
    ImageView backImage;

    private Bundle bundle;

    private String url, title, filePath, apkUrl;

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
        if (bundle.containsKey("url"))
            url = bundle.getString("url");
        if (bundle.containsKey("name"))
            title = bundle.getString("name");
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
        ProgressDialog progressDialog = new ProgressDialog(YouBeiHwHsajJsumShowGoogsInfoActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("下载中...");
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.show();
        progressDialog.setCancelable(false);
        String apkName[] = url.split("/");
        DownloadYouBeiHwHsajJsumUtil.get().downloadApk(url, setFilePath(), apkName[apkName.length - 1], new DownloadYouBeiHwHsajJsumUtil.OnDownloadListener() {
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
