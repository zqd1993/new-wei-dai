package com.qingsongvyrnng.mrjgndsdg.qingsojdkvui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.qingsongvyrnng.mrjgndsdg.R;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvapi.BaseQingSongShfjAFduRetrofitManager;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvbase.BaseBaseQingSongShfjAFduActivity;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvbase.BaseQingSongShfjAFduObserverManager;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.BaseQingSongShfjAFduModel;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.BaseQingSongShfjAFduConfigModel;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.BaseQingSongShfjAFduLoginModel;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvui.qingsojdkvadapter.BaseQingSongShfjAFduBaseFragmentAdapter;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvui.qingsojdkvfragment.GodsListQingSongShfjAFduFragmentBase;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvui.qingsojdkvfragment.MainQingSongShfjAFduFragmentBase;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvui.qingsojdkvfragment.MineQingSongShfjAFduFragmentBase;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.BaseQingSongShfjAFduSharePreferencesUtil;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.BaseQingSongShfjAFduStatusBarUtil;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.ToastBaseQingSongShfjAFduUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainQingSongShfjAFduActivityBase extends BaseBaseQingSongShfjAFduActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.adfh, R.drawable.vbzxdrt, R.drawable.nsrtyhxf};
    private int[] checkedIcon = {R.drawable.lpyukd, R.drawable.utryxf, R.drawable.bdr};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qing_song_sh_udj_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        BaseQingSongShfjAFduStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainQingSongShfjAFduFragmentBase());
        mFragments.add(new GodsListQingSongShfjAFduFragmentBase());
        mFragments.add(new MineQingSongShfjAFduFragmentBase());

        homeViewPager.setAdapter(new BaseQingSongShfjAFduBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastBaseQingSongShfjAFduUtil.showShort("再按一次退出程序");
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
        phone = BaseQingSongShfjAFduSharePreferencesUtil.getString("phone");
        ip = BaseQingSongShfjAFduSharePreferencesUtil.getString("ip");
        Observable<BaseQingSongShfjAFduModel<BaseQingSongShfjAFduLoginModel>> observable = BaseQingSongShfjAFduRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQingSongShfjAFduObserverManager<BaseQingSongShfjAFduModel<BaseQingSongShfjAFduLoginModel>>() {
                    @Override
                    public void onSuccess(BaseQingSongShfjAFduModel<BaseQingSongShfjAFduLoginModel> model) {

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
        Observable<BaseQingSongShfjAFduModel<BaseQingSongShfjAFduConfigModel>> observable = BaseQingSongShfjAFduRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQingSongShfjAFduObserverManager<BaseQingSongShfjAFduModel<BaseQingSongShfjAFduConfigModel>>() {
                    @Override
                    public void onSuccess(BaseQingSongShfjAFduModel<BaseQingSongShfjAFduConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        BaseQingSongShfjAFduConfigModel baseQingSongShfjAFduConfigModel = model.getData();
                        if (baseQingSongShfjAFduConfigModel != null) {
                            BaseQingSongShfjAFduSharePreferencesUtil.saveBool("NO_RECORD", !baseQingSongShfjAFduConfigModel.getVideoTape().equals("0"));
                            if (BaseQingSongShfjAFduSharePreferencesUtil.getBool("NO_RECORD")) {
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