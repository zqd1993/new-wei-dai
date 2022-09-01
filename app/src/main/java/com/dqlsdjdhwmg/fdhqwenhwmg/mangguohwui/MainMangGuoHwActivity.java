package com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.dqlsdjdhwmg.fdhqwenhwmg.R;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwapi.MangGuoHwRetrofitManager;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwbase.BaseMangGuoHwActivity;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwbase.ObserverMangGuoHwManager;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.BaseMangGuoHwModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.MangGuoHwConfigModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.LoginMangGuoHwModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui.mangguohwadapter.BaseFragmentAdapterMangGuoHw;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui.mangguohwfragment.GodsListMangGuoHwFragment;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui.mangguohwfragment.MainMangGuoHwFragment;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui.mangguohwfragment.MineMangGuoHwFragment;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.MangGuoHwSharePreferencesUtils;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.StatusBarUtilMangGuoHw;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.ToastMangGuoHwUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainMangGuoHwActivity extends BaseMangGuoHwActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.shouye2, R.drawable.jingxuan2, R.drawable.wode2};
    private int[] checkedIcon = {R.drawable.shouye1, R.drawable.jingxuan1, R.drawable.wode1};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_mang_guo_hw;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        StatusBarUtilMangGuoHw.setTransparent(this, false);
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
        mFragments.add(new MainMangGuoHwFragment());
        mFragments.add(new GodsListMangGuoHwFragment());
        mFragments.add(new MineMangGuoHwFragment());

        homeViewPager.setAdapter(new BaseFragmentAdapterMangGuoHw(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastMangGuoHwUtil.showShort("再按一次退出程序");
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
        phone = MangGuoHwSharePreferencesUtils.getString("phone");
        ip = MangGuoHwSharePreferencesUtils.getString("ip");
        Observable<BaseMangGuoHwModel<LoginMangGuoHwModel>> observable = MangGuoHwRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverMangGuoHwManager<BaseMangGuoHwModel<LoginMangGuoHwModel>>() {
                    @Override
                    public void onSuccess(BaseMangGuoHwModel<LoginMangGuoHwModel> model) {

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
        Observable<BaseMangGuoHwModel<MangGuoHwConfigModel>> observable = MangGuoHwRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverMangGuoHwManager<BaseMangGuoHwModel<MangGuoHwConfigModel>>() {
                    @Override
                    public void onSuccess(BaseMangGuoHwModel<MangGuoHwConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        MangGuoHwConfigModel mangGuoHwConfigModel = model.getData();
                        if (mangGuoHwConfigModel != null) {
                            MangGuoHwSharePreferencesUtils.saveBool("NO_RECORD", !mangGuoHwConfigModel.getVideoTape().equals("0"));
                            if (MangGuoHwSharePreferencesUtils.getBool("NO_RECORD")) {
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