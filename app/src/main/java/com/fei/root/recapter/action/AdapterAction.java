package com.fei.root.recapter.action;

import android.support.v7.widget.RecyclerView;

import com.fei.root.recapter.listener.AdapterListeners;

import java.util.List;

/**
 * Created by PengFeifei on 17-7-14.
 */

public interface AdapterAction<Data> {

    void appendItem(Data data);

    void appendItems(List<Data> datas);

    void insertItem(int position, Data data);

    void removeItem(int position);

    void updateItem(int positon, Data data);


    void setOnItemClick(AdapterListeners.OnItemClick onItemClick);

    void setOnItemLongClick(AdapterListeners.OnItemLongClick onItemLongClick);


    RecyclerView getRecyclerView();

}
