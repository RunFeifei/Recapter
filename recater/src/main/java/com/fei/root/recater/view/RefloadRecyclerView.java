package com.fei.root.recater.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import static com.fei.root.recater.adapter.RefloadAdapter.LOAD_FOOTER_ID;
import static com.fei.root.recater.adapter.RefloadAdapter.REFRESH_HEADER_ID;


/**
 * Created by PengFeifei on 17-7-17.
 */

public class RefloadRecyclerView extends RecyclerView {

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
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
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


}
