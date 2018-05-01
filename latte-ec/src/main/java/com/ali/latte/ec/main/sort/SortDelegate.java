package com.ali.latte.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ali.latte.delegates.bottom.BottomItemDelegate;
import com.ali.latte.ec.R;

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
}
