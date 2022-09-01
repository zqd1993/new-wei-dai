package com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui.mangguohwfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dqlsdjdhwmg.fdhqwenhwmg.R;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwapi.MangGuoHwRetrofitManager;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwbase.BaseMangGuoHwFragment;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwbase.ObserverMangGuoHwManager;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.BaseMangGuoHwModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.MangGuoHwConfigModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.MangGuoHwMineItemModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui.AppInfoMangGuoHwActivity;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui.CancellationMangGuoHwActivity;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui.LoginMangGuoHwActivity;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui.UserYsxyMangGuoHwActivity;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui.mangguohwadapter.MangGuoHwMineItemAdapter;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.RemindMangGuoHwDialog;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.MangGuoHwSharePreferencesUtils;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.StaticMangGuoHwUtil;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.ToastMangGuoHwUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineMangGuoHwFragment extends BaseMangGuoHwFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList;
    private View logoutBtn;

    private String mobileStr;
    private MangGuoHwMineItemAdapter mangGuoHwMineItemAdapter;
    private List<MangGuoHwMineItemModel> list;
    private int[] imgRes = {R.drawable.tubiao1, R.drawable.tubiao2, R.drawable.tubiao3,
            R.drawable.tubiao4, R.drawable.tubiao6};
    private String[] tvRes = {"注册协议", "隐私协议", "联系客服", "关于我们", "注销账户"};
    private Bundle bundle;
    private RemindMangGuoHwDialog mRemindMangGuoHwDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mang_guo_hw_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        list = new ArrayList<>();
        mobileStr = MangGuoHwSharePreferencesUtils.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 5; i++) {
            MangGuoHwMineItemModel model = new MangGuoHwMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            list.add(model);
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mRemindMangGuoHwDialog = new RemindMangGuoHwDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindMangGuoHwDialog.setBtnClickListener(new RemindMangGuoHwDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindMangGuoHwDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindMangGuoHwDialog.dismiss();
                    MangGuoHwSharePreferencesUtils.saveString("phone", "");
                    StaticMangGuoHwUtil.startActivity(getActivity(), LoginMangGuoHwActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindMangGuoHwDialog.show();
            mRemindMangGuoHwDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        mangGuoHwMineItemAdapter =  new MangGuoHwMineItemAdapter(R.layout.adapter_mine_list_layout_mang_guo_hw, list);
        mangGuoHwMineItemAdapter.setHasStableIds(true);
        mangGuoHwMineItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", MangGuoHwRetrofitManager.ZCXY);
                    StaticMangGuoHwUtil.startActivity(getActivity(), UserYsxyMangGuoHwActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", MangGuoHwRetrofitManager.YSXY);
                    StaticMangGuoHwUtil.startActivity(getActivity(), UserYsxyMangGuoHwActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
                case 3:
                    StaticMangGuoHwUtil.startActivity(getActivity(), AppInfoMangGuoHwActivity.class, null);
                    break;
//                case 4:
//                    mRemindMangGuoHwDialog = new RemindMangGuoHwDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
//                    mRemindMangGuoHwDialog.setBtnClickListener(new RemindMangGuoHwDialog.BtnClickListener() {
//                        @Override
//                        public void leftClicked() {
//                            ToastMangGuoHwUtil.showShort("开启成功");
//                            mRemindMangGuoHwDialog.dismiss();
//                        }
//
//                        @Override
//                        public void rightClicked() {
//                            ToastMangGuoHwUtil.showShort("关闭成功");
//                            mRemindMangGuoHwDialog.dismiss();
//                        }
//                    });
//                    mRemindMangGuoHwDialog.show();
//                    mRemindMangGuoHwDialog.setBtnStr("开启", "关闭");
//                    break;
                case 4:
                    StaticMangGuoHwUtil.startActivity(getActivity(), CancellationMangGuoHwActivity.class, null);
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(mangGuoHwMineItemAdapter);
    }

    private void getConfig() {
        Observable<BaseMangGuoHwModel<MangGuoHwConfigModel>> observable = MangGuoHwRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverMangGuoHwManager<BaseMangGuoHwModel<MangGuoHwConfigModel>>() {
                    @Override
                    public void onSuccess(BaseMangGuoHwModel<MangGuoHwConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        MangGuoHwConfigModel mangGuoHwConfigModel = model.getData();
                        if (mangGuoHwConfigModel != null) {
                            MangGuoHwSharePreferencesUtils.saveString("APP_MAIL", mangGuoHwConfigModel.getAppMail());
                            mRemindMangGuoHwDialog = new RemindMangGuoHwDialog(getActivity(), "温馨提示", mangGuoHwConfigModel.getAppMail(), false);
                            mRemindMangGuoHwDialog.setBtnClickListener(new RemindMangGuoHwDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindMangGuoHwDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, mangGuoHwConfigModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastMangGuoHwUtil.showShort("复制成功");
                                    mRemindMangGuoHwDialog.dismiss();
                                }
                            });
                            mRemindMangGuoHwDialog.show();
                            mRemindMangGuoHwDialog.setBtnStr("取消", "复制");
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
