package com.ali.latte.ui.recycle;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.WeakHashMap;

/**
 * Created by 澄鱼 on 2018/5/4.
 */

public class MultipleItemEntity implements MultiItemEntity{

    // 使用软引用来防止当recycleview数据量过大时造成的内存溢出  http://blog.51cto.com/alinazh/1276173
    private final ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUEUE = new ReferenceQueue<>();
    private final LinkedHashMap<Object, Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object, Object>> FIELDS_REFERENCE =
            new SoftReference<LinkedHashMap<Object, Object>>(MULTIPLE_FIELDS, ITEM_QUEUE);

    public MultipleItemEntity(LinkedHashMap<Object, Object> fields) {
        FIELDS_REFERENCE.get().putAll(fields);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public int getItemType() {
        return (int) FIELDS_REFERENCE.get().get(MultipleFields.ITEM_TYPE);
    }

    @SuppressWarnings("unchecked")
    public final <T> T getField(Object key) {
        return (T) FIELDS_REFERENCE.get().get(key);
    }


    public final LinkedHashMap<?,?> getFields() {
        return FIELDS_REFERENCE.get();
    }

    public final MultipleItemEntity setField(Object key, Object value) {
        FIELDS_REFERENCE.get().put(key, value);
        return this;
    }

    public static class Builder {

        private final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

        public Builder() {
            //先清楚之前的数据
            FIELDS.clear();
        }

        public final Builder setItemType(int itemType) {
            FIELDS.put(MultipleFields.ITEM_TYPE, itemType);
            return this;
        }

        public final Builder setField(Object key, Object value) {
            FIELDS.put(key, value);
            return this;
        }

        public final Builder setField(LinkedHashMap<Object, Object> map) {
            FIELDS.putAll(map);
            return this;
        }

        public final MultipleItemEntity build() {
            return new MultipleItemEntity(FIELDS);
        }
    }
}
