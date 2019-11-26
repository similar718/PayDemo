package com.exam.pay.payInfo;

import android.content.Context;
import android.view.View;

import com.exam.pay.R;
import com.exam.pay.base.TourBaseActivity;
import com.exam.pay.databinding.ActivityPayResultBinding;
import com.exam.pay.payInfo.vm.PayViewModel;
import com.exam.pay.utils.StatusBarUtils;

public class PayResultActivity extends TourBaseActivity<PayViewModel, ActivityPayResultBinding> {

    private Context mContext;

    private boolean isSuccess = false;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected PayViewModel createViewModel() {
        viewModel = new PayViewModel();
        viewModel.setIView(this);
        return viewModel;
    }

    @Override
    protected void initData() {
        mContext = this;
        dataBinding.setModel(viewModel);
        dataBinding.setActivity(this);
//        StatusBarUtils.changeStatusBarColor(this, R.color.main_color);
        isSuccess = getIntent().getBooleanExtra("isSuccess",false);

        initShowView();

        dataBinding.tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayResultActivity.this.finish();
            }
        });
    }
    private void initShowView(){
        if (isSuccess){ // 成功界面
            dataBinding.tvTop.setText("支付成功");
            dataBinding.tvContent.setText("支付成功");
            dataBinding.tvContent.setCompoundDrawablesWithIntrinsicBounds(null, mContext.getResources().getDrawable(R.mipmap.finish), null, null);
        } else { // 失敗界面
            dataBinding.tvTop.setText("支付失敗");
            dataBinding.tvContent.setText("支付失敗");
            dataBinding.tvContent.setCompoundDrawablesWithIntrinsicBounds(null, mContext.getResources().getDrawable(R.mipmap.failed), null, null);
        }
    }
}