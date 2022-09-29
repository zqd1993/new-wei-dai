package com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnactivity;

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

import com.urhdnsertjg.gjuerjfhf.R;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnapp.MainJZhuJsiQIajsdnApp;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnentity.BaseJZhuJsiQIajsdnEntity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnentity.JZhuJsiQIajsdnConfigEntity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnentity.LoginJZhuJsiQIajsdnEntity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnhttp.JZhuJsiQIajsdnMainApi;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.JZhuJsiQIajsdnCommonUtil;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.JZhuJsiQIajsdnCountDownTimerTextView;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.MyJZhuJsiQIajsdnPreferences;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.JZhuJsiQIajsdnStatusBarUtil;
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

public class LoginJZhuJsiQIajsdnActivity extends RxAppCompatActivity {

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
    private String phoneStr, verificationStr, oaidStr, ip = "";
    private boolean isNeedVerification = true, isChecked = false, isOaid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JZhuJsiQIajsdnStatusBarUtil.setTransparent(this, false);
        if (MyJZhuJsiQIajsdnPreferences.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_login_jin_zhu_jqi_djrufn_dfke);
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
                bundle.putString("url", JZhuJsiQIajsdnMainApi.ZCXY);
                JZhuJsiQIajsdnCommonUtil.startActivity(LoginJZhuJsiQIajsdnActivity.this, JZhuJsiQIajsdnUserAgreementActivity.class, bundle);
            }
        };
        if (text.length > 1) {
            spannableString.setSpan(clickableSpan, text[0].length(), text[0].length() + text[1].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#B80F0A")), text[1].length(), text[2].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (text.length > 1) {
            ClickableSpan clickableSpan1 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    bundle = new Bundle();
                    bundle.putString("title", "隐私协议");
                    bundle.putString("url", JZhuJsiQIajsdnMainApi.YSXY);
                    JZhuJsiQIajsdnCommonUtil.startActivity(LoginJZhuJsiQIajsdnActivity.this, JZhuJsiQIajsdnUserAgreementActivity.class, bundle);
                }
            };
            int startNum = spannableString.length() - text[2].length();
            int endNum = spannableString.length();
            spannableString.setSpan(clickableSpan1, startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#B80F0A")), startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
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
            if (!JZhuJsiQIajsdnCommonUtil.isMobile(phoneStr)) {
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
            if (!JZhuJsiQIajsdnCommonUtil.isMobile(phoneStr)) {
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
                DeviceIdentifier.register(MainJZhuJsiQIajsdnApp.getInstance());
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

    private void getConfigValue(){
        JZhuJsiQIajsdnMainApi.getRetrofitManager().getApiService().getConfig().enqueue(new Callback<BaseJZhuJsiQIajsdnEntity<JZhuJsiQIajsdnConfigEntity>>() {
            @Override
            public void onResponse(Call<BaseJZhuJsiQIajsdnEntity<JZhuJsiQIajsdnConfigEntity>> call, retrofit2.Response<BaseJZhuJsiQIajsdnEntity<JZhuJsiQIajsdnConfigEntity>> response) {
                if (response.body() == null){
                    return;
                }
                JZhuJsiQIajsdnConfigEntity entity = response.body().getData();
                if (entity != null) {
                    MyJZhuJsiQIajsdnPreferences.saveString("app_mail", entity.getAppMail());
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
            public void onFailure(Call<BaseJZhuJsiQIajsdnEntity<JZhuJsiQIajsdnConfigEntity>> call, Throwable t) {

            }
        });
    }

    private void sendVerificationCode(String phoneStr){
        JZhuJsiQIajsdnMainApi.getRetrofitManager().getApiService().sendVerifyCode(phoneStr).enqueue(new Callback<BaseJZhuJsiQIajsdnEntity>() {
            @Override
            public void onResponse(Call<BaseJZhuJsiQIajsdnEntity> call, retrofit2.Response<BaseJZhuJsiQIajsdnEntity> response) {
                if (response.body() == null){
                    return;
                }
                if (!TextUtils.isEmpty(response.body().getMsg())) {
                    if (response.body().getMsg().contains("成功")) {
                        JZhuJsiQIajsdnCountDownTimerTextView JZhuJsiQIajsdnCountDownTimerTextView = new JZhuJsiQIajsdnCountDownTimerTextView(getverificationTv, 60000, 1000);
                        JZhuJsiQIajsdnCountDownTimerTextView.start();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseJZhuJsiQIajsdnEntity> call, Throwable t) {
                Toast.makeText(LoginJZhuJsiQIajsdnActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(String phoneStr, String verificationStr, String ip, String oaidStr){
        JZhuJsiQIajsdnMainApi.getRetrofitManager().getApiService().login(phoneStr, verificationStr,"", ip, oaidStr).enqueue(new Callback<BaseJZhuJsiQIajsdnEntity<LoginJZhuJsiQIajsdnEntity>>() {
            @Override
            public void onResponse(Call<BaseJZhuJsiQIajsdnEntity<LoginJZhuJsiQIajsdnEntity>> call, retrofit2.Response<BaseJZhuJsiQIajsdnEntity<LoginJZhuJsiQIajsdnEntity>> response) {
                if (response.body() == null){
                    return;
                }
                LoginJZhuJsiQIajsdnEntity entity = response.body().getData();
                if (entity != null) {
                    MyJZhuJsiQIajsdnPreferences.saveInt("mobileType", entity.getMobileType());
                    MyJZhuJsiQIajsdnPreferences.saveString("phone", phoneStr);
                    MyJZhuJsiQIajsdnPreferences.saveString("ip", ip);
                    JZhuJsiQIajsdnCommonUtil.startActivity(LoginJZhuJsiQIajsdnActivity.this, HomePageJZhuJsiQIajsdnActivity.class, null);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BaseJZhuJsiQIajsdnEntity<LoginJZhuJsiQIajsdnEntity>> call, Throwable t) {
                Toast.makeText(LoginJZhuJsiQIajsdnActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
