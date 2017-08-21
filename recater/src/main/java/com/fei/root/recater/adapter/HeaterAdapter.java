package com.fei.root.recater.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.fei.root.recater.action.HeaterAdapterAction;
import com.fei.root.recater.listener.AdapterListeners;
import com.fei.root.recater.viewholder.CommonHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengFeifei on 17-7-13.
 * 支持多Header和Footer Adapter
 * SparseArrays 不是顺序存储的 插入数据按key从小到大插入
 */

public abstract class HeaterAdapter<Data> extends RecyclerView.Adapter<CommonHolder> implements HeaterAdapterAction<Data> {

    protected SparseArray<View> headers;
    protected SparseArray<View> footers;

    private RecyclerView recyclerView;

    protected AdapterListeners.OnItemClick<Data> onItemClick;
    protected AdapterListeners.OnItemLongClick<Data> onItemLongClick;
    private AdapterListeners.OnHeaderClick onHeaderClick;
    private AdapterListeners.OnFooterClick onFooterClick;

    protected List<Data> listData;
    protected int layoutId;

    private static int UNIQUE_ID_HEADER = -1;
    private static int UNIQUE_ID_FOOTER = 1;
    private final String TAG = HeaterAdapter.this.getClass().getSimpleName();

    public HeaterAdapter(@NonNull List<Data> lisData, @LayoutRes int layoutId) {
        this.listData = lisData;
        this.layoutId = layoutId;
    }

    public HeaterAdapter(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
    }

    /**
     * Attention!!
     * just add for MultiAdapter
     */
    protected HeaterAdapter(List<Data> listData) {
        this.listData = listData;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < size(headers)) {
            return headers.keyAt(position);
        }
        if (footers != null && position >= size(listData) + size(headers)) {
            return footers.keyAt(position - size(listData) - size(headers));
        }
        return 0;
    }

    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headers != null && headers.get(viewType, null) != null) {
            return CommonHolder.create(parent.getContext(), headers.get(viewType));
        }
        if (footers != null && footers.get(viewType, null) != null) {
            return CommonHolder.create(parent.getContext(), footers.get(viewType));
        }
        return CommonHolder.create(parent.getContext(), parent, layoutId);
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        if (position < size(headers)) {
            if (onHeaderClick != null) {
                holder.itemView.setOnClickListener(view -> onHeaderClick.onHeaderClick(view, position));
            }
            return;
        }
        if (footers != null && position >= size(listData) + size(headers)) {
            if (onFooterClick != null) {
                holder.itemView.setOnClickListener(view -> onFooterClick.onHeaderClick(view, position - size(headers) - size(listData)));
            }
            return;
        }
        //// TODO: 17-7-27 Position不是很准确
        int layoutPosition = holder.getLayoutPosition() - size(headers);
        Data data = getItemData(position);
        if (onItemClick != null) {
            holder.itemView.setOnClickListener(view -> onItemClick.onItemClick(data, view, layoutPosition));
        }
        if (onItemLongClick != null) {
            holder.itemView.setOnLongClickListener(view -> onItemLongClick.onItemLongClick(data, view, layoutPosition));
        }
        convert(holder, data, layoutPosition);
    }

    protected abstract void convert(CommonHolder holder, Data data, int position);

    @Override
    public int getItemCount() {
        return size(listData) + size(headers) + size(footers);
    }

    @Override
    public int addHeader(View header) {
        return addHeader(UNIQUE_ID_HEADER--, header);
    }

    protected int addHeader(int uniqueId, View header) {
        if (header.getParent() != null) {
            Log.e(TAG, "already has a parent,can not add again");
            return 404;
        }
        if (headers == null) {
            headers = new SparseArray<>();
        }
        headers.put(uniqueId, header);
        notifyDataSetChanged();
        return uniqueId;
    }

    @Override
    public int addFooter(View footer) {
        return addFooter(UNIQUE_ID_FOOTER++, footer);
    }

    protected int addFooter(int uniqueId, View footer) {
        if (footer.getParent() != null) {
            Log.e(TAG, "already has a parent,can not add again");
            return 404;
        }
        if (footers == null) {
            footers = new SparseArray<>();
        }
        footers.put(uniqueId, footer);
        notifyDataSetChanged();
        return uniqueId;
    }


    @Override
    public View getHeader(int uniqueId) {
        if (headers == null || headers.size() == 0) {
            return null;
        }
        return headers.get(uniqueId);
    }

    @Override
    public View getFooter(int uniqueId) {
        if (footers == null || footers.size() == 0) {
            return null;
        }
        return footers.get(uniqueId);
    }

    public void removeHeader(int uniqueId) {
        if (headers == null || headers.size() == 0) {
            return;
        }
        int position = headers.indexOfKey(uniqueId);
        if (position < 0) {
            Log.e(TAG, "not Header found");
            return;
        }
        headers.remove(uniqueId);
        notifyItemRemoved(position);
    }

    @Override
    public void removeHeader(View view) {
        if (headers == null || headers.size() == 0) {
            return;
        }
        int position = headers.indexOfValue(view);
        if (position < 0) {
            Log.e(TAG, "not Header found");
            return;
        }
        headers.removeAt(position);
        notifyItemRemoved(position);
    }

    public void removeFooter(int uniqueId) {
        if (footers == null || footers.size() == 0) {
            return;
        }
        int position = footers.indexOfKey(uniqueId);
        if (position < 0) {
            Log.e(TAG, "not footer found");
            return;
        }
        footers.remove(uniqueId);
        notifyItemRemoved(position + getHeadersSize() + getDatasSize());
    }

    @Override
    public void removeFooter(View view) {
        if (footers == null || footers.size() == 0) {
            return;
        }
        int position = footers.indexOfValue(view);
        if (position < 0) {
            Log.e(TAG, "not footer found");
            return;
        }
        footers.removeAt(position);
        notifyItemRemoved(position + getHeadersSize() + getDatasSize());
    }

    public int getFootersSize() {
        return size(footers);
    }

    public int getHeadersSize() {
        return size(headers);
    }

    public int getDatasSize() {
        return size(listData);
    }

    protected int size(SparseArray sparseArray) {
        if (sparseArray == null) {
            return 0;
        }
        return sparseArray.size();
    }

    protected int size(List list) {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

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
        notifyItemInserted(position + size(headers));
    }

    @Override
    public void removeItem(int position) {
        if (size(listData) <= position) {
            return;
        }
        listData.remove(position);
        notifyItemRemoved(position + size(headers));
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
    public void setOnItemClick(AdapterListeners.OnItemClick<Data> onItemClick) {
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
        position = position - size(headers);
        if (size(listData) <= position || position < 0) {
            return null;
        }
        return listData.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void setOnHeaderClick(AdapterListeners.OnHeaderClick onHeaderClick) {
        this.onHeaderClick = onHeaderClick;
    }

    @Override
    public void setOnFooterClick(AdapterListeners.OnFooterClick onFooterClick) {
        this.onFooterClick = onFooterClick;
    }
}
