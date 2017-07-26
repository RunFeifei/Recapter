package com.fei.root.recater.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fei.root.recater.action.RefloadViewAction;


/**
 * Created by PengFeifei on 17-7-17.
 */

public class DefaultRefreshFooterView implements RefloadViewAction {

    private Context context;

    public DefaultRefreshFooterView(Context context) {
        this.context = context;
    }

    @Override
    public View onLoadStart() {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(15);
        textView.setText("上拉加载更多");
        textView.setPadding(0, 30, 0, 30);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    @Override
    public View onLoading() {
        return new DefaultLoadIngView(context).setText("正在加载更多");
    }

    @Override
    public View onLoadFail() {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(15);
        textView.setText("加载失败");
        textView.setPadding(0, 30, 0, 30);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    @Override
    public View onLoadSuccess() {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(15);
        textView.setText("加载成功");
        textView.setPadding(0, 30, 0, 30);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    @Override
    public View onLoadNone() {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(15);
        textView.setText("没有更多了");
        textView.setPadding(0, 30, 0, 30);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    @Override
    public void onClick(View view) {
    }

}
