package com.fei.root.recater.adapter.multi;

import android.support.annotation.Nullable;

/**
 * Created by PengFeifei on 17-7-26.
 * 包裹自动装箱的基本类型 or String...
 */
public class ItemWrapper<Data> implements ItemModule {

    private int layoutId;
    private Data content;

    /**
     * @param content 可以为空,应为数据往往是从服务器获取到的
     */
    public ItemWrapper(@Nullable Data content, int layoutId) {
        this.layoutId = layoutId;
        this.content = content;
    }

    public Data getContent() {
        return content;
    }

    @Override
    public int itemViewLayoutId() {
        return layoutId;
    }

    @Override
    public String toString() {
        return "layoutId-->"+layoutId+"  content-->"+content.toString();
    }
}
