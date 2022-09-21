package com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ashasnasgasnzxdfge.asmasjasllldfrety.R;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrapi.YingJiHDSdJdgfsRetrofitManager;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrbase.YingJiHDSdJdgfsBaseActivity;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrbase.YingJiHDSdJdgfsObserverManager;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.YingJiHDSdJdgfsBaseModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.YingJiHDSdJdgfsConfigModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.LoginYingJiHDSdJdgfsModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui.yingjidrhfsdradapter.YingJiHDSdJdgfsBaseFragmentAdapter;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui.yingjidrhfsdrfragment.GodsListYingJiHDSdJdgfsFragment;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui.yingjidrhfsdrfragment.MainYingJiHDSdJdgfsFragment;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui.yingjidrhfsdrfragment.MineYingJiHDSdJdgfsFragment;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsSharePreferencesUtil;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsStatusBarUtil;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.ToastYingJiHDSdJdgfsUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityYingJiHDSdJdgfs extends YingJiHDSdJdgfsBaseActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.lpyrs, R.drawable.nbxfgyt, R.drawable.aethfg};
    private int[] checkedIcon = {R.drawable.earfxghj, R.drawable.fjnfg, R.drawable.erxfgh};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ying_ji_dh_jie_fuerty_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        YingJiHDSdJdgfsStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainYingJiHDSdJdgfsFragment());
        mFragments.add(new GodsListYingJiHDSdJdgfsFragment());
        mFragments.add(new MineYingJiHDSdJdgfsFragment());

        homeViewPager.setAdapter(new YingJiHDSdJdgfsBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastYingJiHDSdJdgfsUtil.showShort("再按一次退出程序");
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
        phone = YingJiHDSdJdgfsSharePreferencesUtil.getString("phone");
        ip = YingJiHDSdJdgfsSharePreferencesUtil.getString("ip");
        Observable<YingJiHDSdJdgfsBaseModel<LoginYingJiHDSdJdgfsModel>> observable = YingJiHDSdJdgfsRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new YingJiHDSdJdgfsObserverManager<YingJiHDSdJdgfsBaseModel<LoginYingJiHDSdJdgfsModel>>() {
                    @Override
                    public void onSuccess(YingJiHDSdJdgfsBaseModel<LoginYingJiHDSdJdgfsModel> model) {

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
        Observable<YingJiHDSdJdgfsBaseModel<YingJiHDSdJdgfsConfigModel>> observable = YingJiHDSdJdgfsRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new YingJiHDSdJdgfsObserverManager<YingJiHDSdJdgfsBaseModel<YingJiHDSdJdgfsConfigModel>>() {
                    @Override
                    public void onSuccess(YingJiHDSdJdgfsBaseModel<YingJiHDSdJdgfsConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        YingJiHDSdJdgfsConfigModel configCaiJieTongYshVdjrytModel = model.getData();
                        if (configCaiJieTongYshVdjrytModel != null) {
                            YingJiHDSdJdgfsSharePreferencesUtil.saveBool("NO_RECORD", !configCaiJieTongYshVdjrytModel.getVideoTape().equals("0"));
                            if (YingJiHDSdJdgfsSharePreferencesUtil.getBool("NO_RECORD")) {
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