package com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.WuYouJieShdfnFjfuirApp;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.R;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsapi.RetrofitWuYouJieShdfnFjfuirManager;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsbase.BaseWuYouJieShdfnFjfuirActivity;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsbase.WuYouJieShdfnFjfuirObserverManager;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.WuYouJieShdfnFjfuirBaseModel;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.ConfigWuYouJieShdfnFjfuirModel;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.LoginWuYouJieShdfnFjfuirModel;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.ToastWuYouJieShdfnFjfuirUtil;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.WuYouJieShdfnFjfuirClickTextView;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.WuYouJieShdfnFjfuirCountDownTimerUtils;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.WuYouJieShdfnFjfuirSharePreferencesUtil;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.StaticWuYouJieShdfnFjfuirUtil;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.WuYouJieShdfnFjfuirStatusBarUtil;
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

public class LoginWuYouJieShdfnFjfuirActivity extends BaseWuYouJieShdfnFjfuirActivity {

    protected static final int RC_PERM = 123;

    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private WuYouJieShdfnFjfuirClickTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wu_you_jie_jdf_eryj_login;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastWuYouJieShdfnFjfuirUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticWuYouJieShdfnFjfuirUtil.isMobile(mobileStr)) {
                ToastWuYouJieShdfnFjfuirUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastWuYouJieShdfnFjfuirUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastWuYouJieShdfnFjfuirUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(WuYouJieShdfnFjfuirApp.getInstance());
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
                ToastWuYouJieShdfnFjfuirUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticWuYouJieShdfnFjfuirUtil.isMobile(mobileStr)) {
                ToastWuYouJieShdfnFjfuirUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", RetrofitWuYouJieShdfnFjfuirManager.ZCXY);
                StaticWuYouJieShdfnFjfuirUtil.startActivity(LoginWuYouJieShdfnFjfuirActivity.this, UserYsxyWuYouJieShdfnFjfuirActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RetrofitWuYouJieShdfnFjfuirManager.YSXY);
                StaticWuYouJieShdfnFjfuirUtil.startActivity(LoginWuYouJieShdfnFjfuirActivity.this, UserYsxyWuYouJieShdfnFjfuirActivity.class, bundle);
            }
        }, "#FFFFFF");
    }

    @Override
    public void initData() {
        if (WuYouJieShdfnFjfuirSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        WuYouJieShdfnFjfuirStatusBarUtil.setTransparent(this, false);
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

    private List<WuYouJieShdfnFjfuirClickTextView.SpanModel> createSpanTexts() {
        List<WuYouJieShdfnFjfuirClickTextView.SpanModel> spanModels = new ArrayList<>();
        WuYouJieShdfnFjfuirClickTextView.ClickSpanModel spanModel = new WuYouJieShdfnFjfuirClickTextView.ClickSpanModel();
        WuYouJieShdfnFjfuirClickTextView.SpanModel textSpanModel = new WuYouJieShdfnFjfuirClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new WuYouJieShdfnFjfuirClickTextView.ClickSpanModel();
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
        Observable<WuYouJieShdfnFjfuirBaseModel<ConfigWuYouJieShdfnFjfuirModel>> observable = RetrofitWuYouJieShdfnFjfuirManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new WuYouJieShdfnFjfuirObserverManager<WuYouJieShdfnFjfuirBaseModel<ConfigWuYouJieShdfnFjfuirModel>>() {
                    @Override
                    public void onSuccess(WuYouJieShdfnFjfuirBaseModel<ConfigWuYouJieShdfnFjfuirModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigWuYouJieShdfnFjfuirModel configWuYouJieShdfnFjfuirModel = model.getData();
                        if (configWuYouJieShdfnFjfuirModel != null) {
                            WuYouJieShdfnFjfuirSharePreferencesUtil.saveString("APP_MAIL", configWuYouJieShdfnFjfuirModel.getAppMail());
                            isNeedVerification = "1".equals(configWuYouJieShdfnFjfuirModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(configWuYouJieShdfnFjfuirModel.getIsSelectLogin()));
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
        Observable<WuYouJieShdfnFjfuirBaseModel<LoginWuYouJieShdfnFjfuirModel>> observable = RetrofitWuYouJieShdfnFjfuirManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new WuYouJieShdfnFjfuirObserverManager<WuYouJieShdfnFjfuirBaseModel<LoginWuYouJieShdfnFjfuirModel>>() {
                    @Override
                    public void onSuccess(WuYouJieShdfnFjfuirBaseModel<LoginWuYouJieShdfnFjfuirModel> model) {
                        if (model == null) {
                            return;
                        }
                        LoginWuYouJieShdfnFjfuirModel loginWuYouJieShdfnFjfuirModel = model.getData();
                        if (loginWuYouJieShdfnFjfuirModel != null) {
                            WuYouJieShdfnFjfuirSharePreferencesUtil.saveInt("mobileType", loginWuYouJieShdfnFjfuirModel.getMobileType());
                            WuYouJieShdfnFjfuirSharePreferencesUtil.saveString("phone", mobileStr);
                            WuYouJieShdfnFjfuirSharePreferencesUtil.saveString("ip", ip);
                            StaticWuYouJieShdfnFjfuirUtil.startActivity(LoginWuYouJieShdfnFjfuirActivity.this, MaintWuYouJieShdfnFjfuirActivity.class, null);
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
        Observable<WuYouJieShdfnFjfuirBaseModel> observable = RetrofitWuYouJieShdfnFjfuirManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new WuYouJieShdfnFjfuirObserverManager<WuYouJieShdfnFjfuirBaseModel>() {
                    @Override
                    public void onSuccess(WuYouJieShdfnFjfuirBaseModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastWuYouJieShdfnFjfuirUtil.showShort("发送成功");
                            WuYouJieShdfnFjfuirCountDownTimerUtils mWuYouJieShdfnFjfuirCountDownTimerUtils = new WuYouJieShdfnFjfuirCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mWuYouJieShdfnFjfuirCountDownTimerUtils.start();
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
