package com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.R;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaapi.NcOpGeiNiHuaRetrofitManager;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuabase.BaseNcOpGeiNiHuaActivity;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuabase.ObserverManagerNcOpGeiNiHua;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuamodel.BaseNcOpGeiNiHuaModel;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuamodel.NcOpGeiNiHuaConfigModel;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuamodel.LoginNcOpGeiNiHuaModel;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaoaid.NcOpGeiNiHuaDevicesIDsHelper;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.ClickTextViewNcOpGeiNiHua;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaCountDownTimerUtils;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaSharePreferencesUtil;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaStaticUtil;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaStatusBarUtil;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaToastUtil;
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

public class NcOpGeiNiHuaLoginNcOpGeiNiHuaActivity extends BaseNcOpGeiNiHuaActivity implements NcOpGeiNiHuaDevicesIDsHelper.AppIdsUpdater{

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
    private boolean isNeedVerification;
    private Bundle bundle;
    private NcOpGeiNiHuaDevicesIDsHelper mNcOpGeiNiHuaDevicesIDsHelper;

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
            rotateLoading.start();
            loadingFl.setVisibility(View.VISIBLE);
            login(mobileStr, verificationStr);
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

    /**
     * 获取设备当前 OAID
     *
     */
    public void getOAID() {
        mNcOpGeiNiHuaDevicesIDsHelper = new NcOpGeiNiHuaDevicesIDsHelper(this);
        mNcOpGeiNiHuaDevicesIDsHelper.getOAID(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        getOAID();
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
                getApiService().login(mobileStr, verificationStr, "", ip, "OAID", oaidStr);

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
