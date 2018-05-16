package com.fei.root.recater.view;

import android.content.Context;
import android.view.View;

import com.fei.root.recater.action.RefloadViewAction;


/**
 * Created by PengFeifei on 17-7-17.
 */

public class DefaultRefreshHeaderView implements RefloadViewAction {

    private Context context;

    public DefaultRefreshHeaderView(Context context) {
        this.context = context;
    }

    @Override
    public View onLoadStart() {
        return new DefaultLoadStartView(context);
    }

    @Override
    public View onLoading() {
        return new DefaultLoadIngView(context);
    }

    @Override
    public View onLoadFail() {
        return new DefaultLoadFailView(context);
    }

    @Override
    public View onLoadSuccess() {
        return new DefaultLoadSuccessView(context);
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public View onLoadNone() {
        return null;
    }

    @Override
    public void onMove(float move) {

    }

    @Override
    public void onUp() {

    }
}
