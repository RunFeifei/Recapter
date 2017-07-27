package com.fei.root;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fei.root.recapter.R;
import com.fei.root.recater.action.OnLoadMoreData;
import com.fei.root.recater.action.OnRefreshData;
import com.fei.root.recater.adapter.RefloadAdapter;
import com.fei.root.recater.listener.AdapterListeners;
import com.fei.root.recater.view.DefaultRefreshFooterView;
import com.fei.root.recater.view.DefaultRefreshHeaderView;
import com.fei.root.recater.view.RefloadRecyclerView;
import com.fei.root.recater.viewholder.CommonHolder;
import com.fei.root.viewbinder.Binder;
import com.fei.root.viewbinder.ViewBinder;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnLoadMoreData<String>,OnRefreshData<String> {

    @Binder
    private TextView btn;
    @Binder
    private RefloadRecyclerView recyclerView;

    private RefloadAdapter<String> commonAdapter;

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(view);
        ViewBinder.bindViews(this, view);
        init();
    }

    private void init() {
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        commonAdapter = new RefloadAdapter<String>(list, R.layout.list_item) {
            @Override
            protected void convert(CommonHolder holder, String s, int position) {
                holder.setText(R.id.btn, s);
            }
        };
        recyclerView.setAdapter(commonAdapter);
        commonAdapter.setRefreshHeader(new DefaultRefreshHeaderView(this));
        commonAdapter.setLoadMoreFooter(new DefaultRefreshFooterView(this));
        commonAdapter.setRefreshDataListener(this);
        commonAdapter.setLoadMoreDataListener(this);
        commonAdapter.setEnablePullLoadMore(true);
        commonAdapter.setEnablePullRefreshing(true);
    }

    @Override
    public void onLoadMoreIng() {
        Toast.makeText(this, "onLoadMoreIng", Toast.LENGTH_SHORT).show();
        int result = new Random(100).nextInt();
        recyclerView.postDelayed(() -> {
            if (result % 2 == 0) {
                commonAdapter.appendItem("load");
                commonAdapter.onLoadSuccess(false);
            } else if (result % 3 == 0) {
                commonAdapter.onLoadNone();
            } else {
                commonAdapter.onLoadFail(false);
            }
        }, 6000);
    }

    @Override
    public void onLoadMoreFail() {
        Toast.makeText(this, "onLoadMoreFail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMoreSuccess() {
        Toast.makeText(this, "onLoadMoreSuccess", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMoreNone() {

    }

    public void onClick(View view) {
        Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
    }

    public void onClick1(View view) {

    }

    @Override
    public void onRefreshing() {
        int result = new Random(100).nextInt();
        recyclerView.postDelayed(() -> {
            if (result % 2 == 0) {
                commonAdapter.appendItem("refresh");
                commonAdapter.onLoadSuccess(true);
            } else {
                commonAdapter.onLoadFail(true);
            }
        }, 6000);
    }

    @Override
    public void onRefreshFail() {

    }

    @Override
    public void onRefreshSuccess() {

    }
}
