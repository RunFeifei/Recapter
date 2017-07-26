package com.fei.root.recater.adapter.multi;

import android.support.annotation.Nullable;

/**
 * Created by PengFeifei on 17-7-26.
 * 包裹自动装箱的基本类型和String等
 * Integer Boolean String...
 */

public class WrapperItemModule<Data> implements ItemModule {

    private int layoutId;
    private Data content;

    /**
     * @param content 可以为空,应为数据往往是从服务器获取到的
     */
    public WrapperItemModule(@Nullable Data content, int layoutId) {
        this.layoutId = layoutId;
        this.content = content;
    }

    @Override
    public int itemViewLayoutId() {
        return layoutId;
    }

    public Data getContent() {
        return content;
    }
}
