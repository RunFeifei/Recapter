package com.fei.root.recater.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


import com.fei.root.recater.action.OnLoadMoreData;
import com.fei.root.recater.adapter.RefloadAdapter;
import com.fei.root.recater.viewholder.CommonHolder;

import java.util.List;

/**
 * Created by PengFeifei on 17-7-23.
 */

public class SwipeRecyclerView<Data> extends SwipeRefreshLayout {

    private RecyclerView recyclerView;
    private RefloadAdapter<Data> refloadAdapter;

    private Convert convert;

    private OnLoadMoreData<Data> onLoadMoreData;

    public SwipeRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);


        recyclerView = new RefloadRecyclerView(context);
        recyclerView.setLayoutParams(layoutParams);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        addView(recyclerView);
        initRecyclerView();
    }

    public void initDatas(@NonNull List<Data> lisData, @LayoutRes int layoutId) {
        refloadAdapter = new RefloadAdapter<Data>(lisData, layoutId) {
            @Override
            protected void convert(CommonHolder holder, Data data, int position) {
                if (convert != null) {
                    convert.convert(holder, data, position);
                }
            }
        };
        recyclerView.setAdapter(refloadAdapter);
    }

    private void initRecyclerView() {
    }

    public RecyclerView getRecyclerView() {
        RecyclerView recyclerView = refloadAdapter.getRecyclerView();
        return recyclerView == null ? this.recyclerView : recyclerView;
    }

    public RefloadAdapter<Data> getAdapter() {
        return refloadAdapter;
    }

    public void setConvert(Convert<Data> convert) {
        this.convert = convert;
    }

    public void setEnablePullLoadMore(boolean enablePullLoadMore) {
        refloadAdapter.setEnablePullLoadMore(enablePullLoadMore);
    }

    public void setOnPullUpDataListener(OnLoadMoreData onLoadMoreData) {
        this.onLoadMoreData = onLoadMoreData;
        refloadAdapter.setLoadMoreDataListener(onLoadMoreData);
    }

    public interface Convert<Data> {
        void convert(CommonHolder holder, Data data, int position);
    }
}
