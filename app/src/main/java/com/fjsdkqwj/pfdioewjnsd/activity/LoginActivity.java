package com.fjsdkqwj.pfdioewjnsd.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fjsdkqwj.pfdioewjnsd.R;
import com.fjsdkqwj.pfdioewjnsd.app.MainApp;
import com.fjsdkqwj.pfdioewjnsd.entity.BaseEntity;
import com.fjsdkqwj.pfdioewjnsd.entity.ConfigEntity;
import com.fjsdkqwj.pfdioewjnsd.entity.LoginEntity;
import com.fjsdkqwj.pfdioewjnsd.http.MainApi;
import com.fjsdkqwj.pfdioewjnsd.util.CommonUtil;
import com.fjsdkqwj.pfdioewjnsd.util.CountDownTimerTextView;
import com.fjsdkqwj.pfdioewjnsd.util.MyPreferences;
import com.fjsdkqwj.pfdioewjnsd.util.StatusBarUtil;
import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.github.gzuliyujiang.oaid.IGetter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends RxAppCompatActivity {

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

    private Bundle bundle;
    private String phoneStr, verificationStr, oaidStr, ip = "";
    private boolean isNeedVerification = true, isChecked = false, isOaid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getIp();
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
                "《注册服务协议》",
                "《用户隐私协议》"
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
                bundle.putString("url", MainApi.ZCXY);
//                CommonUtil.startActivity(LoginActivity.this, AgreementActivity.class, bundle);
            }
        };
        if (text.length > 1) {
            spannableString.setSpan(clickableSpan, text[0].length(), text[0].length() + text[1].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#0ccfe9")), text[1].length(), text[2].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (text.length > 1) {
            ClickableSpan clickableSpan1 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    bundle = new Bundle();
                    bundle.putString("title", "隐私协议");
                    bundle.putString("url", MainApi.YSXY);
//                    CommonUtil.startActivity(LoginActivity.this, AgreementActivity.class, bundle);
                }
            };
            int startNum = spannableString.length() - text[2].length();
            int endNum = spannableString.length();
            spannableString.setSpan(clickableSpan1, startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#0ccfe9")), startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
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
            if (!CommonUtil.isMobile(phoneStr)) {
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
            if (!CommonUtil.isMobile(phoneStr)) {
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
            if (!isOaid){
                DeviceIdentifier.register(MainApp.getInstance());
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
    }

    private void sendVerificationCode(String phoneStr){
        MainApi.getRetrofitManager().getApiService().sendVerifyCode(phoneStr).enqueue(new Callback<BaseEntity>() {
            @Override
            public void onResponse(Call<BaseEntity> call, retrofit2.Response<BaseEntity> response) {
                if (response.body() == null){
                    return;
                }
                if (!TextUtils.isEmpty(response.body().getMsg())) {
                    if (response.body().getMsg().contains("成功")) {
                        CountDownTimerTextView countDownTimerTextView = new CountDownTimerTextView(getverificationTv, 60000, 1000);
                        countDownTimerTextView.start();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(String phoneStr, String verificationStr, String ip, String oaidStr){
        MainApi.getRetrofitManager().getApiService().login(phoneStr, verificationStr,"", ip, oaidStr).enqueue(new Callback<BaseEntity<LoginEntity>>() {
            @Override
            public void onResponse(Call<BaseEntity<LoginEntity>> call, retrofit2.Response<BaseEntity<LoginEntity>> response) {
                if (response.body() == null){
                    return;
                }
                LoginEntity entity = response.body().getData();
                if (entity != null) {
                    MyPreferences.saveInt("mobileType", entity.getMobileType());
                    MyPreferences.saveString("phone", phoneStr);
                    MyPreferences.saveString("ip", ip);
                    CommonUtil.startActivity(LoginActivity.this, HomePageActivity.class, null);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BaseEntity<LoginEntity>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
