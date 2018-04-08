package com.ali.latte.ec.launcher;

/**
 * Created by 澄鱼 on 2018/4/9.
 */

public class LauncherPresenter implements LauncherContract.Presenter{

    private LauncherContract.View mView;

    public LauncherPresenter(LauncherContract.View view) {
        this.mView = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void initSplash() {

    }

    @Override
    public void showSignIn() {

    }

    @Override
    public void showSignUp() {

    }
}
