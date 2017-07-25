package com.fei.root;

import android.view.LayoutInflater;

import com.feifei.common.MultiApplication;

/**
 * Created by PengFeifei on 17-7-12.
 */

public class RecapterApp extends MultiApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
    }

    public static LayoutInflater getlayoutInflate() {
        return (LayoutInflater) RecapterApp.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
    }
}
