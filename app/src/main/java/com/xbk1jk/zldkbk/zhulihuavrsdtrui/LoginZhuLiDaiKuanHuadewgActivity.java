package com.xbk1jk.zldkbk.zhulihuavrsdtrui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.github.gzuliyujiang.oaid.IGetter;
import com.xbk1jk.zldkbk.R;
import com.xbk1jk.zldkbk.ZhuLiDaiKuanHuadewgApp;
import com.xbk1jk.zldkbk.zhulihuavrsdtrapi.ZhuLiDaiKuanHuadewgRetrofitManager;
import com.xbk1jk.zldkbk.zhulihuavrsdtrbase.BaseZhuLiDaiKuanHuadewgFgsActivity;
import com.xbk1jk.zldkbk.zhulihuavrsdtrbase.MiaoBaiTiaoAdfFgsObserverManager;
import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.ZhuLiDaiKuanHuadewgBaseModel;
import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.ConfigZhuLiDaiKuanHuadewgModel;
import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.LoginZhuLiDaiKuanHuadewgModel;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.ClickZhuLiDaiKuanHuadewgTextView;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.ToastZhuLiDaiKuanHuadewgUtil;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.ZhuLiDaiKuanHuadewgCountDownTimerUtils;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.SharePreferencesZhuLiDaiKuanHuadewgUtil;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.StaticZhuLiDaiKuanHuadewgUtil;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.ZhuLiDaiKuanHuadewgStatusBarUtil;
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

public class LoginZhuLiDaiKuanHuadewgActivity extends BaseZhuLiDaiKuanHuadewgFgsActivity{

    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private ClickZhuLiDaiKuanHuadewgTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_zhu_li_dai_kuan_hua_setg_sert;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastZhuLiDaiKuanHuadewgUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticZhuLiDaiKuanHuadewgUtil.isMobile(mobileStr)) {
                ToastZhuLiDaiKuanHuadewgUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastZhuLiDaiKuanHuadewgUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastZhuLiDaiKuanHuadewgUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(ZhuLiDaiKuanHuadewgApp.getInstance());
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
                ToastZhuLiDaiKuanHuadewgUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticZhuLiDaiKuanHuadewgUtil.isMobile(mobileStr)) {
                ToastZhuLiDaiKuanHuadewgUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", ZhuLiDaiKuanHuadewgRetrofitManager.ZCXY);
                StaticZhuLiDaiKuanHuadewgUtil.startActivity(LoginZhuLiDaiKuanHuadewgActivity.this, ZhuLiDaiKuanHuadewgUserYsxyActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ZhuLiDaiKuanHuadewgRetrofitManager.YSXY);
                StaticZhuLiDaiKuanHuadewgUtil.startActivity(LoginZhuLiDaiKuanHuadewgActivity.this, ZhuLiDaiKuanHuadewgUserYsxyActivity.class, bundle);
            }
        }, "#FFF1D2");
    }

    @Override
    public void initData() {
        if (SharePreferencesZhuLiDaiKuanHuadewgUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        ZhuLiDaiKuanHuadewgStatusBarUtil.setTransparent(this, false);
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

    private List<ClickZhuLiDaiKuanHuadewgTextView.SpanModel> createSpanTexts() {
        List<ClickZhuLiDaiKuanHuadewgTextView.SpanModel> spanModels = new ArrayList<>();
        ClickZhuLiDaiKuanHuadewgTextView.ClickSpanModel spanModel = new ClickZhuLiDaiKuanHuadewgTextView.ClickSpanModel();
        ClickZhuLiDaiKuanHuadewgTextView.SpanModel textSpanModel = new ClickZhuLiDaiKuanHuadewgTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new ClickZhuLiDaiKuanHuadewgTextView.ClickSpanModel();
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
        Observable<ZhuLiDaiKuanHuadewgBaseModel<ConfigZhuLiDaiKuanHuadewgModel>> observable = ZhuLiDaiKuanHuadewgRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<ZhuLiDaiKuanHuadewgBaseModel<ConfigZhuLiDaiKuanHuadewgModel>>() {
                    @Override
                    public void onSuccess(ZhuLiDaiKuanHuadewgBaseModel<ConfigZhuLiDaiKuanHuadewgModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigZhuLiDaiKuanHuadewgModel configZhuLiDaiKuanHuadewgModel = model.getData();
                        if (configZhuLiDaiKuanHuadewgModel != null) {
                            SharePreferencesZhuLiDaiKuanHuadewgUtil.saveString("APP_MAIL", configZhuLiDaiKuanHuadewgModel.getAppMail());
                            isNeedVerification = "1".equals(configZhuLiDaiKuanHuadewgModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(configZhuLiDaiKuanHuadewgModel.getIsSelectLogin()));
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
        Observable<ZhuLiDaiKuanHuadewgBaseModel<LoginZhuLiDaiKuanHuadewgModel>> observable = ZhuLiDaiKuanHuadewgRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<ZhuLiDaiKuanHuadewgBaseModel<LoginZhuLiDaiKuanHuadewgModel>>() {
                    @Override
                    public void onSuccess(ZhuLiDaiKuanHuadewgBaseModel<LoginZhuLiDaiKuanHuadewgModel> model) {
                        if (model == null) {
                            return;
                        }
                        LoginZhuLiDaiKuanHuadewgModel loginZhuLiDaiKuanHuadewgModel = model.getData();
                        if (loginZhuLiDaiKuanHuadewgModel != null) {
                            SharePreferencesZhuLiDaiKuanHuadewgUtil.saveInt("mobileType", loginZhuLiDaiKuanHuadewgModel.getMobileType());
                            SharePreferencesZhuLiDaiKuanHuadewgUtil.saveString("phone", mobileStr);
                            SharePreferencesZhuLiDaiKuanHuadewgUtil.saveString("ip", ip);
                            StaticZhuLiDaiKuanHuadewgUtil.startActivity(LoginZhuLiDaiKuanHuadewgActivity.this, MainZhuLiDaiKuanHuadewgActivity.class, null);
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
        Observable<ZhuLiDaiKuanHuadewgBaseModel> observable = ZhuLiDaiKuanHuadewgRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<ZhuLiDaiKuanHuadewgBaseModel>() {
                    @Override
                    public void onSuccess(ZhuLiDaiKuanHuadewgBaseModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastZhuLiDaiKuanHuadewgUtil.showShort("发送成功");
                            ZhuLiDaiKuanHuadewgCountDownTimerUtils mZhuLiDaiKuanHuadewgCountDownTimerUtils = new ZhuLiDaiKuanHuadewgCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mZhuLiDaiKuanHuadewgCountDownTimerUtils.start();
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
