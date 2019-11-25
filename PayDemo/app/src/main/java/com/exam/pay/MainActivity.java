package com.exam.pay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.exam.pay.manager.IntentManager;

public class MainActivity extends AppCompatActivity {

    private TextView tv_pay_goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_pay_goods = findViewById(R.id.tv_pay_goods);
        tv_pay_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentManager.getInstance().goPayActivity(MainActivity.this);
            }
        });
    }
}
