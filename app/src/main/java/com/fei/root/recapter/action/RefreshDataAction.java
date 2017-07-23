package com.fei.root.recapter.action;

/**
 * Created by PengFeifei on 17-7-17.
 */

public interface RefreshDataAction<Data> {

    void onRefreshing();

    void onRefreshFail();

    void onRefreshSuccess();

}
