package com.fei.root.recater.action;

import com.fei.root.recater.listener.AdapterListeners;

/**
 * Created by PengFeifei on 2018/8/29.
 */
public interface OnSLideAction {

    void doSlide(boolean in);

    boolean isSlidedOut();

    int getPositionInList();

    void setOnSlideClicks(AdapterListeners.OnSlideClick onSlideClicks);
}
