package com.fei.root.recapter.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fei.root.recapter.R;

/**
 * Created by PengFeifei on 17-7-19.
 */

public class DefaultLoadStartView extends LinearLayout {

    protected TextView textView;
    protected ImageView imageView;

    public DefaultLoadStartView(Context context) {
        super(context);
        init(context, null);
    }

    public DefaultLoadStartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
        layoutParams.gravity = Gravity.CENTER;

        textView = new TextView(context);
        textView.setTextSize(10);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setPadding(50, 0, 0, 0);


        imageView = new ImageView(context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(100, 100, 1.0f));
        imageView.setScaleType(ImageView.ScaleType.FIT_END);
        imageView.setPadding(0, 0, 50, 0);


        textView.setText("释放刷新");
        imageView.setImageResource(R.drawable.ic_arrow);

        addView(imageView);
        addView(textView);
    }

    private void setViewContent(CharSequence text, @DrawableRes int resId) {
        textView.setText(text);
        imageView.setImageResource(resId);
    }


}
