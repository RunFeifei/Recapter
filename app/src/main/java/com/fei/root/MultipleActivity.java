package com.fei.root;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fei.root.multi.Cat;
import com.fei.root.multi.Dog;
import com.fei.root.multi.Person;
import com.fei.root.recapter.R;
import com.fei.root.recater.action.OnLoadMoreData;
import com.fei.root.recater.action.OnRefreshData;
import com.fei.root.recater.adapter.multi.ItemModule;
import com.fei.root.recater.adapter.multi.ItemWrapper;
import com.fei.root.recater.adapter.multi.MultiAdapter;
import com.fei.root.recater.view.DefaultRefreshFooterView;
import com.fei.root.recater.view.DefaultRefreshHeaderView;
import com.fei.root.recater.view.RefloadRecyclerView;
import com.fei.root.recater.viewholder.CommonHolder;
import com.fei.root.viewbinder.Binder;
import com.fei.root.viewbinder.ViewBinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MultipleActivity extends AppCompatActivity implements OnLoadMoreData<ItemModule>, OnRefreshData<ItemModule> {

    @Binder
    private RefloadRecyclerView recyclerView;

    private MultiAdapter<ItemModule> multiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_multiple, null);
        setContentView(view);
        ViewBinder.bindViews(this, view);
        init();
    }

    private void init() {
        List<ItemModule> list = new ArrayList<>();
        list.add(new Dog());
        list.add(new Cat());
        list.add(new Person());

        list.add(new ItemWrapper<String>("String", R.layout.item_01));
        list.add(new ItemWrapper<Integer>(123, R.layout.item_01));
        list.add(new ItemWrapper<Boolean>(true, R.layout.item_02));

        Collections.shuffle(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        multiAdapter = new MultiAdapter<ItemModule>(list) {

            @Override
            protected boolean convert(CommonHolder holder, String data, int position) {
                holder.setText(R.id.text, data + "");
                return true;
            }

            @Override

            protected boolean convert(CommonHolder holder, Integer data, int position) {
                holder.setText(R.id.text, data + "");
                return true;
            }

            @Override
            protected boolean convert(CommonHolder holder, Boolean data, int position) {
                holder.setText(R.id.text, data + "");
                return true;
            }

            @Override
            protected void convert(CommonHolder holder, ItemModule itemModule, int position) {
                if (itemModule instanceof Dog) {
                    holder.setText(R.id.text, ((Dog) itemModule).name);
                }
                if (itemModule instanceof Cat) {
                    holder.setText(R.id.text, ((Cat) itemModule).name);
                }
                if (itemModule instanceof Person) {
                    holder.setText(R.id.text, ((Person) itemModule).name);
                }
            }
        };
        recyclerView.setAdapter(multiAdapter);
        multiAdapter.setEnablePullLoadMore(true);
        multiAdapter.setEnablePullRefreshing(true);
        multiAdapter.setRefreshHeader(new DefaultRefreshHeaderView(this));
        multiAdapter.setLoadMoreFooter(new DefaultRefreshFooterView(this));
        multiAdapter.setOnItemClick((s, v, p) -> Log.e("TAG-->", "data->" + s + "--adapterPosition-->" + p));
        multiAdapter.setRefreshDataListener(this);
        multiAdapter.setLoadMoreDataListener(this);
    }


    @Override
    public void onLoadMoreIng() {
        Toast.makeText(this, "onLoadMoreIng", Toast.LENGTH_SHORT).show();
        int result = new Random(100).nextInt();
        recyclerView.postDelayed(() -> {
           /* if (result % 2 == 0) {
                multiAdapter.appendItem(new ItemWrapper<String>("onLoadMoreIng", R.layout.item_00));
                multiAdapter.onLoadSuccess(false);
            } else if (result % 3 == 0) {
                multiAdapter.onLoadNone();
            } else {
                multiAdapter.onLoadFail(false);
            }*/
            multiAdapter.appendItem(new ItemWrapper<Integer>(1111, R.layout.item_00));
            multiAdapter.onLoadSuccess(false);
        }, 3000);
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

    @Override
    public void onRefreshing() {
        int result = new Random(100).nextInt();
        recyclerView.postDelayed(() -> {
            multiAdapter.appendItem(new ItemWrapper<String>("onRefreshing", R.layout.item_01));
            multiAdapter.onLoadSuccess(true);
        }, 3000);
    }

    @Override
    public void onRefreshFail() {

    }

    @Override
    public void onRefreshSuccess() {

    }

}
