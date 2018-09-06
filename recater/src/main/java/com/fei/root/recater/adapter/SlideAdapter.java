package com.fei.root.recater.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.fei.root.recater.action.OnSLideAction;
import com.fei.root.recater.action.OnSlideStatus;
import com.fei.root.recater.listener.AdapterListeners;
import com.fei.root.recater.viewholder.CommonHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengFeifei on 2018/8/29.
 */
public abstract class SlideAdapter<Data> extends CommonAdapter<Data> implements OnSlideStatus {

    private List<OnSLideAction> listHolders;
    private int lastSlidedOutPosition;
    private AdapterListeners.OnSlideClick onSlideClicks;

    public SlideAdapter(List<Data> listData, int layoutId) {
        super(listData, layoutId);
        listHolders = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        View view = holder.itemView;
        if (!(view instanceof OnSLideAction)) {
            throw new RuntimeException("item view not OnSLideAction");
        }
        OnSLideAction onSLideAction = (OnSLideAction) view;
        listHolders.add(onSLideAction);
        onSLideAction.setOnSlideClicks(onSlideClicks);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    if (childView instanceof OnSLideAction) {
                        int positionTouch = rv.getChildLayoutPosition(childView);
                        if (positionTouch != lastSlidedOutPosition &&
                                listHolders.size() > lastSlidedOutPosition &&
                                (listHolders.get(lastSlidedOutPosition).isSlidedOut())) {
                            listHolders.get(lastSlidedOutPosition).doSlide(true);
                        }
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    @Override
    public void updateLastSlideOutPosition(int position) {
        this.lastSlidedOutPosition = position;
    }

    public void setOnSlideClicks(AdapterListeners.OnSlideClick onSlideClicks) {
        this.onSlideClicks = onSlideClicks;
    }
}
