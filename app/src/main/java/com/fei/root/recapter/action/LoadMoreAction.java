package com.fei.root.recapter.action;

/**
 * Created by PengFeifei on 17-7-17.
 */

public interface LoadMoreAction<Data> {

    void onLoading();

    void onLoadFail();

    void onLoadSuccess();

    void onLoadNone();
}
