package com.ali.latte.ec.launcher;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.transition.Fade;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.ec.R;
import com.ali.latte.ec.R2;
import com.ali.latte.ui.launcher.ScrollLauncherTag;
import com.ali.latte.utils.storage.LattePreference;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by 澄鱼 on 2018/3/24.
 */

public class LauncherDelegate extends LatteDelagate {


    Unbinder unbinder;
    @BindView(R2.id.img_wall1)
    AppCompatImageView imgWall1;
    @BindView(R2.id.img_wall2)
    AppCompatImageView imgWall2;
    @BindView(R2.id.img_wall3)
    AppCompatImageView imgWall3;
    @BindView(R2.id.img_wall4)
    AppCompatImageView imgWall4;
    @BindView(R2.id.img_wall5)
    AppCompatImageView imgWall5;
    @BindView(R2.id.img_wall6)
    AppCompatImageView imgWall6;

    @BindView(R2.id.img_logo)
    AppCompatImageView imgLogo;
    @BindView(R2.id.img_logo_title)
    AppCompatTextView imgLogoTitle;

    @BindView(R2.id.img_arrow)
    IconTextView imgArrow;
    @BindView(R2.id.img_circle_outside)
    AppCompatImageView imgCircleOutside;
    @BindView(R2.id.img_circle_inside)
    AppCompatImageView imgCircleInside;

    @BindView(R2.id.but_login)
    AppCompatButton butLogin;
    @BindView(R2.id.but_register)
    AppCompatButton butRegister;
    @BindView(R2.id.img_header_login)
    AppCompatImageView imgHeaderLogin;
    @BindView(R2.id.textinput_account)
    TextInputLayout textinputAccount;
    @BindView(R2.id.textinput_password)
    TextInputLayout textinputPassword;
    @BindView(R2.id.tv_account_name)
    AppCompatTextView tvAccountName;
    @BindView(R2.id.layout_mongol)
    AppCompatImageView layoutMongol;
    @BindView(R2.id.layout_bg_login)
    RelativeLayout layoutBgLogin;


    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {


    }

    // init
    private void initPage() {
        initAnimation();
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

        initPage();
    }

    // initAnimation
    private void initAnimation() {

        //1. init splash background
        setAnimation(imgWall1, 800, 0, 120f, 0);
        setAnimation(imgWall2, 800, 300, 120f, 0);
        setAnimation(imgWall3, 600, 350, 120f, 0);
        setAnimation(imgWall4, 800, 700, 120f, 0);
        setAnimation(imgWall5, 500, 800, 120f, 0);
        setAnimation(imgWall6, 500, 800, 120f, 0);
        setAnimation(imgLogo, 900, 1800, 120f, 0);
        setAnimation(imgLogoTitle, 700, 2200, 120f, 0);
        AnimatorSet set = setAnimation(imgArrow, 1000, 2200, 100f, 0);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                //2. init logo animator
                // arrow animation
                imgCircleInside.animate().alphaBy(0.8f).alpha(1).setDuration(200);
                imgCircleOutside.animate()
                        .alphaBy(0.8f).alpha(1)
                        .scaleXBy(0.9f).scaleX(0.1f)
                        .scaleYBy(0.9f).scaleY(0.1f)
                        .setDuration(200);
                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getProxyActivity(), R.animator.set_splash_gaide_arrow);
                set.setTarget(imgCircleOutside);
                set.setStartDelay(300);
                set.start();
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        // disapear imgCircleInside & imgCircleOutside & imgArrow
                        imgCircleInside.animate().alphaBy(1f).alpha(0).setDuration(100).start();
                        imgCircleOutside.animate().alphaBy(1f).alpha(0).setDuration(100).start();
                        imgArrow.animate().alphaBy(1f).alpha(0).setDuration(100).setStartDelay(200).start();

                        // check whether should go to gaideActivity
                        if (true) {

                            GaideDelegate guideFragment = new GaideDelegate();
                            // 这里是使用SharedElement的用例
                            // LOLLIPOP(5.0)系统的 SharedElement支持有 系统BUG， 这里判断大于 > LOLLIPOP
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                                setExitTransition(new Fade());
                                guideFragment.setEnterTransition(new Fade());
                                guideFragment.setSharedElementReturnTransition(new DetailsTransition());
                                guideFragment.setSharedElementEnterTransition(new DetailsTransition());

                                // 25.1.0以下的support包,Material过渡动画只有在进栈时有,返回时没有;
                                // 25.1.0+的support包，SharedElement正常
                                extraTransaction()
                                        .addSharedElement(imgCircleOutside, "gaide")
                                        .start(guideFragment, SINGLETASK);
                            } else{
                                start(guideFragment, SINGLETASK);
                            }
                        } else {

                            // translationY img_logo & img_title to parent's top, alpha layoutMongol
                            imgLogoTitle.animate().translationYBy(0).translationY(-1005f).setDuration(650).setStartDelay(300).start();
                            layoutMongol.animate().alpha(0.9f).setDuration(700).setStartDelay(300).start();
                            imgLogo.animate().translationYBy(0).translationY(-1005f).setDuration(650).setStartDelay(300).start();
                            AnimatorSet set = setAnimation(layoutBgLogin, 400, 700,  700f, 0);
                            set.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);

                                    // Login animation
                                    setAnimation1(imgHeaderLogin, 400, 0, 300, 0);
                                    setAnimation1(textinputAccount, 400, 100, 300, 0);
                                    setAnimation1(textinputPassword, 400, 200, 300, 0);
                                    setAnimation1(butLogin, 400, 300, 300, 0);
                                    setAnimation1(butRegister, 400, 400, 300, 0);
                                }
                            });
                        }
                    }
                });

            }
        });
    }


    private AnimatorSet setAnimation(View view, int duration, int delay, float start, float end) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", start, end);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(duration);
        set.playTogether(animator, animator1);
        set.setStartDelay(delay);
        set.start();

        return set;
    }

    private AnimatorSet setAnimation1(View view, int duration, int delay, float start, float end) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", start, end);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(duration);
        set.playTogether(animator, animator1);
        set.setStartDelay(delay);
        set.start();

        return set;
    }


    // 是否进入引导页
    private boolean checkIsShowGaide() {

        /*
        if (!) {
            start(new GaideDelegate(), SINGLETASK);
        } else {
            // 检查用户是否登陆了app
            start(new RegisterDelegate());
        }
        */

        return LattePreference.getAppFlag(ScrollLauncherTag.IS_FIRST_LAUNCHER_APP.name());
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
}
