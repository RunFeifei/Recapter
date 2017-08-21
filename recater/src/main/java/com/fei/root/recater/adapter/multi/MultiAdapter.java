package com.fei.root.recater.adapter.multi;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.fei.root.recater.action.AdapterAction;
import com.fei.root.recater.listener.AdapterListeners;
import com.fei.root.recater.viewholder.CommonHolder;

import java.util.List;

/**
 * Created by PengFeifei on 17-7-26.
 * 暂没有实现上拉下拉
 */

public abstract class MultiAdapter<Data extends ItemModule> extends RecyclerView.Adapter<CommonHolder> implements AdapterAction<Data> {

    private List<Data> listData;
    private SparseArray<Integer> sparseArray;

    private AdapterListeners.OnItemClick<Data> onItemClick;

    public MultiAdapter(List<Data> listData) {
        this.listData = listData;
        sparseArray = new SparseArray<>();
    }

    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonHolder.create(parent.getContext(),parent, sparseArray.get(viewType));
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        if (size(listData) <= position) {
            return;
        }
        int layoutPosition = holder.getLayoutPosition();
        Data data = getItemData(position);
        if (onItemClick != null) {
            holder.itemView.setOnClickListener(view -> onItemClick.onItemClick(data, view, layoutPosition));
        }

        if (data instanceof ItemWrapper) {
            ItemWrapper wrapper = (ItemWrapper) data;
            Object object = wrapper.getContent();
            // TODO: 17-7-26 如果被包裹的类型是null 此时暂不处理 此时的null是否应该交于外部处理
            if (object == null) {
                return;
            }
            if (object instanceof String && convert(holder, (String) object, position)) {
                return;
            }
            if (object instanceof Integer && convert(holder, (Integer) object, position)) {
                return;
            }
            if (object instanceof Boolean && convert(holder, (Boolean) object, position)) {
                return;
            }
            if (convert(holder, wrapper, position)) {
                return;
            }
        }
        convert(holder, listData.get(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        Data data = listData.get(position);
        int itemViewType = data.itemViewLayoutId();
        sparseArray.put(itemViewType, itemViewType);
        return itemViewType;
    }

    @Override
    public int getItemCount() {
        return size(listData);
    }

    private int size(List list) {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    /**
     * 默认提供一下三种具体类型
     *
     * @return true表示已消费该事件, 否则会继续执行{@link #convert(CommonHolder, ItemWrapper, int)}
     */
    protected boolean convert(CommonHolder holder, String data, int position) {
        return false;
    }

    protected boolean convert(CommonHolder holder, Integer data, int position) {
        return false;
    }

    protected boolean convert(CommonHolder holder, Boolean data, int position) {
        return false;
    }

    /**
     * 除了上述三种具体类型外,提供包装类型
     *
     * @return true表示已消费该事件, 否则会继续执执行{@link #convert(CommonHolder, ItemModule, int)}
     */
    protected boolean convert(CommonHolder holder, ItemWrapper wrapper, int position) {
        return false;
    }

    protected void convert(CommonHolder holder, ItemModule module, int position) {

    }

    @Override
    public void clearAll(boolean isNotify) {
        //// TODO: 17-7-31

    }

    @Override
    public void appendItem(Data data) {
        //// TODO: 17-7-31
    }

    @Override
    public void appendItems(List<Data> datas) {
        //// TODO: 17-7-31
    }

    @Override
    public void insertItem(int position, Data data) {
        //// TODO: 17-7-31
    }

    @Override
    public void removeItem(int position) {
        //// TODO: 17-7-31
    }

    @Override
    public void updateItem(int positon, Data data) {
        //// TODO: 17-7-31
    }

    @Override
    public void setOnItemClick(AdapterListeners.OnItemClick<Data> onItemClick) {
        this.onItemClick=onItemClick;
    }

    @Override
    public void setOnItemLongClick(AdapterListeners.OnItemLongClick<Data> onItemLongClick) {
        //// TODO: 17-7-31
    }

    @Override
    public RecyclerView getRecyclerView() {
        //// TODO: 17-7-31
        return null;
    }

    @Override
    public List<Data> getDataList() {
        //// TODO: 17-7-31
        return null;
    }

    @Override
    public Data getItemData(int position) {
        if (size(listData) <= position || position < 0) {
            return null;
        }
        return listData.get(position);
    }
}
