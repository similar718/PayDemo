package com.exam.pay.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.exam.pay.R;
import com.exam.pay.utils.StatusBarUtils;

public abstract class TourBaseActivity<VM extends BaseViewModel, DB extends ViewDataBinding> extends BaseActivity {

    protected VM viewModel;
    public DB dataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, setLayoutId());
        if (viewModel == null) {
            viewModel = createViewModel();
        }
        viewModel.setIView(this);
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected abstract int setLayoutId();

    protected abstract VM createViewModel();

    protected abstract void initData();
}
