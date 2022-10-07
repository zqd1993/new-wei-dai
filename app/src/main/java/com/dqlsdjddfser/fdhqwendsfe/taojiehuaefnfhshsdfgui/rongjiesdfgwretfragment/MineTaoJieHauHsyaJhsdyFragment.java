package com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui.rongjiesdfgwretfragment;

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

import com.dqlsdjddfser.fdhqwendsfe.R;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgapi.TaoJieHauHsyaJhsdyRetrofitManager;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgbase.BaseTaoJieHauHsyaJhsdyFragment;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgbase.TaoJieHauHsyaJhsdyObserverManager;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyBaseModel;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyConfigModel;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyMineItemModel;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui.TaoJieHauHsyaJhsdyAppInfoActivity;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui.TaoJieHauHsyaJhsdyCancellationActivity;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui.LoginTaoJieHauHsyaJhsdyActivity;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui.TaoJieHauHsyaJhsdyUserYsxyActivity;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui.rongjiesdfgwretadapter.TaoJieHauHsyaJhsdyMineItemAdapter;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.RemindTaoJieHauHsyaJhsdyDialog;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.SharePreferencesUtilTaoJieHauHsyaJhsdy;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.StaticTaoJieHauHsyaJhsdyUtil;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.ToastRongjieSfFgdfUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineTaoJieHauHsyaJhsdyFragment extends BaseTaoJieHauHsyaJhsdyFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList, mineList1;
    private View logoutBtn;

    private String mobileStr;
    private TaoJieHauHsyaJhsdyMineItemAdapter taoJieHauHsyaJhsdyMineItemAdapter, taoJieHauHsyaJhsdyMineItemAdapter1;
    private List<TaoJieHauHsyaJhsdyMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.cvd, R.drawable.erfh, R.drawable.zcvzbery,
            R.drawable.cvd, R.drawable.erfh, R.drawable.zcvzbery, R.drawable.zcvzbery};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户", "退出登录"};
    private Bundle bundle;
    private RemindTaoJieHauHsyaJhsdyDialog mRemindTaoJieHauHsyaJhsdyDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tao_jie_hua_djheru_fhentj_mine;
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
        mobileStr = SharePreferencesUtilTaoJieHauHsyaJhsdy.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 7; i++) {
            TaoJieHauHsyaJhsdyMineItemModel model = new TaoJieHauHsyaJhsdyMineItemModel();
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
            mRemindTaoJieHauHsyaJhsdyDialog = new RemindTaoJieHauHsyaJhsdyDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindTaoJieHauHsyaJhsdyDialog.setBtnClickListener(new RemindTaoJieHauHsyaJhsdyDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindTaoJieHauHsyaJhsdyDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindTaoJieHauHsyaJhsdyDialog.dismiss();
                    SharePreferencesUtilTaoJieHauHsyaJhsdy.saveString("phone", "");
                    StaticTaoJieHauHsyaJhsdyUtil.startActivity(getActivity(), LoginTaoJieHauHsyaJhsdyActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindTaoJieHauHsyaJhsdyDialog.show();
            mRemindTaoJieHauHsyaJhsdyDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        taoJieHauHsyaJhsdyMineItemAdapter =  new TaoJieHauHsyaJhsdyMineItemAdapter(R.layout.adapter_mine_list_layout_tao_jie_hua_djheru_fhentj, list);
        taoJieHauHsyaJhsdyMineItemAdapter.setHasStableIds(true);
        taoJieHauHsyaJhsdyMineItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", TaoJieHauHsyaJhsdyRetrofitManager.ZCXY);
                    StaticTaoJieHauHsyaJhsdyUtil.startActivity(getActivity(), TaoJieHauHsyaJhsdyUserYsxyActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", TaoJieHauHsyaJhsdyRetrofitManager.YSXY);
                    StaticTaoJieHauHsyaJhsdyUtil.startActivity(getActivity(), TaoJieHauHsyaJhsdyUserYsxyActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(taoJieHauHsyaJhsdyMineItemAdapter);
        taoJieHauHsyaJhsdyMineItemAdapter1 =  new TaoJieHauHsyaJhsdyMineItemAdapter(R.layout.adapter_tao_jie_hua_djheru_fhentj_mine_list_layout_hor, list1);
        taoJieHauHsyaJhsdyMineItemAdapter1.setHasStableIds(true);
        taoJieHauHsyaJhsdyMineItemAdapter1.setItemClickListener(position -> {
            switch (position){
                case 0:
                    StaticTaoJieHauHsyaJhsdyUtil.startActivity(getActivity(), TaoJieHauHsyaJhsdyAppInfoActivity.class, null);
                    break;
                case 1:
                    mRemindTaoJieHauHsyaJhsdyDialog = new RemindTaoJieHauHsyaJhsdyDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindTaoJieHauHsyaJhsdyDialog.setBtnClickListener(new RemindTaoJieHauHsyaJhsdyDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastRongjieSfFgdfUtil.showShort("开启成功");
                            mRemindTaoJieHauHsyaJhsdyDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastRongjieSfFgdfUtil.showShort("关闭成功");
                            mRemindTaoJieHauHsyaJhsdyDialog.dismiss();
                        }
                    });
                    mRemindTaoJieHauHsyaJhsdyDialog.show();
                    mRemindTaoJieHauHsyaJhsdyDialog.setBtnStr("开启", "关闭");
                    break;
                case 2:
                    StaticTaoJieHauHsyaJhsdyUtil.startActivity(getActivity(), TaoJieHauHsyaJhsdyCancellationActivity.class, null);
                    break;
                case 3:
                    mRemindTaoJieHauHsyaJhsdyDialog = new RemindTaoJieHauHsyaJhsdyDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
                    mRemindTaoJieHauHsyaJhsdyDialog.setBtnClickListener(new RemindTaoJieHauHsyaJhsdyDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            mRemindTaoJieHauHsyaJhsdyDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            mRemindTaoJieHauHsyaJhsdyDialog.dismiss();
                            SharePreferencesUtilTaoJieHauHsyaJhsdy.saveString("phone", "");
                            StaticTaoJieHauHsyaJhsdyUtil.startActivity(getActivity(), LoginTaoJieHauHsyaJhsdyActivity.class, null);
                            getActivity().finish();
                        }
                    });
                    mRemindTaoJieHauHsyaJhsdyDialog.show();
                    mRemindTaoJieHauHsyaJhsdyDialog.setBtnStr("取消", "退出");
                    break;
            }
        });
        mineList1.setHasFixedSize(true);
        mineList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineList1.setAdapter(taoJieHauHsyaJhsdyMineItemAdapter1);
    }

    private void getConfig() {
        Observable<TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyConfigModel>> observable = TaoJieHauHsyaJhsdyRetrofitManager.getRetrofitManager().getApiService().getConfig();

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
                            SharePreferencesUtilTaoJieHauHsyaJhsdy.saveString("APP_MAIL", taoJieHauHsyaJhsdyConfigModel.getAppMail());
                            mRemindTaoJieHauHsyaJhsdyDialog = new RemindTaoJieHauHsyaJhsdyDialog(getActivity(), "温馨提示", taoJieHauHsyaJhsdyConfigModel.getAppMail(), false);
                            mRemindTaoJieHauHsyaJhsdyDialog.setBtnClickListener(new RemindTaoJieHauHsyaJhsdyDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindTaoJieHauHsyaJhsdyDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, taoJieHauHsyaJhsdyConfigModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastRongjieSfFgdfUtil.showShort("复制成功");
                                    mRemindTaoJieHauHsyaJhsdyDialog.dismiss();
                                }
                            });
                            mRemindTaoJieHauHsyaJhsdyDialog.show();
                            mRemindTaoJieHauHsyaJhsdyDialog.setBtnStr("取消", "复制");
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
