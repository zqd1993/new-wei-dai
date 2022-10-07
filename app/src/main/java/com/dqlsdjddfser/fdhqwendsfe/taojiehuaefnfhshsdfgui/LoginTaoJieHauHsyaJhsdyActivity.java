package com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.github.gzuliyujiang.oaid.IGetter;
import com.dqlsdjddfser.fdhqwendsfe.R;
import com.dqlsdjddfser.fdhqwendsfe.TaoJieHauHsyaJhsdyApp;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgapi.TaoJieHauHsyaJhsdyRetrofitManager;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgbase.BaseTaoJieHauHsyaJhsdyActivity;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgbase.TaoJieHauHsyaJhsdyObserverManager;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyBaseModel;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyConfigModel;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyLoginModel;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.ClickTextViewTaoJieHauHsyaJhsdy;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.TaoJieHauHsyaJhsdyCountDownTimerUtils;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.SharePreferencesUtilTaoJieHauHsyaJhsdy;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.StaticTaoJieHauHsyaJhsdyUtil;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.TaoJieHauHsyaJhsdyStatusBarUtil;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.ToastRongjieSfFgdfUtil;
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

public class LoginTaoJieHauHsyaJhsdyActivity extends BaseTaoJieHauHsyaJhsdyActivity {

    protected static final int RC_PERM = 123;

    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private ClickTextViewTaoJieHauHsyaJhsdy loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tao_jie_hua_djheru_fhentj_login;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastRongjieSfFgdfUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticTaoJieHauHsyaJhsdyUtil.isMobile(mobileStr)) {
                ToastRongjieSfFgdfUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastRongjieSfFgdfUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastRongjieSfFgdfUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(TaoJieHauHsyaJhsdyApp.getInstance());
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
                ToastRongjieSfFgdfUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticTaoJieHauHsyaJhsdyUtil.isMobile(mobileStr)) {
                ToastRongjieSfFgdfUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", TaoJieHauHsyaJhsdyRetrofitManager.ZCXY);
                StaticTaoJieHauHsyaJhsdyUtil.startActivity(LoginTaoJieHauHsyaJhsdyActivity.this, TaoJieHauHsyaJhsdyUserYsxyActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", TaoJieHauHsyaJhsdyRetrofitManager.YSXY);
                StaticTaoJieHauHsyaJhsdyUtil.startActivity(LoginTaoJieHauHsyaJhsdyActivity.this, TaoJieHauHsyaJhsdyUserYsxyActivity.class, bundle);
            }
        }, "#E71C1A");
    }

    @Override
    public void initData() {
        if (SharePreferencesUtilTaoJieHauHsyaJhsdy.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        TaoJieHauHsyaJhsdyStatusBarUtil.setTransparent(this, false);
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

    private List<ClickTextViewTaoJieHauHsyaJhsdy.SpanModel> createSpanTexts() {
        List<ClickTextViewTaoJieHauHsyaJhsdy.SpanModel> spanModels = new ArrayList<>();
        ClickTextViewTaoJieHauHsyaJhsdy.ClickSpanModel spanModel = new ClickTextViewTaoJieHauHsyaJhsdy.ClickSpanModel();
        ClickTextViewTaoJieHauHsyaJhsdy.SpanModel textSpanModel = new ClickTextViewTaoJieHauHsyaJhsdy.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new ClickTextViewTaoJieHauHsyaJhsdy.ClickSpanModel();
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
        Observable<TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyConfigModel>> observable = TaoJieHauHsyaJhsdyRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new TaoJieHauHsyaJhsdyObserverManager<TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyConfigModel>>() {
                    @Override
                    public void onSuccess(TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        TaoJieHauHsyaJhsdyConfigModel taoJieHauHsyaJhsdyConfigModel = model.getData();
                        if (taoJieHauHsyaJhsdyConfigModel != null) {
                            SharePreferencesUtilTaoJieHauHsyaJhsdy.saveString("APP_MAIL", taoJieHauHsyaJhsdyConfigModel.getAppMail());
                            isNeedVerification = "1".equals(taoJieHauHsyaJhsdyConfigModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(taoJieHauHsyaJhsdyConfigModel.getIsSelectLogin()));
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
        Observable<TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyLoginModel>> observable = TaoJieHauHsyaJhsdyRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new TaoJieHauHsyaJhsdyObserverManager<TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyLoginModel>>() {
                    @Override
                    public void onSuccess(TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyLoginModel> model) {
                        if (model == null) {
                            return;
                        }
                        TaoJieHauHsyaJhsdyLoginModel taoJieHauHsyaJhsdyLoginModel = model.getData();
                        if (taoJieHauHsyaJhsdyLoginModel != null) {
                            SharePreferencesUtilTaoJieHauHsyaJhsdy.saveInt("mobileType", taoJieHauHsyaJhsdyLoginModel.getMobileType());
                            SharePreferencesUtilTaoJieHauHsyaJhsdy.saveString("phone", mobileStr);
                            SharePreferencesUtilTaoJieHauHsyaJhsdy.saveString("ip", ip);
                            StaticTaoJieHauHsyaJhsdyUtil.startActivity(LoginTaoJieHauHsyaJhsdyActivity.this, MainTaoJieHauHsyaJhsdyActivity.class, null);
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
        Observable<TaoJieHauHsyaJhsdyBaseModel> observable = TaoJieHauHsyaJhsdyRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new TaoJieHauHsyaJhsdyObserverManager<TaoJieHauHsyaJhsdyBaseModel>() {
                    @Override
                    public void onSuccess(TaoJieHauHsyaJhsdyBaseModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastRongjieSfFgdfUtil.showShort("发送成功");
                            TaoJieHauHsyaJhsdyCountDownTimerUtils mTaoJieHauHsyaJhsdyCountDownTimerUtils = new TaoJieHauHsyaJhsdyCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mTaoJieHauHsyaJhsdyCountDownTimerUtils.start();
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
