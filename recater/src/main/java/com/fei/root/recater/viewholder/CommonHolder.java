package com.fei.root.recater.viewholder;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feifei.common.MultiApplication;


/**
 * Created by PengFeifei on 17-7-12.
 */

public class CommonHolder<Data> extends RecyclerView.ViewHolder {

    private SparseArray<View> views;

    private CommonHolder(@NonNull View itemView) {
        super(itemView);
        views = new SparseArray<>();
    }

    public static CommonHolder create(View itemView) {
        return new CommonHolder(itemView);
    }

    public static CommonHolder create(ViewGroup parent, @LayoutRes int layoutId) {
        View itemView =getlayoutInflate().inflate(layoutId, parent, false);
        return new CommonHolder(itemView);
    }

    public CommonHolder setText(int viewId, CharSequence text) {
        TextView tv = findView(viewId);
        tv.setText(text);
        return this;
    }

    public CommonHolder setImageResource(int viewId, @DrawableRes int resId) {
        ImageView view = findView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public CommonHolder setTextColor(int viewId, @ColorInt int textColor) {
        TextView view = findView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    private <T extends View> T findView(int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        if (view == null) {
            throw new RuntimeException("can not find view in CommonHolder");
        }
        return (T) view;
    }

    private static LayoutInflater getlayoutInflate() {
        return (LayoutInflater) MultiApplication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


}
