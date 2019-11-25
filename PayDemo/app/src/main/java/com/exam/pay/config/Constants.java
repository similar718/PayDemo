package com.exam.pay.config;

import android.os.Environment;

import com.exam.pay.base.PayApplication;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class Constants {
    // crash 文件
    public static final String CRASH_FILE = Environment.getExternalStorageDirectory().getPath() + "/android/com.exam.pay/crash_log/";

    public static final String WX_APP_ID = "wx2421b1c4370ec43b";
    private static IWXAPI wx_api; //全局的微信api对象
    public static IWXAPI getWx_api() {
        if (null == wx_api) {
            wx_api = WXAPIFactory.createWXAPI(PayApplication.getInstance().getContext(), Constants.WX_APP_ID, true);
            wx_api.registerApp(Constants.WX_APP_ID);
        }
        return wx_api;
    }
}
