package com.ali.latte.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * Created by 澄鱼 on 2018/5/1.
 * 用于创建导航栏数据条目集合 --- 简单工厂模式
 */

public final class ItemBuilder {

    // 使用LinkedHashMap而不使用HashMap是为了保证数据的有序
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    protected static  ItemBuilder builder() {
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(BottomTabBean bean, BottomItemDelegate itemDelegate) {
        ITEMS.put(bean, itemDelegate);
        return this;
    }

    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean, BottomItemDelegate> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<BottomTabBean, BottomItemDelegate> build() {
        return ITEMS;
    }
}
