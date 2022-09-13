package com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.sdldsjqwbaszbdskdf.dfpddfgert.R;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteapi.RongjieSfFgdfRetrofitManager;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtebase.BaseRongjieSfFgdfActivity;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtebase.RongjieSfFgdfObserverManager;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtemodel.RongjieSfFgdfBaseModel;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtemodel.RongjieSfFgdfConfigModel;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtemodel.RongjieSfFgdfLoginModel;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.ClickTextViewWeiFenQiadsfSsd;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.ToastWeiFenQiadsfSsdUtil;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.WeiFenQiadsfSsdCountDownTimerUtils;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.SharePreferencesUtilWeiFenQiadsfSsd;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.StaticWeiFenQiadsfSsdUtil;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.WeiFenQiadsfSsdStatusBarUtil;
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

public class WeiFenQiadsfSsdLoginActivity extends BaseRongjieSfFgdfActivity{

    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private ClickTextViewWeiFenQiadsfSsd loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;
    private View head_sl;

    private String mobileStr, verificationStr, ip;
    private boolean isNeedVerification;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wei_fen_qi_dfger_vjkrt_login;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastWeiFenQiadsfSsdUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticWeiFenQiadsfSsdUtil.isMobile(mobileStr)) {
                ToastWeiFenQiadsfSsdUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastWeiFenQiadsfSsdUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastWeiFenQiadsfSsdUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            rotateLoading.start();
            loadingFl.setVisibility(View.VISIBLE);
            login(mobileStr, verificationStr);
        });
        getVerificationTv.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastWeiFenQiadsfSsdUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticWeiFenQiadsfSsdUtil.isMobile(mobileStr)) {
                ToastWeiFenQiadsfSsdUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", RongjieSfFgdfRetrofitManager.ZCXY);
                StaticWeiFenQiadsfSsdUtil.startActivity(WeiFenQiadsfSsdLoginActivity.this, WeiFenQiadsfSsdUserYsxyActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RongjieSfFgdfRetrofitManager.YSXY);
                StaticWeiFenQiadsfSsdUtil.startActivity(WeiFenQiadsfSsdLoginActivity.this, WeiFenQiadsfSsdUserYsxyActivity.class, bundle);
            }
        }, "#E71C1A");
    }

    @Override
    public void initData() {
        if (SharePreferencesUtilWeiFenQiadsfSsd.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        WeiFenQiadsfSsdStatusBarUtil.setTransparent(this, false);
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

    private List<ClickTextViewWeiFenQiadsfSsd.SpanModel> createSpanTexts() {
        List<ClickTextViewWeiFenQiadsfSsd.SpanModel> spanModels = new ArrayList<>();
        ClickTextViewWeiFenQiadsfSsd.ClickSpanModel spanModel = new ClickTextViewWeiFenQiadsfSsd.ClickSpanModel();
        ClickTextViewWeiFenQiadsfSsd.SpanModel textSpanModel = new ClickTextViewWeiFenQiadsfSsd.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《用户注册协议》");
        spanModels.add(spanModel);

        spanModel = new ClickTextViewWeiFenQiadsfSsd.ClickSpanModel();
        spanModel.setStr("《隐私政策》");
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
        Observable<RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel>> observable = RongjieSfFgdfRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RongjieSfFgdfObserverManager<RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel>>() {
                    @Override
                    public void onSuccess(RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        RongjieSfFgdfConfigModel rongjieSfFgdfConfigModel = model.getData();
                        if (rongjieSfFgdfConfigModel != null) {
                            SharePreferencesUtilWeiFenQiadsfSsd.saveString("APP_MAIL", rongjieSfFgdfConfigModel.getAppMail());
                            isNeedVerification = "1".equals(rongjieSfFgdfConfigModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(rongjieSfFgdfConfigModel.getIsSelectLogin()));
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
        Observable<RongjieSfFgdfBaseModel<RongjieSfFgdfLoginModel>> observable = RongjieSfFgdfRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RongjieSfFgdfObserverManager<RongjieSfFgdfBaseModel<RongjieSfFgdfLoginModel>>() {
                    @Override
                    public void onSuccess(RongjieSfFgdfBaseModel<RongjieSfFgdfLoginModel> model) {
                        if (model == null) {
                            return;
                        }
                        RongjieSfFgdfLoginModel rongjieSfFgdfLoginModel = model.getData();
                        if (rongjieSfFgdfLoginModel != null) {
                            SharePreferencesUtilWeiFenQiadsfSsd.saveInt("mobileType", rongjieSfFgdfLoginModel.getMobileType());
                            SharePreferencesUtilWeiFenQiadsfSsd.saveString("phone", mobileStr);
                            SharePreferencesUtilWeiFenQiadsfSsd.saveString("ip", ip);
                            StaticWeiFenQiadsfSsdUtil.startActivity(WeiFenQiadsfSsdLoginActivity.this, WeiFenQiadsfSsdMainWeiFenQiadsfSsdActivity.class, null);
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
        Observable<RongjieSfFgdfBaseModel> observable = RongjieSfFgdfRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RongjieSfFgdfObserverManager<RongjieSfFgdfBaseModel>() {
                    @Override
                    public void onSuccess(RongjieSfFgdfBaseModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastWeiFenQiadsfSsdUtil.showShort("发送成功");
                            WeiFenQiadsfSsdCountDownTimerUtils mWeiFenQiadsfSsdCountDownTimerUtils = new WeiFenQiadsfSsdCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mWeiFenQiadsfSsdCountDownTimerUtils.start();
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
