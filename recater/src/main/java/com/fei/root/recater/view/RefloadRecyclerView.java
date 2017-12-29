package com.fei.root.recater.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Scroller;

import static com.fei.root.recater.adapter.RefloadAdapter.LOAD_FOOTER_ID;
import static com.fei.root.recater.adapter.RefloadAdapter.REFRESH_HEADER_ID;


/**
 * Created by PengFeifei on 17-7-17.
 */

public class RefloadRecyclerView extends RecyclerView {

    private Scroller scroller;

    private int flingsRadio=1;

    public RefloadRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public RefloadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setRecycledViewPool(new RecycledViewPools());
        scroller = new Scroller(context, new AccelerateDecelerateInterpolator());
    }

    /**
     * RecycledViewPool其实是不支持自定义的
     * 即使把RecycledViewPool的源码copy一份也不支持,因为ViewHolder有一个resetInternal()方法是包可见的...
     * 因此getRecycledView()方法,写的不友好
     */
    public class RecycledViewPools extends RecycledViewPool {

        /**
         * 在添加Header时候如果已经存在相同uniqueId的holder RecyclerView会拉取缓存导致添加的Header是之前的header
         * 故而在此强制禁止缓存
         */
        @Override
        public ViewHolder getRecycledView(int viewType) {
            if (viewType == REFRESH_HEADER_ID || viewType == LOAD_FOOTER_ID) {
                return null;
            }
            return super.getRecycledView(viewType);
        }
    }

    public void scrollByY(int y) {
        super.scrollBy(0, y);
    }

    public void scrollByY(int y, OnScrollByEnded onScrollByEnded) {
        this.scrollByY(y);
        if (onScrollByEnded == null) {
            return;
        }
        onScrollByEnded.onScrollByEnded();
    }


    public interface OnScrollByEnded {
        void onScrollByEnded();
    }


    private int sum = 0;
    private float deltaY = 0;
    private OnScrollByEnded onScrollByEnded;

    @Override
    public void computeScroll() {
        if (onScrollByEnded != null && sum > deltaY) {
            sum = 0;
            deltaY = 0;
            onScrollByEnded.onScrollByEnded();
            onScrollByEnded = null;
            if (!scroller.isFinished()) {
                scroller.abortAnimation();
            }
            return;
        }
        if (scroller.computeScrollOffset()) {
            int move = scroller.getCurrY();
            scrollBy(0, move);
            sum = sum + move;
            invalidate();
        }
    }


    public void smoothRemoveHeader(float deltaY, OnScrollByEnded onScrollByEnded) {
        this.deltaY = deltaY;
        this.onScrollByEnded = onScrollByEnded;
        if (!scroller.isFinished()) {
            scroller.abortAnimation();
        }
        scroller.startScroll(0, getScrollY(), 0, (int) deltaY, 3000);
        invalidate();
    }

    public void setflingRadio(int radio){
        this.flingsRadio = radio;
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        velocityY *= flingsRadio;
        return super.fling(velocityX, velocityY);
    }

    public int getFlingsRadio() {
        return flingsRadio;
    }

}
