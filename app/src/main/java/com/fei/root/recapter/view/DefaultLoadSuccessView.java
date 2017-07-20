package com.fei.root.recapter.view;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.fei.root.recapter.R;

/**
 * Created by PengFeifei on 17-7-19.
 */

public class DefaultLoadSuccessView extends DefaultLoadStartView {


    public DefaultLoadSuccessView(Context context) {
        super(context);
    }

    public DefaultLoadSuccessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, @Nullable AttributeSet attrs) {
        super.init(context, attrs);
        textView.setText("刷新成功");
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.btn_check_to_on_mtrl_015));
        imageView.setVisibility(INVISIBLE);
        imageView.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Animator animator = ViewAnimationUtils.createCircularReveal(v, 0,
                        v.getHeight(), 0, (float) Math.hypot(v.getWidth(), v.getHeight()));
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(700);
                try {
                    animator.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                v.setVisibility(VISIBLE);
            }
        });
    }
}
