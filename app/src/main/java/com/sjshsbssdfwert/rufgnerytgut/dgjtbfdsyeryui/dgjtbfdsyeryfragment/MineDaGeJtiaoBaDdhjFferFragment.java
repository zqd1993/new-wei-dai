package com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui.dgjtbfdsyeryfragment;

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

import com.sjshsbssdfwert.rufgnerytgut.R;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryapi.DaGeJtiaoBaDdhjFferRetrofitManager;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerybase.BaseDaGeJtiaoBaDdhjFferFragment;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerybase.DaGeJtiaoBaDdhjFferObserverManager;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.BaseDaGeJtiaoBaDdhjFferModel;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.DaGeJtiaoBaDdhjFferConfigModel;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.DaGeJtiaoBaDdhjFferMineItemModel;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui.AppInfoDaGeJtiaoBaDdhjFferActivity;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui.CancellationDaGeJtiaoBaDdhjFferActivity;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui.LoginDaGeJtiaoBaDdhjFferActivity;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui.UserYsxyDaGeJtiaoBaDdhjFferActivity;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui.dgjtbfdsyeryadapter.DaGeJtiaoBaDdhjFferMineItemAdapter;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.RemindDaGeJtiaoBaDdhjFferDialog;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.DaGeJtiaoBaDdhjFferSharePreferencesUtil;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.StaticDaGeJtiaoBaDdhjFferUtil;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.ToastDaGeJtiaoBaDdhjFferUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineDaGeJtiaoBaDdhjFferFragment extends BaseDaGeJtiaoBaDdhjFferFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList, mineList1;
    private View logoutBtn;

    private String mobileStr;
    private DaGeJtiaoBaDdhjFferMineItemAdapter daGeJtiaoBaDdhjFferMineItemAdapter, daGeJtiaoBaDdhjFferMineItemAdapter1;
    private List<DaGeJtiaoBaDdhjFferMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.whxjn, R.drawable.tyjdfg, R.drawable.gbndrt,
            R.drawable.tyjdfg, R.drawable.tyjdfg, R.drawable.tyjdfg};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户"};
    private Bundle bundle;
    private RemindDaGeJtiaoBaDdhjFferDialog mRemindDaGeJtiaoBaDdhjFferDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_da_ge_jdf_yrjf_mine;
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
        mobileStr = DaGeJtiaoBaDdhjFferSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            DaGeJtiaoBaDdhjFferMineItemModel model = new DaGeJtiaoBaDdhjFferMineItemModel();
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
            mRemindDaGeJtiaoBaDdhjFferDialog = new RemindDaGeJtiaoBaDdhjFferDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindDaGeJtiaoBaDdhjFferDialog.setBtnClickListener(new RemindDaGeJtiaoBaDdhjFferDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindDaGeJtiaoBaDdhjFferDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindDaGeJtiaoBaDdhjFferDialog.dismiss();
                    DaGeJtiaoBaDdhjFferSharePreferencesUtil.saveString("phone", "");
                    StaticDaGeJtiaoBaDdhjFferUtil.startActivity(getActivity(), LoginDaGeJtiaoBaDdhjFferActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindDaGeJtiaoBaDdhjFferDialog.show();
            mRemindDaGeJtiaoBaDdhjFferDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData() {
        daGeJtiaoBaDdhjFferMineItemAdapter = new DaGeJtiaoBaDdhjFferMineItemAdapter(R.layout.adapter_mine_list_layout_da_ge_jdf_yrjf, list);
        daGeJtiaoBaDdhjFferMineItemAdapter.setHasStableIds(true);
        daGeJtiaoBaDdhjFferMineItemAdapter.setItemClickListener(position -> {
            switch (position) {
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", DaGeJtiaoBaDdhjFferRetrofitManager.ZCXY);
                    StaticDaGeJtiaoBaDdhjFferUtil.startActivity(getActivity(), UserYsxyDaGeJtiaoBaDdhjFferActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", DaGeJtiaoBaDdhjFferRetrofitManager.YSXY);
                    StaticDaGeJtiaoBaDdhjFferUtil.startActivity(getActivity(), UserYsxyDaGeJtiaoBaDdhjFferActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(daGeJtiaoBaDdhjFferMineItemAdapter);
        daGeJtiaoBaDdhjFferMineItemAdapter1 = new DaGeJtiaoBaDdhjFferMineItemAdapter(R.layout.adapter_mine_list_layout_1_da_ge_jdf_yrjf, list1);
        daGeJtiaoBaDdhjFferMineItemAdapter1.setHasStableIds(true);
        daGeJtiaoBaDdhjFferMineItemAdapter1.setItemClickListener(position -> {
            switch (position) {
                case 0:
                    StaticDaGeJtiaoBaDdhjFferUtil.startActivity(getActivity(), AppInfoDaGeJtiaoBaDdhjFferActivity.class, null);
                    break;
                case 1:
                    mRemindDaGeJtiaoBaDdhjFferDialog = new RemindDaGeJtiaoBaDdhjFferDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindDaGeJtiaoBaDdhjFferDialog.setBtnClickListener(new RemindDaGeJtiaoBaDdhjFferDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastDaGeJtiaoBaDdhjFferUtil.showShort("开启成功");
                            mRemindDaGeJtiaoBaDdhjFferDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastDaGeJtiaoBaDdhjFferUtil.showShort("关闭成功");
                            mRemindDaGeJtiaoBaDdhjFferDialog.dismiss();
                        }
                    });
                    mRemindDaGeJtiaoBaDdhjFferDialog.show();
                    mRemindDaGeJtiaoBaDdhjFferDialog.setBtnStr("开启", "关闭");
                    break;
                case 2:
                    StaticDaGeJtiaoBaDdhjFferUtil.startActivity(getActivity(), CancellationDaGeJtiaoBaDdhjFferActivity.class, null);
                    break;
            }
        });
        mineList1.setHasFixedSize(true);
        mineList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineList1.setAdapter(daGeJtiaoBaDdhjFferMineItemAdapter1);
    }

    private void getConfig() {
        Observable<BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferConfigModel>> observable = DaGeJtiaoBaDdhjFferRetrofitManager.getRetrofitManager().getApiService().getConfig();

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
                            DaGeJtiaoBaDdhjFferSharePreferencesUtil.saveString("APP_MAIL", daGeJtiaoBaDdhjFferConfigModel.getAppMail());
                            mRemindDaGeJtiaoBaDdhjFferDialog = new RemindDaGeJtiaoBaDdhjFferDialog(getActivity(), "温馨提示", daGeJtiaoBaDdhjFferConfigModel.getAppMail(), false);
                            mRemindDaGeJtiaoBaDdhjFferDialog.setBtnClickListener(new RemindDaGeJtiaoBaDdhjFferDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindDaGeJtiaoBaDdhjFferDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, daGeJtiaoBaDdhjFferConfigModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastDaGeJtiaoBaDdhjFferUtil.showShort("复制成功");
                                    mRemindDaGeJtiaoBaDdhjFferDialog.dismiss();
                                }
                            });
                            mRemindDaGeJtiaoBaDdhjFferDialog.show();
                            mRemindDaGeJtiaoBaDdhjFferDialog.setBtnStr("取消", "复制");
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
