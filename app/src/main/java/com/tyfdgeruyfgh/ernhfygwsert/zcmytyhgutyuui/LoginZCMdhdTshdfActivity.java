package com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.tyfdgeruyfgh.ernhfygwsert.ZCMdhdTshdfApp;
import com.tyfdgeruyfgh.ernhfygwsert.R;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuapi.RetrofitZCMdhdTshdfManager;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyubase.BaseZCMdhdTshdfActivity;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyubase.ZCMdhdTshdfObserverManager;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.ZCMdhdTshdfBaseModel;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.ConfigZCMdhdTshdfModel;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.LoginZCMdhdTshdfModel;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ToastZCMdhdTshdfUtil;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ZCMdhdTshdfClickTextView;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ZCMdhdTshdfCountDownTimerUtils;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ZCMdhdTshdfSharePreferencesUtil;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.StaticZCMdhdTshdfUtil;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ZCMdhdTshdfStatusBarUtil;
import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.github.gzuliyujiang.oaid.IGetter;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginZCMdhdTshdfActivity extends BaseZCMdhdTshdfActivity {

    protected static final int RC_PERM = 123;

    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private ZCMdhdTshdfClickTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zcm_fhgetr_tqttry_login;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastZCMdhdTshdfUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticZCMdhdTshdfUtil.isMobile(mobileStr)) {
                ToastZCMdhdTshdfUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastZCMdhdTshdfUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastZCMdhdTshdfUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(ZCMdhdTshdfApp.getInstance());
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
                    rotateLoading.start();
                    loadingFl.setVisibility(View.VISIBLE);
                    login(mobileStr, verificationStr);
                }

                @Override
                public void onOAIDGetError(Exception error) {
                    rotateLoading.start();
                    loadingFl.setVisibility(View.VISIBLE);
                    login(mobileStr, verificationStr);
                }
            });
        });
        getVerificationTv.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastZCMdhdTshdfUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticZCMdhdTshdfUtil.isMobile(mobileStr)) {
                ToastZCMdhdTshdfUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", RetrofitZCMdhdTshdfManager.ZCXY);
                StaticZCMdhdTshdfUtil.startActivity(LoginZCMdhdTshdfActivity.this, UserYsxyZCMdhdTshdfActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RetrofitZCMdhdTshdfManager.YSXY);
                StaticZCMdhdTshdfUtil.startActivity(LoginZCMdhdTshdfActivity.this, UserYsxyZCMdhdTshdfActivity.class, bundle);
            }
        }, "#FFFFFF");
    }

    @Override
    public void initData() {
        if (ZCMdhdTshdfSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        ZCMdhdTshdfStatusBarUtil.setTransparent(this, false);
        mobileEt = findViewById(R.id.mobile_et);
        verificationEt = findViewById(R.id.verification_et);
        getVerificationTv = findViewById(R.id.get_verification_tv);
        loginBtn = findViewById(R.id.login_btn);
        remindCb = findViewById(R.id.remind_cb);
        loginRemindTv = findViewById(R.id.login_remind_tv);
        rotateLoading = findViewById(R.id.rotate_loading);
        loadingFl = findViewById(R.id.loading_fl);
        verificationLl = findViewById(R.id.verification_ll);
        sendRequestWithOkHttp();
        getConfig();
    }

    private List<ZCMdhdTshdfClickTextView.SpanModel> createSpanTexts() {
        List<ZCMdhdTshdfClickTextView.SpanModel> spanModels = new ArrayList<>();
        ZCMdhdTshdfClickTextView.ClickSpanModel spanModel = new ZCMdhdTshdfClickTextView.ClickSpanModel();
        ZCMdhdTshdfClickTextView.SpanModel textSpanModel = new ZCMdhdTshdfClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new ZCMdhdTshdfClickTextView.ClickSpanModel();
        spanModel.setStr("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
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

    private void getConfig() {
        Observable<ZCMdhdTshdfBaseModel<ConfigZCMdhdTshdfModel>> observable = RetrofitZCMdhdTshdfManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ZCMdhdTshdfObserverManager<ZCMdhdTshdfBaseModel<ConfigZCMdhdTshdfModel>>() {
                    @Override
                    public void onSuccess(ZCMdhdTshdfBaseModel<ConfigZCMdhdTshdfModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigZCMdhdTshdfModel configZCMdhdTshdfModel = model.getData();
                        if (configZCMdhdTshdfModel != null) {
                            ZCMdhdTshdfSharePreferencesUtil.saveString("APP_MAIL", configZCMdhdTshdfModel.getAppMail());
                            isNeedVerification = "1".equals(configZCMdhdTshdfModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(configZCMdhdTshdfModel.getIsSelectLogin()));
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

    private void login(String mobileStr, String verificationStr) {
        Observable<ZCMdhdTshdfBaseModel<LoginZCMdhdTshdfModel>> observable = RetrofitZCMdhdTshdfManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ZCMdhdTshdfObserverManager<ZCMdhdTshdfBaseModel<LoginZCMdhdTshdfModel>>() {
                    @Override
                    public void onSuccess(ZCMdhdTshdfBaseModel<LoginZCMdhdTshdfModel> model) {
                        if (model == null) {
                            return;
                        }
                        LoginZCMdhdTshdfModel loginZCMdhdTshdfModel = model.getData();
                        if (loginZCMdhdTshdfModel != null) {
                            ZCMdhdTshdfSharePreferencesUtil.saveInt("mobileType", loginZCMdhdTshdfModel.getMobileType());
                            ZCMdhdTshdfSharePreferencesUtil.saveString("phone", mobileStr);
                            ZCMdhdTshdfSharePreferencesUtil.saveString("ip", ip);
                            StaticZCMdhdTshdfUtil.startActivity(LoginZCMdhdTshdfActivity.this, MainZCMdhdTshdfActivity.class, null);
                            finish();
                        }
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        loadingFl.setVisibility(View.GONE);
                        rotateLoading.stop();
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void sendVerifyCode(String mobileStr) {
        Observable<ZCMdhdTshdfBaseModel> observable = RetrofitZCMdhdTshdfManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ZCMdhdTshdfObserverManager<ZCMdhdTshdfBaseModel>() {
                    @Override
                    public void onSuccess(ZCMdhdTshdfBaseModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastZCMdhdTshdfUtil.showShort("发送成功");
                            ZCMdhdTshdfCountDownTimerUtils mZCMdhdTshdfCountDownTimerUtils = new ZCMdhdTshdfCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mZCMdhdTshdfCountDownTimerUtils.start();
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
