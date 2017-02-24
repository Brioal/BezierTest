package com.brioal.beziertest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
    }

    public static void satrtThree(Context context) {
        Intent intent = new Intent(context, ThreeActivity.class);
        context.startActivity(intent);
    }
}
