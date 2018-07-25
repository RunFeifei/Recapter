package com.fei.root.recater.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.fei.root.recater.LoadingType;
import com.fei.root.recater.action.OnLoadMoreData;
import com.fei.root.recater.action.OnRefreshData;
import com.fei.root.recater.action.RefloadAdapterAction;
import com.fei.root.recater.action.RefloadViewAction;
import com.fei.root.recater.view.RefloadRecyclerView;
import com.fei.root.recater.viewholder.CommonHolder;

import java.util.List;

/**
 * Created by PengFeifei on 17-7-14.
 * 下拉刷新,上拉加载更多
 * 需要搭配RefloadRecyclerView使用
 */

public abstract class RefloadAdapter<Data> extends HeaterAdapter<Data> implements View.OnTouchListener, RefloadAdapterAction {

    public static final int REFRESH_HEADER_ID = Integer.MIN_VALUE;
    public static final int LOAD_FOOTER_ID = Integer.MAX_VALUE;

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

    private static final int LOADING_TIME_OUT = 10 * 1000;
    private Runnable timeOutPullDown = () -> onLoadFail(true);
    private Runnable timeOutPullUp = () -> onLoadFail(false);

    private VelocityTracker velocityTracker = VelocityTracker.obtain();

    public RefloadAdapter(@NonNull List<Data> lisData, @LayoutRes int layoutId) {
        super(lisData, layoutId);
    }

    public RefloadAdapter(@LayoutRes int layoutId) {
        super(layoutId);
    }

    /**
     * Attention!!
     * just add for MultiAdapter
     */
    protected RefloadAdapter(List<Data> listData) {
        super(listData);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (!(recyclerView instanceof RefloadRecyclerView)) {
            throw new IllegalStateException("adapter only works with efloadRecyclerView.java");
        }
        recyclerView.setOnTouchListener(this);
        recyclerView.setClickable(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                isBeyondScreen = getRecyclerView().getLayoutManager().getChildCount() < getItemCount() - getFootersSize();
            }
        });
    }

    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headers != null && headers.get(viewType, null) != null) {
            return CommonHolder.create(parent.getContext(), headers.get(viewType));
        }
        if (footers != null && footers.get(viewType, null) != null) {
            return CommonHolder.create(parent.getContext(), footers.get(viewType));
        }
        View itemView = getlayoutInflate(parent.getContext()).inflate(layoutId, parent, false);
        itemView.setClickable(true);
        itemView.setOnTouchListener(this);
        return CommonHolder.create(parent.getContext(), itemView);
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

    public void setLoadMoreDataListener(OnLoadMoreData<Data> onLoadMoreData) {
        this.pullUpDataAction = onLoadMoreData;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (getRefreshHeader() != null && isRefreshing) {
            return true;
        }
        if (getRefreshHeader() == null) {
            getRecyclerView().setflingRadio(1);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                resetVelocityTracker(event);
                touchDownY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                getVelocityTracker().addMovement(event);
                getVelocityTracker().computeCurrentVelocity(1000);
                refreshHeader.onMove(event.getRawY() - touchDownY);
                float move = (event.getRawY() - touchDownY) / 3;
                if (cantScollDown() && getRefreshHeader() == null
                        && (move > getTouchSlop() || (getVelocityTracker().getYVelocity() > getScaledMinimumFlingVelocity() && move > 0))) {
                    addStartRefreshHeader();
                    getRecyclerView().setflingRadio(0);
                    return true;
                }
                if (isEnablePullLoadMore && !isLoading
                        && cantScollUp()
                        && (move < -getTouchSlop() || (getVelocityTracker().getYVelocity() > getScaledMinimumFlingVelocity() && move < 0)) && isBeyondScreen) {
                    addLoadingFooter();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
                if (isEnablePullRefreshing && !isRefreshing) {
                    onActionUp();
                }
                releaseVelocityTracker();
                break;
        }
        return false;
    }

    private void addStartRefreshHeader() {
        View view = getPullView(LoadingType.LOAD_START, refreshHeader);
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (getRefreshHeader() == null) {
            addHeader(REFRESH_HEADER_ID, view);
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(layoutParams);
            }
            getRecyclerView().scrollBy(0, layoutParams.height);
        }
    }

    private void addRefreshingHeader() {
        View view = getPullView(LoadingType.LOAD_ING, refreshHeader);
        if (view == null) {
            return;
        }
        addHeader(REFRESH_HEADER_ID, view);
        isRefreshing = true;
        if (onRefreshData != null) {
            onRefreshData.onRefreshing();
        }
        getRecyclerView().scrollBy(0, -1000);
        getRecyclerView().removeCallbacks(timeOutPullDown);
        getRecyclerView().postDelayed(timeOutPullDown, 5000);
    }

    private void addLoadingFooter() {
        View view = getPullView(LoadingType.LOAD_ING, refreshFooter);
        if (view == null) {
            return;
        }
        addFooter(LOAD_FOOTER_ID, view);
        isLoading = true;
        scorllToBottom();
        getRecyclerView().removeCallbacks(timeOutPullUp);
        getRecyclerView().postDelayed(timeOutPullUp, LOADING_TIME_OUT);
        if (pullUpDataAction != null) {
            pullUpDataAction.onLoadMoreIng();
        }
    }

    @Override
    public void onLoadStart(boolean pulldown) {
        if (pulldown) {
            return;
        }
        boolean isReadyToLoad = getFooter(LOAD_FOOTER_ID) != null;
        if (isReadyToLoad && !isLoading) {
            getRecyclerView().postDelayed(() -> onLoading(), 500);
            isLoading = true;
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
    public void onLoading() {
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
        getRecyclerView().removeCallbacks(pulldown ? timeOutPullDown : timeOutPullUp);
        if (pulldown) {
            addHeader(REFRESH_HEADER_ID, getPullView(LoadingType.LOAD_FAIL, refreshHeader));
            removeHeaderImmediately(false);
            if (onRefreshData != null) {
                onRefreshData.onRefreshFail();
            }
            isRefreshing = false;
            scorllToTop();
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
            View view = getPullView(LoadingType.LOAD_SUCCESS, refreshHeader);
            if (view == null) {
                if (onRefreshData != null) {
                    onRefreshData.onRefreshSuccess();
                }
                removeHeaderImmediately(true);
                return;
            }
            addHeader(REFRESH_HEADER_ID, view);
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
        removeFooterImmediately(true);
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

    private void onActionUp() {
        final View view = getRefreshHeader();
        if (view == null) {
            return;
        }
        refreshHeader.onUp();
        int stratHeaderHeight = getHeaderHeight(view);
        View view01 = getRecyclerView().getChildAt(1);
        float offset = stratHeaderHeight - Math.abs(view.getY());
        final View viewIng = getPullView(LoadingType.LOAD_ING, refreshHeader);
        if (viewIng == null) {
            return;
        }
        final boolean needRefresh = view01 != null && view01.getY() >= getHeaderHeight(viewIng);
        offset = offset - (needRefresh ? getHeaderHeight(viewIng) : 0);

        getRecyclerView().smoothRemoveHeader(offset, new RefloadRecyclerView.OnScrollByEnded() {
            @Override
            public void onScrollByEnded() {
                if (needRefresh) {
                    addRefreshingHeader();
                    return;
                }
                removeHeaderImmediately(true);
            }
        });
    }

    private void removeHeaderImmediately(boolean immediately) {
        try {
            getRecyclerView().smoothRemoveHeader(getRefreshHeader().getLayoutParams().height, new RefloadRecyclerView.OnScrollByEnded() {
                @Override
                public void onScrollByEnded() {
                    removeHeader(REFRESH_HEADER_ID);
                }
            });
        } catch (Exception e) {
            if (immediately) {
                removeHeader(REFRESH_HEADER_ID);
                return;
            }
            getRecyclerView().postDelayed(() -> removeHeader(REFRESH_HEADER_ID), 300);
        }
    }

    private void removeFooterImmediately(boolean immediately) {
        if (immediately) {
            removeFooter(LOAD_FOOTER_ID);
            return;
        }
        getRecyclerView().postDelayed(() -> removeFooter(LOAD_FOOTER_ID), 300);
    }

    private int getTouchSlop() {
        return ViewConfiguration.get(getRecyclerView().getContext()).getScaledTouchSlop();
    }

    private int getScaledMinimumFlingVelocity() {
        return ViewConfiguration.get(getRecyclerView().getContext()).getScaledMinimumFlingVelocity();
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

    protected LayoutInflater getlayoutInflate(Context context) {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    private void releaseVelocityTracker() {
        if (null != velocityTracker) {
            velocityTracker.clear();
        }
    }

    private VelocityTracker getVelocityTracker() {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        return velocityTracker;
    }

    private void resetVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
        velocityTracker.addMovement(event);
    }

    private boolean cantScollDown() {
        return !getRecyclerView().canScrollVertically(-1);
    }

    private boolean cantScollUp() {
        return !getRecyclerView().canScrollVertically(1);
    }

    private int getHeaderHeight(View view) {
        if (view == null) {
            return 0;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        return params.height;
    }
}
