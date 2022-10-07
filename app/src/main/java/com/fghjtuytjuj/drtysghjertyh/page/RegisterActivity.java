package com.fghjtuytjuj.drtysghjertyh.page;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fghjtuytjuj.drtysghjertyh.BaseApp;
import com.fghjtuytjuj.drtysghjertyh.bean.BaseModel;
import com.fghjtuytjuj.drtysghjertyh.bean.ConfigBean;
import com.fghjtuytjuj.drtysghjertyh.bean.LoginBean;
import com.fghjtuytjuj.drtysghjertyh.common.SharePreferencesUtil;
import com.fghjtuytjuj.drtysghjertyh.common.StaticCommon;
import com.fghjtuytjuj.drtysghjertyh.common.StatusBarUtil;
import com.fghjtuytjuj.drtysghjertyh.net.NetApi;
import com.fghjtuytjuj.drtysghjertyh.view.CountDownTimerView;
import com.fjsdkqwj.pfdioewjnsd.R;
import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.github.gzuliyujiang.oaid.IGetter;
import com.trello.rxlifecycle2.components.RxActivity;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends RxActivity {

    private EditText phoneEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View registerBtn;
    private CheckBox checkboxCb;
    private TextView registerRemindTv;
    public View verificationLl;

    private String phoneStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (SharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusBarUtil.setTransparent(this, false);
        phoneEt = findViewById(R.id.phone_et);
        verificationEt = findViewById(R.id.verification_et);
        getVerificationTv = findViewById(R.id.get_verification_tv);
        registerBtn = findViewById(R.id.register_btn);
        checkboxCb = findViewById(R.id.checkbox_cb);
        registerRemindTv = findViewById(R.id.register_remind_tv);
        verificationLl = findViewById(R.id.verification_ll);
        sendRequestWithOkHttp();
        getConfigValue();
        registerBtn.setOnClickListener(v -> {
            phoneStr = phoneEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(phoneStr)) {
                Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!StaticCommon.isMobile(phoneStr)) {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!checkboxCb.isChecked()) {
                Toast.makeText(this, "请阅读用户协议及隐私政策", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(BaseApp.getInstance());
                isOaid = true;
            }
            DeviceID.getOAID(this, new IGetter() {
                @Override
                public void onOAIDGetComplete(String result) {
                    if (TextUtils.isEmpty(result)){
                        oaidStr = "";
                    } else {
                        int length = result.length();
                        if (length < 64){
                            for (int i = 0; i < 64 - length; i++){
                                result = result + "0";
                            }
                        }
                        oaidStr = result;
                    }
                    login(phoneStr, verificationStr, ip, oaidStr);
                }

                @Override
                public void onOAIDGetError(Exception error) {
                    login(phoneStr, verificationStr, ip, oaidStr);
                }
            });
        });
        getVerificationTv.setOnClickListener(v -> {
            phoneStr = phoneEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(phoneStr)) {
                Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!StaticCommon.isMobile(phoneStr)) {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            sendVerificationCode(phoneStr);
        });
        String[] text = {
                "我已阅读并同意",
                "《注册服务协议》",
                "《用户隐私协议》"
        };
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < text.length; i++) {
            stringBuffer.append(text[i]);
        }
        registerRemindTv.setText(stringBuffer.toString());
        SpannableString spannableString = new SpannableString(registerRemindTv.getText().toString().trim());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putString("url", NetApi.REGISTRATION_AGREEMENT);
                StaticCommon.startActivity(RegisterActivity.this, PrivacyAgreementActivity.class, bundle);
            }
        };
        if (text.length > 1) {
            spannableString.setSpan(clickableSpan, text[0].length(), text[0].length() + text[1].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#C99F7A")), text[0].length(), text[0].length() + text[1].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (text.length > 1) {
            ClickableSpan clickableSpan1 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    bundle = new Bundle();
                    bundle.putInt("type", 2);
                    bundle.putString("url", NetApi.PRIVACY_AGREEMENT);
                    StaticCommon.startActivity(RegisterActivity.this, PrivacyAgreementActivity.class, bundle);
                }
            };
            int startNum = spannableString.length() - text[2].length();
            int endNum = spannableString.length();
            spannableString.setSpan(clickableSpan1, startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#C99F7A")), startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        registerRemindTv.setText(spannableString);
        registerRemindTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://pv.sohu.com/cityjson")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);//调用json解析的方法
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        String jsonStr = "";
        try {
            if (jsonData.contains("{") && jsonData.contains("}")) {
                jsonStr = jsonData.substring(jsonData.indexOf("{"), jsonData.indexOf("}") + 1);
            }
            JSONObject jsonObject = new JSONObject(jsonStr);//新建json对象实例
            ip = jsonObject.getString("cip");//取得其名字的值，一般是字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getConfigValue(){
        NetApi.getNetApi().getNetInterface().getConfig().enqueue(new Callback<BaseModel<ConfigBean>>() {
            @Override
            public void onResponse(Call<BaseModel<ConfigBean>> call, retrofit2.Response<BaseModel<ConfigBean>> response) {
                if (response.body() == null){
                    return;
                }
                ConfigBean entity = response.body().getData();
                if (entity != null) {
                    SharePreferencesUtil.saveString("app_mail", entity.getAppMail());
                    if ("0".equals(entity.getIsCodeLogin())) {
                        verificationLl.setVisibility(View.GONE);
                    } else {
                        verificationLl.setVisibility(View.VISIBLE);
                    }
                    isNeedVerification = "1".equals(entity.getIsCodeLogin());
                    checkboxCb.setChecked("1".equals(entity.getIsSelectLogin()));
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ConfigBean>> call, Throwable t) {

            }
        });
    }

    private void sendVerificationCode(String phoneStr){
        NetApi.getNetApi().getNetInterface().sendVerifyCode(phoneStr).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, retrofit2.Response<BaseModel> response) {
                if (response.body() == null){
                    return;
                }
                if (!TextUtils.isEmpty(response.body().getMsg())) {
                    if (response.body().getMsg().contains("成功")) {
                        CountDownTimerView countDownTimerTextView = new CountDownTimerView(getVerificationTv, 60000, 1000);
                        countDownTimerTextView.start();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(String phoneStr, String verificationStr, String ip, String oaidStr){
        NetApi.getNetApi().getNetInterface().login(phoneStr, verificationStr,"", ip, oaidStr).enqueue(new Callback<BaseModel<LoginBean>>() {
            @Override
            public void onResponse(Call<BaseModel<LoginBean>> call, retrofit2.Response<BaseModel<LoginBean>> response) {
                if (response.body() == null){
                    return;
                }
                LoginBean entity = response.body().getData();
                if (entity != null) {
                    SharePreferencesUtil.saveInt("mobileType", entity.getMobileType());
                    SharePreferencesUtil.saveString("phone", phoneStr);
                    SharePreferencesUtil.saveString("ip", ip);
                    StaticCommon.startActivity(RegisterActivity.this, WorkActivity.class, null);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BaseModel<LoginBean>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
