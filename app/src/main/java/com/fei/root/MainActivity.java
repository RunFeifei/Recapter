package com.fei.root;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fei.root.recapter.R;
import com.fei.root.recapter.action.RefloadDataAction;
import com.fei.root.recapter.adapter.RefloadAdapter;
import com.fei.root.recapter.listener.AdapterListeners;
import com.fei.root.recapter.view.DefaultRefreshHeaderView;
import com.fei.root.recapter.view.RefloadRecyclerView;
import com.fei.root.recapter.viewholder.CommonHolder;
import com.fei.root.viewbinder.Binder;
import com.fei.root.viewbinder.ViewBinder;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements RefloadDataAction<String> {

    @Binder
    private TextView btn;
    @Binder
    private RefloadRecyclerView recyclerView;

    private RefloadAdapter<String> commonAdapter;

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
        list.add("111");
        list.add("222");
        list.add("333");
        list.add("444");
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        commonAdapter = new RefloadAdapter<String>(list, R.layout.list_item) {
            @Override
            protected void convert(CommonHolder holder, String s, int position) {
                holder.setText(R.id.btn, s);
            }
        };
        recyclerView.setAdapter(commonAdapter);
        commonAdapter.setOnHeaderClick(new AdapterListeners.OnHeaderClick() {
            @Override
            public void onHeaderClick(RecyclerView recyclerView, View header, int Position) {
            }
        });
        commonAdapter.setRefreshHeader(new DefaultRefreshHeaderView(this));
        commonAdapter.setRefloadDataListener(this);
    }

    @Override
    public void onLoading() {
        Toast.makeText(this, "onLoading", Toast.LENGTH_SHORT).show();
        Boolean result = new Random().nextBoolean();
        recyclerView.postDelayed(() -> {
            if (result) {
                commonAdapter.appendItem("good");
                commonAdapter.onLoadSuccess();
            } else {
                commonAdapter.onLoadFail();
            }
        }, 3000);

    }

    @Override
    public void onLoadFail() {
        Toast.makeText(this, "onLoadFail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadSuccess() {
        Toast.makeText(this, "onLoadSuccess", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {


    }

    public void onClick1(View view) {

    }


}
