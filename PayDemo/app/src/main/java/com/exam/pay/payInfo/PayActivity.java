package com.exam.pay.payInfo;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;

import com.alipay.sdk.app.PayTask;
import com.exam.pay.R;
import com.exam.pay.base.TourBaseActivity;
import com.exam.pay.bean.PayRequestInfo;
import com.exam.pay.config.Constants;
import com.exam.pay.databinding.ActivityPayBinding;
import com.exam.pay.dialog.PaySelectDialog;
import com.exam.pay.eventbus.PayResultBus;
import com.exam.pay.manager.IntentManager;
import com.exam.pay.payInfo.vm.PayViewModel;
import com.exam.pay.utils.AsyncUtil;
import com.exam.pay.utils.FastJsonUtils;
import com.exam.pay.utils.StatusBarUtils;
import com.exam.pay.utils.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PayActivity extends TourBaseActivity<PayViewModel, ActivityPayBinding> {

    private Context mContext;

    private boolean mShowWechat = true;

    private PaySelectDialog mDialog;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    protected PayViewModel createViewModel() {
        viewModel = new PayViewModel();
        viewModel.setIView(this);
        viewModel.mSosData.observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {

            }
        });
        return viewModel;
    }

    @Override
    protected void initData() {
        mContext = this;
        dataBinding.setModel(viewModel);
        dataBinding.setActivity(this);
//        StatusBarUtils.changeStatusBarColor(PayActivity.this,R.color.main_color);
        StatusBarUtils.setStatusBarLightMode(PayActivity.this, false);

        initShowView();

        initClick();
    }

    private void initShowView(){
        if (mShowWechat){
            dataBinding.tvPayTypeBtn.setText("微信");
            // 需要顯示的
            dataBinding.rlPayClientReturnNum.setVisibility(View.VISIBLE);// 商戶退款訂單號
            dataBinding.rlPayReturnMoney.setVisibility(View.VISIBLE); // 退款金額
            dataBinding.rlPayOptionPer.setVisibility(View.VISIBLE); // 操作員
            dataBinding.rlPayRoundNumstr.setVisibility(View.VISIBLE); // 隨機字串
            dataBinding.rlPaySign.setVisibility(View.VISIBLE); // 簽名
            // 需要隱藏的
            dataBinding.rlPayClientOrderNum.setVisibility(View.GONE);//商戶訂單號
            dataBinding.rlPayGoodsDesc.setVisibility(View.GONE);//商品描述
            dataBinding.rlPayTeminalIp.setVisibility(View.GONE);//終端IP
            dataBinding.rlPayNotifyAddress.setVisibility(View.GONE);//通知地址
        } else {
            dataBinding.tvPayTypeBtn.setText("支付寶");
            // 需要顯示的
            dataBinding.rlPayClientOrderNum.setVisibility(View.VISIBLE);//商戶訂單號
            dataBinding.rlPayGoodsDesc.setVisibility(View.VISIBLE);//商品描述
            dataBinding.rlPayTeminalIp.setVisibility(View.VISIBLE);//終端IP
            dataBinding.rlPayNotifyAddress.setVisibility(View.VISIBLE);//通知地址
            // 需要隱藏的
            dataBinding.rlPayClientReturnNum.setVisibility(View.GONE);// 商戶退款訂單號
            dataBinding.rlPayReturnMoney.setVisibility(View.GONE); // 退款金額
            dataBinding.rlPayOptionPer.setVisibility(View.GONE); // 操作員
            dataBinding.rlPayRoundNumstr.setVisibility(View.GONE); // 隨機字串
            dataBinding.rlPaySign.setVisibility(View.GONE); // 簽名
        }
    }

    private void initClick(){
        dataBinding.tvPayTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 選擇支付方式
                initDialog();
            }
        });

        dataBinding.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 去支付事件
                goPay();
            }
        });
    }

    private int mPayType = 1;

    private void initDialog(){
        if (mDialog == null){
            mDialog = new PaySelectDialog(mContext, new PaySelectDialog.OnShareClickListener() {
                @Override
                public void onShareClick(int position) {
                    if (position == 1){ // 微信
                        mPayType = 1;
                        mShowWechat = true;
                    } else if (position == 2){ // 支付寶
                        mPayType = 2;
                        mShowWechat = false;
                    }
                    initShowView();
                    mDialog.dismiss();
                }
            });
        }
        mDialog.setType(mPayType);
        mDialog.show();
    }

    private void goPay(){
        // 支付宝支付
        new Thread() {
            @Override
            public void run() {
                // @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject json = new JSONObject();
                try {
                    json.put("mch_create_ip", "127.0.0.1");
                    json.put("out_trade_no", "testtest");
                    json.put("body", "测试");
                    json.put("total_fee", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //申明给服务端传递一个json串
                //创建一个OkHttpClient对象
                OkHttpClient okHttpClient = new OkHttpClient();
                //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
                //json为String类型的json数据
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url("http://203.174.52.78:1123/AliAppPay/UnifiedOrderApp")
                        .post(requestBody)
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //DialogUtils.showPopMsgInHandleThread(Release_Fragment.this.getContext(), mHandler, "数据获取失败，请重新尝试！");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Log.i("info",string+"");
                        PayRequestInfo mInfo = FastJsonUtils.parseObject(string,PayRequestInfo.class);
                        payByZfb(mInfo.getPay_info());
                    }
                });
            }
        }.start();


        viewModel.getPayInfo();
        if (mPayType == 1){ // 微信
            payByWx(null);
        } else if (mPayType == 2){ // 支付寶
            payByZfb("");
        }
    }

    private void payByZfb(final String orderInfo) {
        AsyncUtil.async(new Function<String, Map<String, String>>() {
            @Override
            public Map<String, String> apply(String o) throws Exception {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> map = alipay.payV2(orderInfo, true);
                return map;
            }
        }, new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> map) throws Exception {
                String resultStatus = map.get("resultStatus");//
                String result = map.get("result");//
                String memo = map.get("memo");//
                if ("9000".equals(resultStatus)) {//支付成功
                    try {
                        JSONObject object = new JSONObject(result);
                        String out_trade_no = object.optJSONObject("alipay_trade_app_pay_response").optString("out_trade_no");
                        String trade_no = object.optJSONObject("alipay_trade_app_pay_response").optString("trade_no");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    IntentManager.getInstance().goPayResultActivity(mContext,true);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    ToastUtils.showText(mContext,memo);
                    IntentManager.getInstance().goPayResultActivity(mContext,false);
                }
            }
        });
    }

    private void payByWx(Map<String,String> jsonMap) {
        try {
            PayReq req = new PayReq();
            req.appId = jsonMap.get("appid");
            req.partnerId = jsonMap.get("partnerid");
            req.prepayId = jsonMap.get("prepayid");
            req.packageValue = jsonMap.get("package");
            req.nonceStr = jsonMap.get("noncestr");
            req.timeStamp = jsonMap.get("timestamp");
            req.sign = jsonMap.get("sign");
//                        req.extData = "app data"; // optional
//                        MToast.show("正常调起支付");
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            boolean isHaveWx = Constants.getWx_api().sendReq(req);
            if(!isHaveWx){
                ToastUtils.showText(mContext,"请确定是否已安装微信!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showText(mContext,"支付失败");
        }
    }

    @Subscribe
    public void onPayResult(PayResultBus payResultBus){
        if(payResultBus!=null){
            if(payResultBus.isSuccess()){
                IntentManager.getInstance().goPayResultActivity(mContext,true);
                finish();
            }else {
                IntentManager.getInstance().goPayResultActivity(mContext,false);
            }
        }
    }
}
