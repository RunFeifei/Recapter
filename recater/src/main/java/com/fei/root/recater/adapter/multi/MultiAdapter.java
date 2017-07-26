package com.fei.root.recater.adapter.multi;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.fei.root.recater.viewholder.CommonHolder;

import java.util.List;

/**
 * Created by PengFeifei on 17-7-26.
 * 暂没有实现上拉下拉
 */

public abstract class MultiAdapter<Data extends ItemWrapper> extends RecyclerView.Adapter<CommonHolder> {

    private List<Data> listData;
    private SparseArray<Integer> sparseArray;

    public MultiAdapter(List<Data> listData) {
        this.listData = listData;
        sparseArray = new SparseArray<>();
    }

    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonHolder.create(parent, sparseArray.get(viewType));
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        if (size(listData) <= position) {
            return;
        }
        Data data = listData.get(position);
        if (data == null) {
            throw new IllegalStateException("wrapper is null");
        }
        Object object = data.getContent();
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
        convert(holder, data, position);
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
     * 除了上述三种具体类型外,默认调用此方法
     * @return true表示已消费该事件
     */
    protected abstract void convert(CommonHolder holder, ItemWrapper data, int position);

}
