package com.fghjtuytjuj.drtysghjertyh.page;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fghjtuytjuj.drtysghjertyh.bean.BaseModel;
import com.fghjtuytjuj.drtysghjertyh.bean.ConfigBean;
import com.fghjtuytjuj.drtysghjertyh.common.SharePreferencesUtil;
import com.fghjtuytjuj.drtysghjertyh.common.StaticCommon;
import com.fghjtuytjuj.drtysghjertyh.net.NetApi;
import com.fghjtuytjuj.drtysghjertyh.view.PromptDialog;
import com.fjsdkqwj.pfdioewjnsd.R;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class PersonalCenterFragment extends RxFragment {

    public View rootView;
    protected LayoutInflater layoutInflater;
    private Bundle bundle;

    private TextView phoneTv;
    private RecyclerView setRvy;

    private String mobileStr;
    private PersonalItemAdapter personalItemAdapter;
    private List<PersonalCenterEntity> list;
    private int[] imgRes = {R.drawable.qetgxdf, R.drawable.xvbrt, R.drawable.jtyd,
            R.drawable.wetdf, R.drawable.ktyud, R.drawable.vbsdfhy, R.drawable.etxfhr};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "投诉邮箱", "个性化推荐", "退出登录", "注销账户"};
    private PromptDialog promptDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_personal_center, null);
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        phoneTv = rootView.findViewById(R.id.phone_tv);
        setRvy = rootView.findViewById(R.id.set_rvy);
        list = new ArrayList<>();
        mobileStr = SharePreferencesUtil.getString("phone");
        phoneTv.setText(mobileStr);
        for (int i = 0; i < 7; i++) {
            PersonalCenterEntity entity = new PersonalCenterEntity();
            entity.setImgRes(imgRes[i]);
            entity.setItemTitle(tvRes[i]);
            list.add(entity);
        }
        personalItemAdapter =  new PersonalItemAdapter(getActivity(), list);
        personalItemAdapter.setHasStableIds(true);
        personalItemAdapter.setOnItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("type", 1);
                    bundle.putString("url", NetApi.REGISTRATION_AGREEMENT);
                    StaticCommon.startActivity(getActivity(), PrivacyAgreementActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("type", 2);
                    bundle.putString("url", NetApi.PRIVACY_AGREEMENT);
                    StaticCommon.startActivity(getActivity(), PrivacyAgreementActivity.class, bundle);
                    break;
                case 2:
                    StaticCommon.startActivity(getActivity(), AppVersionActivity.class, null);
                    break;
                case 3:
                    getConfigValue();
                    break;
                case 4:
                    promptDialog = new PromptDialog(getActivity(), "温馨提示", "关闭或开启推送", "开启", "关闭");
                    promptDialog.setBtnClickListener(new PromptDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            Toast.makeText(getActivity(), "开启成功", Toast.LENGTH_SHORT).show();
                            promptDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            Toast.makeText(getActivity(), "关闭成功", Toast.LENGTH_SHORT).show();
                            promptDialog.dismiss();
                        }
                    });
                    promptDialog.show();
                    break;
                case 5:
                    promptDialog = new PromptDialog(getActivity(), "温馨提示", "确定退出当前登录", "取消", "退出");
                    promptDialog.setBtnClickListener(new PromptDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            promptDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            promptDialog.dismiss();
                            SharePreferencesUtil.saveString("phone", "");
                            StaticCommon.startActivity(getActivity(), RegisterActivity.class, null);
                            getActivity().finish();
                        }
                    });
                    promptDialog.show();
                    break;
                case 6:
                    StaticCommon.startActivity(getActivity(), CloseAccountActivity.class, null);
                    break;
            }
        });
        setRvy.setHasFixedSize(true);
        setRvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setRvy.setAdapter(personalItemAdapter);
    }

    private void getConfigValue(){
        NetApi.getNetApi().getNetInterface().getConfig().enqueue(new Callback<BaseModel<ConfigBean>>() {
            @Override
            public void onResponse(Call<BaseModel<ConfigBean>> call, retrofit2.Response<BaseModel<ConfigBean>> response) {
                if (response.body() == null){
                    return;
                }
                ConfigBean entity = response.body().getData();
                if (entity != null) {
                    SharePreferencesUtil.saveString("app_mail", entity.getAppMail());
                    promptDialog = new PromptDialog(getActivity(), "温馨提示", entity.getAppMail(), "取消", "复制");
                    promptDialog.setBtnClickListener(new PromptDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            promptDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            clipData = ClipData.newPlainText(null, entity.getAppMail());
                            clipboard.setPrimaryClip(clipData);
                            Toast.makeText(getActivity(), "复制成功", Toast.LENGTH_SHORT).show();
                            promptDialog.dismiss();
                        }
                    });
                    promptDialog.show();
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ConfigBean>> call, Throwable t) {

            }
        });
    }

    public class PersonalCenterEntity{
        private int imgRes;

        private String itemTitle;

        public int getImgRes() {
            return imgRes;
        }

        public void setImgRes(int imgRes) {
            this.imgRes = imgRes;
        }

        public String getItemTitle() {
            return itemTitle;
        }

        public void setItemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
        }
    }

}
