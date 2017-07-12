package com.fei.root.recapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Binder
    private TextView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=getLayoutInflater().inflate(R.layout.activity_main,null);
        setContentView(view);
        ViewBinder.bindViews(this,view);
    }

    public void onClick(View view) {
        btn.setText("swsws");
        btn.append("----xsxs");
    }

}
