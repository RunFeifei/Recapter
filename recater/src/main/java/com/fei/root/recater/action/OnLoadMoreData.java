package com.fei.root.recater.action;

/**
 * Created by PengFeifei on 17-7-17.
 */

public interface OnLoadMoreData<Data> {

    void onLoadMoreIng();

    void onLoadMoreFail();

    void onLoadMoreSuccess();

    void onLoadMoreNone();
}
