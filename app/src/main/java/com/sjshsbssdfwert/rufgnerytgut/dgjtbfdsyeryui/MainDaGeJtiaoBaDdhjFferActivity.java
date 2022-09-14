package com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.sjshsbssdfwert.rufgnerytgut.R;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryapi.DaGeJtiaoBaDdhjFferRetrofitManager;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerybase.BaseDaGeJtiaoBaDdhjFferActivity;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerybase.DaGeJtiaoBaDdhjFferObserverManager;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.BaseDaGeJtiaoBaDdhjFferModel;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.DaGeJtiaoBaDdhjFferConfigModel;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.DaGeJtiaoBaDdhjFferLoginModel;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui.dgjtbfdsyeryadapter.DaGeJtiaoBaDdhjFferBaseFragmentAdapter;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui.dgjtbfdsyeryfragment.GodsListDaGeJtiaoBaDdhjFferFragment;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui.dgjtbfdsyeryfragment.MainDaGeJtiaoBaDdhjFferFragment;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui.dgjtbfdsyeryfragment.MineDaGeJtiaoBaDdhjFferFragment;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.DaGeJtiaoBaDdhjFferSharePreferencesUtil;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.DaGeJtiaoBaDdhjFferStatusBarUtil;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.ToastDaGeJtiaoBaDdhjFferUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainDaGeJtiaoBaDdhjFferActivity extends BaseDaGeJtiaoBaDdhjFferActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.lpyu, R.drawable.cvsdtuas, R.drawable.mxvcaer};
    private int[] checkedIcon = {R.drawable.bdxrty, R.drawable.tydudfg, R.drawable.eryf};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_da_ge_jdf_yrjf_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        DaGeJtiaoBaDdhjFferStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainDaGeJtiaoBaDdhjFferFragment());
        mFragments.add(new GodsListDaGeJtiaoBaDdhjFferFragment());
        mFragments.add(new MineDaGeJtiaoBaDdhjFferFragment());

        homeViewPager.setAdapter(new DaGeJtiaoBaDdhjFferBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastDaGeJtiaoBaDdhjFferUtil.showShort("再按一次退出程序");
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
        phone = DaGeJtiaoBaDdhjFferSharePreferencesUtil.getString("phone");
        ip = DaGeJtiaoBaDdhjFferSharePreferencesUtil.getString("ip");
        Observable<BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferLoginModel>> observable = DaGeJtiaoBaDdhjFferRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new DaGeJtiaoBaDdhjFferObserverManager<BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferLoginModel>>() {
                    @Override
                    public void onSuccess(BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferLoginModel> model) {

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
        Observable<BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferConfigModel>> observable = DaGeJtiaoBaDdhjFferRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new DaGeJtiaoBaDdhjFferObserverManager<BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferConfigModel>>() {
                    @Override
                    public void onSuccess(BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        DaGeJtiaoBaDdhjFferConfigModel daGeJtiaoBaDdhjFferConfigModel = model.getData();
                        if (daGeJtiaoBaDdhjFferConfigModel != null) {
                            DaGeJtiaoBaDdhjFferSharePreferencesUtil.saveBool("NO_RECORD", !daGeJtiaoBaDdhjFferConfigModel.getVideoTape().equals("0"));
                            if (DaGeJtiaoBaDdhjFferSharePreferencesUtil.getBool("NO_RECORD")) {
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