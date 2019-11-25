package com.exam.pay.payInfo.vm;

import androidx.lifecycle.MutableLiveData;

import com.exam.pay.base.BaseViewModel;
import com.exam.pay.bean.BaseDataBean;
import com.exam.pay.http.ApiRepertory;
import com.exam.pay.http.ApiService;
import com.exam.pay.rxjava2.CommonDisposableObserver;
import com.exam.pay.rxjava2.ComposeUtil;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class PayViewModel extends BaseViewModel {

    public MutableLiveData<Object> mSosData;

    public PayViewModel() {
        mSosData = new MutableLiveData<Object>();
    }

    public void getPayInfo() {
        ApiService mService = ApiRepertory.getInstance().getApiService();
        Observable mObservable = mService.register("SWP25324","wx2421b1c4370ec43b","pay.alipay.app.intl");
        mObservable.subscribeOn(Schedulers.io())
                .compose(ComposeUtil.compose(mView))
                .subscribeWith(new CommonDisposableObserver<BaseDataBean<Object>>(mView) {
                    @Override
                    public void onNext(BaseDataBean<Object> body) {

                    }
                });
    }
}
