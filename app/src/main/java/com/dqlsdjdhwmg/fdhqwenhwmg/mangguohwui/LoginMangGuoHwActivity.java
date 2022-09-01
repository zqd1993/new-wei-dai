package com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dqlsdjdhwmg.fdhqwenhwmg.R;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwapi.MangGuoHwRetrofitManager;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwbase.BaseMangGuoHwActivity;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwbase.ObserverMangGuoHwManager;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.BaseMangGuoHwModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.MangGuoHwConfigModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.LoginMangGuoHwModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.MangGuoHwClickTextView;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.MangGuoHwCountDownTimerUtils;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.MangGuoHwSharePreferencesUtils;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.StaticMangGuoHwUtil;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.StatusBarUtilMangGuoHw;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.ToastMangGuoHwUtil;
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

public class LoginMangGuoHwActivity extends BaseMangGuoHwActivity {

    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private MangGuoHwClickTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;
    private View head_sl;

    private String mobileStr, verificationStr, ip;
    private boolean isNeedVerification;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_mang_guo_hw;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastMangGuoHwUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticMangGuoHwUtil.isMobile(mobileStr)) {
                ToastMangGuoHwUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastMangGuoHwUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastMangGuoHwUtil.showShort("请阅读用户协议及隐私政策");
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
                ToastMangGuoHwUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticMangGuoHwUtil.isMobile(mobileStr)) {
                ToastMangGuoHwUtil.showShort("请输入正确的手机号");
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
                bundle.putString("url", MangGuoHwRetrofitManager.ZCXY);
                StaticMangGuoHwUtil.startActivity(LoginMangGuoHwActivity.this, UserYsxyMangGuoHwActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", MangGuoHwRetrofitManager.YSXY);
                StaticMangGuoHwUtil.startActivity(LoginMangGuoHwActivity.this, UserYsxyMangGuoHwActivity.class, bundle);
            }
        }, "#ffffff");
    }

    @Override
    public void initData() {
        if (MangGuoHwSharePreferencesUtils.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusBarUtilMangGuoHw.setTransparent(this, false);
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

    private List<MangGuoHwClickTextView.SpanModel> createSpanTexts() {
        List<MangGuoHwClickTextView.SpanModel> spanModels = new ArrayList<>();
        MangGuoHwClickTextView.ClickSpanModel spanModel = new MangGuoHwClickTextView.ClickSpanModel();
        MangGuoHwClickTextView.SpanModel textSpanModel = new MangGuoHwClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new MangGuoHwClickTextView.ClickSpanModel();
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
        Observable<BaseMangGuoHwModel<MangGuoHwConfigModel>> observable = MangGuoHwRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverMangGuoHwManager<BaseMangGuoHwModel<MangGuoHwConfigModel>>() {
                    @Override
                    public void onSuccess(BaseMangGuoHwModel<MangGuoHwConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        MangGuoHwConfigModel mangGuoHwConfigModel = model.getData();
                        if (mangGuoHwConfigModel != null) {
                            MangGuoHwSharePreferencesUtils.saveString("APP_MAIL", mangGuoHwConfigModel.getAppMail());
                            isNeedVerification = "1".equals(mangGuoHwConfigModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(mangGuoHwConfigModel.getIsSelectLogin()));
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
        Observable<BaseMangGuoHwModel<LoginMangGuoHwModel>> observable = MangGuoHwRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverMangGuoHwManager<BaseMangGuoHwModel<LoginMangGuoHwModel>>() {
                    @Override
                    public void onSuccess(BaseMangGuoHwModel<LoginMangGuoHwModel> model) {
                        if (model == null) {
                            return;
                        }
                        LoginMangGuoHwModel loginMangGuoHwModel = model.getData();
                        if (loginMangGuoHwModel != null) {
                            MangGuoHwSharePreferencesUtils.saveInt("mobileType", loginMangGuoHwModel.getMobileType());
                            MangGuoHwSharePreferencesUtils.saveString("phone", mobileStr);
                            MangGuoHwSharePreferencesUtils.saveString("ip", ip);
                            StaticMangGuoHwUtil.startActivity(LoginMangGuoHwActivity.this, MainMangGuoHwActivity.class, null);
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
        Observable<BaseMangGuoHwModel> observable = MangGuoHwRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverMangGuoHwManager<BaseMangGuoHwModel>() {
                    @Override
                    public void onSuccess(BaseMangGuoHwModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastMangGuoHwUtil.showShort("发送成功");
                            MangGuoHwCountDownTimerUtils mMangGuoHwCountDownTimerUtils = new MangGuoHwCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mMangGuoHwCountDownTimerUtils.start();
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
