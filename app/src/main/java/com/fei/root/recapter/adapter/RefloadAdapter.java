package com.fei.root.recapter.adapter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.fei.root.recapter.LoadingType;
import com.fei.root.recapter.action.RefloadAdapterAction;
import com.fei.root.recapter.action.RefloadDataAction;
import com.fei.root.recapter.action.RefloadViewAction;
import com.fei.root.recapter.listener.AbsAnimatorListener;

import java.util.List;

/**
 * Created by PengFeifei on 17-7-14.
 * 下拉刷新,上拉加载更多
 */

public abstract class RefloadAdapter<Data> extends HeaterAdapter<Data> implements View.OnTouchListener, RefloadAdapterAction {

    public static final int REFRESH_HEADER_ID = Integer.MIN_VALUE;
    public static final int REFRESH_FOOTER_ID = -1;
    private static final String TAG = RefloadAdapter.class.getSimpleName();

    private RefloadViewAction refreshHeader;
    private float touchDownY;

    private RefloadDataAction<Data> refloadDataAction;

    private static final int LOADING_TIME_OUT = 10 * 1000;
    private Runnable timeOutRunable = () -> onLoadFail();

    public RefloadAdapter(@NonNull List<Data> lisData, @LayoutRes int layoutId) {
        super(lisData, layoutId);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setOnTouchListener(this);
    }

    private View getPullView(@LoadingType int type, RefloadViewAction refloadViewAction) {
        if (refloadViewAction == null) {
            return null;
        }
        switch (type) {
            case LoadingType.LOAD_START: {
                return refloadViewAction.onLoadStart();
            }
            case LoadingType.LOAD_ING: {
                return refloadViewAction.onLoading();
            }
            case LoadingType.LOAD_FAIL: {
                return refloadViewAction.onLoadFail();
            }
            case LoadingType.LOAD_SUCCESS: {
                return refloadViewAction.onLoadSuccess();
            }
        }
        return null;
    }

    public void setRefreshHeader(RefloadViewAction refreshHeader) {
        this.refreshHeader = refreshHeader;
    }
    public void setRefloadDataListener(RefloadDataAction refloadDataAction) {
        this.refloadDataAction = refloadDataAction;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!getRecyclerView().canScrollVertically(-1) && event.getY() - touchDownY > 0) {
                    onLoadStart(event.getY() - touchDownY);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                releaseHeader();
                break;
        }
        return false;
    }

    @Override
    public void onLoadStart(float deltaY) {
        Log.e(TAG, "onLoadStart");
        View view = getPullView(LoadingType.LOAD_START, refreshHeader);
        addHeader(REFRESH_HEADER_ID, view);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
        }
        layoutParams.height = Math.min((int) deltaY / 3, 300);
        view.setLayoutParams(layoutParams);
        if (layoutParams.height == 0) {
            removeHeaderInDelay(0);
        }
    }

    @Override
    public void onLoading() {
        Log.e(TAG, "onLoading");
        getRecyclerView().removeCallbacks(timeOutRunable);
        getRecyclerView().postDelayed(timeOutRunable, LOADING_TIME_OUT);
        addHeader(REFRESH_HEADER_ID, getPullView(LoadingType.LOAD_ING, refreshHeader));
        if (refloadDataAction != null) {
            refloadDataAction.onLoading();
        }
    }

    @Override
    public void onLoadFail() {
        getRecyclerView().removeCallbacks(timeOutRunable);
        if (refloadDataAction != null) {
            refloadDataAction.onLoadFail();
        }
        addHeader(REFRESH_HEADER_ID, getPullView(LoadingType.LOAD_FAIL, refreshHeader));
    }

    @Override
    public void onLoadSuccess() {
        getRecyclerView().removeCallbacks(timeOutRunable);
        if (refloadDataAction != null) {
            refloadDataAction.onLoadSuccess();
        }
        addHeader(REFRESH_HEADER_ID, getPullView(LoadingType.LOAD_SUCCESS, refreshHeader));
    }

    private View getRefreshHeader() {
        return getHeader(REFRESH_HEADER_ID);
    }

    private View getLoadFooter() {
        return getHeader(REFRESH_FOOTER_ID);
    }

    private void releaseHeader() {
        Log.e(TAG, "releaseHeader");
        final View view = getRefreshHeader();
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
        }
        ValueAnimator animator = ValueAnimator.ofInt(layoutParams.height, 0);
        animator.setDuration(300).start();
        animator.addUpdateListener((animation -> {
            ViewGroup.LayoutParams layoutParam = view.getLayoutParams();
            layoutParam.height = (int) animation.getAnimatedValue();
            view.setLayoutParams(layoutParam);
        }));
        animator.addListener(new AbsAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                onLoading();
            }
        });
        animator.start();
    }

    private void removeHeaderInDelay(int delay) {
        if (delay == 0) {
            removeHeader(REFRESH_HEADER_ID);
            return;
        }
        getRecyclerView().postDelayed(() -> removeHeader(REFRESH_HEADER_ID), delay);
    }

}
