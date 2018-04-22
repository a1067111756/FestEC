package com.ali.festec;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.ec.launcher.DetailsTransition;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 澄鱼 on 2018/3/17.
 */

public class ExampleFragment extends LatteDelagate {


    @Override
    public Object setLayout() {
        return R.layout.activity_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final FloatingActionButton button = (FloatingActionButton)rootView.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExampleFragment2 fragment = new ExampleFragment2();

                // 这里是使用SharedElement的用例
                // LOLLIPOP(5.0)系统的 SharedElement支持有 系统BUG， 这里判断大于 > LOLLIPOP
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    setExitTransition(new Fade());
                    fragment.setEnterTransition(new Fade());
                    fragment.setSharedElementReturnTransition(new DetailsTransition());
                    fragment.setSharedElementEnterTransition(new DetailsTransition());

                    // 25.1.0以下的support包,Material过渡动画只有在进栈时有,返回时没有;
                    // 25.1.0+的support包，SharedElement正常
                    extraTransaction()
                            .addSharedElement(button, "float")
                            .replace(fragment);
                } else {
                    //start(fragment);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
