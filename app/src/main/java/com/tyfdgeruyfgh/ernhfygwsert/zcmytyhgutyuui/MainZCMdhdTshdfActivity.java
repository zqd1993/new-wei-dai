package com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.tyfdgeruyfgh.ernhfygwsert.R;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuapi.RetrofitZCMdhdTshdfManager;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyubase.BaseZCMdhdTshdfActivity;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyubase.ZCMdhdTshdfObserverManager;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.ZCMdhdTshdfBaseModel;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.ConfigZCMdhdTshdfModel;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.LoginZCMdhdTshdfModel;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui.zcmytyhgutyuadapter.ZCMdhdTshdfBaseFragmentAdapter;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui.zcmytyhgutyufragment.GodsListZCMdhdTshdfFragment;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui.zcmytyhgutyufragment.MainZCMdhdTshdfFragment;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui.zcmytyhgutyufragment.MineZCMdhdTshdfFragment;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ZCMdhdTshdfSharePreferencesUtil;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ZCMdhdTshdfStatusBarUtil;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ToastZCMdhdTshdfUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainZCMdhdTshdfActivity extends BaseZCMdhdTshdfActivity {

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
        return R.layout.activity_zcm_fhgetr_tqttry_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        ZCMdhdTshdfStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainZCMdhdTshdfFragment());
        mFragments.add(new GodsListZCMdhdTshdfFragment());
        mFragments.add(new MineZCMdhdTshdfFragment());

        homeViewPager.setAdapter(new ZCMdhdTshdfBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastZCMdhdTshdfUtil.showShort("再按一次退出程序");
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
        phone = ZCMdhdTshdfSharePreferencesUtil.getString("phone");
        ip = ZCMdhdTshdfSharePreferencesUtil.getString("ip");
        Observable<ZCMdhdTshdfBaseModel<LoginZCMdhdTshdfModel>> observable = RetrofitZCMdhdTshdfManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ZCMdhdTshdfObserverManager<ZCMdhdTshdfBaseModel<LoginZCMdhdTshdfModel>>() {
                    @Override
                    public void onSuccess(ZCMdhdTshdfBaseModel<LoginZCMdhdTshdfModel> model) {

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
        Observable<ZCMdhdTshdfBaseModel<ConfigZCMdhdTshdfModel>> observable = RetrofitZCMdhdTshdfManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ZCMdhdTshdfObserverManager<ZCMdhdTshdfBaseModel<ConfigZCMdhdTshdfModel>>() {
                    @Override
                    public void onSuccess(ZCMdhdTshdfBaseModel<ConfigZCMdhdTshdfModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigZCMdhdTshdfModel configZCMdhdTshdfModel = model.getData();
                        if (configZCMdhdTshdfModel != null) {
                            ZCMdhdTshdfSharePreferencesUtil.saveBool("NO_RECORD", !configZCMdhdTshdfModel.getVideoTape().equals("0"));
                            if (ZCMdhdTshdfSharePreferencesUtil.getBool("NO_RECORD")) {
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