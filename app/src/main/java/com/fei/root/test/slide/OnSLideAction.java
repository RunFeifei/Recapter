package com.fei.root.test.slide;

/**
 * Created by PengFeifei on 2018/8/29.
 */
public interface OnSLideAction {

    void doSlide(boolean in);

    boolean isSlidedOut();

    int getPositionInList();
}
