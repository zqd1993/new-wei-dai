package com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui.yingjidrhfsdrfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashasnasgasnzxdfge.asmasjasllldfrety.R;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrapi.YingJiHDSdJdgfsRetrofitManager;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrbase.BaseYingJiHDSdJdgfsFragment;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrbase.YingJiHDSdJdgfsObserverManager;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.YingJiHDSdJdgfsBaseModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.YingJiHDSdJdgfsConfigModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.YingJiHDSdJdgfsMineItemModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui.AppInfoActivityYingJiHDSdJdgfs;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui.CancellationActivityYingJiHDSdJdgfs;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui.LoginActivityYingJiHDSdJdgfs;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui.UserYsxyActivityYingJiHDSdJdgfs;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui.yingjidrhfsdradapter.YingJiHDSdJdgfsMineItemAdapter;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsRemindDialog;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsSharePreferencesUtil;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.StaticCYingJiHDSdJdgfsUtil;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.ToastYingJiHDSdJdgfsUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineYingJiHDSdJdgfsFragment extends BaseYingJiHDSdJdgfsFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList, mineList1;
    private View logoutBtn;

    private String mobileStr;
    private YingJiHDSdJdgfsMineItemAdapter yingJiHDSdJdgfsMineItemAdapter, yingJiHDSdJdgfsMineItemAdapter1;
    private List<YingJiHDSdJdgfsMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.xvbnsrt, R.drawable.aegnghk, R.drawable.zxvcbndf,
            R.drawable.lguipf, R.drawable.fgdr, R.drawable.kfyuod};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户"};
    private Bundle bundle;
    private YingJiHDSdJdgfsRemindDialog mYingJiHDSdJdgfsRemindDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ying_ji_dh_jie_fuerty_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        mineList1 = rootView.findViewById(R.id.mine_list_1);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        mobileStr = YingJiHDSdJdgfsSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            YingJiHDSdJdgfsMineItemModel model = new YingJiHDSdJdgfsMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            if (i < 3) {
                list.add(model);
            } else {
                list1.add(model);
            }
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mYingJiHDSdJdgfsRemindDialog = new YingJiHDSdJdgfsRemindDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mYingJiHDSdJdgfsRemindDialog.setBtnClickListener(new YingJiHDSdJdgfsRemindDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mYingJiHDSdJdgfsRemindDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mYingJiHDSdJdgfsRemindDialog.dismiss();
                    YingJiHDSdJdgfsSharePreferencesUtil.saveString("phone", "");
                    StaticCYingJiHDSdJdgfsUtil.startActivity(getActivity(), LoginActivityYingJiHDSdJdgfs.class, null);
                    getActivity().finish();
                }
            });
            mYingJiHDSdJdgfsRemindDialog.show();
            mYingJiHDSdJdgfsRemindDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        yingJiHDSdJdgfsMineItemAdapter =  new YingJiHDSdJdgfsMineItemAdapter(R.layout.adapter_ying_ji_dh_jie_fuerty_mine_list_layout, list);
        yingJiHDSdJdgfsMineItemAdapter.setHasStableIds(true);
        yingJiHDSdJdgfsMineItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", YingJiHDSdJdgfsRetrofitManager.ZCXY);
                    StaticCYingJiHDSdJdgfsUtil.startActivity(getActivity(), UserYsxyActivityYingJiHDSdJdgfs.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", YingJiHDSdJdgfsRetrofitManager.YSXY);
                    StaticCYingJiHDSdJdgfsUtil.startActivity(getActivity(), UserYsxyActivityYingJiHDSdJdgfs.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(yingJiHDSdJdgfsMineItemAdapter);
        yingJiHDSdJdgfsMineItemAdapter1 =  new YingJiHDSdJdgfsMineItemAdapter(R.layout.adapter_ying_ji_dh_jie_fuerty_mine_list_layout, list1);
        yingJiHDSdJdgfsMineItemAdapter1.setHasStableIds(true);
        yingJiHDSdJdgfsMineItemAdapter1.setItemClickListener(position -> {
            switch (position){
                case 0:
                    StaticCYingJiHDSdJdgfsUtil.startActivity(getActivity(), AppInfoActivityYingJiHDSdJdgfs.class, null);
                    break;
                case 1:
                    mYingJiHDSdJdgfsRemindDialog = new YingJiHDSdJdgfsRemindDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mYingJiHDSdJdgfsRemindDialog.setBtnClickListener(new YingJiHDSdJdgfsRemindDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastYingJiHDSdJdgfsUtil.showShort("开启成功");
                            mYingJiHDSdJdgfsRemindDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastYingJiHDSdJdgfsUtil.showShort("关闭成功");
                            mYingJiHDSdJdgfsRemindDialog.dismiss();
                        }
                    });
                    mYingJiHDSdJdgfsRemindDialog.show();
                    mYingJiHDSdJdgfsRemindDialog.setBtnStr("开启", "关闭");
                    break;
                case 2:
                    StaticCYingJiHDSdJdgfsUtil.startActivity(getActivity(), CancellationActivityYingJiHDSdJdgfs.class, null);
                    break;
            }
        });
        mineList1.setHasFixedSize(true);
        mineList1.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList1.setAdapter(yingJiHDSdJdgfsMineItemAdapter1);
    }

    private void getConfig() {
        Observable<YingJiHDSdJdgfsBaseModel<YingJiHDSdJdgfsConfigModel>> observable = YingJiHDSdJdgfsRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new YingJiHDSdJdgfsObserverManager<YingJiHDSdJdgfsBaseModel<YingJiHDSdJdgfsConfigModel>>() {
                    @Override
                    public void onSuccess(YingJiHDSdJdgfsBaseModel<YingJiHDSdJdgfsConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        YingJiHDSdJdgfsConfigModel configCaiJieTongYshVdjrytModel = model.getData();
                        if (configCaiJieTongYshVdjrytModel != null) {
                            YingJiHDSdJdgfsSharePreferencesUtil.saveString("APP_MAIL", configCaiJieTongYshVdjrytModel.getAppMail());
                            mYingJiHDSdJdgfsRemindDialog = new YingJiHDSdJdgfsRemindDialog(getActivity(), "温馨提示", configCaiJieTongYshVdjrytModel.getAppMail(), false);
                            mYingJiHDSdJdgfsRemindDialog.setBtnClickListener(new YingJiHDSdJdgfsRemindDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mYingJiHDSdJdgfsRemindDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, configCaiJieTongYshVdjrytModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastYingJiHDSdJdgfsUtil.showShort("复制成功");
                                    mYingJiHDSdJdgfsRemindDialog.dismiss();
                                }
                            });
                            mYingJiHDSdJdgfsRemindDialog.show();
                            mYingJiHDSdJdgfsRemindDialog.setBtnStr("取消", "复制");
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
