package com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.endhvwwkfiwe.njsrtaegs.R;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgapi.NewCodeXiaoNiuKuaiRetrofitManager;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgbase.BaseNewCodeXiaoNiuKuaiActivity;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgbase.NewCodeXiaoNiuKuaiObserverManager;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgmodel.BaseNewCodeXiaoNiuKuaiModel;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgmodel.ConfigNewCodeXiaoNiuKuaiModel;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgmodel.NewCodeXiaoNiuKuaiLoginModel;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgui.xiaoniuvwedfgadapter.NewCodeXiaoNiuKuaiBaseFragmentAdapter;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgui.xiaoniuvwedfgfragment.NewCodeXiaoNiuKuaiGodsListNewCodeXiaoNiuKuaiFragment;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgui.xiaoniuvwedfgfragment.MainNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiFragment;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgui.xiaoniuvwedfgfragment.MineNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiFragment;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgutil.NewCodeXiaoNiuKuaiSharePreferencesUtil;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgutil.NewCodeXiaoNiuKuaiStatusBarUtil;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgutil.ToastNewCodeXiaoNiuKuaiUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiActivity extends BaseNewCodeXiaoNiuKuaiActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.mnmzxzeryh, R.drawable.wwegzdh, R.drawable.cvhdfghaz};
    private int[] checkedIcon = {R.drawable.rrtxfhg, R.drawable.trhfgx, R.drawable.ifthjgvj};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_xiao_niu_kuai_sdf_efbdgh_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        NewCodeXiaoNiuKuaiStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiFragment());
        mFragments.add(new NewCodeXiaoNiuKuaiGodsListNewCodeXiaoNiuKuaiFragment());
        mFragments.add(new MineNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiFragment());

        homeViewPager.setAdapter(new NewCodeXiaoNiuKuaiBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastNewCodeXiaoNiuKuaiUtil.showShort("再按一次退出程序");
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
        phone = NewCodeXiaoNiuKuaiSharePreferencesUtil.getString("phone");
        ip = NewCodeXiaoNiuKuaiSharePreferencesUtil.getString("ip");
        Observable<BaseNewCodeXiaoNiuKuaiModel<NewCodeXiaoNiuKuaiLoginModel>> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseNewCodeXiaoNiuKuaiModel<NewCodeXiaoNiuKuaiLoginModel>>() {
                    @Override
                    public void onSuccess(BaseNewCodeXiaoNiuKuaiModel<NewCodeXiaoNiuKuaiLoginModel> model) {

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
        Observable<BaseNewCodeXiaoNiuKuaiModel<ConfigNewCodeXiaoNiuKuaiModel>> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseNewCodeXiaoNiuKuaiModel<ConfigNewCodeXiaoNiuKuaiModel>>() {
                    @Override
                    public void onSuccess(BaseNewCodeXiaoNiuKuaiModel<ConfigNewCodeXiaoNiuKuaiModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigNewCodeXiaoNiuKuaiModel configNewCodeXiaoNiuKuaiModel = model.getData();
                        if (configNewCodeXiaoNiuKuaiModel != null) {
                            NewCodeXiaoNiuKuaiSharePreferencesUtil.saveBool("NO_RECORD", !configNewCodeXiaoNiuKuaiModel.getVideoTape().equals("0"));
                            if (NewCodeXiaoNiuKuaiSharePreferencesUtil.getBool("NO_RECORD")) {
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