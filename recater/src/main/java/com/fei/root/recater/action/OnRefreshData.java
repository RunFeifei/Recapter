package com.fei.root.recater.action;

/**
 * Created by PengFeifei on 17-7-17.
 */

public interface OnRefreshData<Data> {

    void onRefreshing();

    void onRefreshFail();

    void onRefreshSuccess();

}
