package com.fei.root.recapter.action;

/**
 * Created by PengFeifei on 17-7-17.
 */

public interface RefloadAdapterAction {

    void onLoadStart(float deltaY);

    void onLoading();

    void onLoadFail();

    void onLoadSuccess();

    // TODO: 17-7-20
    //void onLoadSuccess(List<Data>);
}
