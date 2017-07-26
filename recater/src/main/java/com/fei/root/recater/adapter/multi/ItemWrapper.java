package com.fei.root.recater.adapter.multi;

import android.support.annotation.Nullable;

/**
 * Created by PengFeifei on 17-7-26.
 * 包裹bean or 自动装箱的基本类型 or String...
 */
public class ItemWrapper<Data> {

    private int layoutId;
    private Data content;

    /**
     * @param content 可以为空,应为数据往往是从服务器获取到的
     */
    public ItemWrapper(@Nullable Data content, int layoutId) {
        this.layoutId = layoutId;
        this.content = content;
    }

    public int itemViewLayoutId() {
        return layoutId;
    }

    public Data getContent() {
        return content;
    }
}
