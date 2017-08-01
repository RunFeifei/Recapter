package com.fei.root.recater.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.fei.root.recater.R;


/**
 * Created by PengFeifei on 17-7-19.
 */

public class DefaultLoadFailView extends LinearLayout {


    public DefaultLoadFailView(Context context) {
        super(context);
        init(context, null);
    }

    public DefaultLoadFailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 150));

        LayoutParams layoutParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
        layoutParams.gravity = Gravity.CENTER;

        TextView textView = new TextView(context);
        textView.setTextSize(15);
        textView.setText("刷新失败");
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setPadding(50, 0, 0, 0);

        LottieAnimationView lottieView = new LottieAnimationView(context);
        lottieView.setLayoutParams(layoutParams);
        lottieView.setPadding(0, 0, 50, 0);
        lottieView.setAnimation("x_pop.json");
        lottieView.setScaleType(ImageView.ScaleType.FIT_END);
        lottieView.loop(true);

        lottieView.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                lottieView.playAnimation();
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                lottieView.pauseAnimation();
            }
        });
        addView(lottieView);
        addView(textView);
    }
}
