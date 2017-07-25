package com.fei.root.recater.action;

import android.view.View;

/**
 * Created by PengFeifei on 17-7-17.
 */

public interface RefloadViewAction {

    View onLoadStart();

    View onLoading();

    View onLoadFail();

    View onLoadSuccess();

    View onLoadNone();

    void onClick(View view);
}
