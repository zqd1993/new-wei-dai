package com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yamansdjfernt.yongqbdrgrth.R;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretapi.RongjieSfFgdfRetrofitManager;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretbase.BaseRongjieSfFgdfActivity;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretbase.RongjieSfFgdfObserverManager;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretmodel.RongjieSfFgdfBaseModel;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretmodel.RongjieSfFgdfConfigModel;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretmodel.RongjieSfFgdfLoginModel;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretoaid.RongjieSfFgdfDevicesIDsHelper;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.ClickTextViewRongjieSfFgdf;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.RongjieSfFgdfCountDownTimerUtils;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.SharePreferencesUtilRongjieSfFgdf;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.StaticRongjieSfFgdfUtil;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.RongjieSfFgdfStatusBarUtil;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.ToastRongjieSfFgdfUtil;
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

public class RongjieSfFgdfLoginRongjieSfFgdfActivity extends BaseRongjieSfFgdfActivity implements RongjieSfFgdfDevicesIDsHelper.AppIdsUpdater{

    protected static final int RC_PERM = 123;

    private EditText mobileEt;
    private EditText verificationEt;
    private TextView getVerificationTv;
    private View loginBtn;
    private CheckBox remindCb;
    private ClickTextViewRongjieSfFgdf loginRemindTv;
    private RotateLoading rotateLoading;
    private View loadingFl;
    public View verificationLl;
    private View head_sl;

    private String mobileStr, verificationStr, ip, oaidStr;
    private boolean isNeedVerification;
    private Bundle bundle;
    private RongjieSfFgdfDevicesIDsHelper mRongjieSfFgdfDevicesIDsHelper;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rong_jie_sdf_brty_login;
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
            if (!StaticRongjieSfFgdfUtil.isMobile(mobileStr)) {
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
            rotateLoading.start();
            loadingFl.setVisibility(View.VISIBLE);
            login(mobileStr, verificationStr);
        });
        getVerificationTv.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)) {
                ToastRongjieSfFgdfUtil.showShort("请输入手机号");
                return;
            }
            if (!StaticRongjieSfFgdfUtil.isMobile(mobileStr)) {
                ToastRongjieSfFgdfUtil.showShort("请输入正确的手机号");
                return;
            }
            sendVerifyCode(mobileStr);
        });
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (position == 1) {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", RongjieSfFgdfRetrofitManager.ZCXY);
                StaticRongjieSfFgdfUtil.startActivity(RongjieSfFgdfLoginRongjieSfFgdfActivity.this, RongjieSfFgdfUserYsxyRongjieSfFgdfActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RongjieSfFgdfRetrofitManager.YSXY);
                StaticRongjieSfFgdfUtil.startActivity(RongjieSfFgdfLoginRongjieSfFgdfActivity.this, RongjieSfFgdfUserYsxyRongjieSfFgdfActivity.class, bundle);
            }
        }, "#E71C1A");
    }

    /**
     * 获取设备当前 OAID
     *
     */
    public void getOAID() {
        mRongjieSfFgdfDevicesIDsHelper = new RongjieSfFgdfDevicesIDsHelper(this);
        mRongjieSfFgdfDevicesIDsHelper.getOAID(this);
    }

    @Override
    public void initData() {
        if (SharePreferencesUtilRongjieSfFgdf.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        RongjieSfFgdfStatusBarUtil.setTransparent(this, false);
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

    private List<ClickTextViewRongjieSfFgdf.SpanModel> createSpanTexts() {
        List<ClickTextViewRongjieSfFgdf.SpanModel> spanModels = new ArrayList<>();
        ClickTextViewRongjieSfFgdf.ClickSpanModel spanModel = new ClickTextViewRongjieSfFgdf.ClickSpanModel();
        ClickTextViewRongjieSfFgdf.SpanModel textSpanModel = new ClickTextViewRongjieSfFgdf.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new ClickTextViewRongjieSfFgdf.ClickSpanModel();
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
        Observable<RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel>> observable = RongjieSfFgdfRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RongjieSfFgdfObserverManager<RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel>>() {
                    @Override
                    public void onSuccess(RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        RongjieSfFgdfConfigModel rongjieSfFgdfConfigModel = model.getData();
                        if (rongjieSfFgdfConfigModel != null) {
                            SharePreferencesUtilRongjieSfFgdf.saveString("APP_MAIL", rongjieSfFgdfConfigModel.getAppMail());
                            isNeedVerification = "1".equals(rongjieSfFgdfConfigModel.getIsCodeLogin());
                            verificationLl.setVisibility(isNeedVerification ? View.VISIBLE : View.GONE);
                            remindCb.setChecked("1".equals(rongjieSfFgdfConfigModel.getIsSelectLogin()));
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
        Observable<RongjieSfFgdfBaseModel<RongjieSfFgdfLoginModel>> observable = RongjieSfFgdfRetrofitManager.getRetrofitManager().
                getApiService().login(mobileStr, verificationStr, "", ip, "OAID", oaidStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RongjieSfFgdfObserverManager<RongjieSfFgdfBaseModel<RongjieSfFgdfLoginModel>>() {
                    @Override
                    public void onSuccess(RongjieSfFgdfBaseModel<RongjieSfFgdfLoginModel> model) {
                        if (model == null) {
                            return;
                        }
                        RongjieSfFgdfLoginModel rongjieSfFgdfLoginModel = model.getData();
                        if (rongjieSfFgdfLoginModel != null) {
                            SharePreferencesUtilRongjieSfFgdf.saveInt("mobileType", rongjieSfFgdfLoginModel.getMobileType());
                            SharePreferencesUtilRongjieSfFgdf.saveString("phone", mobileStr);
                            SharePreferencesUtilRongjieSfFgdf.saveString("ip", ip);
                            StaticRongjieSfFgdfUtil.startActivity(RongjieSfFgdfLoginRongjieSfFgdfActivity.this, RongjieSfFgdfMainRongjieSfFgdfActivity.class, null);
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
        Observable<RongjieSfFgdfBaseModel> observable = RongjieSfFgdfRetrofitManager.getRetrofitManager().
                getApiService().sendVerifyCode(mobileStr);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RongjieSfFgdfObserverManager<RongjieSfFgdfBaseModel>() {
                    @Override
                    public void onSuccess(RongjieSfFgdfBaseModel model) {
                        if (model == null) {
                            return;
                        }
                        if (model.getCode() == 200) {
                            ToastRongjieSfFgdfUtil.showShort("发送成功");
                            RongjieSfFgdfCountDownTimerUtils mRongjieSfFgdfCountDownTimerUtils = new RongjieSfFgdfCountDownTimerUtils(getVerificationTv, 60000, 1000);
                            mRongjieSfFgdfCountDownTimerUtils.start();
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
