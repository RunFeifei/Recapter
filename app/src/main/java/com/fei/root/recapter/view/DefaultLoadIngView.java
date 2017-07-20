package com.fei.root.recapter.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.fei.root.recapter.R;

/**
 * Created by PengFeifei on 17-7-19.
 * ProgressBar既没有scaleType属性也没有gravity属性...
 * 所以将MaterialProgressDrawable copy到工程下
 */

public class DefaultLoadIngView extends DefaultLoadStartView {


    public DefaultLoadIngView(Context context) {
        super(context);
    }

    public DefaultLoadIngView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, @Nullable AttributeSet attrs) {
        super.init(context, attrs);
        textView.setText("正在刷新");
        MaterialProgressDrawable materialProgressDrawable = new MaterialProgressDrawable(context, imageView);
        materialProgressDrawable.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent));
        materialProgressDrawable.setAlpha(255);
        materialProgressDrawable.setStartEndTrim(0f, 0.8f);
        materialProgressDrawable.setProgressRotation(1);
        imageView.setImageDrawable(materialProgressDrawable);
        materialProgressDrawable.start();
    }
}
