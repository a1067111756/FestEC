package com.ali.festec;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.utils.log.LatteLogger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 澄鱼 on 2018/4/12.
 */

public class ExampleFragment2 extends LatteDelagate {


    FloatingActionButton button;
    RelativeLayout container;

    @Override
    public Object setLayout() {
        return R.layout.activity_example2;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        button = (FloatingActionButton)rootView.findViewById(R.id.button);
        container = (RelativeLayout)rootView.findViewById(R.id.container);

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

        enterAnimation();

    }

    private void enterAnimation() {

        int centerX = (button.getLeft() + button.getRight()) / 2;
        int centerY = (button.getTop() + button.getBottom()) / 2;
        float finalRadius = (float) Math.hypot((double) centerX, (double) centerY);

        Animator mCircularReveal = ViewAnimationUtils.createCircularReveal(container, centerX, centerY, 0, finalRadius);
        mCircularReveal.setDuration(1000);
        mCircularReveal.start();
    }


    @Override
    public void onResume() {
        super.onResume();

    }
}
