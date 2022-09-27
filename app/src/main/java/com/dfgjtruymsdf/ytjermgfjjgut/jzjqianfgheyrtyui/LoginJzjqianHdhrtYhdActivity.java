package com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dfgjtruymsdf.ytjermgfjjgut.JzjqianHdhrtYhdApp;
import com.dfgjtruymsdf.ytjermgfjjgut.R;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyapi.JzjqianHdhrtYhdRetrofitManager;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtybase.BaseJzjqianHdhrtYhdActivity;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtybase.JzjqianHdhrtYhdObserverManager;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.BaseJzjqianHdhrtYhdModel;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.JzjqianHdhrtYhdConfigModel;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.LoginJzjqianHdhrtYhdModel;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.JzjqianHdhrtYhdClickTextView;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.CountDownTimerUtilsJzjqianHdhrtYhd;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.JzjqianHdhrtYhdSharePreferencesUtil;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.StaticJzjqianHdhrtYhdUtil;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.StatusJzjqianHdhrtYhdBarUtil;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.ToastJzjqianHdhrtYhdUtil;
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

public class LoginJzjqianHdhrtYhdActivity extends BaseJzjqianHdhrtYhdActivity {

    protected static final int RC_PERM = 123;

    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private JzjqianHdhrtYhdClickTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_jzjqian_sdhr_utryn_login;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastJzjqianHdhrtYhdUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticJzjqianHdhrtYhdUtil.isMobile(mobileStr)) {
                ToastJzjqianHdhrtYhdUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastJzjqianHdhrtYhdUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastJzjqianHdhrtYhdUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(JzjqianHdhrtYhdApp.getInstance());
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
                ToastJzjqianHdhrtYhdUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticJzjqianHdhrtYhdUtil.isMobile(mobileStr)) {
                ToastJzjqianHdhrtYhdUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", JzjqianHdhrtYhdRetrofitManager.ZCXY);
                StaticJzjqianHdhrtYhdUtil.startActivity(LoginJzjqianHdhrtYhdActivity.this, UserYsxyJzjqianHdhrtYhdActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", JzjqianHdhrtYhdRetrofitManager.YSXY);
                StaticJzjqianHdhrtYhdUtil.startActivity(LoginJzjqianHdhrtYhdActivity.this, UserYsxyJzjqianHdhrtYhdActivity.class, bundle);
            }
        }, "#13AE63");
    }

    @Override
    public void initData() {
        if (JzjqianHdhrtYhdSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusJzjqianHdhrtYhdBarUtil.setTransparent(this, false);
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

    private List<JzjqianHdhrtYhdClickTextView.SpanModel> createSpanTexts() {
        List<JzjqianHdhrtYhdClickTextView.SpanModel> spanModels = new ArrayList<>();
        JzjqianHdhrtYhdClickTextView.ClickSpanModel spanModel = new JzjqianHdhrtYhdClickTextView.ClickSpanModel();
        JzjqianHdhrtYhdClickTextView.SpanModel textSpanModel = new JzjqianHdhrtYhdClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new JzjqianHdhrtYhdClickTextView.ClickSpanModel();
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
        Observable<BaseJzjqianHdhrtYhdModel<JzjqianHdhrtYhdConfigModel>> observable = JzjqianHdhrtYhdRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JzjqianHdhrtYhdObserverManager<BaseJzjqianHdhrtYhdModel<JzjqianHdhrtYhdConfigModel>>() {
                    @Override
                    public void onSuccess(BaseJzjqianHdhrtYhdModel<JzjqianHdhrtYhdConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        JzjqianHdhrtYhdConfigModel jzjqianHdhrtYhdConfigModel = model.getData();
                        if (jzjqianHdhrtYhdConfigModel != null) {
                            JzjqianHdhrtYhdSharePreferencesUtil.saveString("APP_MAIL", jzjqianHdhrtYhdConfigModel.getAppMail());
                            isNeedVerification = "1".equals(jzjqianHdhrtYhdConfigModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(jzjqianHdhrtYhdConfigModel.getIsSelectLogin()));
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
        Observable<BaseJzjqianHdhrtYhdModel<LoginJzjqianHdhrtYhdModel>> observable = JzjqianHdhrtYhdRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JzjqianHdhrtYhdObserverManager<BaseJzjqianHdhrtYhdModel<LoginJzjqianHdhrtYhdModel>>() {
                    @Override
                    public void onSuccess(BaseJzjqianHdhrtYhdModel<LoginJzjqianHdhrtYhdModel> model) {
                        if (model == null) {
                            return;
                        }
                        LoginJzjqianHdhrtYhdModel loginJzjqianHdhrtYhdModel = model.getData();
                        if (loginJzjqianHdhrtYhdModel != null) {
                            JzjqianHdhrtYhdSharePreferencesUtil.saveInt("mobileType", loginJzjqianHdhrtYhdModel.getMobileType());
                            JzjqianHdhrtYhdSharePreferencesUtil.saveString("phone", mobileStr);
                            JzjqianHdhrtYhdSharePreferencesUtil.saveString("ip", ip);
                            StaticJzjqianHdhrtYhdUtil.startActivity(LoginJzjqianHdhrtYhdActivity.this, MainJzjqianHdhrtYhdActivity.class, null);
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
        Observable<BaseJzjqianHdhrtYhdModel> observable = JzjqianHdhrtYhdRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JzjqianHdhrtYhdObserverManager<BaseJzjqianHdhrtYhdModel>() {
                    @Override
                    public void onSuccess(BaseJzjqianHdhrtYhdModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastJzjqianHdhrtYhdUtil.showShort("发送成功");
                            CountDownTimerUtilsJzjqianHdhrtYhd mCountDownTimerUtilsJzjqianHdhrtYhd = new CountDownTimerUtilsJzjqianHdhrtYhd(getVerificationTv, 60000, 1000);
                            mCountDownTimerUtilsJzjqianHdhrtYhd.start();
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
