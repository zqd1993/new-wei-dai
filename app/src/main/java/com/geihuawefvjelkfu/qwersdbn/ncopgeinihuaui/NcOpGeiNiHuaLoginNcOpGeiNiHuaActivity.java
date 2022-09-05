package com.geihuawefvjelkfu.qwersdbn.ncopgeinihuaui;

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
import com.geihuawefvjelkfu.qwersdbn.NcOpGeiNiHuaApp;
import com.geihuawefvjelkfu.qwersdbn.R;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuaapi.NcOpGeiNiHuaRetrofitManager;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuabase.BaseNcOpGeiNiHuaActivity;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuabase.ObserverManagerNcOpGeiNiHua;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuamodel.BaseNcOpGeiNiHuaModel;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuamodel.NcOpGeiNiHuaConfigModel;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuamodel.LoginNcOpGeiNiHuaModel;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil.ClickTextViewNcOpGeiNiHua;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil.NcOpGeiNiHuaCountDownTimerUtils;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil.NcOpGeiNiHuaSharePreferencesUtil;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil.NcOpGeiNiHuaStaticUtil;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil.NcOpGeiNiHuaStatusBarUtil;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil.NcOpGeiNiHuaToastUtil;
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

public class NcOpGeiNiHuaLoginNcOpGeiNiHuaActivity extends BaseNcOpGeiNiHuaActivity{

    protected static final int RC_PERM = 123;

    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private ClickTextViewNcOpGeiNiHua loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;
    private View head_sl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification, isOaid;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                NcOpGeiNiHuaToastUtil.showShort("请输入手机号");
                return;
            }
            if (!NcOpGeiNiHuaStaticUtil.isMobile(mobileStr)) {
                NcOpGeiNiHuaToastUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                NcOpGeiNiHuaToastUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                NcOpGeiNiHuaToastUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(NcOpGeiNiHuaApp.getInstance());
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
                NcOpGeiNiHuaToastUtil.showShort("请输入手机号");
                return;
            }
            if (!NcOpGeiNiHuaStaticUtil.isMobile(mobileStr)) {
                NcOpGeiNiHuaToastUtil.showShort("请输入正确的手机号");
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
                bundle.putString("url", NcOpGeiNiHuaRetrofitManager.ZCXY);
                NcOpGeiNiHuaStaticUtil.startActivity(NcOpGeiNiHuaLoginNcOpGeiNiHuaActivity.this, NcOpGeiNiHuaUserYsxyNcOpGeiNiHuaActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", NcOpGeiNiHuaRetrofitManager.YSXY);
                NcOpGeiNiHuaStaticUtil.startActivity(NcOpGeiNiHuaLoginNcOpGeiNiHuaActivity.this, NcOpGeiNiHuaUserYsxyNcOpGeiNiHuaActivity.class, bundle);
            }
        }, "#ffffff");
    }

    @Override
    public void initData() {
        if (NcOpGeiNiHuaSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        NcOpGeiNiHuaStatusBarUtil.setTransparent(this, false);
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

    private List<ClickTextViewNcOpGeiNiHua.SpanModel> createSpanTexts() {
        List<ClickTextViewNcOpGeiNiHua.SpanModel> spanModels = new ArrayList<>();
        ClickTextViewNcOpGeiNiHua.ClickSpanModel spanModel = new ClickTextViewNcOpGeiNiHua.ClickSpanModel();
        ClickTextViewNcOpGeiNiHua.SpanModel textSpanModel = new ClickTextViewNcOpGeiNiHua.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new ClickTextViewNcOpGeiNiHua.ClickSpanModel();
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
        Observable<BaseNcOpGeiNiHuaModel<NcOpGeiNiHuaConfigModel>> observable = NcOpGeiNiHuaRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManagerNcOpGeiNiHua<BaseNcOpGeiNiHuaModel<NcOpGeiNiHuaConfigModel>>() {
                    @Override
                    public void onSuccess(BaseNcOpGeiNiHuaModel<NcOpGeiNiHuaConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        NcOpGeiNiHuaConfigModel ncOpGeiNiHuaConfigModel = model.getData();
                        if (ncOpGeiNiHuaConfigModel != null) {
                            NcOpGeiNiHuaSharePreferencesUtil.saveString("APP_MAIL", ncOpGeiNiHuaConfigModel.getAppMail());
                            isNeedVerification = "1".equals(ncOpGeiNiHuaConfigModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(ncOpGeiNiHuaConfigModel.getIsSelectLogin()));
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
        Observable<BaseNcOpGeiNiHuaModel<LoginNcOpGeiNiHuaModel>> observable = NcOpGeiNiHuaRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManagerNcOpGeiNiHua<BaseNcOpGeiNiHuaModel<LoginNcOpGeiNiHuaModel>>() {
                    @Override
                    public void onSuccess(BaseNcOpGeiNiHuaModel<LoginNcOpGeiNiHuaModel> model) {
                        if (model == null) {
                            return;
                        }
                        LoginNcOpGeiNiHuaModel loginNcOpGeiNiHuaModel = model.getData();
                        if (loginNcOpGeiNiHuaModel != null) {
                            NcOpGeiNiHuaSharePreferencesUtil.saveInt("mobileType", loginNcOpGeiNiHuaModel.getMobileType());
                            NcOpGeiNiHuaSharePreferencesUtil.saveString("phone", mobileStr);
                            NcOpGeiNiHuaSharePreferencesUtil.saveString("ip", ip);
                            NcOpGeiNiHuaStaticUtil.startActivity(NcOpGeiNiHuaLoginNcOpGeiNiHuaActivity.this, NcOpGeiNiHuaMainNcOpGeiNiHuaActivity.class, null);
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
        Observable<BaseNcOpGeiNiHuaModel> observable = NcOpGeiNiHuaRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManagerNcOpGeiNiHua<BaseNcOpGeiNiHuaModel>() {
                    @Override
                    public void onSuccess(BaseNcOpGeiNiHuaModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            NcOpGeiNiHuaToastUtil.showShort("发送成功");
                            NcOpGeiNiHuaCountDownTimerUtils mNcOpGeiNiHuaCountDownTimerUtils = new NcOpGeiNiHuaCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mNcOpGeiNiHuaCountDownTimerUtils.start();
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
