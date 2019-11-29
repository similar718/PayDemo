package com.exam.pay.payInfo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.alipay.sdk.app.PayTask;
import com.exam.pay.R;
import com.exam.pay.base.TourBaseActivity;
import com.exam.pay.bean.PayRequestInfo;
import com.exam.pay.bean.PayResult;
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

        setPermission();

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

            dataBinding.rlPayTotalMoney.setVisibility(View.VISIBLE); // 總金額
            dataBinding.rlPayInterfaceType.setVisibility(View.VISIBLE); // 接口類型
            dataBinding.rlPayClientNum.setVisibility(View.VISIBLE); // 商戶號
        } else {
            dataBinding.tvPayTypeBtn.setText("支付寶");
            // 需要顯示的
            dataBinding.rlPayClientOrderNum.setVisibility(View.VISIBLE);//商戶訂單號
            dataBinding.rlPayGoodsDesc.setVisibility(View.VISIBLE);//商品描述
            dataBinding.rlPayTeminalIp.setVisibility(View.VISIBLE);//終端IP

            dataBinding.rlPayTotalMoney.setVisibility(View.VISIBLE); // 總金額

            dataBinding.rlPayInterfaceType.setVisibility(View.GONE); // 接口類型
            dataBinding.rlPayClientNum.setVisibility(View.GONE); // 商戶號

            dataBinding.rlPayNotifyAddress.setVisibility(View.GONE);//通知地址
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
                if (mPayType == 1) { // 微信
                    ToastUtils.showText(mContext, "微信支付正在努力開發中……");
                    return;
                } else {
                    String order_id = dataBinding.etPayClientOrderNum.getText().toString().trim();
                    if (TextUtils.isEmpty(order_id)) {
                        ToastUtils.showText(mContext, "請輸入商戶訂單號");
                        return;
                    }
                    if (TextUtils.isEmpty(dataBinding.etPayGoodsDesc.getText().toString().trim())) {
                        ToastUtils.showText(mContext, "請輸入商品描述");
                        return;
                    }
                    if (TextUtils.isEmpty(dataBinding.etPayTotalMoney.getText().toString().trim())) {
                        ToastUtils.showText(mContext, "請輸入總金額");
                        return;
                    }
                    if (TextUtils.isEmpty(dataBinding.etPayTeminalIp.getText().toString().trim())) {
                        ToastUtils.showText(mContext, "請輸入終端IP");
                        return;
                    }
                    mOrderId = order_id;
                    // 去支付事件
                    goPay();
                }
            }
        });
    }
    private String mOrderId = "";

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

//    private String mAlipayUrl = "http://203.174.52.78:1123/api/AliAppPay/UnifiedOrderApp";
    private String mAlipayUrl = "http://168.63.248.244:81/api/AliAppPay/UnifiedOrderApp";
    private boolean mIsAlipay = false;

    private void goPay(){
        if (mPayType == 1){ // 微信
            ToastUtils.showText(mContext,"微信支付正在努力開發中……");
//            payByWx(null);
        } else if (mPayType == 2){ // 支付寶
            mIsAlipay = true;
            startPayAlipay();
        }
    }

    private void startPayAlipay(){ // Order already paid
        // 支付宝支付
        new Thread() {
            @Override
            public void run() {
                // @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject json = new JSONObject();
                try {
                    json.put("mch_create_ip", dataBinding.etPayTeminalIp.getText().toString().trim());
                    json.put("out_trade_no", mOrderId);
                    json.put("body", dataBinding.etPayGoodsDesc.getText().toString().trim());
                    json.put("total_fee", dataBinding.etPayTotalMoney.getText().toString().trim());
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
                        .url(mAlipayUrl)
                        .post(requestBody)
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showText(mContext,"数据获取失败，请重新尝试！");
                            }
                        });
                        //DialogUtils.showPopMsgInHandleThread(Release_Fragment.this.getContext(), mHandler, "数据获取失败，请重新尝试！");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Log.i("info",string+"");
                        PayRequestInfo mInfo = FastJsonUtils.parseObject(string,PayRequestInfo.class);
                        if (mInfo.isIsSuccess()) {
                            payByZfb(mInfo.getData().getPay_info());
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showText(mContext,mInfo.getData().getErr_msg());
                                }
                            });
                        }
                    }
                });
            }
        }.start();
    }

    private void payByZfb(final String orderInfo) {
//        AsyncUtil.async(new Function<String, Map<String, String>>() {
//            @Override
//            public Map<String, String> apply(String o) throws Exception {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> map = alipay.payV2(orderInfo, true);
//                return map;
//            }
//        }, new Consumer<Map<String, String>>() {
//            @Override
//            public void accept(Map<String, String> map) throws Exception {
                String resultStatus = map.get("resultStatus");//
                String result = map.get("result");//
                String memo = map.get("memo");//
                Log.e("ooooooooo","resultStatus = "+resultStatus + " result = "+result + " memo = "+memo);
                if ("9000".equals(resultStatus)) {//支付成功
//                    try {
//                        JSONObject object = new JSONObject(result);
//                        String out_trade_no = object.optJSONObject("alipay_trade_app_pay_response").optString("out_trade_no");
//                        String trade_no = object.optJSONObject("alipay_trade_app_pay_response").optString("trade_no");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                    mHandler.sendEmptyMessage(MSG_ALIPAY_SUCCESS);
                } else {
                    mDemo = memo;
                    mHandler.sendEmptyMessage(MSG_ALIPAY_FAILED);
                }
//            }
//        });
    }

    private String mDemo = "";
    private final int MSG_ALIPAY_FAILED = 0x03;
    private final int MSG_ALIPAY_SUCCESS = 0x04;
    private final int SDK_PAY_FLAG = 0x05;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        mHandler.sendEmptyMessage(MSG_ALIPAY_SUCCESS);
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        mDemo = payResult.getMemo();
                        mHandler.sendEmptyMessage(MSG_ALIPAY_FAILED);
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MSG_ALIPAY_FAILED:
                    ToastUtils.showText(mContext,mDemo);
                    IntentManager.getInstance().goPayResultActivity(mContext,false);
                    finish();
                    break;
                case MSG_ALIPAY_SUCCESS:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            IntentManager.getInstance().goPayResultActivity(mContext,true);
                        }
                    });
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

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

    // 定位需要的权限
    private static String[] mPermission = {
            Manifest.permission.READ_PHONE_STATE
    };

    private static final int WRITE_COARSE_LOCATION_REQUEST_CODE = 0x001;

    private void setPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, mPermission, WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        if (requestCode == WRITE_COARSE_LOCATION_REQUEST_CODE) {

        }
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
