package com.fei.root.recapter;

/**
 * Created by PengFeifei on 17-7-14.
 */

public interface AdapterListeners {

    interface OnItemClick {
        void onItemClick(int Position);
    }

    interface OnItemLongClick {
        boolean onItemLongClick(int Position);
    }

    interface OnHeaderClick {
        boolean onHeaderClick(int Position);
    }

    interface OnFooterClick {
        boolean onHeaderClick(int Position);
    }
}
