package com.ali.latte.ec.main.index;

import com.ali.latte.ui.recycle.DataConverter;
import com.ali.latte.ui.recycle.ItemType;
import com.ali.latte.ui.recycle.MultipleFields;
import com.ali.latte.ui.recycle.MultipleItemEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * Created by 澄鱼 on 2018/5/4.
 */

public class IndexDataConverter extends DataConverter{

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        // 解析数据
        final JSONArray dataArray = JSON.parseObject(getmJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);

            final String imageUrl = data.getString("imageUrl");
            final String text = data.getString("text");
            final int spanSize = data.getInteger("spanSize");
            final int id = data.getInteger("goodsId");
            final JSONArray banners = data.getJSONArray("banners");

            final ArrayList<String> bannerImages = new ArrayList<>();
            int type = 0;
            if (imageUrl == null && text != null) {
                type = ItemType.TEXT;
            } else  if (imageUrl != null && text == null) {
                type = ItemType.IMAGE;
            } else if (imageUrl != null && text != null) {
                type = ItemType.TEXT_IMAGE;
            } else if (banners != null) {
                type = ItemType.BANNER;

                //Banner 初始化
                final  int bannerSize = banners.size();
                for (int j = 0; j < bannerSize; j++) {
                    final String banner = banners.getString(j);
                    bannerImages.add(banner);
                }
            }

            final MultipleItemEntity entity = MultipleItemEntity
                    .builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.SPAN_SIZE, spanSize)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, text)
                    .setField(MultipleFields.IMAGE_URL, imageUrl)
                    .setField(MultipleFields.BANNERS, bannerImages)
                    .build();
            ENTITYS.add(entity);
        }

        return ENTITYS;
    }
}
