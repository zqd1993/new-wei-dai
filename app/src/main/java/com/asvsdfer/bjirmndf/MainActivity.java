package com.asvsdfer.bjirmndf;

import android.util.Log;
import android.widget.TextView;

import com.asvsdfer.bjirmndf.api.RetrofitManager;
import com.asvsdfer.bjirmndf.base.BaseActivity;
import com.asvsdfer.bjirmndf.base.ObserverManager;
import com.asvsdfer.bjirmndf.model.BaseModel;
import com.asvsdfer.bjirmndf.model.ConfigEntity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    TextView tv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initListener() {
        tv.setOnClickListener(v -> {getConfig();});
    }

    @Override
    public void initData() {
        tv = (TextView) findViewById(R.id.tv);
    }

    private void getConfig(){
        Observable<BaseModel<ConfigEntity>> observable = RetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManager<BaseModel<ConfigEntity>>() {
                    @Override
                    public void onSuccess(BaseModel<ConfigEntity> model) {
                        Log.d("zqd", model.getData().getAppMail());
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable",throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        Log.e("请求验证码结果","完成");
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }
}