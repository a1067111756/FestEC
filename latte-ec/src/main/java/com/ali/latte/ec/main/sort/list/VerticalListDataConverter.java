package com.ali.latte.ec.main.sort.list;

import com.ali.latte.ui.recycle.DataConverter;
import com.ali.latte.ui.recycle.ItemType;
import com.ali.latte.ui.recycle.MultipleFields;
import com.ali.latte.ui.recycle.MultipleItemEntity;
import com.ali.latte.utils.log.LatteLogger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * Created by 澄鱼 on 2018/5/11.
 */

public class VerticalListDataConverter extends DataConverter{

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON
                .parseObject(getmJsonData())
                .getJSONObject("data")
                .getJSONArray("list");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, name)
                    .setField(MultipleFields.TAG, false)
                    .build();
            dataList.add(entity);

            //设置第一个被选中
            dataList.get(0).setField(MultipleFields.TAG, true);
        }


        return dataList;
    }
}
