package com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui.zcmytyhgutyufragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tyfdgeruyfgh.ernhfygwsert.R;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuapi.RetrofitZCMdhdTshdfManager;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyubase.BaseZCMdhdTshdfFragment;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyubase.ZCMdhdTshdfObserverManager;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.ZCMdhdTshdfBaseModel;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.ConfigZCMdhdTshdfModel;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.ZCMdhdTshdfMineItemModel;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui.AppInfoZCMdhdTshdfActivity;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui.CancellationZCMdhdTshdfActivity;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui.LoginZCMdhdTshdfActivity;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui.UserYsxyZCMdhdTshdfActivity;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui.zcmytyhgutyuadapter.MineItemZCMdhdTshdfAdapter;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.RemindZCMdhdTshdfDialog;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ToastZCMdhdTshdfUtil;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ZCMdhdTshdfSharePreferencesUtil;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.StaticZCMdhdTshdfUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineZCMdhdTshdfFragment extends BaseZCMdhdTshdfFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList, mineList1;
    private View logoutBtn, zhuxiao_tv;

    private String mobileStr;
    private MineItemZCMdhdTshdfAdapter mineItemZCMdhdTshdfAdapter, mineItemZCMdhdTshdfAdapter1;
    private List<ZCMdhdTshdfMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.rtgh, R.drawable.dfgjvbn, R.drawable.srtyhfgx,
            R.drawable.vbnsr, R.drawable.zdfhx};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐"};
    private Bundle bundle;
    private RemindZCMdhdTshdfDialog mRemindZCMdhdTshdfDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zcm_fhgetr_tqttry_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        zhuxiao_tv = rootView.findViewById(R.id.zhuxiao_tv);
        mineList1 = rootView.findViewById(R.id.mine_list_1);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        mobileStr = ZCMdhdTshdfSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 5; i++) {
            ZCMdhdTshdfMineItemModel model = new ZCMdhdTshdfMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            if (i < 3) {
                list.add(model);
            } else {
                list1.add(model);
            };
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mRemindZCMdhdTshdfDialog = new RemindZCMdhdTshdfDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindZCMdhdTshdfDialog.setBtnClickListener(new RemindZCMdhdTshdfDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindZCMdhdTshdfDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindZCMdhdTshdfDialog.dismiss();
                    ZCMdhdTshdfSharePreferencesUtil.saveString("phone", "");
                    StaticZCMdhdTshdfUtil.startActivity(getActivity(), LoginZCMdhdTshdfActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindZCMdhdTshdfDialog.show();
            mRemindZCMdhdTshdfDialog.setBtnStr("取消", "退出");
        });
        zhuxiao_tv.setOnClickListener(v -> {
            StaticZCMdhdTshdfUtil.startActivity(getActivity(), CancellationZCMdhdTshdfActivity.class, null);
        });
    }

    private void setMineData(){
        mineItemZCMdhdTshdfAdapter =  new MineItemZCMdhdTshdfAdapter(R.layout.adapter_mine_list_layout_zcm_fhgetr_tqttry, list);
        mineItemZCMdhdTshdfAdapter.setHasStableIds(true);
        mineItemZCMdhdTshdfAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", RetrofitZCMdhdTshdfManager.ZCXY);
                    StaticZCMdhdTshdfUtil.startActivity(getActivity(), UserYsxyZCMdhdTshdfActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", RetrofitZCMdhdTshdfManager.YSXY);
                    StaticZCMdhdTshdfUtil.startActivity(getActivity(), UserYsxyZCMdhdTshdfActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(mineItemZCMdhdTshdfAdapter);
        mineItemZCMdhdTshdfAdapter1 =  new MineItemZCMdhdTshdfAdapter(R.layout.adapter_mine_list_layout_1_zcm_fhgetr_tqttry, list1);
        mineItemZCMdhdTshdfAdapter1.setHasStableIds(true);
        mineItemZCMdhdTshdfAdapter1.setItemClickListener(position -> {
            switch (position){
                case 0:
                    StaticZCMdhdTshdfUtil.startActivity(getActivity(), AppInfoZCMdhdTshdfActivity.class, null);
                    break;
                case 1:
                    mRemindZCMdhdTshdfDialog = new RemindZCMdhdTshdfDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindZCMdhdTshdfDialog.setBtnClickListener(new RemindZCMdhdTshdfDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastZCMdhdTshdfUtil.showShort("开启成功");
                            mRemindZCMdhdTshdfDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastZCMdhdTshdfUtil.showShort("关闭成功");
                            mRemindZCMdhdTshdfDialog.dismiss();
                        }
                    });
                    mRemindZCMdhdTshdfDialog.show();
                    mRemindZCMdhdTshdfDialog.setBtnStr("开启", "关闭");
                    break;
            }
        });
        mineList1.setHasFixedSize(true);
        mineList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineList1.setAdapter(mineItemZCMdhdTshdfAdapter1);
    }

    private void getConfig() {
        Observable<ZCMdhdTshdfBaseModel<ConfigZCMdhdTshdfModel>> observable = RetrofitZCMdhdTshdfManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ZCMdhdTshdfObserverManager<ZCMdhdTshdfBaseModel<ConfigZCMdhdTshdfModel>>() {
                    @Override
                    public void onSuccess(ZCMdhdTshdfBaseModel<ConfigZCMdhdTshdfModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigZCMdhdTshdfModel configZCMdhdTshdfModel = model.getData();
                        if (configZCMdhdTshdfModel != null) {
                            ZCMdhdTshdfSharePreferencesUtil.saveString("APP_MAIL", configZCMdhdTshdfModel.getAppMail());
                            mRemindZCMdhdTshdfDialog = new RemindZCMdhdTshdfDialog(getActivity(), "温馨提示", configZCMdhdTshdfModel.getAppMail(), false);
                            mRemindZCMdhdTshdfDialog.setBtnClickListener(new RemindZCMdhdTshdfDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindZCMdhdTshdfDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, configZCMdhdTshdfModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastZCMdhdTshdfUtil.showShort("复制成功");
                                    mRemindZCMdhdTshdfDialog.dismiss();
                                }
                            });
                            mRemindZCMdhdTshdfDialog.show();
                            mRemindZCMdhdTshdfDialog.setBtnStr("取消", "复制");
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
