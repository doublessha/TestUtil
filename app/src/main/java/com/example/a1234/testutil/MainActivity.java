package com.example.a1234.testutil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import util.SDCardUtil;

public class MainActivity extends AppCompatActivity {

    private Button test_btn;
    private TextView test_tv;
    private ImageView test_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

    }

    private void initListener() {

        test_btn.setOnClickListener(btnClickListen);
    }

    private void initView() {

        test_btn = (Button) findViewById(R.id.test_btn);
        test_tv = (TextView) findViewById(R.id.test_tv);
        test_img = (ImageView) findViewById(R.id.test_img);
    }

    private View.OnClickListener btnClickListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           boolean isMounted =  SDCardUtil.isMounted();
            test_tv.setText(""+isMounted);

        }
    };
}
