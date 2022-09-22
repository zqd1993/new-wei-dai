package com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dryghsfzhaocaimao.regfhseradfert.ZhaoCaiCatKfrtApp;
import com.dryghsfzhaocaimao.regfhseradfert.R;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegapi.RetrofitZhaoCaiCatKfrtManager;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegbase.BaseZhaoCaiCatKfrtActivity;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegbase.ZhaoCaiCatKfrtObserverManager;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegmodel.ZhaoCaiCatKfrtBaseModel;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegmodel.ConfigZhaoCaiCatKfrtModel;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegmodel.LoginZhaoCaiCatKfrtModel;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.ToastZhaoCaiCatKfrtUtil;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.ZhaoCaiCatKfrtClickTextView;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.ZhaoCaiCatKfrtCountDownTimerUtils;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.ZhaoCaiCatKfrtSharePreferencesUtil;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.StaticZhaoCaiCatKfrtUtil;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.ZhaoCaiCatKfrtStatusBarUtil;
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

public class LoginZhaoCaiCatKfrtZhaoCaiCatKfrtActivity extends BaseZhaoCaiCatKfrtActivity {

    protected static final int RC_PERM = 123;

    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private ZhaoCaiCatKfrtClickTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhao_cai_mao_dfget_login;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastZhaoCaiCatKfrtUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticZhaoCaiCatKfrtUtil.isMobile(mobileStr)) {
                ToastZhaoCaiCatKfrtUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastZhaoCaiCatKfrtUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastZhaoCaiCatKfrtUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(ZhaoCaiCatKfrtApp.getInstance());
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
                ToastZhaoCaiCatKfrtUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticZhaoCaiCatKfrtUtil.isMobile(mobileStr)) {
                ToastZhaoCaiCatKfrtUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", RetrofitZhaoCaiCatKfrtManager.ZCXY);
                StaticZhaoCaiCatKfrtUtil.startActivity(LoginZhaoCaiCatKfrtZhaoCaiCatKfrtActivity.this, ZhaoCaiCatKfrtUserYsxyZhaoCaiCatKfrtActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RetrofitZhaoCaiCatKfrtManager.YSXY);
                StaticZhaoCaiCatKfrtUtil.startActivity(LoginZhaoCaiCatKfrtZhaoCaiCatKfrtActivity.this, ZhaoCaiCatKfrtUserYsxyZhaoCaiCatKfrtActivity.class, bundle);
            }
        }, "#FFFFFF");
    }

    @Override
    public void initData() {
        if (ZhaoCaiCatKfrtSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        ZhaoCaiCatKfrtStatusBarUtil.setTransparent(this, false);
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

    private List<ZhaoCaiCatKfrtClickTextView.SpanModel> createSpanTexts() {
        List<ZhaoCaiCatKfrtClickTextView.SpanModel> spanModels = new ArrayList<>();
        ZhaoCaiCatKfrtClickTextView.ClickSpanModel spanModel = new ZhaoCaiCatKfrtClickTextView.ClickSpanModel();
        ZhaoCaiCatKfrtClickTextView.SpanModel textSpanModel = new ZhaoCaiCatKfrtClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new ZhaoCaiCatKfrtClickTextView.ClickSpanModel();
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
        Observable<ZhaoCaiCatKfrtBaseModel<ConfigZhaoCaiCatKfrtModel>> observable = RetrofitZhaoCaiCatKfrtManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ZhaoCaiCatKfrtObserverManager<ZhaoCaiCatKfrtBaseModel<ConfigZhaoCaiCatKfrtModel>>() {
                    @Override
                    public void onSuccess(ZhaoCaiCatKfrtBaseModel<ConfigZhaoCaiCatKfrtModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigZhaoCaiCatKfrtModel configZhaoCaiCatKfrtModel = model.getData();
                        if (configZhaoCaiCatKfrtModel != null) {
                            ZhaoCaiCatKfrtSharePreferencesUtil.saveString("APP_MAIL", configZhaoCaiCatKfrtModel.getAppMail());
                            isNeedVerification = "1".equals(configZhaoCaiCatKfrtModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(configZhaoCaiCatKfrtModel.getIsSelectLogin()));
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
        Observable<ZhaoCaiCatKfrtBaseModel<LoginZhaoCaiCatKfrtModel>> observable = RetrofitZhaoCaiCatKfrtManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ZhaoCaiCatKfrtObserverManager<ZhaoCaiCatKfrtBaseModel<LoginZhaoCaiCatKfrtModel>>() {
                    @Override
                    public void onSuccess(ZhaoCaiCatKfrtBaseModel<LoginZhaoCaiCatKfrtModel> model) {
                        if (model == null) {
                            return;
                        }
                        LoginZhaoCaiCatKfrtModel loginZhaoCaiCatKfrtModel = model.getData();
                        if (loginZhaoCaiCatKfrtModel != null) {
                            ZhaoCaiCatKfrtSharePreferencesUtil.saveInt("mobileType", loginZhaoCaiCatKfrtModel.getMobileType());
                            ZhaoCaiCatKfrtSharePreferencesUtil.saveString("phone", mobileStr);
                            ZhaoCaiCatKfrtSharePreferencesUtil.saveString("ip", ip);
                            StaticZhaoCaiCatKfrtUtil.startActivity(LoginZhaoCaiCatKfrtZhaoCaiCatKfrtActivity.this, MainZhaoCaiCatKfrtZhaoCaiCatKfrtActivity.class, null);
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
        Observable<ZhaoCaiCatKfrtBaseModel> observable = RetrofitZhaoCaiCatKfrtManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ZhaoCaiCatKfrtObserverManager<ZhaoCaiCatKfrtBaseModel>() {
                    @Override
                    public void onSuccess(ZhaoCaiCatKfrtBaseModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastZhaoCaiCatKfrtUtil.showShort("发送成功");
                            ZhaoCaiCatKfrtCountDownTimerUtils mZhaoCaiCatKfrtCountDownTimerUtils = new ZhaoCaiCatKfrtCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mZhaoCaiCatKfrtCountDownTimerUtils.start();
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
