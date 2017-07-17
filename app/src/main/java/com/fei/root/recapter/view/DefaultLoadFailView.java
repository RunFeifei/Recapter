package com.fei.root.recapter.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.fei.root.recapter.R;

/**
 * Created by PengFeifei on 17-7-19.
 */

public class DefaultLoadFailView extends DefaultLoadSuccessView {


    public DefaultLoadFailView(Context context) {
        super(context);
    }

    public DefaultLoadFailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, @Nullable AttributeSet attrs) {
        super.init(context, attrs);
        textView.setText("刷新失败");
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dialog_alert_holo_light));
    }
}
