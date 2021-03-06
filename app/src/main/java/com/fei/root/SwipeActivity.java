package com.fei.root;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fei.root.common.viewbinder.Binder;
import com.fei.root.common.viewbinder.ViewBinder;
import com.fei.root.recapter.R;
import com.fei.root.recater.action.OnLoadMoreData;
import com.fei.root.recater.adapter.RefloadAdapter;
import com.fei.root.recater.view.DefaultRefreshFooterView;
import com.fei.root.recater.view.SwipeRecyclerView;
import com.fei.root.recater.viewholder.CommonHolder;

import java.util.ArrayList;

public class SwipeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreData {

    @Binder
    private SwipeRecyclerView<String> swipe;

    private RefloadAdapter<String> refloadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_swipe, null);
        setContentView(view);
        ViewBinder.bindViews(this, view);
        init();
    }

    private void init() {
        int i = 1;
        ArrayList<String> list = new ArrayList<>();
        list.add(i++ + "");
        list.add(i++ + "");
        list.add(i++ + "");
        list.add(i++ + "");
        list.add(i++ + "");
        list.add(i++ + "");
        list.add(i++ + "");
        list.add(i++ + "");
        list.add(i++ + "");
        list.add(i++ + "");
        list.add(i++ + "");
        list.add(i++ + "");
        swipe.initDatas(list, R.layout.list_item);
        swipe.setConvert(new SwipeRecyclerView.Convert<String>() {
            @Override
            public void convert(CommonHolder holder, String s, int position) {
                holder.setText(R.id.btn, s);
            }
        });
        refloadAdapter = swipe.getAdapter();
        swipe.setOnRefreshListener(this);

        swipe.setEnablePullLoadMore(true);
        refloadAdapter.setLoadMoreFooter(new DefaultRefreshFooterView(this));
        refloadAdapter.setLoadMoreDataListener(this);
    }

    @Override
    public void onRefresh() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                refloadAdapter.appendItem("refresh");
                swipe.setRefreshing(false);
                refloadAdapter.scorllToBottom();
            }
        }, 3000);

    }

    @Override
    public void onLoadMoreIng() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                refloadAdapter.appendItem("load");
                refloadAdapter.onLoadSuccess(false);
                refloadAdapter.scorllToBottom();
            }
        }, 3000);
    }

    @Override
    public void onLoadMoreFail() {

    }

    @Override
    public void onLoadMoreSuccess() {

    }

    @Override
    public void onLoadMoreNone() {

    }
}
