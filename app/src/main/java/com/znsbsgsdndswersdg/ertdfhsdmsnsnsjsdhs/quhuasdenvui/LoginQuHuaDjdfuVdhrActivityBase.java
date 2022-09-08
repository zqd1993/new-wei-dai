package com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.BaseQuHuaDjdfuVdhrApp;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.R;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvapi.BaseQuHuaDjdfuVdhrRetrofitManager;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvbase.BaseBaseQuHuaDjdfuVdhrActivity;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvbase.BaseQuHuaDjdfuVdhrObserverManager;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.BaseQuHuaDjdfuVdhrModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.BaseQuHuaDjdfuVdhrConfigModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.BaseQuHuaDjdfuVdhrLoginModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrClickTextView;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrCountDownTimerUtils;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrSharePreferencesUtil;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.StaticBaseQuHuaDjdfuVdhrUtil;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrStatusBarUtil;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.ToastBaseQuHuaDjdfuVdhrUtil;
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

public class LoginQuHuaDjdfuVdhrActivityBase extends BaseBaseQuHuaDjdfuVdhrActivity {
    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private BaseQuHuaDjdfuVdhrClickTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;
    private View head_sl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qu_hua_hua_erf_engh_login;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastBaseQuHuaDjdfuVdhrUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticBaseQuHuaDjdfuVdhrUtil.isMobile(mobileStr)) {
                ToastBaseQuHuaDjdfuVdhrUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastBaseQuHuaDjdfuVdhrUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastBaseQuHuaDjdfuVdhrUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(BaseQuHuaDjdfuVdhrApp.getInstance());
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
                ToastBaseQuHuaDjdfuVdhrUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticBaseQuHuaDjdfuVdhrUtil.isMobile(mobileStr)) {
                ToastBaseQuHuaDjdfuVdhrUtil.showShort("请输入正确的手机号");
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
                bundle.putString("url", BaseQuHuaDjdfuVdhrRetrofitManager.ZCXY);
                StaticBaseQuHuaDjdfuVdhrUtil.startActivity(LoginQuHuaDjdfuVdhrActivityBase.this, UserYsxyQuHuaDjdfuVdhrActivityBase.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", BaseQuHuaDjdfuVdhrRetrofitManager.YSXY);
                StaticBaseQuHuaDjdfuVdhrUtil.startActivity(LoginQuHuaDjdfuVdhrActivityBase.this, UserYsxyQuHuaDjdfuVdhrActivityBase.class, bundle);
            }
        }, "#262765");
    }

    @Override
    public void initData() {
        if (BaseQuHuaDjdfuVdhrSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        BaseQuHuaDjdfuVdhrStatusBarUtil.setTransparent(this, false);
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

    private List<BaseQuHuaDjdfuVdhrClickTextView.SpanModel> createSpanTexts() {
        List<BaseQuHuaDjdfuVdhrClickTextView.SpanModel> spanModels = new ArrayList<>();
        BaseQuHuaDjdfuVdhrClickTextView.ClickSpanModel spanModel = new BaseQuHuaDjdfuVdhrClickTextView.ClickSpanModel();
        BaseQuHuaDjdfuVdhrClickTextView.SpanModel textSpanModel = new BaseQuHuaDjdfuVdhrClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new BaseQuHuaDjdfuVdhrClickTextView.ClickSpanModel();
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
        Observable<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrConfigModel>> observable = BaseQuHuaDjdfuVdhrRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQuHuaDjdfuVdhrObserverManager<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrConfigModel>>() {
                    @Override
                    public void onSuccess(BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        BaseQuHuaDjdfuVdhrConfigModel baseQuHuaDjdfuVdhrConfigModel = model.getData();
                        if (baseQuHuaDjdfuVdhrConfigModel != null) {
                            BaseQuHuaDjdfuVdhrSharePreferencesUtil.saveString("APP_MAIL", baseQuHuaDjdfuVdhrConfigModel.getAppMail());
                            isNeedVerification = "1".equals(baseQuHuaDjdfuVdhrConfigModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(baseQuHuaDjdfuVdhrConfigModel.getIsSelectLogin()));
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
        Observable<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrLoginModel>> observable = BaseQuHuaDjdfuVdhrRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQuHuaDjdfuVdhrObserverManager<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrLoginModel>>() {
                    @Override
                    public void onSuccess(BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrLoginModel> model) {
                        if (model == null) {
                            return;
                        }
                        BaseQuHuaDjdfuVdhrLoginModel baseQuHuaDjdfuVdhrLoginModel = model.getData();
                        if (baseQuHuaDjdfuVdhrLoginModel != null) {
                            BaseQuHuaDjdfuVdhrSharePreferencesUtil.saveInt("mobileType", baseQuHuaDjdfuVdhrLoginModel.getMobileType());
                            BaseQuHuaDjdfuVdhrSharePreferencesUtil.saveString("phone", mobileStr);
                            BaseQuHuaDjdfuVdhrSharePreferencesUtil.saveString("ip", ip);
                            StaticBaseQuHuaDjdfuVdhrUtil.startActivity(LoginQuHuaDjdfuVdhrActivityBase.this, MainQuHuaDjdfuVdhrActivityBase.class, null);
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
        Observable<BaseQuHuaDjdfuVdhrModel> observable = BaseQuHuaDjdfuVdhrRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQuHuaDjdfuVdhrObserverManager<BaseQuHuaDjdfuVdhrModel>() {
                    @Override
                    public void onSuccess(BaseQuHuaDjdfuVdhrModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastBaseQuHuaDjdfuVdhrUtil.showShort("发送成功");
                            BaseQuHuaDjdfuVdhrCountDownTimerUtils mBaseQuHuaDjdfuVdhrCountDownTimerUtils = new BaseQuHuaDjdfuVdhrCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mBaseQuHuaDjdfuVdhrCountDownTimerUtils.start();
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
