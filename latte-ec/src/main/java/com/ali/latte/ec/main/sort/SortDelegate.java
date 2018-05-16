package com.ali.latte.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ali.latte.delegates.bottom.BottomItemDelegate;
import com.ali.latte.ec.R;
import com.ali.latte.ec.main.sort.content.ContentDelegate;
import com.ali.latte.ec.main.sort.list.VerticalListDelegate;

/**
 * Created by 澄鱼 on 2018/5/2.
 */

public class SortDelegate extends BottomItemDelegate{
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    /* 让布局渲染使用懒加载 */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 加载根布局
        final VerticalListDelegate verticalListDelegate = new VerticalListDelegate();
        loadRootFragment(R.id.vertical_list_container, verticalListDelegate);
        // 默认显示分类一
        loadRootFragment(R.id.vertical_sort_container, ContentDelegate.newInstanc(1));
    }
}
