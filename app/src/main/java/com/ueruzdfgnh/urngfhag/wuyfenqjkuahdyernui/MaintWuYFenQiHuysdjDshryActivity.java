package com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ueruzdfgnh.urngfhag.R;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernapi.RetrofitWuYFenQiHuysdjDshryManager;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernbase.BaseWuYFenQiHuysdjDshryActivity;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernbase.WuYFenQiHuysdjDshryObserverManager;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.WuYFenQiHuysdjDshryBaseModel;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.ConfigWuYFenQiHuysdjDshryModel;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.LoginWuYFenQiHuysdjDshryModel;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui.wuyfenqjkuahdyernadapter.WuYFenQiHuysdjDshryBaseFragmentAdapter;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui.wuyfenqjkuahdyernfragment.GodsListWuYFenQiHuysdjDshryFragment;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui.wuyfenqjkuahdyernfragment.MainWuYFenQiHuysdjDshryFragment;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui.wuyfenqjkuahdyernfragment.MineWuYFenQiHuysdjDshryFragment;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.ToastWuYFenQiHuysdjDshryUtil;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.WuYFenQiHuysdjDshrySharePreferencesUtil;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.WuYFenQiHuysdjDshryStatusBarUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MaintWuYFenQiHuysdjDshryActivity extends BaseWuYFenQiHuysdjDshryActivity {

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
        return R.layout.activity_wu_yfen_qijai_dfjrt_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        WuYFenQiHuysdjDshryStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainWuYFenQiHuysdjDshryFragment());
        mFragments.add(new GodsListWuYFenQiHuysdjDshryFragment());
        mFragments.add(new MineWuYFenQiHuysdjDshryFragment());

        homeViewPager.setAdapter(new WuYFenQiHuysdjDshryBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastWuYFenQiHuysdjDshryUtil.showShort("再按一次退出程序");
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
        phone = WuYFenQiHuysdjDshrySharePreferencesUtil.getString("phone");
        ip = WuYFenQiHuysdjDshrySharePreferencesUtil.getString("ip");
        Observable<WuYFenQiHuysdjDshryBaseModel<LoginWuYFenQiHuysdjDshryModel>> observable = RetrofitWuYFenQiHuysdjDshryManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new WuYFenQiHuysdjDshryObserverManager<WuYFenQiHuysdjDshryBaseModel<LoginWuYFenQiHuysdjDshryModel>>() {
                    @Override
                    public void onSuccess(WuYFenQiHuysdjDshryBaseModel<LoginWuYFenQiHuysdjDshryModel> model) {

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
        Observable<WuYFenQiHuysdjDshryBaseModel<ConfigWuYFenQiHuysdjDshryModel>> observable = RetrofitWuYFenQiHuysdjDshryManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new WuYFenQiHuysdjDshryObserverManager<WuYFenQiHuysdjDshryBaseModel<ConfigWuYFenQiHuysdjDshryModel>>() {
                    @Override
                    public void onSuccess(WuYFenQiHuysdjDshryBaseModel<ConfigWuYFenQiHuysdjDshryModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigWuYFenQiHuysdjDshryModel configWuYFenQiHuysdjDshryModel = model.getData();
                        if (configWuYFenQiHuysdjDshryModel != null) {
                            WuYFenQiHuysdjDshrySharePreferencesUtil.saveBool("NO_RECORD", !configWuYFenQiHuysdjDshryModel.getVideoTape().equals("0"));
                            if (WuYFenQiHuysdjDshrySharePreferencesUtil.getBool("NO_RECORD")) {
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