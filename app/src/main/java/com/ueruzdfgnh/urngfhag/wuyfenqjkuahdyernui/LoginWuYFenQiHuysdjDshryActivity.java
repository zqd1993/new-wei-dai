package com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ueruzdfgnh.urngfhag.WuYFenQiHuysdjDshryApp;
import com.ueruzdfgnh.urngfhag.R;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernapi.RetrofitWuYFenQiHuysdjDshryManager;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernbase.BaseWuYFenQiHuysdjDshryActivity;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernbase.WuYFenQiHuysdjDshryObserverManager;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.WuYFenQiHuysdjDshryBaseModel;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.ConfigWuYFenQiHuysdjDshryModel;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.LoginWuYFenQiHuysdjDshryModel;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.ToastWuYFenQiHuysdjDshryUtil;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.WuYFenQiHuysdjDshryClickTextView;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.WuYFenQiHuysdjDshryCountDownTimerUtils;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.WuYFenQiHuysdjDshrySharePreferencesUtil;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.StaticWuYFenQiHuysdjDshryUtil;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.WuYFenQiHuysdjDshryStatusBarUtil;
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

public class LoginWuYFenQiHuysdjDshryActivity extends BaseWuYFenQiHuysdjDshryActivity {

    protected static final int RC_PERM = 123;

    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private WuYFenQiHuysdjDshryClickTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wu_yfen_qijai_dfjrt_login;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastWuYFenQiHuysdjDshryUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticWuYFenQiHuysdjDshryUtil.isMobile(mobileStr)) {
                ToastWuYFenQiHuysdjDshryUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastWuYFenQiHuysdjDshryUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastWuYFenQiHuysdjDshryUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(WuYFenQiHuysdjDshryApp.getInstance());
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
                ToastWuYFenQiHuysdjDshryUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticWuYFenQiHuysdjDshryUtil.isMobile(mobileStr)) {
                ToastWuYFenQiHuysdjDshryUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", RetrofitWuYFenQiHuysdjDshryManager.ZCXY);
                StaticWuYFenQiHuysdjDshryUtil.startActivity(LoginWuYFenQiHuysdjDshryActivity.this, UserYsxyWuYFenQiHuysdjDshryActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RetrofitWuYFenQiHuysdjDshryManager.YSXY);
                StaticWuYFenQiHuysdjDshryUtil.startActivity(LoginWuYFenQiHuysdjDshryActivity.this, UserYsxyWuYFenQiHuysdjDshryActivity.class, bundle);
            }
        }, "#FFFFFF");
    }

    @Override
    public void initData() {
        if (WuYFenQiHuysdjDshrySharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        WuYFenQiHuysdjDshryStatusBarUtil.setTransparent(this, false);
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

    private List<WuYFenQiHuysdjDshryClickTextView.SpanModel> createSpanTexts() {
        List<WuYFenQiHuysdjDshryClickTextView.SpanModel> spanModels = new ArrayList<>();
        WuYFenQiHuysdjDshryClickTextView.ClickSpanModel spanModel = new WuYFenQiHuysdjDshryClickTextView.ClickSpanModel();
        WuYFenQiHuysdjDshryClickTextView.SpanModel textSpanModel = new WuYFenQiHuysdjDshryClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new WuYFenQiHuysdjDshryClickTextView.ClickSpanModel();
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
        Observable<WuYFenQiHuysdjDshryBaseModel<ConfigWuYFenQiHuysdjDshryModel>> observable = RetrofitWuYFenQiHuysdjDshryManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new WuYFenQiHuysdjDshryObserverManager<WuYFenQiHuysdjDshryBaseModel<ConfigWuYFenQiHuysdjDshryModel>>() {
                    @Override
                    public void onSuccess(WuYFenQiHuysdjDshryBaseModel<ConfigWuYFenQiHuysdjDshryModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigWuYFenQiHuysdjDshryModel configWuYFenQiHuysdjDshryModel = model.getData();
                        if (configWuYFenQiHuysdjDshryModel != null) {
                            WuYFenQiHuysdjDshrySharePreferencesUtil.saveString("APP_MAIL", configWuYFenQiHuysdjDshryModel.getAppMail());
                            isNeedVerification = "1".equals(configWuYFenQiHuysdjDshryModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(configWuYFenQiHuysdjDshryModel.getIsSelectLogin()));
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
        Observable<WuYFenQiHuysdjDshryBaseModel<LoginWuYFenQiHuysdjDshryModel>> observable = RetrofitWuYFenQiHuysdjDshryManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new WuYFenQiHuysdjDshryObserverManager<WuYFenQiHuysdjDshryBaseModel<LoginWuYFenQiHuysdjDshryModel>>() {
                    @Override
                    public void onSuccess(WuYFenQiHuysdjDshryBaseModel<LoginWuYFenQiHuysdjDshryModel> model) {
                        if (model == null) {
                            return;
                        }
                        LoginWuYFenQiHuysdjDshryModel loginWuYFenQiHuysdjDshryModel = model.getData();
                        if (loginWuYFenQiHuysdjDshryModel != null) {
                            WuYFenQiHuysdjDshrySharePreferencesUtil.saveInt("mobileType", loginWuYFenQiHuysdjDshryModel.getMobileType());
                            WuYFenQiHuysdjDshrySharePreferencesUtil.saveString("phone", mobileStr);
                            WuYFenQiHuysdjDshrySharePreferencesUtil.saveString("ip", ip);
                            StaticWuYFenQiHuysdjDshryUtil.startActivity(LoginWuYFenQiHuysdjDshryActivity.this, MaintWuYFenQiHuysdjDshryActivity.class, null);
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
        Observable<WuYFenQiHuysdjDshryBaseModel> observable = RetrofitWuYFenQiHuysdjDshryManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new WuYFenQiHuysdjDshryObserverManager<WuYFenQiHuysdjDshryBaseModel>() {
                    @Override
                    public void onSuccess(WuYFenQiHuysdjDshryBaseModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastWuYFenQiHuysdjDshryUtil.showShort("发送成功");
                            WuYFenQiHuysdjDshryCountDownTimerUtils mWuYFenQiHuysdjDshryCountDownTimerUtils = new WuYFenQiHuysdjDshryCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mWuYFenQiHuysdjDshryCountDownTimerUtils.start();
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
