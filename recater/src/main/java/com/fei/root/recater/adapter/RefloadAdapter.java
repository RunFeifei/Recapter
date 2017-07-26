package com.fei.root.recater.adapter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;


import com.fei.root.recater.LoadingType;
import com.fei.root.recater.action.OnLoadMoreData;
import com.fei.root.recater.action.RefloadAdapterAction;
import com.fei.root.recater.action.RefloadViewAction;
import com.fei.root.recater.action.OnRefreshData;
import com.fei.root.recater.listener.AbsAnimatorListener;

import java.util.List;

/**
 * Created by PengFeifei on 17-7-14.
 * 下拉刷新,上拉加载更多
 * 需要搭配RefloadRecyclerView使用
 */

public abstract class RefloadAdapter<Data> extends HeaterAdapter<Data> implements View.OnTouchListener, RefloadAdapterAction {

    public static final int REFRESH_HEADER_ID = Integer.MIN_VALUE;
    public static final int LOAD_FOOTER_ID = 0;
    private static final String TAG = RefloadAdapter.class.getSimpleName();

    private boolean isEnablePullRefreshing;
    private boolean isEnablePullLoadMore;

    private RefloadViewAction refreshHeader;
    private RefloadViewAction refreshFooter;
    private float touchDownY;
    private boolean isRefreshing;
    private boolean isLoading;
    private boolean isBeyondScreen;

    private OnRefreshData<Data> onRefreshData;
    private OnLoadMoreData<Data> pullUpDataAction;

    private static final int LOADING_TIME_OUT = 30 * 1000;
    private Runnable timeOutPullDown = () -> onLoadFail(true);
    private Runnable timeOutPullUp = () -> onLoadFail(false);

    public RefloadAdapter(@NonNull List<Data> lisData, @LayoutRes int layoutId) {
        super(lisData, layoutId);
    }

    public RefloadAdapter(@LayoutRes int layoutId) {
        super(layoutId);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setOnTouchListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
               /* View lastChildView = recyclerView.getLayoutManager().findViewByPosition(getItemCount() - getFootersSize() - 1);
                boolean isLastItemVisible = lastChildView != null;
                Log.e(TAG, "isLastItemVisible-->" + isLastItemVisible);*/
                isBeyondScreen = getRecyclerView().getLayoutManager().getChildCount() < getItemCount() - getFootersSize();
            }
        });
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
            case LoadingType.LOAD_NONE: {
                return refloadViewAction.onLoadNone();
            }
        }
        return null;
    }

    public void setRefreshHeader(RefloadViewAction refreshHeader) {
        this.refreshHeader = refreshHeader;
    }

    public void setLoadMoreFooter(RefloadViewAction refreshFooter) {
        this.refreshFooter = refreshFooter;
    }

    public void setRefreshDataListener(OnRefreshData<Data> onRefreshData) {
        this.onRefreshData = onRefreshData;
    }

    public void setLoadMoreDataListener(OnLoadMoreData onLoadMoreData) {
        this.pullUpDataAction = onLoadMoreData;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isEnablePullRefreshing && !isRefreshing
                        && !getRecyclerView().canScrollVertically(-1)
                        && event.getY() - touchDownY > getTouchSlop()) {
                    onLoadStart(event.getY() - touchDownY, true);
                    return true;
                }
                if (isEnablePullLoadMore && !isLoading
                        && !getRecyclerView().canScrollVertically(1)
                        && touchDownY - event.getY() > 0 && isBeyondScreen) {
                    onLoadStart(event.getY() - touchDownY, false);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isEnablePullRefreshing && !isRefreshing) {
                    doRefreshing();
                }
                break;
        }
        return false;
    }

    @Override
    public void onLoadStart(float deltaY, boolean pulldown) {
        if (pulldown) {
            View view = getPullView(LoadingType.LOAD_START, refreshHeader);
            if (view == null) {
                return;
            }
            addHeader(REFRESH_HEADER_ID, view);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(layoutParams);
            }
            int height=(int) deltaY;
//            int height = Math.min((int) deltaY / 3, 300);
            height = height <= getTouchSlop() / 2 ? 0 : height;
            if (height == 0) {
                removeHeaderImmediately(true);
                return;
            }
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
            return;
        }
        boolean isReadyToLoad = getFooter(LOAD_FOOTER_ID) != null;
        if (isReadyToLoad) {
            onLoading(false);
            return;
        }
        View view = getPullView(LoadingType.LOAD_START, refreshFooter);
        if (view == null) {
            return;
        }
        addFooter(LOAD_FOOTER_ID, view);
        scorllToBottom();
    }

    @Override
    public void onLoading(boolean pullDown) {
        Log.e(TAG, "onLoadMoreIng");
        if (pullDown) {
            isRefreshing = true;
            getRecyclerView().removeCallbacks(timeOutPullDown);
            getRecyclerView().postDelayed(timeOutPullDown, LOADING_TIME_OUT);
            addHeader(REFRESH_HEADER_ID, getPullView(LoadingType.LOAD_ING, refreshHeader));
            if (onRefreshData != null) {
                onRefreshData.onRefreshing();
            }
            return;
        }
        isLoading = true;
        View view = getPullView(LoadingType.LOAD_ING, refreshFooter);
        addFooter(LOAD_FOOTER_ID, view);
        getRecyclerView().removeCallbacks(timeOutPullUp);
        getRecyclerView().postDelayed(timeOutPullUp, LOADING_TIME_OUT);
        if (pullUpDataAction != null) {
            pullUpDataAction.onLoadMoreIng();
        }
    }

    @Override
    public void onLoadFail(boolean pulldown) {
        isRefreshing = false;
        getRecyclerView().removeCallbacks(pulldown ? timeOutPullDown : timeOutPullUp);
        if (pulldown) {
            addHeader(REFRESH_HEADER_ID, getPullView(LoadingType.LOAD_FAIL, refreshHeader));
            scorllToTop();
            removeHeaderImmediately(false);
            if (onRefreshData != null) {
                onRefreshData.onRefreshFail();
            }
            return;
        }
        isLoading = false;
        addFooter(LOAD_FOOTER_ID, getPullView(LoadingType.LOAD_FAIL, refreshFooter));
        scorllToBottom();
        removeFooterImmediately(false);
        if (pullUpDataAction != null) {
            pullUpDataAction.onLoadMoreFail();
        }
    }

    @Override
    public void onLoadSuccess(boolean pulldown) {
        if (pulldown) {
            isRefreshing = false;
            getRecyclerView().removeCallbacks(timeOutPullDown);
            addHeader(REFRESH_HEADER_ID, getPullView(LoadingType.LOAD_SUCCESS, refreshHeader));
            scorllToTop();
            removeHeaderImmediately(false);
            if (onRefreshData != null) {
                onRefreshData.onRefreshSuccess();
            }
            return;
        }
        isLoading = false;
        getRecyclerView().removeCallbacks(timeOutPullUp);
        addFooter(LOAD_FOOTER_ID, getPullView(LoadingType.LOAD_SUCCESS, refreshFooter));
        scorllToBottom();
        removeFooterImmediately(false);
        if (pullUpDataAction != null) {
            pullUpDataAction.onLoadMoreSuccess();
        }
    }

    @Override
    public void onLoadNone() {
        isLoading = false;
        getRecyclerView().removeCallbacks(timeOutPullUp);
        addFooter(LOAD_FOOTER_ID, getPullView(LoadingType.LOAD_NONE, refreshFooter));
        scorllToBottom();
        removeFooterImmediately(false);
        if (pullUpDataAction != null) {
            pullUpDataAction.onLoadMoreNone();
        }
    }

    private View getRefreshHeader() {
        return getHeader(REFRESH_HEADER_ID);
    }

    private View getLoadFooter() {
        return getHeader(LOAD_FOOTER_ID);
    }

    private void doRefreshing() {
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
                onLoading(true);
            }
        });
        animator.start();
    }

    private void removeHeaderImmediately(boolean immediately) {
        if (immediately) {
            removeHeader(REFRESH_HEADER_ID);
            return;
        }
        getRecyclerView().postDelayed(() -> removeHeader(REFRESH_HEADER_ID), 1500);
    }

    private void removeFooterImmediately(boolean immediately) {
        if (immediately) {
            removeFooter(LOAD_FOOTER_ID);
            return;
        }
        getRecyclerView().postDelayed(() -> removeFooter(LOAD_FOOTER_ID), 1500);
    }

    private int getTouchSlop() {
        return ViewConfiguration.get(getRecyclerView().getContext()).getScaledTouchSlop();
    }

    public void scorllToBottom() {
        RecyclerView recyclerView = getRecyclerView();
        recyclerView.scrollToPosition(recyclerView.getLayoutManager().getItemCount() - 1);
    }

    public void scorllToTop() {
        getRecyclerView().smoothScrollToPosition(0);
    }

    public void setEnablePullRefreshing(boolean enablePullRefreshing) {
        isEnablePullRefreshing = enablePullRefreshing;
    }

    public void setEnablePullLoadMore(boolean enablePullLoadMore) {
        isEnablePullLoadMore = enablePullLoadMore;
    }
}
