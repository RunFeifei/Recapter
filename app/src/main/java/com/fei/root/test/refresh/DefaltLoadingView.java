package com.fei.root.test.refresh;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fei.root.recapter.R;

/**
 * Created by PengFeifei on 2018/5/16.
 */
public class DefaltLoadingView extends LinearLayout {

    private ImageView imageView;
    private boolean rotating;

    public DefaltLoadingView(Context context, boolean rotating) {
        super(context);
        init(context);
        this.rotating = rotating;
    }

    public DefaltLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DefaltLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_load_ing, this);
        imageView = (ImageView) view.findViewById(R.id.imgLoad);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) com.fei.root.common.Display.dp2px(60)));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        doRotate(imageView);
    }

    private void doRotate(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, rotating ? 360 : -360);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.setRepeatCount(Integer.MAX_VALUE);
        animator.start();
    }

    public void onMove()
    {

    }

}
