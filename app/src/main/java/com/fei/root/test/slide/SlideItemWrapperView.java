package com.fei.root.test.slide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by PengFeifei on 2018/8/24.
 */
public class SlideItemWrapperView extends HorizontalScrollView {

    private ViewGroup wrapperView;
    private int SLIDE_VIEW_LENGTH;


    public SlideItemWrapperView(Context context) {
        super(context);
        init(context);
    }

    public SlideItemWrapperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideItemWrapperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.setOverScrollMode(OVER_SCROLL_NEVER);
        setHorizontalScrollBarEnabled(false);
        setFillViewport(true);
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
    }

    private void initViews() {
        scrollTo(0, 0);
        if (wrapperView != null) {
            return;
        }
        wrapperView = (ViewGroup) getChildAt(0);
        if (!(wrapperView instanceof LinearLayout)) {
            throw new RuntimeException("not linerlayout");
        }
        View contentView = wrapperView.getChildAt(0);
        if (contentView.getLayoutParams() != null) {
            contentView.getLayoutParams().width = getScreenWidth(getContext());
        }
        View slideView = wrapperView.getChildAt(1);
        SLIDE_VIEW_LENGTH = slideView.getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initViews();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP: {
               return onActionUp();
            }
            case MotionEvent.ACTION_CANCEL: {
                return onActionUp();
            }
        }
        return super.onTouchEvent(ev);
    }


    private int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }


    private boolean onActionUp() {
        int srcollX = getScrollX();
        if (srcollX > SLIDE_VIEW_LENGTH / 2) {
            smoothScrollTo(SLIDE_VIEW_LENGTH, 0);
        } else {
            reset();
        }
        return true;
    }

    private void reset() {
        smoothScrollTo(0, 0);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        scrollTo(0, 0);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        scrollTo(0, 0);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        scrollTo(0, 0);
    }

}
