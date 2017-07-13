package com.fei.root.recapter;

import android.view.View;

/**
 * Created by PengFeifei on 17-7-14.
 */

interface HeaterAdapterAction<Data> extends AdapterAction<Data> {

    void addHeader(int uniqueId, View header);

    void addHeader(View header);

    void addFooter(int uniqueId, View footer);

    void addFooter(View footer);

    View getHeader(int uniqueId);

    View getFooter(int uniqueId);

    void removeHeader(int uniqueId);

    void removeHeader(View view);

    void removeFooter(int uniqueId);

    void removeFooter(View view);

    void setOnHeaderClick(AdapterListeners.OnHeaderClick onHeaderClick);

    void setOnFooterClick(AdapterListeners.OnFooterClick onFooterClick);
}

    
    
    

