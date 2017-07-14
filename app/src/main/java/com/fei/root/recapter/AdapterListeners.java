package com.fei.root.recapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by PengFeifei on 17-7-14.
 */

public interface AdapterListeners {

    interface OnItemClick {
        void onItemClick(RecyclerView recyclerView, View header,int Position);
    }

    interface OnItemLongClick {
        boolean onItemLongClick(RecyclerView recyclerView, View header,int Position);
    }

    interface OnHeaderClick {
        void onHeaderClick(RecyclerView recyclerView, View header, int Position);
    }

    interface OnFooterClick {
        void onHeaderClick(RecyclerView recyclerView, View footer,int Position);
    }
}
