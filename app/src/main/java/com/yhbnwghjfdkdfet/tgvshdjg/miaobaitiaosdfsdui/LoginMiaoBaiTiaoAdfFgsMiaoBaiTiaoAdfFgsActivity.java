package com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yhbnwghjfdkdfet.tgvshdjg.R;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdapi.MiaoBaiTiaoAdfFgsRetrofitManager;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdbase.BaseMiaoBaiTiaoAdfFgsActivity;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdbase.MiaoBaiTiaoAdfFgsObserverManager;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.MiaoBaiTiaoAdfFgsBaseModel;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.ConfigMiaoBaiTiaoAdfFgsModel;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.LoginMiaoBaiTiaoAdfFgsModel;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdoaid.MiaoBaiTiaoAdfFgsDevicesIDsHelper;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.ClickMiaoBaiTiaoAdfFgsTextView;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.MiaoBaiTiaoAdfFgsCountDownTimerUtils;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.SharePreferencesMiaoBaiTiaoAdfFgsUtil;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.StaticMiaoBaiTiaoAdfFgsUtil;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.MiaoBaiTiaoAdfFgsStatusBarUtil;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.ToastMiaoBaiTiaoAdfFgsUtil;
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

public class LoginMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity extends BaseMiaoBaiTiaoAdfFgsActivity implements MiaoBaiTiaoAdfFgsDevicesIDsHelper.AppIdsUpdater{

    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private ClickMiaoBaiTiaoAdfFgsTextView loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification;
    private Bundle bundle;
    private MiaoBaiTiaoAdfFgsDevicesIDsHelper mDevicesIDsHelper;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_miao_bai_tiao_sdf;
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastMiaoBaiTiaoAdfFgsUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticMiaoBaiTiaoAdfFgsUtil.isMobile(mobileStr)) {
                ToastMiaoBaiTiaoAdfFgsUtil.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastMiaoBaiTiaoAdfFgsUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastMiaoBaiTiaoAdfFgsUtil.showShort("请阅读用户协议及隐私政策");
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
                ToastMiaoBaiTiaoAdfFgsUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticMiaoBaiTiaoAdfFgsUtil.isMobile(mobileStr)) {
                ToastMiaoBaiTiaoAdfFgsUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", MiaoBaiTiaoAdfFgsRetrofitManager.ZCXY);
                StaticMiaoBaiTiaoAdfFgsUtil.startActivity(LoginMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity.this, MiaoBaiTiaoAdfFgsUserYsxyMiaoBaiTiaoAdfFgsActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", MiaoBaiTiaoAdfFgsRetrofitManager.YSXY);
                StaticMiaoBaiTiaoAdfFgsUtil.startActivity(LoginMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity.this, MiaoBaiTiaoAdfFgsUserYsxyMiaoBaiTiaoAdfFgsActivity.class, bundle);
            }
        }, "#FFF1D2");
    }

    /**
     * 获取设备当前 OAID
     *
     */
    public void getOAID() {
        mDevicesIDsHelper = new MiaoBaiTiaoAdfFgsDevicesIDsHelper(this);
        mDevicesIDsHelper.getOAID(this);
    }

    @Override
    public void initData() {
        if (SharePreferencesMiaoBaiTiaoAdfFgsUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        MiaoBaiTiaoAdfFgsStatusBarUtil.setTransparent(this, false);
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

    private List<ClickMiaoBaiTiaoAdfFgsTextView.SpanModel> createSpanTexts() {
        List<ClickMiaoBaiTiaoAdfFgsTextView.SpanModel> spanModels = new ArrayList<>();
        ClickMiaoBaiTiaoAdfFgsTextView.ClickSpanModel spanModel = new ClickMiaoBaiTiaoAdfFgsTextView.ClickSpanModel();
        ClickMiaoBaiTiaoAdfFgsTextView.SpanModel textSpanModel = new ClickMiaoBaiTiaoAdfFgsTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new ClickMiaoBaiTiaoAdfFgsTextView.ClickSpanModel();
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

    @Override
    protected void onResume() {
        super.onResume();
        getOAID();
    }

    private void getConfig() {
        Observable<MiaoBaiTiaoAdfFgsBaseModel<ConfigMiaoBaiTiaoAdfFgsModel>> observable = MiaoBaiTiaoAdfFgsRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<MiaoBaiTiaoAdfFgsBaseModel<ConfigMiaoBaiTiaoAdfFgsModel>>() {
                    @Override
                    public void onSuccess(MiaoBaiTiaoAdfFgsBaseModel<ConfigMiaoBaiTiaoAdfFgsModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigMiaoBaiTiaoAdfFgsModel configMiaoBaiTiaoAdfFgsModel = model.getData();
                        if (configMiaoBaiTiaoAdfFgsModel != null) {
                            SharePreferencesMiaoBaiTiaoAdfFgsUtil.saveString("APP_MAIL", configMiaoBaiTiaoAdfFgsModel.getAppMail());
                            isNeedVerification = "1".equals(configMiaoBaiTiaoAdfFgsModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(configMiaoBaiTiaoAdfFgsModel.getIsSelectLogin()));
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
        Observable<MiaoBaiTiaoAdfFgsBaseModel<LoginMiaoBaiTiaoAdfFgsModel>> observable = MiaoBaiTiaoAdfFgsRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, "OAID", oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<MiaoBaiTiaoAdfFgsBaseModel<LoginMiaoBaiTiaoAdfFgsModel>>() {
                    @Override
                    public void onSuccess(MiaoBaiTiaoAdfFgsBaseModel<LoginMiaoBaiTiaoAdfFgsModel> model) {
                        if (model == null) {
                            return;
                        }
                        LoginMiaoBaiTiaoAdfFgsModel loginMiaoBaiTiaoAdfFgsModel = model.getData();
                        if (loginMiaoBaiTiaoAdfFgsModel != null) {
                            SharePreferencesMiaoBaiTiaoAdfFgsUtil.saveInt("mobileType", loginMiaoBaiTiaoAdfFgsModel.getMobileType());
                            SharePreferencesMiaoBaiTiaoAdfFgsUtil.saveString("phone", mobileStr);
                            SharePreferencesMiaoBaiTiaoAdfFgsUtil.saveString("ip", ip);
                            StaticMiaoBaiTiaoAdfFgsUtil.startActivity(LoginMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity.this, MainMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity.class, null);
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
        Observable<MiaoBaiTiaoAdfFgsBaseModel> observable = MiaoBaiTiaoAdfFgsRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<MiaoBaiTiaoAdfFgsBaseModel>() {
                    @Override
                    public void onSuccess(MiaoBaiTiaoAdfFgsBaseModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastMiaoBaiTiaoAdfFgsUtil.showShort("发送成功");
                            MiaoBaiTiaoAdfFgsCountDownTimerUtils mMiaoBaiTiaoAdfFgsCountDownTimerUtils = new MiaoBaiTiaoAdfFgsCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mMiaoBaiTiaoAdfFgsCountDownTimerUtils.start();
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

    @Override
    public void OnIdsAvalid(@NonNull String ids, boolean support) {
        if (TextUtils.isEmpty(ids)){
            oaidStr = "";
        } else {
            int length = ids.length();
            if (length < 64){
                for (int i = 0; i < 64 - length; i++){
                    ids = ids + "0";
                }
            }
            oaidStr = ids;
        }
    }

}
