package com.ali.latte.ui.recycle;

import com.ali.latte.utils.log.LatteLogger;

import java.util.ArrayList;

/**
 * Created by 澄鱼 on 2018/5/4.
 */

public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITYS = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    public String getmJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("Data is Null!");
        }
        return mJsonData;
    }
}
