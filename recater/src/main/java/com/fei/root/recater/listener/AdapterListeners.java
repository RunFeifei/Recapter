package com.fei.root.recater.listener;

import android.view.View;

/**
 * Created by PengFeifei on 17-7-14.
 */

public interface AdapterListeners {

    interface OnItemClick<Data> {
        void onItemClick(Data data, View itemView, int Position);
    }

    interface OnItemLongClick<Data> {
        boolean onItemLongClick(Data data, View itemView, int Position);
    }

    interface OnHeaderClick {
        void onHeaderClick(View header, int Position);
    }

    interface OnFooterClick {
        void onHeaderClick(View footer, int Position);
    }

    interface OnSlideClick<Data> {
        void onSlideViewClick(Data data,View itemView,View[] slideiews, int position);
    }
}
