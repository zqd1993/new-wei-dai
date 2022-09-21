package com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrbase;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class YingJiHDSdJdgfsObserverManager<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
        onDisposable(d);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFail(e);
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    public abstract void onSuccess(T t);                        //调用成功
    public abstract void onFail(Throwable throwable);           //调用失败或者报错
    public abstract void onFinish();                            //调用完成
    public abstract void onDisposable(Disposable disposable);   //调用前准备工作
}
