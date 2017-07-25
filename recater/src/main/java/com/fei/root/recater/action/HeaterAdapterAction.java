package com.fei.root.recater.action;

import android.view.View;

import com.fei.root.recater.listener.AdapterListeners;


/**
 * Created by PengFeifei on 17-7-14.
 */

public interface HeaterAdapterAction<Data> extends AdapterAction<Data> {


    int addHeader(View header);

    int addFooter(View footer);

    View getHeader(int uniqueId);

    View getFooter(int uniqueId);

    void removeHeader(View view);

    void removeFooter(View view);

    void setOnHeaderClick(AdapterListeners.OnHeaderClick onHeaderClick);

    void setOnFooterClick(AdapterListeners.OnFooterClick onFooterClick);
}

    
    
    

