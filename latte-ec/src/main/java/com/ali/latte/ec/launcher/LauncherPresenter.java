package com.ali.latte.ec.launcher;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import com.ali.latte.app.AccountManager;
import com.ali.latte.ui.launcher.IUserChecker;
import com.ali.latte.utils.log.LatteLogger;
import com.ali.latte.utils.storage.LattePreference;

/**
 * Created by 澄鱼 on 2018/4/9.
 */

public class LauncherPresenter implements LauncherContract.Presenter{

    private LauncherContract.View mView;
    private Context mContext;
    private boolean IS_SHOW_SIGN_IN = false;


    public LauncherPresenter(Context context, LauncherContract.View view) {
        this.mView = view;
        this.mContext = context;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        initSplash();
    }


    /* show Splash page */
    @Override
    public void initSplash() {

        //1. init splash bakground
        mView.splashBgInit().addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                //2. whether show gaide page
                mView.showArrow().addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mView.hideArrow();
                        checkIsShowGaide();
                    }
                });

            }
        });
    }

    /* show Sign In page */
    @Override
    public void showSignInPage() {

        mView.moveLogo().addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mView.showMongol();
                mView.showLogin();

            }
        });

        IS_SHOW_SIGN_IN = true;
    }

    /* show Sign Up page */
    @Override
    public void showSignUpPage() {
        mView.showRegister();
        IS_SHOW_SIGN_IN = false;
    }

    @Override
    public void hideSignUpPage() {
        mView.hideRegister();
        IS_SHOW_SIGN_IN = true;
    }

    @Override
    public boolean getIsShowSignIn() {
        return IS_SHOW_SIGN_IN;
    }

    // 2. whether first enter app, if not enter gaidePage, else enter sign In / Up page
    private void checkIsShowGaide() {

        // go to gaide page
        //if (!LattePreference.getAppFlag(LauncherTag.IS_FIRST_LAUNCHER_APP.name())) {
        if (false) {

            mView.goToGaidePage();

        } else {

            AccountManager.checkAccount(new IUserChecker() {
                // go to signIn page

                @Override
                public void onSignIn() {

                    // 跳转到主页
                    /*
                    if (mLanucherListener != null) {
                        mLanucherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                    */
                }

                // go to signUp page
                @Override
                public void onNotSignIn() {

                    // 跳转到登陆页
                    showSignInPage();
                    /*
                    if (mLanucherListener != null) {
                        mLanucherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                    */

                }
            });
        }
    }

}
