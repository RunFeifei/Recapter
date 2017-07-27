package com.fei.root.recater.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.fei.root.recater.action.AdapterAction;
import com.fei.root.recater.listener.AdapterListeners;
import com.fei.root.recater.viewholder.CommonHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by PengFeifei on 17-7-12.
 */

public abstract class CommonAdapter<Data> extends RecyclerView.Adapter<CommonHolder> implements AdapterAction<Data> {

    private List<Data> listData;
    private int layoutId;

    private RecyclerView recyclerView;

    private AdapterListeners.OnItemClick<Data> onItemClick;
    private AdapterListeners.OnItemLongClick<Data> onItemLongClick;

    public CommonAdapter(List<Data> listData, @LayoutRes int layoutId) {
        this.listData = listData;
        this.layoutId = layoutId;
    }

    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonHolder.create(parent, layoutId);
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        Data data=getItemData(position);
        if (onItemClick != null) {
            holder.itemView.setOnClickListener(view -> onItemClick.onItemClick(data, view, position));
        }
        if (onItemLongClick != null) {
            holder.itemView.setOnLongClickListener(view -> onItemLongClick.onItemLongClick(data, view, position));
        }
        convert(holder, listData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    protected abstract void convert(CommonHolder holder, Data data, int position);


    @Override
    public void clearAll(boolean isNotify) {
        if (listData == null) {
            listData = new ArrayList<Data>();
            return;
        }
        listData.clear();
        if (isNotify) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void appendItem(Data data) {
        if (listData == null) {
            listData = new ArrayList<Data>();
        }
        listData.add(data);
        notifyDataSetChanged();
    }

    @Override
    public void appendItems(List<Data> datas) {
        if (listData == null) {
            listData = new ArrayList<Data>();
        }
        listData.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void insertItem(int position, Data data) {
        if (size(listData) <= position) {
            return;
        }
        listData.add(position, data);
        notifyItemInserted(position);
    }

    @Override
    public void removeItem(int position) {
        if (size(listData) <= position) {
            return;
        }
        listData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void updateItem(int positon, Data data) {
        if (size(listData) <= positon) {
            return;
        }
        listData.set(positon, data);
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

    @Override
    public List<Data> getDataList() {
        return listData;
    }

    @Override
    public Data getItemData(int position) {
        if (size(listData) <= position) {
            return null;
        }
        return listData.get(position);
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
