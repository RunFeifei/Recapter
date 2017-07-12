package com.fei.root.recapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by PengFeifei on 17-7-12.
 */

public abstract class CommonAdapter<Data> extends RecyclerView.Adapter<CommonHolder> {

    private List<Data> lisData;
    private int layoutId;

    private OnItemClick onItemClick;

    public CommonAdapter(List<Data> lisData, @LayoutRes int layoutId) {
        this.lisData = lisData;
        this.layoutId = layoutId;
    }

    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = RecapterApp.getlayoutInflate().inflate(layoutId, parent, false);
        return bindData(itemView);
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        holder.itemView.setOnClickListener((view) -> {
            if (onItemClick != null) {
                onItemClick.onOItemClick(position);
            }
        });
        holder.bindData(lisData.get(position));
    }

    @Override
    public int getItemCount() {
        return lisData == null ? 0 : lisData.size();
    }


    public void insertItem(int position, Data data) {
        if (lisData == null) {
            return;
        }
        lisData.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        if (lisData == null || lisData.size() <= position) {
            return;
        }
        lisData.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemClick(@NonNull OnItemClick onItemClick) {
        if (onItemClick == null) {
            throw new NullPointerException("onItemClick can not be null");
        }
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onOItemClick(int Position);
    }

    protected abstract CommonHolder<Data> bindData(View itemView);
}
