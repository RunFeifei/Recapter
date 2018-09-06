package com.fei.root;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.fei.root.recapter.R;
import com.fei.root.recater.adapter.SlideAdapter;
import com.fei.root.recater.decoration.DividerDecoration;
import com.fei.root.recater.listener.AdapterListeners;
import com.fei.root.recater.viewholder.CommonHolder;

import java.util.ArrayList;

public class SlideTestActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SlideAdapter<String> commonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_test);
        recyclerView = (RecyclerView) findViewById(R.id.listView);
        init();
    }

    private void init() {
        int i = 0;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerDecoration(ContextCompat.getColor(this, R.color.colorAccent)));

        commonAdapter = new SlideAdapter<String>(list, R.layout.list_item_slide) {
            @Override
            protected void convert(CommonHolder holder, String s, int position) {
                holder.setText(R.id.btn, s);
            }
        };
        recyclerView.setAdapter(commonAdapter);
        commonAdapter.setOnItemClick(new AdapterListeners.OnItemClick() {
            @Override
            public void onItemClick(Object o, View header, int Position) {
                header.toString();
            }
        });
        commonAdapter.setOnSlideClicks(new AdapterListeners.OnSlideClick<String>() {
            @Override
            public void onSlideViewClick(String s, View itemView, int itemPosition, View[] slideViews, int slidePosition) {

            }
        });
    }

}
