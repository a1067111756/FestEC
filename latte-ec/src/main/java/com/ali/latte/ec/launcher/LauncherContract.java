package com.ali.latte.ec.launcher;

import com.ali.latte.ec.mvp.BasePresenter;
import com.ali.latte.ec.mvp.BaseView;

/**
 * Created by 澄鱼 on 2018/4/8.
 */

public interface LauncherContract {

    interface View extends BaseView<Presenter>{

        void initSplash();

        void showSignIn();

        void showSignUp();
    }

    interface Presenter extends BasePresenter{


    }
}
