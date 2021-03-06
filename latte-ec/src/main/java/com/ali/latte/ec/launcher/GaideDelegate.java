package com.ali.latte.ec.launcher;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.ec.R;
import com.ali.latte.ec.R2;
import com.ali.latte.utils.storage.LattePreference;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GaideDelegate extends LatteDelagate {


    Unbinder unbinder;

    private static final ArrayList<Integer> IMAGE_LIST = new ArrayList<>();
    @BindView(R2.id.convenientBanner)
    ConvenientBanner mConvenientBanner;
    @BindView(R2.id.container)
    RelativeLayout container;

    @Override
    public Object setLayout() {
        return R.layout.delegate_gaide;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }


    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        initBanner();
        enterAnimation();
    }

    private void enterAnimation() {

        int centerX = container.getLeft();
        int centerY = container.getBottom();
        float finalRadius = (float) Math.hypot((double) centerX, (double) centerY);

        Animator mCircularReveal = ViewAnimationUtils.createCircularReveal(container, centerX, centerY, 0, finalRadius);
        mCircularReveal.setDuration(1500);
        mCircularReveal.start();

    }

    private void initBanner() {


        IMAGE_LIST.add(R.drawable.launcher_00);
        IMAGE_LIST.add(R.drawable.launcher_01);
        IMAGE_LIST.add(R.drawable.launcher_02);
        IMAGE_LIST.add(R.drawable.launcher_last);

        ABaseTransformer transforemer = null;
        try {
            transforemer = (ABaseTransformer) AccordionTransformer.class.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mConvenientBanner.setPages(new BannerHolderCreator(), IMAGE_LIST)
                .setPageIndicator(new int[]{R.drawable.border_indicator_gaide_normal, R.drawable.border_indicator_gaide_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(transforemer)
                .setCanLoop(false);

        mConvenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == IMAGE_LIST.size() - 1) {
                    // 退栈返回LauncherDelegate, 但是要保证注册页面布局显现
                    LattePreference.setAppFlag(LauncherTag.IS_FIRST_LAUNCHER_APP.name(), true);
                    setResult("register");
                    pop();
                }
            }
        });
    }

    private void setResult(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("overback", title);
        setFragmentResult(RESULT_OK, bundle);
    }


    @Override
    public boolean onBackPressedSupport() {

        // 退栈返回LauncherDelegate, 但是要保证登陆页面布局显现
        setResult("login");
        return super.onBackPressedSupport();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /*
    @Override
    public void onItemClick(int position) {
        // 如果点击的是最后一个
        if(position == IMAGE_LIST.size() - 1) {



            // 检查用户是否已经登陆
        }
    }
    */

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
