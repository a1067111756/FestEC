package com.ali.latte.ui.banner;

import android.widget.AdapterView;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.ali.latte.R;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by 澄鱼 on 2018/5/5.
 */

public class BannerHolderCreator implements CBViewHolderCreator<ImageHolerd>{

    @Override
    public ImageHolerd createHolder() {
        return new ImageHolerd();
    }
}
