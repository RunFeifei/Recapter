package com.fei.root.recater.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by PengFeifei on 17-7-19.
 * ProgressBar既没有scaleType属性也没有gravity属性...
 * 所以将MaterialProgressDrawable copy到工程下
 */

public class DefaultLoadIngView extends LinearLayout {

    private TextView textView;

    public DefaultLoadIngView(Context context) {
        super(context);
        init(context, null);
    }

    public DefaultLoadIngView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;

        textView = new TextView(context);
        textView.setTextSize(15);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setPadding(20, 0, 0, 0);

        ProgressBar progressBar = new ProgressBar(context);
        layoutParams.gravity = Gravity.RIGHT;
        progressBar.setLayoutParams(layoutParams);
        progressBar.setPadding(0, 0, 20, 0);

        textView.setText("正在刷新");
        addView(progressBar);
        addView(textView);
    }

    public DefaultLoadIngView setText(CharSequence charSequence) {
        textView.setText(charSequence);
        return this;
    }
}
