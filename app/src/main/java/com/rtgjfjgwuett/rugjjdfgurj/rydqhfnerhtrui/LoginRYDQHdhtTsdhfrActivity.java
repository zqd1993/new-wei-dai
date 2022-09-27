package com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.rtgjfjgwuett.rugjjdfgurj.RYDQHdhtTsdhfrApp;
import com.rtgjfjgwuett.rugjjdfgurj.R;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrapi.RYDQHdhtTsdhfrRetrofitManager;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrbase.BaseRYDQHdhtTsdhfrActivity;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrbase.RYDQHdhtTsdhfrObserverManager;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrmodel.BaseRYDQHdhtTsdhfrModel;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrmodel.ConfigRYDQHdhtTsdhfrModel;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrmodel.LoginRYDQHdhtTsdhfrModel;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.ClickRYDQHdhtTsdhfrTextView;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.CountRYDQHdhtTsdhfrDownTimerUtils;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.RYDQHdhtTsdhfrSharePreferencesUtil;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.StaticRYDQHdhtTsdhfrUtil;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.RYDQHdhtTsdhfrStatusBarUtil;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.ToastRYDQHdhtTsdhfrUtil;
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

public class LoginRYDQHdhtTsdhfrActivity extends BaseRYDQHdhtTsdhfrActivity {
    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private ClickRYDQHdhtTsdhfrTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_rydqh_fdhr_yrtehy;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastRYDQHdhtTsdhfrUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticRYDQHdhtTsdhfrUtil.isMobile(mobileStr)) {
                ToastRYDQHdhtTsdhfrUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastRYDQHdhtTsdhfrUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastRYDQHdhtTsdhfrUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(RYDQHdhtTsdhfrApp.getInstance());
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
                ToastRYDQHdhtTsdhfrUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticRYDQHdhtTsdhfrUtil.isMobile(mobileStr)) {
                ToastRYDQHdhtTsdhfrUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", RYDQHdhtTsdhfrRetrofitManager.ZCXY);
                StaticRYDQHdhtTsdhfrUtil.startActivity(LoginRYDQHdhtTsdhfrActivity.this, UserYsxyRYDQHdhtTsdhfrActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RYDQHdhtTsdhfrRetrofitManager.YSXY);
                StaticRYDQHdhtTsdhfrUtil.startActivity(LoginRYDQHdhtTsdhfrActivity.this, UserYsxyRYDQHdhtTsdhfrActivity.class, bundle);
            }
        }, "#006E4F");
    }

    @Override
    public void initData() {
        if (RYDQHdhtTsdhfrSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        RYDQHdhtTsdhfrStatusBarUtil.setTransparent(this, false);
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

    private List<ClickRYDQHdhtTsdhfrTextView.SpanModel> createSpanTexts() {
        List<ClickRYDQHdhtTsdhfrTextView.SpanModel> spanModels = new ArrayList<>();
        ClickRYDQHdhtTsdhfrTextView.ClickSpanModel spanModel = new ClickRYDQHdhtTsdhfrTextView.ClickSpanModel();
        ClickRYDQHdhtTsdhfrTextView.SpanModel textSpanModel = new ClickRYDQHdhtTsdhfrTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new ClickRYDQHdhtTsdhfrTextView.ClickSpanModel();
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
        Observable<BaseRYDQHdhtTsdhfrModel<ConfigRYDQHdhtTsdhfrModel>> observable = RYDQHdhtTsdhfrRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RYDQHdhtTsdhfrObserverManager<BaseRYDQHdhtTsdhfrModel<ConfigRYDQHdhtTsdhfrModel>>() {
                    @Override
                    public void onSuccess(BaseRYDQHdhtTsdhfrModel<ConfigRYDQHdhtTsdhfrModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigRYDQHdhtTsdhfrModel configRYDQHdhtTsdhfrModel = model.getData();
                        if (configRYDQHdhtTsdhfrModel != null) {
                            RYDQHdhtTsdhfrSharePreferencesUtil.saveString("APP_MAIL", configRYDQHdhtTsdhfrModel.getAppMail());
                            isNeedVerification = "1".equals(configRYDQHdhtTsdhfrModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(configRYDQHdhtTsdhfrModel.getIsSelectLogin()));
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
        Observable<BaseRYDQHdhtTsdhfrModel<LoginRYDQHdhtTsdhfrModel>> observable = RYDQHdhtTsdhfrRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RYDQHdhtTsdhfrObserverManager<BaseRYDQHdhtTsdhfrModel<LoginRYDQHdhtTsdhfrModel>>() {
                    @Override
                    public void onSuccess(BaseRYDQHdhtTsdhfrModel<LoginRYDQHdhtTsdhfrModel> model) {
                        if (model == null) {
                            return;
                        }
                        LoginRYDQHdhtTsdhfrModel loginRYDQHdhtTsdhfrModel = model.getData();
                        if (loginRYDQHdhtTsdhfrModel != null) {
                            RYDQHdhtTsdhfrSharePreferencesUtil.saveInt("mobileType", loginRYDQHdhtTsdhfrModel.getMobileType());
                            RYDQHdhtTsdhfrSharePreferencesUtil.saveString("phone", mobileStr);
                            RYDQHdhtTsdhfrSharePreferencesUtil.saveString("ip", ip);
                            StaticRYDQHdhtTsdhfrUtil.startActivity(LoginRYDQHdhtTsdhfrActivity.this, MainRYDQHdhtTsdhfrActivity.class, null);
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
        Observable<BaseRYDQHdhtTsdhfrModel> observable = RYDQHdhtTsdhfrRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RYDQHdhtTsdhfrObserverManager<BaseRYDQHdhtTsdhfrModel>() {
                    @Override
                    public void onSuccess(BaseRYDQHdhtTsdhfrModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastRYDQHdhtTsdhfrUtil.showShort("发送成功");
                            CountRYDQHdhtTsdhfrDownTimerUtils mCountRYDQHdhtTsdhfrDownTimerUtils = new CountRYDQHdhtTsdhfrDownTimerUtils(getVerificationTv, 60000, 1000);
                            mCountRYDQHdhtTsdhfrDownTimerUtils.start();
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
