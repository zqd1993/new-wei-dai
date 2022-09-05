package com.youjiegherh.pocketqwrh.youjiewetdfhui.youjiewetdfhfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.youjiegherh.pocketqwrh.R;
import com.youjiegherh.pocketqwrh.youjiewetdfhapi.NewCodeXiaoNiuKuaiRetrofitManager;
import com.youjiegherh.pocketqwrh.youjiewetdfhbase.BaseNewCodeXiaoNiuKuaiFragment;
import com.youjiegherh.pocketqwrh.youjiewetdfhbase.NewCodeXiaoNiuKuaiObserverManager;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.BaseYouJieSDjdfiModel;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.ConfigYouJieSDjdfiModel;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.YouJieSDjdfiMineItemModel;
import com.youjiegherh.pocketqwrh.youjiewetdfhui.YouJieSDjdfiAppInfoActivity;
import com.youjiegherh.pocketqwrh.youjiewetdfhui.CancellationActivityYouJieSDjdfi;
import com.youjiegherh.pocketqwrh.youjiewetdfhui.LoginYouJieSDjdfiActivity;
import com.youjiegherh.pocketqwrh.youjiewetdfhui.YouJieSDjdfiUserYsxyActivity;
import com.youjiegherh.pocketqwrh.youjiewetdfhui.youjiewetdfhadapter.MineYouJieSDjdfiItemAdapter;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.RemindYouJieSDjdfiDialog;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.ToastYouJieSDjdfiUtil;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.YouJieSDjdfiSharePreferencesUtil;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.StaticYouJieSDjdfiUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineYouJieSDjdfiFragment extends BaseNewCodeXiaoNiuKuaiFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList;
    private View logoutBtn;

    private String mobileStr;
    private MineYouJieSDjdfiItemAdapter mineYouJieSDjdfiItemAdapter;
    private List<YouJieSDjdfiMineItemModel> list;
    private int[] imgRes = {R.drawable.dfgeryxch, R.drawable.fnsrtu, R.drawable.kfghx,
            R.drawable.bdtfujs, R.drawable.fdyurtyu, R.drawable.tyjghj};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户"};
    private Bundle bundle;
    private RemindYouJieSDjdfiDialog mRemindYouJieSDjdfiDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_you_jie_iejbvr_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        list = new ArrayList<>();
        mobileStr = YouJieSDjdfiSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            YouJieSDjdfiMineItemModel model = new YouJieSDjdfiMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            list.add(model);
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mRemindYouJieSDjdfiDialog = new RemindYouJieSDjdfiDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindYouJieSDjdfiDialog.setBtnClickListener(new RemindYouJieSDjdfiDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindYouJieSDjdfiDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindYouJieSDjdfiDialog.dismiss();
                    YouJieSDjdfiSharePreferencesUtil.saveString("phone", "");
                    StaticYouJieSDjdfiUtil.startActivity(getActivity(), LoginYouJieSDjdfiActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindYouJieSDjdfiDialog.show();
            mRemindYouJieSDjdfiDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        mineYouJieSDjdfiItemAdapter =  new MineYouJieSDjdfiItemAdapter(R.layout.adapter_mine_list_layout_you_jie_iejbvr, list);
        mineYouJieSDjdfiItemAdapter.setHasStableIds(true);
        mineYouJieSDjdfiItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", NewCodeXiaoNiuKuaiRetrofitManager.ZCXY);
                    StaticYouJieSDjdfiUtil.startActivity(getActivity(), YouJieSDjdfiUserYsxyActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", NewCodeXiaoNiuKuaiRetrofitManager.YSXY);
                    StaticYouJieSDjdfiUtil.startActivity(getActivity(), YouJieSDjdfiUserYsxyActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
                case 3:
                    StaticYouJieSDjdfiUtil.startActivity(getActivity(), YouJieSDjdfiAppInfoActivity.class, null);
                    break;
                case 4:
                    mRemindYouJieSDjdfiDialog = new RemindYouJieSDjdfiDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindYouJieSDjdfiDialog.setBtnClickListener(new RemindYouJieSDjdfiDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastYouJieSDjdfiUtil.showShort("开启成功");
                            mRemindYouJieSDjdfiDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastYouJieSDjdfiUtil.showShort("关闭成功");
                            mRemindYouJieSDjdfiDialog.dismiss();
                        }
                    });
                    mRemindYouJieSDjdfiDialog.show();
                    mRemindYouJieSDjdfiDialog.setBtnStr("开启", "关闭");
                    break;
                case 5:
                    StaticYouJieSDjdfiUtil.startActivity(getActivity(), CancellationActivityYouJieSDjdfi.class, null);
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(mineYouJieSDjdfiItemAdapter);
    }

    private void getConfig() {
        Observable<BaseYouJieSDjdfiModel<ConfigYouJieSDjdfiModel>> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseYouJieSDjdfiModel<ConfigYouJieSDjdfiModel>>() {
                    @Override
                    public void onSuccess(BaseYouJieSDjdfiModel<ConfigYouJieSDjdfiModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigYouJieSDjdfiModel configYouJieSDjdfiModel = model.getData();
                        if (configYouJieSDjdfiModel != null) {
                            YouJieSDjdfiSharePreferencesUtil.saveString("APP_MAIL", configYouJieSDjdfiModel.getAppMail());
                            mRemindYouJieSDjdfiDialog = new RemindYouJieSDjdfiDialog(getActivity(), "温馨提示", configYouJieSDjdfiModel.getAppMail(), false);
                            mRemindYouJieSDjdfiDialog.setBtnClickListener(new RemindYouJieSDjdfiDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindYouJieSDjdfiDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, configYouJieSDjdfiModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastYouJieSDjdfiUtil.showShort("复制成功");
                                    mRemindYouJieSDjdfiDialog.dismiss();
                                }
                            });
                            mRemindYouJieSDjdfiDialog.show();
                            mRemindYouJieSDjdfiDialog.setBtnStr("取消", "复制");
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
