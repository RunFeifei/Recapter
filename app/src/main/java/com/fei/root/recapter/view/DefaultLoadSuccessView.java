package com.fei.root.recapter.view;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
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
        imageView.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                v.postDelayed(() -> {
                    Animator animator = ViewAnimationUtils.createCircularReveal(imageView, 0,
                            imageView.getHeight(), 0, (float) Math.hypot(imageView.getWidth(), imageView.getHeight()));
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(700);
                    animator.start();
                    imageView.setVisibility(VISIBLE);
                }, 30);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });
    }
}
