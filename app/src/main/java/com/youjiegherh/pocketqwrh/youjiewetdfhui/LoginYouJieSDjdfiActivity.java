package com.youjiegherh.pocketqwrh.youjiewetdfhui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.youjiegherh.pocketqwrh.YouJieSDjdfiApp;
import com.youjiegherh.pocketqwrh.R;
import com.youjiegherh.pocketqwrh.youjiewetdfhapi.NewCodeXiaoNiuKuaiRetrofitManager;
import com.youjiegherh.pocketqwrh.youjiewetdfhbase.BaseNewCodeXiaoNiuKuaiActivity;
import com.youjiegherh.pocketqwrh.youjiewetdfhbase.NewCodeXiaoNiuKuaiObserverManager;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.BaseYouJieSDjdfiModel;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.ConfigYouJieSDjdfiModel;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.YouJieSDjdfiLoginModel;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.ToastYouJieSDjdfiUtil;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.YouJieSDjdfiClickTextView;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.CountDownTimerUtilsYouJieSDjdfi;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.YouJieSDjdfiSharePreferencesUtil;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.StaticYouJieSDjdfiUtil;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.YouJieSDjdfiStatusBarUtil;
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

public class LoginYouJieSDjdfiActivity extends BaseNewCodeXiaoNiuKuaiActivity{

    protected static final int RC_PERM = 123;

    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private YouJieSDjdfiClickTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_you_jie_iejbvr_login;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastYouJieSDjdfiUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticYouJieSDjdfiUtil.isMobile(mobileStr)) {
                ToastYouJieSDjdfiUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastYouJieSDjdfiUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastYouJieSDjdfiUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(YouJieSDjdfiApp.getInstance());
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
                ToastYouJieSDjdfiUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticYouJieSDjdfiUtil.isMobile(mobileStr)) {
                ToastYouJieSDjdfiUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", NewCodeXiaoNiuKuaiRetrofitManager.ZCXY);
                StaticYouJieSDjdfiUtil.startActivity(LoginYouJieSDjdfiActivity.this, YouJieSDjdfiUserYsxyActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", NewCodeXiaoNiuKuaiRetrofitManager.YSXY);
                StaticYouJieSDjdfiUtil.startActivity(LoginYouJieSDjdfiActivity.this, YouJieSDjdfiUserYsxyActivity.class, bundle);
            }
        }, "#F4C580");
    }
    @Override
    public void initData() {
        if (YouJieSDjdfiSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        YouJieSDjdfiStatusBarUtil.setTransparent(this, false);
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

    private List<YouJieSDjdfiClickTextView.SpanModel> createSpanTexts() {
        List<YouJieSDjdfiClickTextView.SpanModel> spanModels = new ArrayList<>();
        YouJieSDjdfiClickTextView.ClickSpanModel spanModel = new YouJieSDjdfiClickTextView.ClickSpanModel();
        YouJieSDjdfiClickTextView.SpanModel textSpanModel = new YouJieSDjdfiClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new YouJieSDjdfiClickTextView.ClickSpanModel();
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
        Observable<BaseYouJieSDjdfiModel<ConfigYouJieSDjdfiModel>> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseYouJieSDjdfiModel<ConfigYouJieSDjdfiModel>>() {
                    @Override
                    public void onSuccess(BaseYouJieSDjdfiModel<ConfigYouJieSDjdfiModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigYouJieSDjdfiModel configYouJieSDjdfiModel = model.getData();
                        if (configYouJieSDjdfiModel != null) {
                            YouJieSDjdfiSharePreferencesUtil.saveString("APP_MAIL", configYouJieSDjdfiModel.getAppMail());
                            isNeedVerification = "1".equals(configYouJieSDjdfiModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(configYouJieSDjdfiModel.getIsSelectLogin()));
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
        Observable<BaseYouJieSDjdfiModel<YouJieSDjdfiLoginModel>> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseYouJieSDjdfiModel<YouJieSDjdfiLoginModel>>() {
                    @Override
                    public void onSuccess(BaseYouJieSDjdfiModel<YouJieSDjdfiLoginModel> model) {
                        if (model == null) {
                            return;
                        }
                        YouJieSDjdfiLoginModel youJieSDjdfiLoginModel = model.getData();
                        if (youJieSDjdfiLoginModel != null) {
                            YouJieSDjdfiSharePreferencesUtil.saveInt("mobileType", youJieSDjdfiLoginModel.getMobileType());
                            YouJieSDjdfiSharePreferencesUtil.saveString("phone", mobileStr);
                            YouJieSDjdfiSharePreferencesUtil.saveString("ip", ip);
                            StaticYouJieSDjdfiUtil.startActivity(LoginYouJieSDjdfiActivity.this, MainYouJieSDjdfiActivity.class, null);
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
        Observable<BaseYouJieSDjdfiModel> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseYouJieSDjdfiModel>() {
                    @Override
                    public void onSuccess(BaseYouJieSDjdfiModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastYouJieSDjdfiUtil.showShort("发送成功");
                            CountDownTimerUtilsYouJieSDjdfi mCountDownTimerUtilsYouJieSDjdfi = new CountDownTimerUtilsYouJieSDjdfi(getVerificationTv, 60000, 1000);
                            mCountDownTimerUtilsYouJieSDjdfi.start();
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
