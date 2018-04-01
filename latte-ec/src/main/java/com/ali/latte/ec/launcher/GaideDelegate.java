package com.ali.latte.ec.launcher;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.transition.TransitionInflater;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.ToxicBakery.viewpager.transforms.FlipHorizontalTransformer;
import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;
import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.ToxicBakery.viewpager.transforms.StackTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;
import com.ali.latte.app.Latte;
import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.ec.R;
import com.ali.latte.ec.R2;
import com.ali.latte.ui.launcher.BannerHolderCreator;
import com.ali.latte.ui.launcher.ScrollLauncherTag;
import com.ali.latte.utils.storage.LattePreference;
import com.ali.latte.utils.transition.GuiUtils;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;


import java.util.ArrayList;

import javax.xml.transform.Transformer;

public class GaideDelegate extends LatteDelagate implements OnItemClickListener {

    private ConvenientBanner mConvenientBanner = null;
    private static final ArrayList<Integer> IMAGE_LIST = new ArrayList<>();

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner(getActivity());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        initBanner();
    }

    private void initBanner() {

        IMAGE_LIST.add(R.drawable.launcher_00);
        IMAGE_LIST.add(R.drawable.launcher_01);
        IMAGE_LIST.add(R.drawable.launcher_02);
        IMAGE_LIST.add(R.drawable.launcher_03);

        ABaseTransformer transforemer= null;
        try {
            transforemer = (ABaseTransformer)AccordionTransformer.class.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mConvenientBanner.setPages(new BannerHolderCreator(), IMAGE_LIST)
                .setPageIndicator(new int[]{R.drawable.border_indicator_gaide_normal, R.drawable.border_indicator_gaide_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setPageTransformer(transforemer)
                .setCanLoop(false);
    }

    @Override
    public void onItemClick(int position) {
        // 如果点击的是最后一个
        if(position == IMAGE_LIST.size() - 1) {
            LattePreference.setAppFlag(ScrollLauncherTag.IS_FIRST_LAUNCHER_APP.name(), true);
            // 检查用户是否已经登陆
        }
    }


    /*
    // 入场动画
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater.from(getProxyActivity())
                .inflateTransition(R.transition.gaide_share_transition);

        //setSharedElementEnterTransition(transition);
    }

    // 动画展示
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow() {
        GuiUtils.animateRevealShow(
                this, mRlContainer,
                mFabCircle.getWidth() / 2, R.color.colorAccent,
                new GuiUtils.OnRevealAnimationListener() {
                    @Override public void onRevealHide() {

                    }

                    @Override public void onRevealShow() {

                    }
                });
    }


    // 退出动画
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupExitAnimation() {
        Fade fade = new Fade();
        fade.setDuration(300);
        setReturnTransition(fade);
    }
    */
}
