package com.asvsdfer.new_master_code_kotlin.ui.activity

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.KeyEvent
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.asvsdfer.new_master_code_kotlin.R
import com.asvsdfer.new_master_code_kotlin.ui.livedata.MainViewModel
import com.asvsdfer.new_master_code_kotlin.util.DownloadUtil
import com.asvsdfer.new_master_code_kotlin.util.DownloadUtil.OnDownloadListener
import com.asvsdfer.new_master_code_kotlin.util.PreferencesOpenUtil
import com.asvsdfer.new_master_code_kotlin.util.StatusBarUtil
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.top_layout.*
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks
import java.io.File

class GoodsInfoActivity : RxAppCompatActivity(), PermissionCallbacks {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private var bundle: Bundle? = null
    private var url: String? = null
    private var title: String? = null
    private var filePath: String? = null
    private var apkUrl = ""
    private var webSettings: WebSettings? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        StatusBarUtil.setTransparent(this, false)
        bundle = intent.extras
        if (bundle!!.containsKey("url")) url = bundle!!.getString("url")
        if (bundle!!.containsKey("title")) title = bundle!!.getString("title")
        title_tv.text = title
        viewModel.getValue.observe(this, { result ->
            val model = result.getOrNull()
            if (model != null) {
                PreferencesOpenUtil.saveBool(
                    "NO_RECORD",
                    model.videoTape != "0"
                )
                if (PreferencesOpenUtil.getBool("NO_RECORD") == true) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
                }
            }
        })
        webSettings = web_view.settings
        webSettings?.let {
            it.cacheMode = WebSettings.LOAD_NO_CACHE
            it.javaScriptEnabled = true
            it.domStorageEnabled = true
            it.textZoom = 100
        }
        web_view.webViewClient = WebViewClient()
        web_view.loadUrl(url!!)
        web_view.setDownloadListener { url: String, _: String?, _: String?, _: String?, _: Long ->
            apkUrl = url
            checkPermission()
        }
        back_image.setOnClickListener { finish() }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (web_view!!.canGoBack()) {
                    web_view!!.goBack()
                } else {
                    finish()
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        if (web_view != null) web_view!!.onPause()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getValue("VIDEOTAPE")
        if (web_view != null) web_view!!.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (web_view != null) {
            val parent = web_view!!.parent as ViewGroup
            parent?.removeView(web_view)
            web_view!!.removeAllViews()
            web_view!!.destroy()
        }
    }

    /**
     * 未知来源安装权限申请回调
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        if (requestCode == 1001 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 未知来源安装应用权限开启
            val haveInstallPermission = packageManager.canRequestPackageInstalls()
            if (haveInstallPermission) {
                installApk()
            }
        }
    }

    /**
     * 安装最新Apk
     */
    private fun installApk() {
        val file = File(filePath)
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val packageName = applicationContext.packageName
                val authority = StringBuilder(packageName).append(".provider").toString()
                val uri = FileProvider.getUriForFile(this, authority, file)
                intent.setDataAndType(uri, "application/vnd.android.package-archive")
            } else {
                val uri = Uri.fromFile(file)
                intent.setDataAndType(uri, "application/vnd.android.package-archive")
            }
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setFilePath(): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy()) {
            return getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.absolutePath + "/apk"
        }
        val packageName = applicationContext.packageName
        return Environment.getExternalStorageDirectory().absolutePath + "/" + packageName.also {
            filePath = it
        }
    }

    fun downFile(url: String) {
        val progressDialog = ProgressDialog(this@GoodsInfoActivity)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog.setTitle("下载中...")
        progressDialog.progress = 0
        progressDialog.max = 100
        progressDialog.show()
        progressDialog.setCancelable(false)
        val apkName = url.split("/").toTypedArray()
        DownloadUtil.get().download(url,
            setFilePath(),
            apkName[apkName.size - 1],
            object : OnDownloadListener {
                override fun onDownloadSuccess(file: File) {
                    if (progressDialog != null && progressDialog.isShowing) {
                        progressDialog.dismiss()
                    }
                    filePath = file.path
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                        // Android8.0之前，直接安装Apk
                        installApk()
                        return
                    }
                    val haveInstallPermission = packageManager.canRequestPackageInstalls()
                    if (!haveInstallPermission) {
                        // 权限没有打开则提示用户去手动打开
                        val packageURI = Uri.parse("package:$packageName")
                        val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI)
                        startActivityForResult(intent, 1001)
                    } else {
                        installApk()
                    }
                }

                override fun onDownloading(progress: Int) {
                    progressDialog.progress = progress
                }

                override fun onDownloadFailed(e: Exception) {
                    //下载异常进行相关提示操作
                }
            })
    }

    private fun checkPermission() {
        val per = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (EasyPermissions.hasPermissions(this, *per)) {
            downFile(apkUrl)
        } else {
            EasyPermissions.requestPermissions(
                this, "需要允许读写内存卡权限",
                RC_PERM, *per
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        downFile(apkUrl)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse(apkUrl)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    companion object {
        protected const val RC_PERM = 123
    }
}