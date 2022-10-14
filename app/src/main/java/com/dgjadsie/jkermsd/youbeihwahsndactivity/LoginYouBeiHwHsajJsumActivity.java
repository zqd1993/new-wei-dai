package com.dgjadsie.jkermsd.youbeihwahsndactivity;

import android.graphics.Color;
import android.os.Bundle;
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

import com.dgjadsie.jkermsd.R;
import com.dgjadsie.jkermsd.youbeihwahsndentity.BaseYouBeiHwHsajJsumEntity;
import com.dgjadsie.jkermsd.youbeihwahsndentity.ConfigYouBeiHwHsajJsumEntity;
import com.dgjadsie.jkermsd.youbeihwahsndentity.YouBeiHwHsajJsumLoginEntity;
import com.dgjadsie.jkermsd.youbeihwahsndhttp.MainYouBeiHwHsajJsumApi;
import com.dgjadsie.jkermsd.youbeihwahsndutil.CommonYouBeiHwHsajJsumUtil;
import com.dgjadsie.jkermsd.youbeihwahsndutil.YouBeiHwHsajJsumCountDownTimerTextView;
import com.dgjadsie.jkermsd.youbeihwahsndutil.MyYouBeiHwHsajJsumPreferences;
import com.dgjadsie.jkermsd.youbeihwahsndutil.StatusBarYouBeiHwHsajJsumUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginYouBeiHwHsajJsumActivity extends RxAppCompatActivity {

    @BindView(R.id.login_remind_tv)
    TextView loginRemindTv;
    @BindView(R.id.mobile_et)
    EditText mobileEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getverificationTv;
    @BindView(R.id.login_btn)
    View loginBtn;
    @BindView(R.id.checkbox_cb)
    CheckBox checkBoxCb;
    @BindView(R.id.verification_ll)
    View verificationLl;

    private Bundle bundle;
    private String phoneStr, verificationStr, ip = "";
    private boolean isNeedVerification = true, isChecked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarYouBeiHwHsajJsumUtil.setTransparent(this, false);
        if (MyYouBeiHwHsajJsumPreferences.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_login_you_bei_he_dje_yrhr);
        ButterKnife.bind(this);
        getIp();
        getConfigValue();
        setBottomTv();
        initListener();
    }

    private void getIp() {
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
                    parseJSONToIp(responseData);//调用json解析的方法
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONToIp(String jsonData) {
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

    private void setBottomTv(){
        String[] text = {
                "我已阅读并同意",
                "《用户注册协议》",
                "《隐私政策》"
        };
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < text.length; i++) {
            stringBuffer.append(text[i]);
        }
        loginRemindTv.setText(stringBuffer.toString());
        SpannableString spannableString = new SpannableString(loginRemindTv.getText().toString().trim());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                bundle = new Bundle();
                bundle.putString("title", "注册协议");
                bundle.putString("url", MainYouBeiHwHsajJsumApi.ZCXY);
                CommonYouBeiHwHsajJsumUtil.startActivity(LoginYouBeiHwHsajJsumActivity.this, YouBeiHwHsajJsumUserAgreementActivity.class, bundle);
            }
        };
        if (text.length > 1) {
            spannableString.setSpan(clickableSpan, text[0].length(), text[0].length() + text[1].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#DEAD27")), text[1].length(), text[2].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (text.length > 1) {
            ClickableSpan clickableSpan1 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    bundle = new Bundle();
                    bundle.putString("title", "隐私协议");
                    bundle.putString("url", MainYouBeiHwHsajJsumApi.YSXY);
                    CommonYouBeiHwHsajJsumUtil.startActivity(LoginYouBeiHwHsajJsumActivity.this, YouBeiHwHsajJsumUserAgreementActivity.class, bundle);
                }
            };
            int startNum = spannableString.length() - text[2].length();
            int endNum = spannableString.length();
            spannableString.setSpan(clickableSpan1, startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#DEAD27")), startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        loginRemindTv.setText(spannableString);
        loginRemindTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initListener(){
        getverificationTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (TextUtils.isEmpty(phoneStr)){
                Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!CommonYouBeiHwHsajJsumUtil.isMobile(phoneStr)) {
                Toast.makeText(this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
                return;
            }
            sendVerificationCode(phoneStr);
        });
        loginBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(phoneStr)){
                Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!CommonYouBeiHwHsajJsumUtil.isMobile(phoneStr)) {
                Toast.makeText(this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification){
                Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!checkBoxCb.isChecked()){
                Toast.makeText(this, "请阅读用户协议及隐私政策", Toast.LENGTH_SHORT).show();
                return;
            }
            login(phoneStr, verificationStr, ip);
        });
    }

    private void getConfigValue(){
        MainYouBeiHwHsajJsumApi.getRetrofitManager().getApiService().getConfig().enqueue(new Callback<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>>() {
            @Override
            public void onResponse(Call<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>> call, retrofit2.Response<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>> response) {
                if (response.body() == null){
                    return;
                }
                ConfigYouBeiHwHsajJsumEntity entity = response.body().getData();
                if (entity != null) {
                    MyYouBeiHwHsajJsumPreferences.saveString("app_mail", entity.getAppMail());
                    if ("0".equals(entity.getIsCodeLogin())) {
                        verificationLl.setVisibility(View.GONE);
                    } else {
                        verificationLl.setVisibility(View.VISIBLE);
                    }
                    isNeedVerification = "1".equals(entity.getIsCodeLogin());
                    isChecked = "1".equals(entity.getIsSelectLogin());
                    checkBoxCb.setChecked(isChecked);
                }
            }

            @Override
            public void onFailure(Call<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>> call, Throwable t) {

            }
        });
    }

    private void sendVerificationCode(String phoneStr){
        MainYouBeiHwHsajJsumApi.getRetrofitManager().getApiService().sendVerifyCode(phoneStr).enqueue(new Callback<BaseYouBeiHwHsajJsumEntity>() {
            @Override
            public void onResponse(Call<BaseYouBeiHwHsajJsumEntity> call, retrofit2.Response<BaseYouBeiHwHsajJsumEntity> response) {
                if (response.body() == null){
                    return;
                }
                if (!TextUtils.isEmpty(response.body().getMsg())) {
                    if (response.body().getMsg().contains("成功")) {
                        YouBeiHwHsajJsumCountDownTimerTextView youBeiHwHsajJsumCountDownTimerTextView = new YouBeiHwHsajJsumCountDownTimerTextView(getverificationTv, 60000, 1000);
                        youBeiHwHsajJsumCountDownTimerTextView.start();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseYouBeiHwHsajJsumEntity> call, Throwable t) {
                Toast.makeText(LoginYouBeiHwHsajJsumActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(String phoneStr, String verificationStr, String ip){
        MainYouBeiHwHsajJsumApi.getRetrofitManager().getApiService().login(phoneStr, verificationStr,"", ip).enqueue(new Callback<BaseYouBeiHwHsajJsumEntity<YouBeiHwHsajJsumLoginEntity>>() {
            @Override
            public void onResponse(Call<BaseYouBeiHwHsajJsumEntity<YouBeiHwHsajJsumLoginEntity>> call, retrofit2.Response<BaseYouBeiHwHsajJsumEntity<YouBeiHwHsajJsumLoginEntity>> response) {
                if (response.body() == null){
                    return;
                }
                YouBeiHwHsajJsumLoginEntity entity = response.body().getData();
                if (entity != null) {
                    MyYouBeiHwHsajJsumPreferences.saveInt("mobileType", entity.getMobileType());
                    MyYouBeiHwHsajJsumPreferences.saveString("phone", phoneStr);
                    MyYouBeiHwHsajJsumPreferences.saveString("ip", ip);
                    CommonYouBeiHwHsajJsumUtil.startActivity(LoginYouBeiHwHsajJsumActivity.this, YouBeiHwHsajJsumHomePageActivity.class, null);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BaseYouBeiHwHsajJsumEntity<YouBeiHwHsajJsumLoginEntity>> call, Throwable t) {
                Toast.makeText(LoginYouBeiHwHsajJsumActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}