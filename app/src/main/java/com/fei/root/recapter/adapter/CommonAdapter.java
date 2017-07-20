package com.fei.root.recapter.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.fei.root.recapter.action.AdapterAction;
import com.fei.root.recapter.listener.AdapterListeners;
import com.fei.root.recapter.viewholder.CommonHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by PengFeifei on 17-7-12.
 */

public abstract class CommonAdapter<Data> extends RecyclerView.Adapter<CommonHolder> implements AdapterAction<Data> {

    private List<Data> lisData;
    private int layoutId;

    private RecyclerView recyclerView;

    private AdapterListeners.OnItemClick onItemClick;
    private AdapterListeners.OnItemLongClick onItemLongClick;

    public CommonAdapter(List<Data> lisData, @LayoutRes int layoutId) {
        this.lisData = lisData;
        this.layoutId = layoutId;
    }

    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonHolder.create(parent, layoutId);
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        if (onItemClick != null) {
            holder.itemView.setOnClickListener(view -> onItemClick.onItemClick(getRecyclerView(), view, position));
        }
        if (onItemLongClick != null) {
            holder.itemView.setOnLongClickListener(view -> onItemLongClick.onItemLongClick(getRecyclerView(), view, position));
        }
        convert(holder, lisData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return lisData == null ? 0 : lisData.size();
    }

    protected abstract void convert(CommonHolder holder, Data data, int position);

    @Override
    public void appendItem(Data data) {
        if (lisData == null) {
            lisData = new ArrayList<Data>();
        }
        lisData.add(data);
        notifyDataSetChanged();
    }

    @Override
    public void appendItems(List<Data> datas) {
        if (lisData == null) {
            lisData = new ArrayList<Data>();
        }
        lisData.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void insertItem(int position, Data data) {
        if (size(lisData) <= position) {
            return;
        }
        lisData.add(position, data);
        notifyItemInserted(position);
    }

    @Override
    public void removeItem(int position) {
        if (size(lisData) <= position) {
            return;
        }
        lisData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void updateItem(int positon, Data data) {
        if (size(lisData) <= positon) {
            return;
        }
        lisData.set(positon, data);
        notifyDataSetChanged();
    }

    @Override
    public void setOnItemClick(AdapterListeners.OnItemClick onItemClick) {
        if (onItemClick == null) {
            throw new NullPointerException("onItemClick can not be null");
        }
        this.onItemClick = onItemClick;
    }

    @Override
    public void setOnItemLongClick(AdapterListeners.OnItemLongClick onItemLongClick) {
        if (onItemLongClick == null) {
            throw new NullPointerException("onItemClick can not be null");
        }
        this.onItemLongClick = onItemLongClick;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    private int size(Collection collection) {
        return collection == null ? 0 : collection.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }
}
