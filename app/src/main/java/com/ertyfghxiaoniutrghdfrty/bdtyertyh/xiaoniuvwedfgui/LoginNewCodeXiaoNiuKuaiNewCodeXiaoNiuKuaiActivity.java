package com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ertyfghxiaoniutrghdfrty.bdtyertyh.NewCodeXiaoNiuKuaiApp;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.R;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgapi.NewCodeXiaoNiuKuaiRetrofitManager;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgbase.BaseNewCodeXiaoNiuKuaiActivity;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgbase.NewCodeXiaoNiuKuaiObserverManager;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgmodel.BaseNewCodeXiaoNiuKuaiModel;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgmodel.ConfigNewCodeXiaoNiuKuaiModel;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgmodel.NewCodeXiaoNiuKuaiLoginModel;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgutil.NewCodeXiaoNiuKuaiClickTextView;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgutil.CountDownTimerUtilsNewCodeXiaoNiuKuai;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgutil.NewCodeXiaoNiuKuaiSharePreferencesUtil;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgutil.StaticNewCodeXiaoNiuKuaiUtil;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgutil.NewCodeXiaoNiuKuaiStatusBarUtil;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgutil.ToastNewCodeXiaoNiuKuaiUtil;
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

public class LoginNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiActivity extends BaseNewCodeXiaoNiuKuaiActivity{

    protected static final int RC_PERM = 123;

    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private NewCodeXiaoNiuKuaiClickTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_xiao_niu_kuai_sdf_efbdgh_login;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastNewCodeXiaoNiuKuaiUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticNewCodeXiaoNiuKuaiUtil.isMobile(mobileStr)) {
                ToastNewCodeXiaoNiuKuaiUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastNewCodeXiaoNiuKuaiUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastNewCodeXiaoNiuKuaiUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(NewCodeXiaoNiuKuaiApp.getInstance());
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
                ToastNewCodeXiaoNiuKuaiUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticNewCodeXiaoNiuKuaiUtil.isMobile(mobileStr)) {
                ToastNewCodeXiaoNiuKuaiUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", NewCodeXiaoNiuKuaiRetrofitManager.ZCXY);
                StaticNewCodeXiaoNiuKuaiUtil.startActivity(LoginNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiActivity.this, NewCodeXiaoNiuKuaiUserYsxyNewCodeXiaoNiuKuaiActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", NewCodeXiaoNiuKuaiRetrofitManager.YSXY);
                StaticNewCodeXiaoNiuKuaiUtil.startActivity(LoginNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiActivity.this, NewCodeXiaoNiuKuaiUserYsxyNewCodeXiaoNiuKuaiActivity.class, bundle);
            }
        }, "#F4C580");
    }
    @Override
    public void initData() {
        if (NewCodeXiaoNiuKuaiSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        NewCodeXiaoNiuKuaiStatusBarUtil.setTransparent(this, false);
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

    private List<NewCodeXiaoNiuKuaiClickTextView.SpanModel> createSpanTexts() {
        List<NewCodeXiaoNiuKuaiClickTextView.SpanModel> spanModels = new ArrayList<>();
        NewCodeXiaoNiuKuaiClickTextView.ClickSpanModel spanModel = new NewCodeXiaoNiuKuaiClickTextView.ClickSpanModel();
        NewCodeXiaoNiuKuaiClickTextView.SpanModel textSpanModel = new NewCodeXiaoNiuKuaiClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new NewCodeXiaoNiuKuaiClickTextView.ClickSpanModel();
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
        Observable<BaseNewCodeXiaoNiuKuaiModel<ConfigNewCodeXiaoNiuKuaiModel>> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseNewCodeXiaoNiuKuaiModel<ConfigNewCodeXiaoNiuKuaiModel>>() {
                    @Override
                    public void onSuccess(BaseNewCodeXiaoNiuKuaiModel<ConfigNewCodeXiaoNiuKuaiModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigNewCodeXiaoNiuKuaiModel configNewCodeXiaoNiuKuaiModel = model.getData();
                        if (configNewCodeXiaoNiuKuaiModel != null) {
                            NewCodeXiaoNiuKuaiSharePreferencesUtil.saveString("APP_MAIL", configNewCodeXiaoNiuKuaiModel.getAppMail());
                            isNeedVerification = "1".equals(configNewCodeXiaoNiuKuaiModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(configNewCodeXiaoNiuKuaiModel.getIsSelectLogin()));
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
        Observable<BaseNewCodeXiaoNiuKuaiModel<NewCodeXiaoNiuKuaiLoginModel>> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseNewCodeXiaoNiuKuaiModel<NewCodeXiaoNiuKuaiLoginModel>>() {
                    @Override
                    public void onSuccess(BaseNewCodeXiaoNiuKuaiModel<NewCodeXiaoNiuKuaiLoginModel> model) {
                        if (model == null) {
                            return;
                        }
                        NewCodeXiaoNiuKuaiLoginModel newCodeXiaoNiuKuaiLoginModel = model.getData();
                        if (newCodeXiaoNiuKuaiLoginModel != null) {
                            NewCodeXiaoNiuKuaiSharePreferencesUtil.saveInt("mobileType", newCodeXiaoNiuKuaiLoginModel.getMobileType());
                            NewCodeXiaoNiuKuaiSharePreferencesUtil.saveString("phone", mobileStr);
                            NewCodeXiaoNiuKuaiSharePreferencesUtil.saveString("ip", ip);
                            StaticNewCodeXiaoNiuKuaiUtil.startActivity(LoginNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiActivity.this, MainNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiActivity.class, null);
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
        Observable<BaseNewCodeXiaoNiuKuaiModel> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseNewCodeXiaoNiuKuaiModel>() {
                    @Override
                    public void onSuccess(BaseNewCodeXiaoNiuKuaiModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastNewCodeXiaoNiuKuaiUtil.showShort("发送成功");
                            CountDownTimerUtilsNewCodeXiaoNiuKuai mCountDownTimerUtilsNewCodeXiaoNiuKuai = new CountDownTimerUtilsNewCodeXiaoNiuKuai(getVerificationTv, 60000, 1000);
                            mCountDownTimerUtilsNewCodeXiaoNiuKuai.start();
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
