package com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.MineApp
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.R
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.api.NetSimple
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.BaseModel
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.ConfigModel
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.EmptyModel
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.LoginModel
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.SPUtil
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.StaticUtil
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.StatusBarUtil
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.widget.ClickTextView
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.widget.CountDownTimerUtils
import com.github.gzuliyujiang.oaid.DeviceID
import com.github.gzuliyujiang.oaid.DeviceIdentifier
import com.github.gzuliyujiang.oaid.IGetter
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.loading_layout.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginActivity : RxAppCompatActivity() {

    private var bundle: Bundle? = null
    private var mobileStr = ""
    private  var verificationStr = ""
    private  var ip = ""
    private  var oaidStr = ""
    private var isNeedVerification = false
    private  var isOaid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTransparent(this, false)
        setContentView(R.layout.activity_login_page)
        sendRequestWithOkHttp()
        initListener()
        getConfig();
    }

    private fun initListener(){
        login_remind_tv.setText(createSpanTexts(), { position ->
            if (position === 1) {
                bundle = Bundle()
                bundle!!.putInt("tag", 1)
                bundle!!.putString("url", NetSimple.ZCXY)
                StaticUtil.startActivity(this@LoginActivity, UserAgreementActivity::class.java, bundle)
            } else {
                bundle = Bundle()
                bundle!!.putInt("tag", 2)
                bundle!!.putString("url", NetSimple.YSXY)
                StaticUtil.startActivity(this@LoginActivity, UserAgreementActivity::class.java, bundle)
            }
        }, "#FDCA6E")
        login_btn.setOnClickListener(View.OnClickListener { v: View? ->
            mobileStr = mobile_et.text.toString().trim { it <= ' ' }
            verificationStr = verification_et.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(mobileStr)) {
                Toast.makeText(this@LoginActivity, "请输入手机号", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (!StaticUtil.isMobile(mobileStr)) {
                Toast.makeText(this@LoginActivity, "请输入正确的手机号", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                Toast.makeText(this@LoginActivity, "验证码不能为空", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (!remind_cb.isChecked()) {
                Toast.makeText(this@LoginActivity, "请阅读用户协议及隐私政策", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (!isOaid) {
                DeviceIdentifier.register(MineApp.getInstance())
                isOaid = true
            }
            DeviceID.getOAID(this, object : IGetter {
                override fun onOAIDGetComplete(result: String) {
                    var result = result
                    if (TextUtils.isEmpty(result)) {
                        oaidStr = ""
                    } else {
                        val length = result.length
                        if (length < 64) {
                            for (i in 0 until 64 - length) {
                                result = result + "0"
                            }
                        }
                        oaidStr = result
                    }
                    rotate_loading.start()
                    loading_fl.visibility = View.VISIBLE
                    login(mobileStr, verificationStr)
                }

                override fun onOAIDGetError(error: Exception) {
                    rotate_loading.start()
                    loading_fl.visibility = View.VISIBLE
                    login(mobileStr, verificationStr)
                }
            })
        })
        get_verification_tv.setOnClickListener(View.OnClickListener { v: View? ->
            mobileStr = mobile_et.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(mobileStr)) {
                Toast.makeText(this@LoginActivity, "请输入手机号", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (!StaticUtil.isMobile(mobileStr)) {
                Toast.makeText(this@LoginActivity, "请输入正确的手机号", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            sendVerifyCode(mobileStr)
        })
    }

    private fun sendRequestWithOkHttp() {
        Thread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("http://pv.sohu.com/cityjson")
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body().string()
                parseJSONWithJSONObject(responseData) //调用json解析的方法
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun parseJSONWithJSONObject(jsonData: String) {
        var jsonStr = ""
        try {
            if (jsonData.contains("{") && jsonData.contains("}")) {
                jsonStr = jsonData.substring(jsonData.indexOf("{"), jsonData.indexOf("}") + 1)
            }
            val jsonObject = JSONObject(jsonStr) //新建json对象实例
            ip = jsonObject.getString("cip") //取得其名字的值，一般是字符串
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun getConfig() {
        val apiService = NetSimple.getRetrofitManager().apiService
        apiService.config.enqueue(object : Callback<BaseModel<ConfigModel>> {
            override fun onFailure(call: Call<BaseModel<ConfigModel>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<BaseModel<ConfigModel>>,
                response: Response<BaseModel<ConfigModel>>
            ) {
                val model = response.body() ?: return
                val configModel = model.data
                if (configModel != null) {
                    SPUtil.saveString("APP_MAIL", configModel.appMail)
                    isNeedVerification = "1" == configModel.isCodeLogin
                    verification_ll.visibility = if (isNeedVerification) View.VISIBLE else View.GONE
                    remind_cb.isChecked = "1" == configModel.isSelectLogin
                }
            }
        })
    }

    private fun sendVerifyCode(mobileStr: String){
        val apiService = NetSimple.getRetrofitManager().apiService
        apiService.sendVerifyCode(mobileStr).enqueue(object : Callback<BaseModel<EmptyModel>> {
            override fun onFailure(call: Call<BaseModel<EmptyModel>>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@LoginActivity, "验证码发送失败", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<BaseModel<EmptyModel>>,
                response: Response<BaseModel<EmptyModel>>
            ) {
                val model = response.body() ?: return
                if (model.code === 200) {
                    Toast.makeText(this@LoginActivity, "发送成功", Toast.LENGTH_SHORT).show()
                    val mCountDownTimerUtils = CountDownTimerUtils(get_verification_tv, 60000, 1000)
                    mCountDownTimerUtils.start()
                }
            }
        })
    }

    private fun login(mobileStr: String, verificationStr: String){
        val apiService = NetSimple.getRetrofitManager().apiService
        apiService.login(mobileStr, verificationStr, "", ip, oaidStr).enqueue(object : Callback<BaseModel<LoginModel>> {
            override fun onFailure(call: Call<BaseModel<LoginModel>>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@LoginActivity, "登录失败", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<BaseModel<LoginModel>>,
                response: Response<BaseModel<LoginModel>>
            ) {
                val model = response.body() ?: return
                val loginModel = model.data
                if (loginModel != null) {
                    SPUtil.saveInt("mobileType", loginModel.mobileType)
                    SPUtil.saveString("phone", mobileStr)
                    SPUtil.saveString("ip", ip)
                    StaticUtil.startActivity(this@LoginActivity, MainActivity::class.java, null)
                    finish()
                }
            }
        })
    }

    private fun createSpanTexts(): List<ClickTextView.SpanModel>? {
        val spanModels: MutableList<ClickTextView.SpanModel> = ArrayList<ClickTextView.SpanModel>()
        var spanModel = ClickTextView.ClickSpanModel()
        val textSpanModel = ClickTextView.SpanModel()
        textSpanModel.str = "我已阅读并同意"
        spanModels.add(textSpanModel)
        spanModel.str = "《注册服务协议》"
        spanModels.add(spanModel)
        spanModel = ClickTextView.ClickSpanModel()
        spanModel.str = "《用户隐私协议》"
        spanModels.add(spanModel)
        return spanModels
    }

}