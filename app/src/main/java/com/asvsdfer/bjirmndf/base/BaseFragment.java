package com.asvsdfer.bjirmndf.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxFragment;

public abstract class BaseFragment extends RxFragment {

    private View rootView;
    protected LayoutInflater layoutInflater;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (rootView == null && getLayoutId() > 0) {
            rootView = inflater.inflate(getLayoutId(), null);
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }

        return rootView;
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
