package com.fei.root;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fei.root.common.viewbinder.Binder;
import com.fei.root.common.viewbinder.OnClicker;
import com.fei.root.common.viewbinder.ViewBinder;
import com.fei.root.recapter.R;
import com.fei.root.recater.viewgroup.PullRefreshLayout;

public class RefreshLayoutActivity extends AppCompatActivity {

    @Binder
    private TextView test1;
    @Binder
    private TextView test2;
    @Binder
    private TextView test3;
    @Binder
    private TextView test4;
    @Binder
    private TextView test5;
    @Binder
    private PullRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_refresh_layout, null);
        setContentView(view);
        ViewBinder.bindViews(this, view);
    }

    @OnClicker(id = R.id.test1)
    public void test1(View view) {
    }

    @OnClicker(id = R.id.test2)
    public void test2(View view) {
        Toast.makeText(this, "test2", Toast.LENGTH_SHORT).show();
        refresh.stopRefreshing();
    }

    @OnClicker(id = R.id.test3)
    private void test3(View view) {
        Toast.makeText(this, "tes3", Toast.LENGTH_SHORT).show();
    }

    @OnClicker(id = R.id.test4)
    private void test4(View view) {
        Toast.makeText(this, "test4", Toast.LENGTH_SHORT).show();
    }

    @OnClicker(id = R.id.test5)
    private void test5(View view) {
        Toast.makeText(this, "test5", Toast.LENGTH_SHORT).show();
    }

}
