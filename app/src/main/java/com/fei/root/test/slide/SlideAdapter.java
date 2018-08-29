package com.fei.root.test.slide;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.fei.root.recater.adapter.CommonAdapter;
import com.fei.root.recater.viewholder.CommonHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengFeifei on 2018/8/29.
 */
public abstract class SlideAdapter<Data> extends CommonAdapter<Data> implements OnSlideStatus {

    private List<OnSLideAction> listHolders;
    private int lastSlidedOutPosition;

    public SlideAdapter(List<Data> listData, int layoutId) {
        super(listData, layoutId);
        listHolders = new ArrayList<>();
    }

    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonHolder commonHolder = super.onCreateViewHolder(parent, viewType);
        View view = commonHolder.itemView;
        if (!(view instanceof OnSLideAction)) {
            throw new RuntimeException("item view not OnSLideAction");
        }
        listHolders.add((OnSLideAction) view);
        return commonHolder;
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
                        if (positionTouch != lastSlidedOutPosition) {
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
}
