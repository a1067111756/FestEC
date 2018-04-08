package com.ali.latte.ec.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * Created by 澄鱼 on 2018/3/24.
 */

public class BannerHolderCreator implements CBViewHolderCreator<BannerHolder> {

    @Override
    public BannerHolder createHolder() {
        return new BannerHolder();
    }
}