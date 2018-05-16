package com.ali.latte.ec.main.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;

import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.delegates.bottom.BottomItemDelegate;
import com.ali.latte.delegates.web.WebDelegateImpl;
import com.ali.latte.ec.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by 澄鱼 on 2018/5/13.
 */

public class DiscoverDelegate extends BottomItemDelegate{

    @Override
    public Object setLayout() {
        return R.layout.delegate_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final WebDelegateImpl delegate = WebDelegateImpl.create("https://www.mi.com/");
        delegate.setTopDelegate(this.getParentDelegate());
        loadRootFragment(R.id.web_discover_container, delegate);
    }

    /* 横向动画 */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
