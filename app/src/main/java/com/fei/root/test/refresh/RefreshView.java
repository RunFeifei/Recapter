package com.fei.root.test.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fei.root.recapter.R;
import com.fei.root.recater.action.RefloadViewAction;


/**
 * Created by PengFeifei on 2018/5/14.
 */
public class RefreshView implements RefloadViewAction {

    private Context context;
    private DefaltLoadingView defaltLoadingView;
    private DefaltLoadStartView defaltStartView;

    public RefreshView(Context context) {
        this.context = context;
    }

    @Override
    public View onLoadStart() {
        defaltStartView = new DefaltLoadStartView(context, true);
        return defaltStartView;
    }

    @Override
    public View onLoading() {
        defaltLoadingView = new DefaltLoadingView(context, false);
        return defaltLoadingView;
    }

    @Override
    public View onLoadFail() {
        return getTextView("刷新失败", true);
    }

    @Override
    public View onLoadSuccess() {
//        return getTextView("刷新成功", true);
        return null;
    }

    @Override
    public View onLoadNone() {
        return getTextView("暂无数据", true);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onMove(float move) {
        if (defaltStartView != null) {
            defaltStartView.onMove(move);
        }
    }

    @Override
    public void onUp() {
        if (defaltStartView != null) {
            defaltStartView.onUp();
        }
    }

    private View getTextView(CharSequence content, boolean tips) {
        View view = LayoutInflater.from(context).inflate(R.layout.header, null);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(content);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, tips ? 150 : 300));
        return view;
    }


}
