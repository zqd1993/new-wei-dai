package com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.R;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaapi.NcOpGeiNiHuaRetrofitManager;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuabase.BaseNcOpGeiNiHuaActivity;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuabase.ObserverManagerNcOpGeiNiHua;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuamodel.BaseNcOpGeiNiHuaModel;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuamodel.NcOpGeiNiHuaConfigModel;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuamodel.LoginNcOpGeiNiHuaModel;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaui.ncopgeinihuaadapter.NcOpGeiNiHuaBaseFragmentAdapter;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaui.ncopgeinihuafragment.NcOpGeiNiHuaGodsListFragmentNcOpGeiNiHua;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaui.ncopgeinihuafragment.NcOpGeiNiHuaMainFragmentNcOpGeiNiHua;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaui.ncopgeinihuafragment.NcOpGeiNiHuaMineFragmentNcOpGeiNiHua;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaSharePreferencesUtil;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaStatusBarUtil;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaToastUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NcOpGeiNiHuaMainNcOpGeiNiHuaActivity extends BaseNcOpGeiNiHuaActivity {

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
        return R.layout.activity_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        NcOpGeiNiHuaStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new NcOpGeiNiHuaMainFragmentNcOpGeiNiHua());
        mFragments.add(new NcOpGeiNiHuaGodsListFragmentNcOpGeiNiHua());
        mFragments.add(new NcOpGeiNiHuaMineFragmentNcOpGeiNiHua());

        homeViewPager.setAdapter(new NcOpGeiNiHuaBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                NcOpGeiNiHuaToastUtil.showShort("再按一次退出程序");
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
        phone = NcOpGeiNiHuaSharePreferencesUtil.getString("phone");
        ip = NcOpGeiNiHuaSharePreferencesUtil.getString("ip");
        Observable<BaseNcOpGeiNiHuaModel<LoginNcOpGeiNiHuaModel>> observable = NcOpGeiNiHuaRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManagerNcOpGeiNiHua<BaseNcOpGeiNiHuaModel<LoginNcOpGeiNiHuaModel>>() {
                    @Override
                    public void onSuccess(BaseNcOpGeiNiHuaModel<LoginNcOpGeiNiHuaModel> model) {

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
        Observable<BaseNcOpGeiNiHuaModel<NcOpGeiNiHuaConfigModel>> observable = NcOpGeiNiHuaRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManagerNcOpGeiNiHua<BaseNcOpGeiNiHuaModel<NcOpGeiNiHuaConfigModel>>() {
                    @Override
                    public void onSuccess(BaseNcOpGeiNiHuaModel<NcOpGeiNiHuaConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        NcOpGeiNiHuaConfigModel ncOpGeiNiHuaConfigModel = model.getData();
                        if (ncOpGeiNiHuaConfigModel != null) {
                            NcOpGeiNiHuaSharePreferencesUtil.saveBool("NO_RECORD", !ncOpGeiNiHuaConfigModel.getVideoTape().equals("0"));
                            if (NcOpGeiNiHuaSharePreferencesUtil.getBool("NO_RECORD")) {
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