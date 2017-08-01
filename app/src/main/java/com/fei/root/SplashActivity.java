package com.fei.root;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fei.root.recapter.R;
import com.fei.root.viewbinder.ViewBinder;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_splash, null);
        setContentView(view);
        ViewBinder.bindViews(this, view);
    }

    public void onClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onClick1(View view) {
        startActivity(new Intent(this, SwipeActivity.class));
    }

    public void onClick2(View view) {
        startActivity(new Intent(this, MultipleActivity.class));
    }

}
