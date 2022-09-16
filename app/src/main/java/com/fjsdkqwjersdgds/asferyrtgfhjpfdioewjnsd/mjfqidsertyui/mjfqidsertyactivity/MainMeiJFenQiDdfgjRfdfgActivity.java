package com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyactivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.R;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyapi.MeiJFenQiDdfgjRfdfgRetrofitManager;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertybase.BaseMeiJFenQiDdfgjRfdfgActivity;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertybase.MeiJFenQiDdfgjRfdfgObserverManager;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.BaseMeiJFenQiDdfgjRfdfgModel;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.MeiJFenQiDdfgjRfdfgConfigModel;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.MeiJFenQiDdfgjRfdfgLoginModel;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyadapter.MeiJFenQiDdfgjRfdfgBaseFragmentAdapter;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyfragment.GodsListFragmentMeiJFenQiDdfgjRfdfg;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyfragment.MainFragmentMeiJFenQiDdfgjRfdfg;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyfragment.MineFragmentMeiJFenQiDdfgjRfdfg;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.MeiJFenQiDdfgjRfdfgSharePreferencesUtil;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.MeiJFenQiDdfgjRfdfgStatusBarUtil;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.ToastMeiJFenQiDdfgjRfdfgUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainMeiJFenQiDdfgjRfdfgActivity extends BaseMeiJFenQiDdfgjRfdfgActivity {

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
        return R.layout.activity_mei_jie_sfgh_ewyfhg_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        MeiJFenQiDdfgjRfdfgStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainFragmentMeiJFenQiDdfgjRfdfg());
        mFragments.add(new GodsListFragmentMeiJFenQiDdfgjRfdfg());
        mFragments.add(new MineFragmentMeiJFenQiDdfgjRfdfg());

        homeViewPager.setAdapter(new MeiJFenQiDdfgjRfdfgBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastMeiJFenQiDdfgjRfdfgUtil.showShort("再按一次退出程序");
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
        phone = MeiJFenQiDdfgjRfdfgSharePreferencesUtil.getString("phone");
        ip = MeiJFenQiDdfgjRfdfgSharePreferencesUtil.getString("ip");
        Observable<BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgLoginModel>> observable = MeiJFenQiDdfgjRfdfgRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MeiJFenQiDdfgjRfdfgObserverManager<BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgLoginModel>>() {
                    @Override
                    public void onSuccess(BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgLoginModel> model) {

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
        Observable<BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgConfigModel>> observable = MeiJFenQiDdfgjRfdfgRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MeiJFenQiDdfgjRfdfgObserverManager<BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgConfigModel>>() {
                    @Override
                    public void onSuccess(BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        MeiJFenQiDdfgjRfdfgConfigModel meiJFenQiDdfgjRfdfgConfigModel = model.getData();
                        if (meiJFenQiDdfgjRfdfgConfigModel != null) {
                            MeiJFenQiDdfgjRfdfgSharePreferencesUtil.saveBool("NO_RECORD", !meiJFenQiDdfgjRfdfgConfigModel.getVideoTape().equals("0"));
                            if (MeiJFenQiDdfgjRfdfgSharePreferencesUtil.getBool("NO_RECORD")) {
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