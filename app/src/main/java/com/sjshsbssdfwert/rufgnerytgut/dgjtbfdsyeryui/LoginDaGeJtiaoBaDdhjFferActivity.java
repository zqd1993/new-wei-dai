package com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.sjshsbssdfwert.rufgnerytgut.DaGeJtiaoBaDdhjFferApp;
import com.sjshsbssdfwert.rufgnerytgut.R;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryapi.DaGeJtiaoBaDdhjFferRetrofitManager;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerybase.BaseDaGeJtiaoBaDdhjFferActivity;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerybase.DaGeJtiaoBaDdhjFferObserverManager;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.BaseDaGeJtiaoBaDdhjFferModel;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.DaGeJtiaoBaDdhjFferConfigModel;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.DaGeJtiaoBaDdhjFferLoginModel;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.DaGeJtiaoBaDdhjFferClickTextView;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.DaGeJtiaoBaDdhjFferCountDownTimerUtils;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.DaGeJtiaoBaDdhjFferSharePreferencesUtil;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.StaticDaGeJtiaoBaDdhjFferUtil;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.DaGeJtiaoBaDdhjFferStatusBarUtil;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.ToastDaGeJtiaoBaDdhjFferUtil;
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

public class LoginDaGeJtiaoBaDdhjFferActivity extends BaseDaGeJtiaoBaDdhjFferActivity {
    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private DaGeJtiaoBaDdhjFferClickTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;
    private View head_sl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_da_ge_jdf_yrjf_login;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastDaGeJtiaoBaDdhjFferUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticDaGeJtiaoBaDdhjFferUtil.isMobile(mobileStr)) {
                ToastDaGeJtiaoBaDdhjFferUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastDaGeJtiaoBaDdhjFferUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastDaGeJtiaoBaDdhjFferUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(DaGeJtiaoBaDdhjFferApp.getInstance());
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
                ToastDaGeJtiaoBaDdhjFferUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticDaGeJtiaoBaDdhjFferUtil.isMobile(mobileStr)) {
                ToastDaGeJtiaoBaDdhjFferUtil.showShort("请输入正确的手机号");
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
                bundle.putString("url", DaGeJtiaoBaDdhjFferRetrofitManager.ZCXY);
                StaticDaGeJtiaoBaDdhjFferUtil.startActivity(LoginDaGeJtiaoBaDdhjFferActivity.this, UserYsxyDaGeJtiaoBaDdhjFferActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", DaGeJtiaoBaDdhjFferRetrofitManager.YSXY);
                StaticDaGeJtiaoBaDdhjFferUtil.startActivity(LoginDaGeJtiaoBaDdhjFferActivity.this, UserYsxyDaGeJtiaoBaDdhjFferActivity.class, bundle);
            }
        }, "#EED4BD");
    }

    @Override
    public void initData() {
        if (DaGeJtiaoBaDdhjFferSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        DaGeJtiaoBaDdhjFferStatusBarUtil.setTransparent(this, false);
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

    private List<DaGeJtiaoBaDdhjFferClickTextView.SpanModel> createSpanTexts() {
        List<DaGeJtiaoBaDdhjFferClickTextView.SpanModel> spanModels = new ArrayList<>();
        DaGeJtiaoBaDdhjFferClickTextView.ClickSpanModel spanModel = new DaGeJtiaoBaDdhjFferClickTextView.ClickSpanModel();
        DaGeJtiaoBaDdhjFferClickTextView.SpanModel textSpanModel = new DaGeJtiaoBaDdhjFferClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new DaGeJtiaoBaDdhjFferClickTextView.ClickSpanModel();
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
        Observable<BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferConfigModel>> observable = DaGeJtiaoBaDdhjFferRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new DaGeJtiaoBaDdhjFferObserverManager<BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferConfigModel>>() {
                    @Override
                    public void onSuccess(BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        DaGeJtiaoBaDdhjFferConfigModel daGeJtiaoBaDdhjFferConfigModel = model.getData();
                        if (daGeJtiaoBaDdhjFferConfigModel != null) {
                            DaGeJtiaoBaDdhjFferSharePreferencesUtil.saveString("APP_MAIL", daGeJtiaoBaDdhjFferConfigModel.getAppMail());
                            isNeedVerification = "1".equals(daGeJtiaoBaDdhjFferConfigModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(daGeJtiaoBaDdhjFferConfigModel.getIsSelectLogin()));
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
        Observable<BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferLoginModel>> observable = DaGeJtiaoBaDdhjFferRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new DaGeJtiaoBaDdhjFferObserverManager<BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferLoginModel>>() {
                    @Override
                    public void onSuccess(BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferLoginModel> model) {
                        if (model == null) {
                            return;
                        }
                        DaGeJtiaoBaDdhjFferLoginModel daGeJtiaoBaDdhjFferLoginModel = model.getData();
                        if (daGeJtiaoBaDdhjFferLoginModel != null) {
                            DaGeJtiaoBaDdhjFferSharePreferencesUtil.saveInt("mobileType", daGeJtiaoBaDdhjFferLoginModel.getMobileType());
                            DaGeJtiaoBaDdhjFferSharePreferencesUtil.saveString("phone", mobileStr);
                            DaGeJtiaoBaDdhjFferSharePreferencesUtil.saveString("ip", ip);
                            StaticDaGeJtiaoBaDdhjFferUtil.startActivity(LoginDaGeJtiaoBaDdhjFferActivity.this, MainDaGeJtiaoBaDdhjFferActivity.class, null);
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
        Observable<BaseDaGeJtiaoBaDdhjFferModel> observable = DaGeJtiaoBaDdhjFferRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new DaGeJtiaoBaDdhjFferObserverManager<BaseDaGeJtiaoBaDdhjFferModel>() {
                    @Override
                    public void onSuccess(BaseDaGeJtiaoBaDdhjFferModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastDaGeJtiaoBaDdhjFferUtil.showShort("发送成功");
                            DaGeJtiaoBaDdhjFferCountDownTimerUtils mDaGeJtiaoBaDdhjFferCountDownTimerUtils = new DaGeJtiaoBaDdhjFferCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mDaGeJtiaoBaDdhjFferCountDownTimerUtils.start();
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
