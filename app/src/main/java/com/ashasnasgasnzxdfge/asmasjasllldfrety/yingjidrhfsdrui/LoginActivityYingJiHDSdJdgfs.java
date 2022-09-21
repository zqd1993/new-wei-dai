package com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ashasnasgasnzxdfge.asmasjasllldfrety.YingJiHDSdJdgfsApp;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.R;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrapi.YingJiHDSdJdgfsRetrofitManager;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrbase.YingJiHDSdJdgfsBaseActivity;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrbase.YingJiHDSdJdgfsObserverManager;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.YingJiHDSdJdgfsBaseModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.YingJiHDSdJdgfsConfigModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.LoginYingJiHDSdJdgfsModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsClickTextView;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsCountDownTimerUtils;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsSharePreferencesUtil;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.StaticCYingJiHDSdJdgfsUtil;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsStatusBarUtil;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.ToastYingJiHDSdJdgfsUtil;
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

public class LoginActivityYingJiHDSdJdgfs extends YingJiHDSdJdgfsBaseActivity {
    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private YingJiHDSdJdgfsClickTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;
    private View head_sl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_ying_ji_dh_jie_fuerty;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastYingJiHDSdJdgfsUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticCYingJiHDSdJdgfsUtil.isMobile(mobileStr)) {
                ToastYingJiHDSdJdgfsUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastYingJiHDSdJdgfsUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastYingJiHDSdJdgfsUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(YingJiHDSdJdgfsApp.getInstance());
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
                ToastYingJiHDSdJdgfsUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticCYingJiHDSdJdgfsUtil.isMobile(mobileStr)) {
                ToastYingJiHDSdJdgfsUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        head_sl.setOnClickListener(v -> {
            getConfig();
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", YingJiHDSdJdgfsRetrofitManager.ZCXY);
                StaticCYingJiHDSdJdgfsUtil.startActivity(LoginActivityYingJiHDSdJdgfs.this, UserYsxyActivityYingJiHDSdJdgfs.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", YingJiHDSdJdgfsRetrofitManager.YSXY);
                StaticCYingJiHDSdJdgfsUtil.startActivity(LoginActivityYingJiHDSdJdgfs.this, UserYsxyActivityYingJiHDSdJdgfs.class, bundle);
            }
        }, "#F74628");
    }

    @Override
    public void initData() {
        if (YingJiHDSdJdgfsSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        YingJiHDSdJdgfsStatusBarUtil.setTransparent(this, false);
        mobileEt = findViewById(R.id.mobile_et);
        verificationEt = findViewById(R.id.verification_et);
        getVerificationTv = findViewById(R.id.get_verification_tv);
        loginBtn = findViewById(R.id.login_btn);
        remindCb = findViewById(R.id.remind_cb);
        loginRemindTv = findViewById(R.id.login_remind_tv);
        rotateLoading = findViewById(R.id.rotate_loading);
        loadingFl = findViewById(R.id.loading_fl);
        verificationLl = findViewById(R.id.verification_ll);
        head_sl = findViewById(R.id.head_sl);
        sendRequestWithOkHttp();
        getConfig();
    }

    private List<YingJiHDSdJdgfsClickTextView.SpanModel> createSpanTexts() {
        List<YingJiHDSdJdgfsClickTextView.SpanModel> spanModels = new ArrayList<>();
        YingJiHDSdJdgfsClickTextView.ClickSpanModel spanModel = new YingJiHDSdJdgfsClickTextView.ClickSpanModel();
        YingJiHDSdJdgfsClickTextView.SpanModel textSpanModel = new YingJiHDSdJdgfsClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new YingJiHDSdJdgfsClickTextView.ClickSpanModel();
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
        Observable<YingJiHDSdJdgfsBaseModel<YingJiHDSdJdgfsConfigModel>> observable = YingJiHDSdJdgfsRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new YingJiHDSdJdgfsObserverManager<YingJiHDSdJdgfsBaseModel<YingJiHDSdJdgfsConfigModel>>() {
                    @Override
                    public void onSuccess(YingJiHDSdJdgfsBaseModel<YingJiHDSdJdgfsConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        YingJiHDSdJdgfsConfigModel configCaiJieTongYshVdjrytModel = model.getData();
                        if (configCaiJieTongYshVdjrytModel != null) {
                            YingJiHDSdJdgfsSharePreferencesUtil.saveString("APP_MAIL", configCaiJieTongYshVdjrytModel.getAppMail());
                            isNeedVerification = "1".equals(configCaiJieTongYshVdjrytModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(configCaiJieTongYshVdjrytModel.getIsSelectLogin()));
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
        Observable<YingJiHDSdJdgfsBaseModel<LoginYingJiHDSdJdgfsModel>> observable = YingJiHDSdJdgfsRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new YingJiHDSdJdgfsObserverManager<YingJiHDSdJdgfsBaseModel<LoginYingJiHDSdJdgfsModel>>() {
                    @Override
                    public void onSuccess(YingJiHDSdJdgfsBaseModel<LoginYingJiHDSdJdgfsModel> model) {
                        if (model == null) {
                            return;
                        }
                        LoginYingJiHDSdJdgfsModel loginYingJiHDSdJdgfsModel = model.getData();
                        if (loginYingJiHDSdJdgfsModel != null) {
                            YingJiHDSdJdgfsSharePreferencesUtil.saveInt("mobileType", loginYingJiHDSdJdgfsModel.getMobileType());
                            YingJiHDSdJdgfsSharePreferencesUtil.saveString("phone", mobileStr);
                            YingJiHDSdJdgfsSharePreferencesUtil.saveString("ip", ip);
                            StaticCYingJiHDSdJdgfsUtil.startActivity(LoginActivityYingJiHDSdJdgfs.this, MainActivityYingJiHDSdJdgfs.class, null);
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
        Observable<YingJiHDSdJdgfsBaseModel> observable = YingJiHDSdJdgfsRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new YingJiHDSdJdgfsObserverManager<YingJiHDSdJdgfsBaseModel>() {
                    @Override
                    public void onSuccess(YingJiHDSdJdgfsBaseModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastYingJiHDSdJdgfsUtil.showShort("发送成功");
                            YingJiHDSdJdgfsCountDownTimerUtils mYingJiHDSdJdgfsCountDownTimerUtils = new YingJiHDSdJdgfsCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mYingJiHDSdJdgfsCountDownTimerUtils.start();
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
