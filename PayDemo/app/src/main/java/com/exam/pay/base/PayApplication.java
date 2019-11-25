package com.exam.pay.base;

import android.app.Application;
import android.content.Context;

import com.exam.pay.utils.CrashHandlerUtils;

public class PayApplication extends Application {

    public static PayApplication INSTANCE;

    public static PayApplication getInstance() {
        return INSTANCE;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandlerUtils crashHandler = CrashHandlerUtils.getInstance();
        crashHandler.init(this);
    }

    public Context getContext(){
        return getApplicationContext();
    }
}
