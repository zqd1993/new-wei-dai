package com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyactivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.MeiJFenQiDdfgjRfdfgApp;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.R;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyapi.MeiJFenQiDdfgjRfdfgRetrofitManager;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertybase.BaseMeiJFenQiDdfgjRfdfgActivity;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertybase.MeiJFenQiDdfgjRfdfgObserverManager;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.BaseMeiJFenQiDdfgjRfdfgModel;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.MeiJFenQiDdfgjRfdfgConfigModel;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.MeiJFenQiDdfgjRfdfgLoginModel;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.MeiJFenQiDdfgjRfdfgClickTextView;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.MeiJFenQiDdfgjRfdfgCountDownTimerUtils;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.MeiJFenQiDdfgjRfdfgSharePreferencesUtil;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.StaticMeiJFenQiDdfgjRfdfgUtil;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.MeiJFenQiDdfgjRfdfgStatusBarUtil;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.ToastMeiJFenQiDdfgjRfdfgUtil;
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

public class LoginMeiJFenQiDdfgjRfdfgActivity extends BaseMeiJFenQiDdfgjRfdfgActivity {
    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private MeiJFenQiDdfgjRfdfgClickTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;
    private View head_sl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_mei_jie_sfgh_ewyfhg;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastMeiJFenQiDdfgjRfdfgUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticMeiJFenQiDdfgjRfdfgUtil.isMobile(mobileStr)) {
                ToastMeiJFenQiDdfgjRfdfgUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastMeiJFenQiDdfgjRfdfgUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastMeiJFenQiDdfgjRfdfgUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(MeiJFenQiDdfgjRfdfgApp.getInstance());
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
                ToastMeiJFenQiDdfgjRfdfgUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticMeiJFenQiDdfgjRfdfgUtil.isMobile(mobileStr)) {
                ToastMeiJFenQiDdfgjRfdfgUtil.showShort("请输入正确的手机号");
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
                bundle.putString("url", MeiJFenQiDdfgjRfdfgRetrofitManager.ZCXY);
                StaticMeiJFenQiDdfgjRfdfgUtil.startActivity(LoginMeiJFenQiDdfgjRfdfgActivity.this, UserYsxyMeiJFenQiDdfgjRfdfgActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", MeiJFenQiDdfgjRfdfgRetrofitManager.YSXY);
                StaticMeiJFenQiDdfgjRfdfgUtil.startActivity(LoginMeiJFenQiDdfgjRfdfgActivity.this, UserYsxyMeiJFenQiDdfgjRfdfgActivity.class, bundle);
            }
        }, "#ffffff");
    }

    @Override
    public void initData() {
        if (MeiJFenQiDdfgjRfdfgSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        MeiJFenQiDdfgjRfdfgStatusBarUtil.setTransparent(this, false);
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

    private List<MeiJFenQiDdfgjRfdfgClickTextView.SpanModel> createSpanTexts() {
        List<MeiJFenQiDdfgjRfdfgClickTextView.SpanModel> spanModels = new ArrayList<>();
        MeiJFenQiDdfgjRfdfgClickTextView.ClickSpanModel spanModel = new MeiJFenQiDdfgjRfdfgClickTextView.ClickSpanModel();
        MeiJFenQiDdfgjRfdfgClickTextView.SpanModel textSpanModel = new MeiJFenQiDdfgjRfdfgClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new MeiJFenQiDdfgjRfdfgClickTextView.ClickSpanModel();
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
        Observable<BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgConfigModel>> observable = MeiJFenQiDdfgjRfdfgRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MeiJFenQiDdfgjRfdfgObserverManager<BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgConfigModel>>() {
                    @Override
                    public void onSuccess(BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        MeiJFenQiDdfgjRfdfgConfigModel meiJFenQiDdfgjRfdfgConfigModel = model.getData();
                        if (meiJFenQiDdfgjRfdfgConfigModel != null) {
                            MeiJFenQiDdfgjRfdfgSharePreferencesUtil.saveString("APP_MAIL", meiJFenQiDdfgjRfdfgConfigModel.getAppMail());
                            isNeedVerification = "1".equals(meiJFenQiDdfgjRfdfgConfigModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(meiJFenQiDdfgjRfdfgConfigModel.getIsSelectLogin()));
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
        Observable<BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgLoginModel>> observable = MeiJFenQiDdfgjRfdfgRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MeiJFenQiDdfgjRfdfgObserverManager<BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgLoginModel>>() {
                    @Override
                    public void onSuccess(BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgLoginModel> model) {
                        if (model == null) {
                            return;
                        }
                        MeiJFenQiDdfgjRfdfgLoginModel meiJFenQiDdfgjRfdfgLoginModel = model.getData();
                        if (meiJFenQiDdfgjRfdfgLoginModel != null) {
                            MeiJFenQiDdfgjRfdfgSharePreferencesUtil.saveInt("mobileType", meiJFenQiDdfgjRfdfgLoginModel.getMobileType());
                            MeiJFenQiDdfgjRfdfgSharePreferencesUtil.saveString("phone", mobileStr);
                            MeiJFenQiDdfgjRfdfgSharePreferencesUtil.saveString("ip", ip);
                            StaticMeiJFenQiDdfgjRfdfgUtil.startActivity(LoginMeiJFenQiDdfgjRfdfgActivity.this, MainMeiJFenQiDdfgjRfdfgActivity.class, null);
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
        Observable<BaseMeiJFenQiDdfgjRfdfgModel> observable = MeiJFenQiDdfgjRfdfgRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MeiJFenQiDdfgjRfdfgObserverManager<BaseMeiJFenQiDdfgjRfdfgModel>() {
                    @Override
                    public void onSuccess(BaseMeiJFenQiDdfgjRfdfgModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastMeiJFenQiDdfgjRfdfgUtil.showShort("发送成功");
                            MeiJFenQiDdfgjRfdfgCountDownTimerUtils mMeiJFenQiDdfgjRfdfgCountDownTimerUtils = new MeiJFenQiDdfgjRfdfgCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mMeiJFenQiDdfgjRfdfgCountDownTimerUtils.start();
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
