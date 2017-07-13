package com.fei.root.recapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengFeifei on 17-7-13.
 * 支持多Header和Footer Adapter
 */

public abstract class HeaterAdapter<Data> extends RecyclerView.Adapter<CommonHolder> implements HeaterAdapterAction<Data> {

    //SparseArray 不是顺序存取的!!!!
    //用linkedhashMap
    private SparseArray<View> headers;
    private SparseArray<View> footers;

    private RecyclerView recyclerView;

    private AdapterListeners.OnItemClick onItemClick;
    private AdapterListeners.OnItemLongClick onItemLongClick;
    private AdapterListeners.OnHeaderClick onHeaderClick;
    private AdapterListeners.OnFooterClick onFooterClick;

    private List<Data> lisData;
    private int layoutId;

    private static int UNIQUE_ID = -1;
    private final String TAG = HeaterAdapter.this.getClass().getSimpleName();

    public HeaterAdapter(@NonNull List<Data> lisData, @LayoutRes int layoutId) {
        this.lisData = lisData;
        this.layoutId = layoutId;
    }

    @Override
    public int getItemViewType(int position) {
        if (headers != null && position < headers.size()) {
            return headers.keyAt(position);
        }
        if (footers != null && position >= lisData.size() + headers.size()) {
            return footers.keyAt(position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headers != null && headers.get(viewType, null) != null) {
            return CommonHolder.create(headers.get(viewType));
        }
        if (footers != null && footers.get(viewType, null) != null) {
            return CommonHolder.create(footers.get(viewType));
        }
        return CommonHolder.create(parent, layoutId);
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        if (headers != null && position < headers.size()) {
            holder.itemView.setOnClickListener(view -> {
                if (onHeaderClick != null) {
                    onHeaderClick.onHeaderClick(position);
                }
            });
            return;
        }
        if (footers != null && position >= lisData.size() + headers.size()) {
            holder.itemView.setOnClickListener(view -> {
                if (onFooterClick != null) {
                    onFooterClick.onHeaderClick(position);
                }
            });
            return;
        }
        holder.itemView.setOnClickListener(view -> {
            if (onItemClick != null) {
                onItemClick.onItemClick(position);
            }
        });
        holder.itemView.setOnLongClickListener(view -> {
            if (onItemLongClick != null) {
                return onItemLongClick.onItemLongClick(position);
            }
            return false;
        });
        convert(holder, lisData.get(position- size(headers)), position- size(headers));
    }
    protected abstract void convert(CommonHolder holder, Data data, int position);

    @Override
    public int getItemCount() {
        return size(lisData) + size(headers) + size(footers);
    }

    public void addHeader(int uniqueId, View header) {
        if (headers == null) {
            headers = new SparseArray<>();
        }
        int position = headers.indexOfKey(uniqueId);
        if (position >= 0) {
            return;
        }
        headers.put(uniqueId, header);
        notifyDataSetChanged();
    }

    public void addHeader(View header) {
        addHeader(UNIQUE_ID--, header);
    }

    public void addFooter(int uniqueId, View footer) {
        if (footers == null) {
            footers = new SparseArray<>();
        }
        int position = footers.indexOfKey(uniqueId);
        if (position >= 0) {
            return;
        }
        footers.put(uniqueId, footer);
        notifyDataSetChanged();
    }

    public void addFooter(View footer) {
        addFooter(UNIQUE_ID--, footer);
    }

    public View getHeader(int uniqueId) {
        if (headers == null || headers.size() == 0) {
            return null;
        }
        return headers.get(uniqueId);
    }

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
            return;
        }
        headers.remove(uniqueId);
        notifyItemRemoved(position);
    }

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
        notifyItemRemoved(position);
    }

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
        notifyItemRemoved(position);
    }

    private int size(SparseArray sparseArray) {
        if (sparseArray == null) {
            return 0;
        }
        return sparseArray.size();
    }

    private int size(List list) {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

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
        notifyItemInserted(position+size(headers));
    }

    @Override
    public void removeItem(int position) {
        if (size(lisData) <= position) {
            return;
        }
        lisData.remove(position);
        notifyItemRemoved(position+size(headers));
    }

    @Override
    public void updateItem(int positon, Data data) {
        if (size(lisData)<=positon) {
            return;
        }
        lisData.set(positon,data);
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
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void setOnHeaderClick(AdapterListeners.OnHeaderClick onHeaderClick) {
        this.onHeaderClick=onHeaderClick;
    }

    @Override
    public void setOnFooterClick(AdapterListeners.OnFooterClick onFooterClick) {
        this.onFooterClick=onFooterClick;
    }
}
