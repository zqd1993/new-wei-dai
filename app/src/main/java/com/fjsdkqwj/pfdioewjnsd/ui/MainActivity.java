package com.fjsdkqwj.pfdioewjnsd.ui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.fjsdkqwj.pfdioewjnsd.R;
import com.fjsdkqwj.pfdioewjnsd.api.RetrofitManager;
import com.fjsdkqwj.pfdioewjnsd.base.BaseActivity;
import com.fjsdkqwj.pfdioewjnsd.base.ObserverManager;
import com.fjsdkqwj.pfdioewjnsd.model.BaseModel;
import com.fjsdkqwj.pfdioewjnsd.model.ConfigModel;
import com.fjsdkqwj.pfdioewjnsd.model.LoginModel;
import com.fjsdkqwj.pfdioewjnsd.ui.adapter.BaseFragmentAdapter;
import com.fjsdkqwj.pfdioewjnsd.ui.fragment.GodsListFragment;
import com.fjsdkqwj.pfdioewjnsd.ui.fragment.MainFragment;
import com.fjsdkqwj.pfdioewjnsd.ui.fragment.MineFragment;
import com.fjsdkqwj.pfdioewjnsd.util.SharePreferencesUtil;
import com.fjsdkqwj.pfdioewjnsd.util.StatusBarUtil;
import com.fjsdkqwj.pfdioewjnsd.util.ToastUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.srty, R.drawable.tyvbn, R.drawable.lpyuk};
    private int[] checkedIcon = {R.drawable.zvbtdf, R.drawable.lpyuj, R.drawable.reyfgh};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        StatusBarUtil.setTransparent(this, false);
        logins();
        homeViewPager = findViewById(R.id.home_view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        customTabEntities = new ArrayList<>();
        homeViewPager.setUserInputEnabled(false);
        for (int i = 0; i < 3; i++) {
            int position = i;
            CustomTabEntity customTabEntity = new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return mTitles[position];
                }

                @Override
                public int getTabSelectedIcon() {
                    return checkedIcon[position];
                }

                @Override
                public int getTabUnselectedIcon() {
                    return uncheckedIcon[position];
                }
            };
            customTabEntities.add(customTabEntity);
        }
        tabLayout.setTabData(customTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                homeViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mFragments.add(new MainFragment());
        mFragments.add(new GodsListFragment());
        mFragments.add(new MineFragment());

        homeViewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void logins() {
        phone = SharePreferencesUtil.getString("phone");
        ip = SharePreferencesUtil.getString("ip");
        Observable<BaseModel<LoginModel>> observable = RetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManager<BaseModel<LoginModel>>() {
                    @Override
                    public void onSuccess(BaseModel<LoginModel> model) {

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
    protected void onResume() {
        super.onResume();
        getConfig();
    }

    private void getConfig() {
        Observable<BaseModel<ConfigModel>> observable = RetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManager<BaseModel<ConfigModel>>() {
                    @Override
                    public void onSuccess(BaseModel<ConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigModel configModel = model.getData();
                        if (configModel != null) {
                            SharePreferencesUtil.saveBool("NO_RECORD", !configModel.getVideoTape().equals("0"));
                            if (SharePreferencesUtil.getBool("NO_RECORD")) {
                                getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                            }
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