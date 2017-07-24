package com.fei.root.recapter.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.fei.root.recapter.action.LoadMoreAction;
import com.fei.root.recapter.adapter.RefloadAdapter;
import com.fei.root.recapter.viewholder.CommonHolder;

import java.util.List;

/**
 * Created by PengFeifei on 17-7-23.
 */

public class SwipeRecyclerView<Data> extends SwipeRefreshLayout {

    private RecyclerView recyclerView;
    private RefloadAdapter<Data> refloadAdapter;

    private Convert convert;

    private LoadMoreAction<Data> loadMoreAction;

    public SwipeRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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

    public void setOnPullUpDataListener(LoadMoreAction loadMoreAction) {
        this.loadMoreAction = loadMoreAction;
        refloadAdapter.setLoadMoreDataListener(loadMoreAction);
    }

    public interface Convert<Data> {
        void convert(CommonHolder holder, Data data, int position);
    }
}
