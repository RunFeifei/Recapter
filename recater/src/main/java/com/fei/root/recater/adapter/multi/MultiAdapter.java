package com.fei.root.recater.adapter.multi;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.fei.root.recater.adapter.RefloadAdapter;
import com.fei.root.recater.viewholder.CommonHolder;

import java.util.List;

/**
 * Created by PengFeifei on 17-7-26.
 */

public abstract class MultiAdapter<Data extends ItemModule> extends RefloadAdapter<Data> {

    private SparseArray<Integer> sparseArray;

    protected MultiAdapter(List<Data> listData) {
        super(listData);
        sparseArray = new SparseArray<>();
    }

    private MultiAdapter(@NonNull List<Data> lisData, @LayoutRes int layoutId) {
        super(lisData, layoutId);
        throw new IllegalStateException("forbidden");
    }

    private MultiAdapter(@LayoutRes int layoutId) {
        super(layoutId);
        throw new IllegalStateException("forbidden");
    }

    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headers != null && headers.get(viewType, null) != null) {
            return CommonHolder.create(parent.getContext(), headers.get(viewType));
        }
        if (footers != null && footers.get(viewType, null) != null) {
            return CommonHolder.create(parent.getContext(), footers.get(viewType));
        }
        return CommonHolder.create(parent.getContext(), parent, sparseArray.get(viewType));
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        if (position < size(headers)) {
            return;
        }
        if (footers != null && position >= size(listData) + size(headers)) {
            return;
        }
        int layoutPosition = holder.getLayoutPosition() - size(headers);
        Data data = getItemData(position);
        position=position-size(headers);
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
        convert(holder, data, position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < size(headers)) {
            return headers.keyAt(position);
        }
        if (footers != null && position >= size(listData) + size(headers)) {
            return footers.keyAt(position - size(listData) - size(headers));
        }
        Data data = getItemData(position);
        int itemViewType = data.itemViewLayoutId();
        sparseArray.put(itemViewType, itemViewType);
        return itemViewType;
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
}
