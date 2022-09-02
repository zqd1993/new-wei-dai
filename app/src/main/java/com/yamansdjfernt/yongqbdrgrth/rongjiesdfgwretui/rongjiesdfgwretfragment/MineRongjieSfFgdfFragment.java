package com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretui.rongjiesdfgwretfragment;

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

import com.yamansdjfernt.yongqbdrgrth.R;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretapi.RongjieSfFgdfRetrofitManager;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretbase.BaseRongjieSfFgdfFragment;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretbase.RongjieSfFgdfObserverManager;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretmodel.RongjieSfFgdfBaseModel;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretmodel.RongjieSfFgdfConfigModel;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretmodel.RongjieSfFgdfMineItemModel;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretui.RongjieSfFgdfAppInfoRongjieSfFgdfActivity;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretui.RongjieSfFgdfCancellationRongjieSfFgdfActivity;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretui.RongjieSfFgdfLoginRongjieSfFgdfActivity;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretui.RongjieSfFgdfUserYsxyRongjieSfFgdfActivity;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretui.rongjiesdfgwretadapter.RongjieSfFgdfMineItemAdapter;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.RemindRongjieSfFgdfDialog;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.SharePreferencesUtilRongjieSfFgdf;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.StaticRongjieSfFgdfUtil;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.ToastRongjieSfFgdfUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineRongjieSfFgdfFragment extends BaseRongjieSfFgdfFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList, mineList1;
    private View logoutBtn;

    private String mobileStr;
    private RongjieSfFgdfMineItemAdapter rongjieSfFgdfMineItemAdapter, rongjieSfFgdfMineItemAdapter1;
    private List<RongjieSfFgdfMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.cvd, R.drawable.erfh, R.drawable.zcvzbery,
            R.drawable.cvd, R.drawable.erfh, R.drawable.zcvzbery, R.drawable.zcvzbery};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户", "退出登录"};
    private Bundle bundle;
    private RemindRongjieSfFgdfDialog mRemindRongjieSfFgdfDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_rong_jie_sdf_brty_mine;
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
        mobileStr = SharePreferencesUtilRongjieSfFgdf.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 7; i++) {
            RongjieSfFgdfMineItemModel model = new RongjieSfFgdfMineItemModel();
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
            mRemindRongjieSfFgdfDialog = new RemindRongjieSfFgdfDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindRongjieSfFgdfDialog.setBtnClickListener(new RemindRongjieSfFgdfDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindRongjieSfFgdfDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindRongjieSfFgdfDialog.dismiss();
                    SharePreferencesUtilRongjieSfFgdf.saveString("phone", "");
                    StaticRongjieSfFgdfUtil.startActivity(getActivity(), RongjieSfFgdfLoginRongjieSfFgdfActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindRongjieSfFgdfDialog.show();
            mRemindRongjieSfFgdfDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        rongjieSfFgdfMineItemAdapter =  new RongjieSfFgdfMineItemAdapter(R.layout.adapter_mine_list_layout_rong_jie_sdf_brty, list);
        rongjieSfFgdfMineItemAdapter.setHasStableIds(true);
        rongjieSfFgdfMineItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", RongjieSfFgdfRetrofitManager.ZCXY);
                    StaticRongjieSfFgdfUtil.startActivity(getActivity(), RongjieSfFgdfUserYsxyRongjieSfFgdfActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", RongjieSfFgdfRetrofitManager.YSXY);
                    StaticRongjieSfFgdfUtil.startActivity(getActivity(), RongjieSfFgdfUserYsxyRongjieSfFgdfActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(rongjieSfFgdfMineItemAdapter);
        rongjieSfFgdfMineItemAdapter1 =  new RongjieSfFgdfMineItemAdapter(R.layout.adapter_rong_jie_sdf_brty_mine_list_layout_hor, list1);
        rongjieSfFgdfMineItemAdapter1.setHasStableIds(true);
        rongjieSfFgdfMineItemAdapter1.setItemClickListener(position -> {
            switch (position){
                case 0:
                    StaticRongjieSfFgdfUtil.startActivity(getActivity(), RongjieSfFgdfAppInfoRongjieSfFgdfActivity.class, null);
                    break;
                case 1:
                    mRemindRongjieSfFgdfDialog = new RemindRongjieSfFgdfDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindRongjieSfFgdfDialog.setBtnClickListener(new RemindRongjieSfFgdfDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastRongjieSfFgdfUtil.showShort("开启成功");
                            mRemindRongjieSfFgdfDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastRongjieSfFgdfUtil.showShort("关闭成功");
                            mRemindRongjieSfFgdfDialog.dismiss();
                        }
                    });
                    mRemindRongjieSfFgdfDialog.show();
                    mRemindRongjieSfFgdfDialog.setBtnStr("开启", "关闭");
                    break;
                case 2:
                    StaticRongjieSfFgdfUtil.startActivity(getActivity(), RongjieSfFgdfCancellationRongjieSfFgdfActivity.class, null);
                    break;
                case 3:
                    mRemindRongjieSfFgdfDialog = new RemindRongjieSfFgdfDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
                    mRemindRongjieSfFgdfDialog.setBtnClickListener(new RemindRongjieSfFgdfDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            mRemindRongjieSfFgdfDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            mRemindRongjieSfFgdfDialog.dismiss();
                            SharePreferencesUtilRongjieSfFgdf.saveString("phone", "");
                            StaticRongjieSfFgdfUtil.startActivity(getActivity(), RongjieSfFgdfLoginRongjieSfFgdfActivity.class, null);
                            getActivity().finish();
                        }
                    });
                    mRemindRongjieSfFgdfDialog.show();
                    mRemindRongjieSfFgdfDialog.setBtnStr("取消", "退出");
                    break;
            }
        });
        mineList1.setHasFixedSize(true);
        mineList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineList1.setAdapter(rongjieSfFgdfMineItemAdapter1);
    }

    private void getConfig() {
        Observable<RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel>> observable = RongjieSfFgdfRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RongjieSfFgdfObserverManager<RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel>>() {
                    @Override
                    public void onSuccess(RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        RongjieSfFgdfConfigModel rongjieSfFgdfConfigModel = model.getData();
                        if (rongjieSfFgdfConfigModel != null) {
                            SharePreferencesUtilRongjieSfFgdf.saveString("APP_MAIL", rongjieSfFgdfConfigModel.getAppMail());
                            mRemindRongjieSfFgdfDialog = new RemindRongjieSfFgdfDialog(getActivity(), "温馨提示", rongjieSfFgdfConfigModel.getAppMail(), false);
                            mRemindRongjieSfFgdfDialog.setBtnClickListener(new RemindRongjieSfFgdfDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindRongjieSfFgdfDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, rongjieSfFgdfConfigModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastRongjieSfFgdfUtil.showShort("复制成功");
                                    mRemindRongjieSfFgdfDialog.dismiss();
                                }
                            });
                            mRemindRongjieSfFgdfDialog.show();
                            mRemindRongjieSfFgdfDialog.setBtnStr("取消", "复制");
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
