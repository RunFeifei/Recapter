package com.fei.root.recater.action;

/**
 * Created by PengFeifei on 17-7-17.
 */

public interface RefloadAdapterAction {

    void onLoadStart(float deltaY, boolean pulldown);

    void onLoading(boolean pulldown);

    void onLoadFail(boolean pulldown);

    void onLoadSuccess(boolean pulldown);

    void onLoadNone();

    // TODO: 17-7-20
    //void onLoadMoreSuccess(List<Data>);
}
