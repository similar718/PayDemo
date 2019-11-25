package com.exam.pay.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.exam.pay.payInfo.PayActivity;
import com.exam.pay.payInfo.PayResultActivity;

public class IntentManager {

    private final String TAG = "IntentManager";

    public static final int EDIT_ACTIVITY = 888;

    private IntentManager() {
    }

    public static final IntentManager getInstance() {
        return IntentManagerHolder.instance;
    }

    private void startActivity(Context context, Intent intent) {
        if (context == null) {
            return;
        }
        context.startActivity(intent);
        // page jump anim
//        if (context instanceof Activity) {
//            ((Activity) context).overridePendingTransition(R.anim.anim_push_left_in,
//                    R.anim.anim_push_left_out);
//        }
    }

    public void startActivity(Context context, Class clz) {
        startActivity(context, new Intent(context, clz));
    }

    private void startAcitivityForResult(Activity context, Intent intent, int requestCode) {
        if (context == null) {
            return;
        }
        context.startActivityForResult(intent, requestCode);
    }

    public void goActivity(Context context, Intent intent) {
        startActivity(context, intent);
    }


    private static class IntentManagerHolder {
        private static final IntentManager instance = new IntentManager();
    }

    public void goPayActivity(Context context){
        startActivity(context, PayActivity.class);
    }

    public void goPayResultActivity(Context context,boolean isSuccess){
        Intent intent = new Intent(context, PayResultActivity.class);
        intent.putExtra("isSuccess",isSuccess);
        startActivity(context,intent);
    }

}
