package com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.R;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyapi.CaiJieTongYshVdjrytRetrofitManager;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhybase.CaiJieTongYshVdjrytBaseActivity;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhybase.CaiJieTongYshVdjrytObserverManager;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhymodel.CaiJieTongYshVdjrytBaseModel;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhymodel.ConfigCaiJieTongYshVdjrytModel;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhymodel.LoginCaiJieTongYshVdjrytModel;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui.caijietongshrtnhyadapter.CaiJieTongYshVdjrytBaseFragmentAdapter;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui.caijietongshrtnhyfragment.GodsListCaiJieTongYshVdjrytFragment;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui.caijietongshrtnhyfragment.MainCaiJieTongYshVdjrytFragment;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui.caijietongshrtnhyfragment.MineCaiJieTongYshVdjrytFragment;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.CaiJieTongYshVdjrytSharePreferencesUtil;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.CaiJieTongYshVdjrytStatusBarUtil;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.ToastCaiJieTongYshVdjrytUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityCaiJieTongYshVdjryt extends CaiJieTongYshVdjrytBaseActivity {

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
        return R.layout.activity_cai_jie_tong_drt_etfnh_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        CaiJieTongYshVdjrytStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainCaiJieTongYshVdjrytFragment());
        mFragments.add(new GodsListCaiJieTongYshVdjrytFragment());
        mFragments.add(new MineCaiJieTongYshVdjrytFragment());

        homeViewPager.setAdapter(new CaiJieTongYshVdjrytBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastCaiJieTongYshVdjrytUtil.showShort("再按一次退出程序");
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
        phone = CaiJieTongYshVdjrytSharePreferencesUtil.getString("phone");
        ip = CaiJieTongYshVdjrytSharePreferencesUtil.getString("ip");
        Observable<CaiJieTongYshVdjrytBaseModel<LoginCaiJieTongYshVdjrytModel>> observable = CaiJieTongYshVdjrytRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new CaiJieTongYshVdjrytObserverManager<CaiJieTongYshVdjrytBaseModel<LoginCaiJieTongYshVdjrytModel>>() {
                    @Override
                    public void onSuccess(CaiJieTongYshVdjrytBaseModel<LoginCaiJieTongYshVdjrytModel> model) {

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
        Observable<CaiJieTongYshVdjrytBaseModel<ConfigCaiJieTongYshVdjrytModel>> observable = CaiJieTongYshVdjrytRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new CaiJieTongYshVdjrytObserverManager<CaiJieTongYshVdjrytBaseModel<ConfigCaiJieTongYshVdjrytModel>>() {
                    @Override
                    public void onSuccess(CaiJieTongYshVdjrytBaseModel<ConfigCaiJieTongYshVdjrytModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigCaiJieTongYshVdjrytModel configCaiJieTongYshVdjrytModel = model.getData();
                        if (configCaiJieTongYshVdjrytModel != null) {
                            CaiJieTongYshVdjrytSharePreferencesUtil.saveBool("NO_RECORD", !configCaiJieTongYshVdjrytModel.getVideoTape().equals("0"));
                            if (CaiJieTongYshVdjrytSharePreferencesUtil.getBool("NO_RECORD")) {
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