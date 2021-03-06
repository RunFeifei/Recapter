package com.fei.root.recater.action;

import android.support.v7.widget.RecyclerView;


import com.fei.root.recater.listener.AdapterListeners;

import java.util.List;

/**
 * Created by PengFeifei on 17-7-14.
 */

public interface AdapterAction<Data> {

    void clearAll(boolean isNotify);

    void appendItem(Data data);

    void appendItems(List<Data> datas);

    void insertItem(int position, Data data);

    void removeItem(int position);

    void updateItem(int positon, Data data);


    void setOnItemClick(AdapterListeners.OnItemClick<Data>  onItemClick);

    void setOnItemLongClick(AdapterListeners.OnItemLongClick<Data>  onItemLongClick);


    RecyclerView getRecyclerView();

    List<Data> getDataList();

    Data getItemData(int position);

}
