package com.fei.root.recapter.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by PengFeifei on 17-7-14.
 * 下拉刷新,上拉加载更多
 */

public abstract class RefloadAdapter<Data> extends HeaterAdapter<Data> {

    public RefloadAdapter(@NonNull List<Data> lisData, @LayoutRes int layoutId) {
        super(lisData, layoutId);
    }
}
