package com.ali.latte.ec.launcher;

import android.animation.AnimatorSet;

import com.ali.latte.ec.mvp.BasePresenter;
import com.ali.latte.ec.mvp.BaseView;

/**
 * Created by 澄鱼 on 2018/4/8.
 */

public interface LauncherContract {

    interface View extends BaseView<Presenter>{

        AnimatorSet splashBgInit();

        AnimatorSet showArrow();

        AnimatorSet moveLogo();

        void showLogin();

        void hideLogin();

        void showRegister();

        void hideRegister();

        void showMongol();

        void hideArrow();

        void goToGaidePage();
    }

    interface Presenter extends BasePresenter{

        public boolean IS_SHOW_SIGN_IN = false;

        void initSplash();

        void showSignInPage();

        void showSignUpPage();

        void hideSignUpPage();

        boolean getIsShowSignIn();

    }
}
