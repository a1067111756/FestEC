package com.ali.latte.ui.banner;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.ali.latte.R;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by 澄鱼 on 2018/5/5.
 */

public class BannerCreator {

    public static void setDefault(ConvenientBanner<String> convenientBanner, ArrayList<String> banners,
                                  OnItemClickListener clickListener) {
        convenientBanner
                .setPages(new BannerHolderCreator(), banners)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(clickListener)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);
    }

}
