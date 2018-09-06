package com.fei.root.recater.action;

import android.view.View;

import com.fei.root.recater.listener.AdapterListeners;

/**
 * Created by PengFeifei on 2018/8/29.
 */
public interface OnSLideAction<Data> {

    void doSlide(boolean in);

    boolean isSlidedOut();

    int getPositionInList();

    void setOnSlideClicks(Data data,int position, AdapterListeners.OnSlideClick<Data> onSlideClicks);
}
