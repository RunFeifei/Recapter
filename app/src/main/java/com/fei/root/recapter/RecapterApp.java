package com.fei.root.recapter;

import android.app.Application;
import android.content.Context;

/**
 * Created by PengFeifei on 17-7-12.
 */

public class RecapterApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }

    public static Context getContext() {
        return context;
    }
}
