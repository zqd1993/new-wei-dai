package com.qingsongvyrnng.mrjgndsdg.qingsojdkvui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.qingsongvyrnng.mrjgndsdg.BaseQingSongShfjAFduApp;
import com.qingsongvyrnng.mrjgndsdg.R;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvapi.BaseQingSongShfjAFduRetrofitManager;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvbase.BaseBaseQingSongShfjAFduActivity;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvbase.BaseQingSongShfjAFduObserverManager;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.BaseQingSongShfjAFduModel;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.BaseQingSongShfjAFduConfigModel;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.BaseQingSongShfjAFduLoginModel;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.BaseQingSongShfjAFduClickTextView;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.BaseQingSongShfjAFduCountDownTimerUtils;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.BaseQingSongShfjAFduSharePreferencesUtil;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.StaticBaseQingSongShfjAFduUtil;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.BaseQingSongShfjAFduStatusBarUtil;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.ToastBaseQingSongShfjAFduUtil;
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

public class LoginQingSongShfjAFduActivityBase extends BaseBaseQingSongShfjAFduActivity {
    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private BaseQingSongShfjAFduClickTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;
    private View head_sl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qing_song_sh_udj_login;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastBaseQingSongShfjAFduUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticBaseQingSongShfjAFduUtil.isMobile(mobileStr)) {
                ToastBaseQingSongShfjAFduUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastBaseQingSongShfjAFduUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastBaseQingSongShfjAFduUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(BaseQingSongShfjAFduApp.getInstance());
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
                ToastBaseQingSongShfjAFduUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticBaseQingSongShfjAFduUtil.isMobile(mobileStr)) {
                ToastBaseQingSongShfjAFduUtil.showShort("请输入正确的手机号");
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
                bundle.putString("url", BaseQingSongShfjAFduRetrofitManager.ZCXY);
                StaticBaseQingSongShfjAFduUtil.startActivity(LoginQingSongShfjAFduActivityBase.this, UserYsxyQingSongShfjAFduActivityBase.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", BaseQingSongShfjAFduRetrofitManager.YSXY);
                StaticBaseQingSongShfjAFduUtil.startActivity(LoginQingSongShfjAFduActivityBase.this, UserYsxyQingSongShfjAFduActivityBase.class, bundle);
            }
        }, "#262765");
    }

    @Override
    public void initData() {
        if (BaseQingSongShfjAFduSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        BaseQingSongShfjAFduStatusBarUtil.setTransparent(this, false);
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

    private List<BaseQingSongShfjAFduClickTextView.SpanModel> createSpanTexts() {
        List<BaseQingSongShfjAFduClickTextView.SpanModel> spanModels = new ArrayList<>();
        BaseQingSongShfjAFduClickTextView.ClickSpanModel spanModel = new BaseQingSongShfjAFduClickTextView.ClickSpanModel();
        BaseQingSongShfjAFduClickTextView.SpanModel textSpanModel = new BaseQingSongShfjAFduClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new BaseQingSongShfjAFduClickTextView.ClickSpanModel();
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
        Observable<BaseQingSongShfjAFduModel<BaseQingSongShfjAFduConfigModel>> observable = BaseQingSongShfjAFduRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQingSongShfjAFduObserverManager<BaseQingSongShfjAFduModel<BaseQingSongShfjAFduConfigModel>>() {
                    @Override
                    public void onSuccess(BaseQingSongShfjAFduModel<BaseQingSongShfjAFduConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        BaseQingSongShfjAFduConfigModel baseQingSongShfjAFduConfigModel = model.getData();
                        if (baseQingSongShfjAFduConfigModel != null) {
                            BaseQingSongShfjAFduSharePreferencesUtil.saveString("APP_MAIL", baseQingSongShfjAFduConfigModel.getAppMail());
                            isNeedVerification = "1".equals(baseQingSongShfjAFduConfigModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(baseQingSongShfjAFduConfigModel.getIsSelectLogin()));
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
        Observable<BaseQingSongShfjAFduModel<BaseQingSongShfjAFduLoginModel>> observable = BaseQingSongShfjAFduRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQingSongShfjAFduObserverManager<BaseQingSongShfjAFduModel<BaseQingSongShfjAFduLoginModel>>() {
                    @Override
                    public void onSuccess(BaseQingSongShfjAFduModel<BaseQingSongShfjAFduLoginModel> model) {
                        if (model == null) {
                            return;
                        }
                        BaseQingSongShfjAFduLoginModel baseQingSongShfjAFduLoginModel = model.getData();
                        if (baseQingSongShfjAFduLoginModel != null) {
                            BaseQingSongShfjAFduSharePreferencesUtil.saveInt("mobileType", baseQingSongShfjAFduLoginModel.getMobileType());
                            BaseQingSongShfjAFduSharePreferencesUtil.saveString("phone", mobileStr);
                            BaseQingSongShfjAFduSharePreferencesUtil.saveString("ip", ip);
                            StaticBaseQingSongShfjAFduUtil.startActivity(LoginQingSongShfjAFduActivityBase.this, MainQingSongShfjAFduActivityBase.class, null);
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
        Observable<BaseQingSongShfjAFduModel> observable = BaseQingSongShfjAFduRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQingSongShfjAFduObserverManager<BaseQingSongShfjAFduModel>() {
                    @Override
                    public void onSuccess(BaseQingSongShfjAFduModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastBaseQingSongShfjAFduUtil.showShort("发送成功");
                            BaseQingSongShfjAFduCountDownTimerUtils mBaseQingSongShfjAFduCountDownTimerUtils = new BaseQingSongShfjAFduCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mBaseQingSongShfjAFduCountDownTimerUtils.start();
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
