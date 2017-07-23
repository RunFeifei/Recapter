package com.fei.root;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fei.root.recapter.R;
import com.fei.root.recapter.action.LoadMoreAction;
import com.fei.root.recapter.adapter.RefloadAdapter;
import com.fei.root.recapter.view.DefaultRefreshFooterView;
import com.fei.root.recapter.view.SwipeRecyclerView;
import com.fei.root.recapter.viewholder.CommonHolder;
import com.fei.root.viewbinder.Binder;
import com.fei.root.viewbinder.ViewBinder;

import java.util.ArrayList;

public class SwipeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,LoadMoreAction {

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
        },3000);

    }

    @Override
    public void onLoading() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                refloadAdapter.appendItem("load");
                refloadAdapter.onLoadSuccess(false);
                refloadAdapter.scorllToBottom();
            }
        },3000);
    }

    @Override
    public void onLoadFail() {

    }

    @Override
    public void onLoadSuccess() {

    }

    @Override
    public void onLoadNone() {

    }
}
