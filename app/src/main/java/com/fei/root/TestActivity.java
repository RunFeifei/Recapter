package com.fei.root;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.fei.root.recapter.R;
import com.fei.root.viewbinder.Binder;
import com.fei.root.viewbinder.ViewBinder;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_test2, null);
        setContentView(view);
    }



//    @Binder
//    private LottieAnimationView lottie;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View view = getLayoutInflater().inflate(R.layout.activity_test, null);
//        setContentView(view);
//        ViewBinder.bindViews(this, view);
//        lottie.setAnimation("love.json");
//        lottie.setScaleType(ImageView.ScaleType.FIT_END);
//    }
//
//
//
//    public void onClickTest(View view) {
//        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 0.6f);
//        animator.setInterpolator(new LinearInterpolator());
//        animator.setDuration(2000);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                lottie.setProgress((Float) animation.getAnimatedValue());
//            }
//        });
//        animator.start();
//    }


}
