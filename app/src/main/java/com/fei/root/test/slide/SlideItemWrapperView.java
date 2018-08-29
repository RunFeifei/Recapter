package com.fei.root.test.slide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by PengFeifei on 2018/8/24.
 */
public class SlideItemWrapperView extends HorizontalScrollView implements OnSLideAction {

    private ViewGroup wrapperView;
    private int SLIDE_VIEW_LENGTH;
    private boolean isSlidedOut;
    private int positionInList;
    private OnSlideStatus onSlideStatus;


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
            View slideView = wrapperView.getChildAt(1);
            SLIDE_VIEW_LENGTH = slideView.getMeasuredWidth();
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
        isSlidedOut = srcollX > SLIDE_VIEW_LENGTH / 2;
        doSlide(!isSlidedOut);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initViews();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        smoothScrollTo(0, 0);
        initViews();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (!(parent instanceof RecyclerView)) {
            throw new IllegalStateException("parent view is not recyclerView");
        }
        RecyclerView recyclerView = (RecyclerView) parent;
        positionInList = recyclerView.getChildLayoutPosition(this);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof OnSlideStatus) {
            onSlideStatus = (OnSlideStatus) adapter;
        }


    }

    @Override
    public int getPositionInList() {
        return positionInList;
    }

    @Override
    public boolean isSlidedOut() {
        return isSlidedOut;
    }

    @Override
    public void doSlide(boolean in) {
        smoothScrollTo(in ? 0 : SLIDE_VIEW_LENGTH, 0);
        if ((!in) && (onSlideStatus != null)) {
            onSlideStatus.updateLastSlideOutPosition(getPositionInList());
        }
    }
}
