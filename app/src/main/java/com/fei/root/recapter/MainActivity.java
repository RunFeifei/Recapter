package com.fei.root.recapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Binder
    private TextView btn;
    @Binder
    private RecyclerView recyclerView;
    @Binder
    private LinearLayout lay;

    private HeaterAdapter<String> commonAdapter;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        commonAdapter = new HeaterAdapter<String>(list, R.layout.list_item) {
            @Override
            protected void convert(CommonHolder holder, String s, int position) {
                holder.setText(R.id.btn, s);
            }
        };
        recyclerView.setAdapter(commonAdapter);
        t1 = new TextView(this);
        t1.setText("Header " + i++);
    }

    TextView t1;
    int i = 10;

    public void onClick(View view) {
        t1.setText("Header " + i++);
        i=commonAdapter.addHeader(t1);
    }

    public void onClick1(View view) {
        ((TextView)commonAdapter.getHeader(i)).setText("-----------");
    }

}
