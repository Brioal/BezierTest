package com.brioal.beziertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //二阶曲线
    public void startTwo(View view) {
        TwoActivity.startTwo(this);
    }

    //三阶曲线
    public void startThree(View view) {
        ThreeActivity.satrtThree(this);
    }
}
