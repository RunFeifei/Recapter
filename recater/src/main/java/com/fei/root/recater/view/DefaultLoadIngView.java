package com.fei.root.recater.view;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Created by PengFeifei on 17-7-19.
 * ProgressBar既没有scaleType属性也没有gravity属性...
 * 所以将MaterialProgressDrawable copy到工程下
 */

public class DefaultLoadIngView extends LinearLayout {

    private TextView textView;
    private LottieAnimationView lottieView;
    private boolean autoPlay = true;

    public DefaultLoadIngView(Context context) {
        super(context);
        init(context, null);
    }

    public DefaultLoadIngView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 150));

        LayoutParams layoutParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
        layoutParams.gravity = Gravity.CENTER;

        textView = new TextView(context);
        textView.setTextSize(15);
        textView.setText("正在刷新");
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setPadding(50, 0, 0, 0);

        lottieView = new LottieAnimationView(context);
        lottieView.setLayoutParams(layoutParams);
        lottieView.setPadding(0, 0, 50, 0);
        lottieView.setAnimation("love.json");
        lottieView.setScaleType(ImageView.ScaleType.FIT_END);
        lottieView.loop(autoPlay);

        lottieView.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                if (autoPlay) {
                    lottieView.playAnimation();
                }
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                if (lottieView.isAnimating()) {
                    lottieView.cancelAnimation();
                }
            }
        });
        addView(lottieView);
        addView(textView);
    }

    public DefaultLoadIngView setText(CharSequence charSequence) {
        textView.setText(charSequence);
        return this;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public void setProgress(@FloatRange(from = 0f, to = 1f) float progress) {
        lottieView.setProgress(progress);
    }

    public void setAnimation(String animationName) {
        lottieView.setAnimation(animationName);
    }

    public LottieAnimationView getLottieView() {
        return lottieView;
    }
}
