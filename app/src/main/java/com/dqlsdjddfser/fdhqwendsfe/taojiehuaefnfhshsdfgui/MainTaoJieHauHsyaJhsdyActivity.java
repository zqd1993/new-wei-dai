package com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.dqlsdjddfser.fdhqwendsfe.R;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgapi.TaoJieHauHsyaJhsdyRetrofitManager;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgbase.BaseTaoJieHauHsyaJhsdyActivity;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgbase.TaoJieHauHsyaJhsdyObserverManager;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyBaseModel;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyConfigModel;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyLoginModel;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui.rongjiesdfgwretadapter.TaoJieHauHsyaJhsdyBaseFragmentAdapter;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui.rongjiesdfgwretfragment.GodsListTaoJieHauHsyaJhsdyFragment;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui.rongjiesdfgwretfragment.MainTaoJieHauHsyaJhsdyFragment;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui.rongjiesdfgwretfragment.MineTaoJieHauHsyaJhsdyFragment;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.SharePreferencesUtilTaoJieHauHsyaJhsdy;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.TaoJieHauHsyaJhsdyStatusBarUtil;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.ToastRongjieSfFgdfUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainTaoJieHauHsyaJhsdyActivity extends BaseTaoJieHauHsyaJhsdyActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.nxfdyzery, R.drawable.nnsrtiraes, R.drawable.xfbrtdy};
    private int[] checkedIcon = {R.drawable.kyfofyjdfg, R.drawable.ergdfhsruy, R.drawable.xdfgdfy};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tao_jie_hua_djheru_fhentj_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        TaoJieHauHsyaJhsdyStatusBarUtil.setTransparent(this, false);
        TaoJieHauHsyaJhsdyStatusBarUtil.setLightMode(this);
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
        mFragments.add(new MainTaoJieHauHsyaJhsdyFragment());
        mFragments.add(new GodsListTaoJieHauHsyaJhsdyFragment());
        mFragments.add(new MineTaoJieHauHsyaJhsdyFragment());

        homeViewPager.setAdapter(new TaoJieHauHsyaJhsdyBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    public void changePage(){
        tabLayout.setCurrentTab(1);
        homeViewPager.setCurrentItem(1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastRongjieSfFgdfUtil.showShort("再按一次退出程序");
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
        phone = SharePreferencesUtilTaoJieHauHsyaJhsdy.getString("phone");
        ip = SharePreferencesUtilTaoJieHauHsyaJhsdy.getString("ip");
        Observable<TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyLoginModel>> observable = TaoJieHauHsyaJhsdyRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new TaoJieHauHsyaJhsdyObserverManager<TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyLoginModel>>() {
                    @Override
                    public void onSuccess(TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyLoginModel> model) {

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
        Observable<TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyConfigModel>> observable = TaoJieHauHsyaJhsdyRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new TaoJieHauHsyaJhsdyObserverManager<TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyConfigModel>>() {
                    @Override
                    public void onSuccess(TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        TaoJieHauHsyaJhsdyConfigModel taoJieHauHsyaJhsdyConfigModel = model.getData();
                        if (taoJieHauHsyaJhsdyConfigModel != null) {
                            SharePreferencesUtilTaoJieHauHsyaJhsdy.saveBool("NO_RECORD", !taoJieHauHsyaJhsdyConfigModel.getVideoTape().equals("0"));
                            if (SharePreferencesUtilTaoJieHauHsyaJhsdy.getBool("NO_RECORD")) {
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