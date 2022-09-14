package com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui.miaobaitiaosdfsdfragment;

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

import com.yhbnwghjfdkdfet.tgvshdjg.R;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdapi.MiaoBaiTiaoAdfFgsRetrofitManager;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdbase.BaseMiaoBaiTiaoAdfFgsFragment;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdbase.MiaoBaiTiaoAdfFgsObserverManager;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.MiaoBaiTiaoAdfFgsBaseModel;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.ConfigMiaoBaiTiaoAdfFgsModel;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.MiaoBaiTiaoAdfFgsMineItemModel;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui.MiaoBaiTiaoAdfFgsAppInfoMiaoBaiTiaoAdfFgsActivity;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui.MiaoBaiTiaoAdfFgsCancellationMiaoBaiTiaoAdfFgsActivity;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui.LoginMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui.MiaoBaiTiaoAdfFgsUserYsxyMiaoBaiTiaoAdfFgsActivity;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui.miaobaitiaosdfsdadapter.MineItemMiaoBaiTiaoAdfFgsAdapter;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.RemindMiaoBaiTiaoAdfFgsDialog;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.SharePreferencesMiaoBaiTiaoAdfFgsUtil;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.StaticMiaoBaiTiaoAdfFgsUtil;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.ToastMiaoBaiTiaoAdfFgsUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsFragment extends BaseMiaoBaiTiaoAdfFgsFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList, mineList1;
    private View logoutBtn;

    private String mobileStr;
    private MineItemMiaoBaiTiaoAdfFgsAdapter mineItemMiaoBaiTiaoAdfFgsAdapter, mineItemMiaoBaiTiaoAdfFgsAdapter1;
    private List<MiaoBaiTiaoAdfFgsMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.aefgh, R.drawable.rtyuxfgn, R.drawable.cvbsrtyu,
            R.drawable.cvbsrtyu, R.drawable.cvbsrtyu, R.drawable.cvbsrtyu, R.drawable.cvbsrtyu};
    private String[] tvRes = {"注册协议", "隐私协议", "联系客服", "关于我们", "注销账户", "退出登录"};
    private Bundle bundle;
    private RemindMiaoBaiTiaoAdfFgsDialog mRemindMiaoBaiTiaoAdfFgsDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_miao_bai_tiao_sdf_mine;
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
        mobileStr = SharePreferencesMiaoBaiTiaoAdfFgsUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            MiaoBaiTiaoAdfFgsMineItemModel model = new MiaoBaiTiaoAdfFgsMineItemModel();
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
            mRemindMiaoBaiTiaoAdfFgsDialog = new RemindMiaoBaiTiaoAdfFgsDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindMiaoBaiTiaoAdfFgsDialog.setBtnClickListener(new RemindMiaoBaiTiaoAdfFgsDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindMiaoBaiTiaoAdfFgsDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindMiaoBaiTiaoAdfFgsDialog.dismiss();
                    SharePreferencesMiaoBaiTiaoAdfFgsUtil.saveString("phone", "");
                    StaticMiaoBaiTiaoAdfFgsUtil.startActivity(getActivity(), LoginMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindMiaoBaiTiaoAdfFgsDialog.show();
            mRemindMiaoBaiTiaoAdfFgsDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        mineItemMiaoBaiTiaoAdfFgsAdapter =  new MineItemMiaoBaiTiaoAdfFgsAdapter(R.layout.adapter_mine_list_layout_miao_bai_tiao_sdf, list);
        mineItemMiaoBaiTiaoAdfFgsAdapter.setHasStableIds(true);
        mineItemMiaoBaiTiaoAdfFgsAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", MiaoBaiTiaoAdfFgsRetrofitManager.ZCXY);
                    StaticMiaoBaiTiaoAdfFgsUtil.startActivity(getActivity(), MiaoBaiTiaoAdfFgsUserYsxyMiaoBaiTiaoAdfFgsActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", MiaoBaiTiaoAdfFgsRetrofitManager.YSXY);
                    StaticMiaoBaiTiaoAdfFgsUtil.startActivity(getActivity(), MiaoBaiTiaoAdfFgsUserYsxyMiaoBaiTiaoAdfFgsActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(mineItemMiaoBaiTiaoAdfFgsAdapter);
        mineItemMiaoBaiTiaoAdfFgsAdapter1 =  new MineItemMiaoBaiTiaoAdfFgsAdapter(R.layout.adapter_mine_list_layout_1, list1);
        mineItemMiaoBaiTiaoAdfFgsAdapter1.setHasStableIds(true);
        mineItemMiaoBaiTiaoAdfFgsAdapter1.setItemClickListener(position -> {
            switch (position){
                case 0:
                    StaticMiaoBaiTiaoAdfFgsUtil.startActivity(getActivity(), MiaoBaiTiaoAdfFgsAppInfoMiaoBaiTiaoAdfFgsActivity.class, null);
                    break;
                case 1:
                    StaticMiaoBaiTiaoAdfFgsUtil.startActivity(getActivity(), MiaoBaiTiaoAdfFgsCancellationMiaoBaiTiaoAdfFgsActivity.class, null);
                    break;
                case 2:
                    mRemindMiaoBaiTiaoAdfFgsDialog = new RemindMiaoBaiTiaoAdfFgsDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
                    mRemindMiaoBaiTiaoAdfFgsDialog.setBtnClickListener(new RemindMiaoBaiTiaoAdfFgsDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            mRemindMiaoBaiTiaoAdfFgsDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            mRemindMiaoBaiTiaoAdfFgsDialog.dismiss();
                            SharePreferencesMiaoBaiTiaoAdfFgsUtil.saveString("phone", "");
                            StaticMiaoBaiTiaoAdfFgsUtil.startActivity(getActivity(), LoginMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity.class, null);
                            getActivity().finish();
                        }
                    });
                    mRemindMiaoBaiTiaoAdfFgsDialog.show();
                    mRemindMiaoBaiTiaoAdfFgsDialog.setBtnStr("取消", "退出");
                    break;
            }
        });
        mineList1.setHasFixedSize(true);
        mineList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineList1.setAdapter(mineItemMiaoBaiTiaoAdfFgsAdapter1);
    }

    private void getConfig() {
        Observable<MiaoBaiTiaoAdfFgsBaseModel<ConfigMiaoBaiTiaoAdfFgsModel>> observable = MiaoBaiTiaoAdfFgsRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<MiaoBaiTiaoAdfFgsBaseModel<ConfigMiaoBaiTiaoAdfFgsModel>>() {
                    @Override
                    public void onSuccess(MiaoBaiTiaoAdfFgsBaseModel<ConfigMiaoBaiTiaoAdfFgsModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigMiaoBaiTiaoAdfFgsModel configMiaoBaiTiaoAdfFgsModel = model.getData();
                        if (configMiaoBaiTiaoAdfFgsModel != null) {
                            SharePreferencesMiaoBaiTiaoAdfFgsUtil.saveString("APP_MAIL", configMiaoBaiTiaoAdfFgsModel.getAppMail());
                            mRemindMiaoBaiTiaoAdfFgsDialog = new RemindMiaoBaiTiaoAdfFgsDialog(getActivity(), "温馨提示", configMiaoBaiTiaoAdfFgsModel.getAppMail(), false);
                            mRemindMiaoBaiTiaoAdfFgsDialog.setBtnClickListener(new RemindMiaoBaiTiaoAdfFgsDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindMiaoBaiTiaoAdfFgsDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, configMiaoBaiTiaoAdfFgsModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastMiaoBaiTiaoAdfFgsUtil.showShort("复制成功");
                                    mRemindMiaoBaiTiaoAdfFgsDialog.dismiss();
                                }
                            });
                            mRemindMiaoBaiTiaoAdfFgsDialog.show();
                            mRemindMiaoBaiTiaoAdfFgsDialog.setBtnStr("取消", "复制");
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
