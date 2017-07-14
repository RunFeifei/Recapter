package com.fei.root;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;

/**
 * Created by PengFeifei on 17-7-12.
 */

public class RecapterApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }

    public static LayoutInflater getlayoutInflate() {
        return (LayoutInflater) RecapterApp.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
    }
}
