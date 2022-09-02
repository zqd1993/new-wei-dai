package com.xbk1jk.zldkbk.zhulihuavrsdtrui.zhulihuavrsdtrfragment;

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

import com.xbk1jk.zldkbk.R;
import com.xbk1jk.zldkbk.zhulihuavrsdtrapi.ZhuLiDaiKuanHuadewgRetrofitManager;
import com.xbk1jk.zldkbk.zhulihuavrsdtrbase.BaseZhuLiDaiKuanHuadewgFragment;
import com.xbk1jk.zldkbk.zhulihuavrsdtrbase.MiaoBaiTiaoAdfFgsObserverManager;
import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.ZhuLiDaiKuanHuadewgBaseModel;
import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.ConfigZhuLiDaiKuanHuadewgModel;
import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.ZhuLiDaiKuanHuadewgMineItemModel;
import com.xbk1jk.zldkbk.zhulihuavrsdtrui.AppInfoZhuLiDaiKuanHuadewgActivity;
import com.xbk1jk.zldkbk.zhulihuavrsdtrui.CancellationZhuLiDaiKuanHuadewgActivity;
import com.xbk1jk.zldkbk.zhulihuavrsdtrui.LoginZhuLiDaiKuanHuadewgActivity;
import com.xbk1jk.zldkbk.zhulihuavrsdtrui.ZhuLiDaiKuanHuadewgUserYsxyActivity;
import com.xbk1jk.zldkbk.zhulihuavrsdtrui.zhulihuavrsdtradapter.MineItemZhuLiDaiKuanHuadewgAdapter;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.RemindZhuLiDaiKuanHuadewgDialog;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.SharePreferencesZhuLiDaiKuanHuadewgUtil;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.StaticZhuLiDaiKuanHuadewgUtil;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.ToastZhuLiDaiKuanHuadewgUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineZhuLiDaiKuanHuadewgFragment extends BaseZhuLiDaiKuanHuadewgFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList, mineList1;
    private View logoutBtn;

    private String mobileStr;
    private MineItemZhuLiDaiKuanHuadewgAdapter mineItemZhuLiDaiKuanHuadewgAdapter, mineItemZhuLiDaiKuanHuadewgAdapter1;
    private List<ZhuLiDaiKuanHuadewgMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.aefgh, R.drawable.rtyuxfgn, R.drawable.cvbsrtyu,
            R.drawable.cvbsrtyu, R.drawable.cvbsrtyu, R.drawable.cvbsrtyu, R.drawable.cvbsrtyu};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户", "退出登录"};
    private Bundle bundle;
    private RemindZhuLiDaiKuanHuadewgDialog mRemindZhuLiDaiKuanHuadewgDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zhu_li_dai_kuan_hua_setg_sert_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        mineList1 = rootView.findViewById(R.id.mine_list_1);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        mobileStr = SharePreferencesZhuLiDaiKuanHuadewgUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 7; i++) {
            ZhuLiDaiKuanHuadewgMineItemModel model = new ZhuLiDaiKuanHuadewgMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            if (i < 3){
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
            mRemindZhuLiDaiKuanHuadewgDialog = new RemindZhuLiDaiKuanHuadewgDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindZhuLiDaiKuanHuadewgDialog.setBtnClickListener(new RemindZhuLiDaiKuanHuadewgDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindZhuLiDaiKuanHuadewgDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindZhuLiDaiKuanHuadewgDialog.dismiss();
                    SharePreferencesZhuLiDaiKuanHuadewgUtil.saveString("phone", "");
                    StaticZhuLiDaiKuanHuadewgUtil.startActivity(getActivity(), LoginZhuLiDaiKuanHuadewgActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindZhuLiDaiKuanHuadewgDialog.show();
            mRemindZhuLiDaiKuanHuadewgDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        mineItemZhuLiDaiKuanHuadewgAdapter =  new MineItemZhuLiDaiKuanHuadewgAdapter(R.layout.adapter_mine_list_layout_zhu_li_dai_kuan_hua_setg_sert, list);
        mineItemZhuLiDaiKuanHuadewgAdapter.setHasStableIds(true);
        mineItemZhuLiDaiKuanHuadewgAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", ZhuLiDaiKuanHuadewgRetrofitManager.ZCXY);
                    StaticZhuLiDaiKuanHuadewgUtil.startActivity(getActivity(), ZhuLiDaiKuanHuadewgUserYsxyActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", ZhuLiDaiKuanHuadewgRetrofitManager.YSXY);
                    StaticZhuLiDaiKuanHuadewgUtil.startActivity(getActivity(), ZhuLiDaiKuanHuadewgUserYsxyActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(mineItemZhuLiDaiKuanHuadewgAdapter);
        mineItemZhuLiDaiKuanHuadewgAdapter1 =  new MineItemZhuLiDaiKuanHuadewgAdapter(R.layout.adapter_mine_list_layout_1_zhu_li_dai_kuan_hua_setg_sert, list1);
        mineItemZhuLiDaiKuanHuadewgAdapter1.setHasStableIds(true);
        mineItemZhuLiDaiKuanHuadewgAdapter1.setItemClickListener(position -> {
            switch (position){
                case 0:
                    StaticZhuLiDaiKuanHuadewgUtil.startActivity(getActivity(), AppInfoZhuLiDaiKuanHuadewgActivity.class, null);
                    break;
                case 1:
                    mRemindZhuLiDaiKuanHuadewgDialog = new RemindZhuLiDaiKuanHuadewgDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindZhuLiDaiKuanHuadewgDialog.setBtnClickListener(new RemindZhuLiDaiKuanHuadewgDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastZhuLiDaiKuanHuadewgUtil.showShort("开启成功");
                            mRemindZhuLiDaiKuanHuadewgDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastZhuLiDaiKuanHuadewgUtil.showShort("关闭成功");
                            mRemindZhuLiDaiKuanHuadewgDialog.dismiss();
                        }
                    });
                    mRemindZhuLiDaiKuanHuadewgDialog.show();
                    mRemindZhuLiDaiKuanHuadewgDialog.setBtnStr("开启", "关闭");
                    break;
                case 2:
                    StaticZhuLiDaiKuanHuadewgUtil.startActivity(getActivity(), CancellationZhuLiDaiKuanHuadewgActivity.class, null);
                    break;
                case 3:
                    mRemindZhuLiDaiKuanHuadewgDialog = new RemindZhuLiDaiKuanHuadewgDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
                    mRemindZhuLiDaiKuanHuadewgDialog.setBtnClickListener(new RemindZhuLiDaiKuanHuadewgDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            mRemindZhuLiDaiKuanHuadewgDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            mRemindZhuLiDaiKuanHuadewgDialog.dismiss();
                            SharePreferencesZhuLiDaiKuanHuadewgUtil.saveString("phone", "");
                            StaticZhuLiDaiKuanHuadewgUtil.startActivity(getActivity(), LoginZhuLiDaiKuanHuadewgActivity.class, null);
                            getActivity().finish();
                        }
                    });
                    mRemindZhuLiDaiKuanHuadewgDialog.show();
                    mRemindZhuLiDaiKuanHuadewgDialog.setBtnStr("取消", "退出");
                    break;
            }
        });
        mineList1.setHasFixedSize(true);
        mineList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineList1.setAdapter(mineItemZhuLiDaiKuanHuadewgAdapter1);
    }

    private void getConfig() {
        Observable<ZhuLiDaiKuanHuadewgBaseModel<ConfigZhuLiDaiKuanHuadewgModel>> observable = ZhuLiDaiKuanHuadewgRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<ZhuLiDaiKuanHuadewgBaseModel<ConfigZhuLiDaiKuanHuadewgModel>>() {
                    @Override
                    public void onSuccess(ZhuLiDaiKuanHuadewgBaseModel<ConfigZhuLiDaiKuanHuadewgModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigZhuLiDaiKuanHuadewgModel configZhuLiDaiKuanHuadewgModel = model.getData();
                        if (configZhuLiDaiKuanHuadewgModel != null) {
                            SharePreferencesZhuLiDaiKuanHuadewgUtil.saveString("APP_MAIL", configZhuLiDaiKuanHuadewgModel.getAppMail());
                            mRemindZhuLiDaiKuanHuadewgDialog = new RemindZhuLiDaiKuanHuadewgDialog(getActivity(), "温馨提示", configZhuLiDaiKuanHuadewgModel.getAppMail(), false);
                            mRemindZhuLiDaiKuanHuadewgDialog.setBtnClickListener(new RemindZhuLiDaiKuanHuadewgDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindZhuLiDaiKuanHuadewgDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, configZhuLiDaiKuanHuadewgModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastZhuLiDaiKuanHuadewgUtil.showShort("复制成功");
                                    mRemindZhuLiDaiKuanHuadewgDialog.dismiss();
                                }
                            });
                            mRemindZhuLiDaiKuanHuadewgDialog.show();
                            mRemindZhuLiDaiKuanHuadewgDialog.setBtnStr("取消", "复制");
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
