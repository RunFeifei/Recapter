package com.fei.root.multi;

import com.fei.root.recapter.R;
import com.fei.root.recater.adapter.multi.ItemModule;

/**
 * Created by PengFeifei on 17-7-26.
 */

public class Person implements ItemModule {

    private static int i = 0;

    public String name = "Person" + i++;

    @Override
    public int itemViewLayoutId() {
        return R.layout.item_02;
    }


}