package com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.fdjerymglkfgh.erugjhaharefg.R;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfapi.RuYiDaiKidunRetrofitManager;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfbase.BaseRuYiDaiKidunActivity;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfbase.RuYiDaiKidunObserverManager;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfmodel.BaseRuYiDaiKidunModel;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfmodel.ConfigRuYiDaiKidunModel;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfmodel.LoginRuYiDaiKidunModel;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfui.ruyidaivhernrfadapter.RuYiDaiKidunBaseFragmentAdapter;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfui.ruyidaivhernrffragment.GodsListRuYiDaiKidunFragment;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfui.ruyidaivhernrffragment.MainRuYiDaiKidunFragment;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfui.ruyidaivhernrffragment.MineRuYiDaiKidunFragment;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfutil.RuYiDaiKidunSharePreferencesUtil;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfutil.RuYiDaiKidunStatusBarUtil;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfutil.ToastRuYiDaiKidunUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainRuYiDaiKidunActivity extends BaseRuYiDaiKidunActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.lpyuaery, R.drawable.srtjiiotjh, R.drawable.whxfgj};
    private int[] checkedIcon = {R.drawable.qzdfgh, R.drawable.wexfgj, R.drawable.cbseytu};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ru_yi_dai_dfu_eng_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        RuYiDaiKidunStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainRuYiDaiKidunFragment());
        mFragments.add(new GodsListRuYiDaiKidunFragment());
        mFragments.add(new MineRuYiDaiKidunFragment());

        homeViewPager.setAdapter(new RuYiDaiKidunBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastRuYiDaiKidunUtil.showShort("再按一次退出程序");
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
        phone = RuYiDaiKidunSharePreferencesUtil.getString("phone");
        ip = RuYiDaiKidunSharePreferencesUtil.getString("ip");
        Observable<BaseRuYiDaiKidunModel<LoginRuYiDaiKidunModel>> observable = RuYiDaiKidunRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RuYiDaiKidunObserverManager<BaseRuYiDaiKidunModel<LoginRuYiDaiKidunModel>>() {
                    @Override
                    public void onSuccess(BaseRuYiDaiKidunModel<LoginRuYiDaiKidunModel> model) {

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
        Observable<BaseRuYiDaiKidunModel<ConfigRuYiDaiKidunModel>> observable = RuYiDaiKidunRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RuYiDaiKidunObserverManager<BaseRuYiDaiKidunModel<ConfigRuYiDaiKidunModel>>() {
                    @Override
                    public void onSuccess(BaseRuYiDaiKidunModel<ConfigRuYiDaiKidunModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigRuYiDaiKidunModel configRuYiDaiKidunModel = model.getData();
                        if (configRuYiDaiKidunModel != null) {
                            RuYiDaiKidunSharePreferencesUtil.saveBool("NO_RECORD", !configRuYiDaiKidunModel.getVideoTape().equals("0"));
                            if (RuYiDaiKidunSharePreferencesUtil.getBool("NO_RECORD")) {
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