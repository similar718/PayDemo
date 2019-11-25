package com.exam.pay.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import androidx.databinding.DataBindingUtil;

import com.exam.pay.R;
import com.exam.pay.bean.PaySelectType;
import com.exam.pay.databinding.DialogPaySelectBinding;

public class PaySelectDialog extends Dialog {

    private Context mContext;

    DialogPaySelectBinding binding;
    private OnShareClickListener listener;
    public OnShareClickListener getListener() {
        return listener;
    }

    public void setListener(OnShareClickListener listener) {
        this.listener = listener;
    }

    public interface OnShareClickListener{
        void onShareClick(int position);
    }

    public PaySelectDialog(Context context, OnShareClickListener onClickListener) {
        super(context, R.style.dialog_custom);
        this.mContext = context;
        setListener(onClickListener);
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_pay_select, null, false);
        setContentView(binding.getRoot());
        initView();
    }
    protected void initView() {
        this.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        this.getWindow().setAttributes(params);
        // 点击之外的地方 dismiss
        setCancelable(true);
        binding.alipayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaySelectType paySelectType = new PaySelectType();
                paySelectType.setType(2);
                binding.setBean(paySelectType);
                listener.onShareClick(2);
            }
        });
        binding.wechatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaySelectType paySelectType = new PaySelectType();
                paySelectType.setType(1);
                binding.setBean(paySelectType);
                listener.onShareClick(1);
            }
        });
        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    public void setType(int type){
        PaySelectType paySelectType = new PaySelectType();
        paySelectType.setType(type);
        binding.setBean(paySelectType);
    }
}
