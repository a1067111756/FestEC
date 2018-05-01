package com.ali.latte.ec.main;

import android.graphics.Color;

import com.ali.latte.delegates.bottom.BaseBottomDelegate;
import com.ali.latte.delegates.bottom.BottomItemDelegate;
import com.ali.latte.delegates.bottom.BottomTabBean;
import com.ali.latte.delegates.bottom.ItemBuilder;
import com.ali.latte.ec.main.index.IndexDelegate;

import java.util.LinkedHashMap;

/**
 * Created by 澄鱼 on 2018/5/2.
 */

public class EcBottomDelegate extends BaseBottomDelegate{

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {

        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-compass}", "发现"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-user}", "我的"), new IndexDelegate());

        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
