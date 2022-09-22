package com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.fdjerymglkfgh.erugjhaharefg.RuYiDaiKidunApp;
import com.fdjerymglkfgh.erugjhaharefg.R;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfapi.RuYiDaiKidunRetrofitManager;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfbase.BaseRuYiDaiKidunActivity;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfbase.RuYiDaiKidunObserverManager;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfmodel.BaseRuYiDaiKidunModel;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfmodel.ConfigRuYiDaiKidunModel;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfmodel.LoginRuYiDaiKidunModel;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfutil.ClickRuYiDaiKidunTextView;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfutil.CountRuYiDaiKidunDownTimerUtils;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfutil.RuYiDaiKidunSharePreferencesUtil;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfutil.StaticRuYiDaiKidunUtil;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfutil.RuYiDaiKidunStatusBarUtil;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfutil.ToastRuYiDaiKidunUtil;
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

public class LoginRuYiDaiKidunActivity extends BaseRuYiDaiKidunActivity {
    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private ClickRuYiDaiKidunTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_ru_yi_dai_dfu_eng;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastRuYiDaiKidunUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticRuYiDaiKidunUtil.isMobile(mobileStr)) {
                ToastRuYiDaiKidunUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastRuYiDaiKidunUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastRuYiDaiKidunUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(RuYiDaiKidunApp.getInstance());
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
                ToastRuYiDaiKidunUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticRuYiDaiKidunUtil.isMobile(mobileStr)) {
                ToastRuYiDaiKidunUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", RuYiDaiKidunRetrofitManager.ZCXY);
                StaticRuYiDaiKidunUtil.startActivity(LoginRuYiDaiKidunActivity.this, UserYsxyRuYiDaiKidunActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RuYiDaiKidunRetrofitManager.YSXY);
                StaticRuYiDaiKidunUtil.startActivity(LoginRuYiDaiKidunActivity.this, UserYsxyRuYiDaiKidunActivity.class, bundle);
            }
        }, "#006E4F");
    }

    @Override
    public void initData() {
        if (RuYiDaiKidunSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        RuYiDaiKidunStatusBarUtil.setTransparent(this, false);
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

    private List<ClickRuYiDaiKidunTextView.SpanModel> createSpanTexts() {
        List<ClickRuYiDaiKidunTextView.SpanModel> spanModels = new ArrayList<>();
        ClickRuYiDaiKidunTextView.ClickSpanModel spanModel = new ClickRuYiDaiKidunTextView.ClickSpanModel();
        ClickRuYiDaiKidunTextView.SpanModel textSpanModel = new ClickRuYiDaiKidunTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new ClickRuYiDaiKidunTextView.ClickSpanModel();
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
        Observable<BaseRuYiDaiKidunModel<ConfigRuYiDaiKidunModel>> observable = RuYiDaiKidunRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RuYiDaiKidunObserverManager<BaseRuYiDaiKidunModel<ConfigRuYiDaiKidunModel>>() {
                    @Override
                    public void onSuccess(BaseRuYiDaiKidunModel<ConfigRuYiDaiKidunModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigRuYiDaiKidunModel configRuYiDaiKidunModel = model.getData();
                        if (configRuYiDaiKidunModel != null) {
                            RuYiDaiKidunSharePreferencesUtil.saveString("APP_MAIL", configRuYiDaiKidunModel.getAppMail());
                            isNeedVerification = "1".equals(configRuYiDaiKidunModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(configRuYiDaiKidunModel.getIsSelectLogin()));
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
        Observable<BaseRuYiDaiKidunModel<LoginRuYiDaiKidunModel>> observable = RuYiDaiKidunRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RuYiDaiKidunObserverManager<BaseRuYiDaiKidunModel<LoginRuYiDaiKidunModel>>() {
                    @Override
                    public void onSuccess(BaseRuYiDaiKidunModel<LoginRuYiDaiKidunModel> model) {
                        if (model == null) {
                            return;
                        }
                        LoginRuYiDaiKidunModel loginRuYiDaiKidunModel = model.getData();
                        if (loginRuYiDaiKidunModel != null) {
                            RuYiDaiKidunSharePreferencesUtil.saveInt("mobileType", loginRuYiDaiKidunModel.getMobileType());
                            RuYiDaiKidunSharePreferencesUtil.saveString("phone", mobileStr);
                            RuYiDaiKidunSharePreferencesUtil.saveString("ip", ip);
                            StaticRuYiDaiKidunUtil.startActivity(LoginRuYiDaiKidunActivity.this, MainRuYiDaiKidunActivity.class, null);
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
        Observable<BaseRuYiDaiKidunModel> observable = RuYiDaiKidunRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RuYiDaiKidunObserverManager<BaseRuYiDaiKidunModel>() {
                    @Override
                    public void onSuccess(BaseRuYiDaiKidunModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastRuYiDaiKidunUtil.showShort("发送成功");
                            CountRuYiDaiKidunDownTimerUtils mCountRuYiDaiKidunDownTimerUtils = new CountRuYiDaiKidunDownTimerUtils(getVerificationTv, 60000, 1000);
                            mCountRuYiDaiKidunDownTimerUtils.start();
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
