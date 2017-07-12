package com.fei.root.recapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by PengFeifei on 17-7-12.
 */

public abstract class CommonHolder<Data> extends RecyclerView.ViewHolder {

    public CommonHolder(View itemView) {
        super(itemView);
        ViewBinder.bindViews(this, itemView);
    }

    protected abstract void bindData(Data data);
}
