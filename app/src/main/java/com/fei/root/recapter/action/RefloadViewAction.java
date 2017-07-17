package com.fei.root.recapter.action;

import android.view.View;

/**
 * Created by PengFeifei on 17-7-17.
 */

public interface RefloadViewAction {

    View onLoadStart();

    View onLoading();

    View onLoadFail();

    View onLoadSuccess();

    void onClick(View view);
}
