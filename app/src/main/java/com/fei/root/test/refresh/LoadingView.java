package com.fei.root.test.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.fei.root.recapter.R;
import com.fei.root.recater.action.RefloadViewAction;
import com.fei.root.recater.view.DefaultLoadIngView;


/**
 * Created by PengFeifei on 17-7-17.
 */

public class LoadingView implements RefloadViewAction {

    private Context context;

    public LoadingView(Context context) {
        this.context = context;
    }

    @Override
    public View onLoadStart() {
        return getTextView("释放加载", true);
    }

    @Override
    public View onLoading() {
        return getLottie();
    }


    @Override
    public View onLoadFail() {
        return getTextView("加载失败", true);
    }

    @Override
    public View onLoadSuccess() {
        return getTextView("加载成功", true);
    }

    @Override
    public View onLoadNone() {
        return getTextView("暂无数据", true);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onMove(float move) {
    }

    @Override
    public void onUp() {
    }

    private View getTextView(CharSequence content, boolean tips) {
        View view = LayoutInflater.from(context).inflate(R.layout.header, null);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(content);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, tips ? 150 : 300));
        return view;
    }


    private View getLottie() {
        View view = LayoutInflater.from(context).inflate(R.layout.lottie, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        LottieAnimationView lottieView = (LottieAnimationView) view.findViewById(R.id.lottie);
        lottieView.setAnimation("loading4.json");
        lottieView.playAnimation();
        lottieView.loop(true);
        lottieView.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
        lottieView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return view;
    }


}
