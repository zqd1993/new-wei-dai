package com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.JinZhuPigThdfgApp;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.R;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfapi.JinZhuPigThdfgRetrofitManager;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfbase.BaseJinZhuPigThdfgActivity;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfbase.JinZhuPigThdfgObserverManager;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfmodel.BaseJinZhuPigThdfgModel;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfmodel.JinZhuPigThdfgConfigModel;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfmodel.LoginJinZhuPigThdfgModel;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.JinZhuPigThdfgClickTextView;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.CountDownTimerUtilsJinZhuPigThdfg;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.JinZhuPigThdfgSharePreferencesUtil;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.StaticJinZhuPigThdfgUtil;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.StatusJinZhuPigThdfgBarUtil;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.ToastJinZhuPigThdfgUtil;
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

public class LoginJinZhuPigThdfgActivity extends BaseJinZhuPigThdfgActivity {

    protected static final int RC_PERM = 123;

    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private JinZhuPigThdfgClickTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_jin_zhu_asf_pig_login;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastJinZhuPigThdfgUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticJinZhuPigThdfgUtil.isMobile(mobileStr)) {
                ToastJinZhuPigThdfgUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastJinZhuPigThdfgUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastJinZhuPigThdfgUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(JinZhuPigThdfgApp.getInstance());
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
                ToastJinZhuPigThdfgUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticJinZhuPigThdfgUtil.isMobile(mobileStr)) {
                ToastJinZhuPigThdfgUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", JinZhuPigThdfgRetrofitManager.ZCXY);
                StaticJinZhuPigThdfgUtil.startActivity(LoginJinZhuPigThdfgActivity.this, UserYsxyJinZhuPigThdfgActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", JinZhuPigThdfgRetrofitManager.YSXY);
                StaticJinZhuPigThdfgUtil.startActivity(LoginJinZhuPigThdfgActivity.this, UserYsxyJinZhuPigThdfgActivity.class, bundle);
            }
        }, "#13AE63");
    }

    @Override
    public void initData() {
        if (JinZhuPigThdfgSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusJinZhuPigThdfgBarUtil.setTransparent(this, false);
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

    private List<JinZhuPigThdfgClickTextView.SpanModel> createSpanTexts() {
        List<JinZhuPigThdfgClickTextView.SpanModel> spanModels = new ArrayList<>();
        JinZhuPigThdfgClickTextView.ClickSpanModel spanModel = new JinZhuPigThdfgClickTextView.ClickSpanModel();
        JinZhuPigThdfgClickTextView.SpanModel textSpanModel = new JinZhuPigThdfgClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new JinZhuPigThdfgClickTextView.ClickSpanModel();
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
        Observable<BaseJinZhuPigThdfgModel<JinZhuPigThdfgConfigModel>> observable = JinZhuPigThdfgRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JinZhuPigThdfgObserverManager<BaseJinZhuPigThdfgModel<JinZhuPigThdfgConfigModel>>() {
                    @Override
                    public void onSuccess(BaseJinZhuPigThdfgModel<JinZhuPigThdfgConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        JinZhuPigThdfgConfigModel jinZhuPigThdfgConfigModel = model.getData();
                        if (jinZhuPigThdfgConfigModel != null) {
                            JinZhuPigThdfgSharePreferencesUtil.saveString("APP_MAIL", jinZhuPigThdfgConfigModel.getAppMail());
                            isNeedVerification = "1".equals(jinZhuPigThdfgConfigModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(jinZhuPigThdfgConfigModel.getIsSelectLogin()));
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
        Observable<BaseJinZhuPigThdfgModel<LoginJinZhuPigThdfgModel>> observable = JinZhuPigThdfgRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JinZhuPigThdfgObserverManager<BaseJinZhuPigThdfgModel<LoginJinZhuPigThdfgModel>>() {
                    @Override
                    public void onSuccess(BaseJinZhuPigThdfgModel<LoginJinZhuPigThdfgModel> model) {
                        if (model == null) {
                            return;
                        }
                        LoginJinZhuPigThdfgModel loginJinZhuPigThdfgModel = model.getData();
                        if (loginJinZhuPigThdfgModel != null) {
                            JinZhuPigThdfgSharePreferencesUtil.saveInt("mobileType", loginJinZhuPigThdfgModel.getMobileType());
                            JinZhuPigThdfgSharePreferencesUtil.saveString("phone", mobileStr);
                            JinZhuPigThdfgSharePreferencesUtil.saveString("ip", ip);
                            StaticJinZhuPigThdfgUtil.startActivity(LoginJinZhuPigThdfgActivity.this, MainJinZhuPigThdfgActivity.class, null);
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
        Observable<BaseJinZhuPigThdfgModel> observable = JinZhuPigThdfgRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JinZhuPigThdfgObserverManager<BaseJinZhuPigThdfgModel>() {
                    @Override
                    public void onSuccess(BaseJinZhuPigThdfgModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastJinZhuPigThdfgUtil.showShort("发送成功");
                            CountDownTimerUtilsJinZhuPigThdfg mCountDownTimerUtilsJinZhuPigThdfg = new CountDownTimerUtilsJinZhuPigThdfg(getVerificationTv, 60000, 1000);
                            mCountDownTimerUtilsJinZhuPigThdfg.start();
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
