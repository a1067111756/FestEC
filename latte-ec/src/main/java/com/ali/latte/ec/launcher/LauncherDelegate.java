package com.ali.latte.ec.launcher;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.transition.Fade;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;


import com.ali.latte.ui.launcher.ILanucherListener;
import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.ec.R;
import com.ali.latte.ec.R2;
import com.ali.latte.net.RestClient;
import com.ali.latte.net.callback.IError;
import com.ali.latte.net.callback.IFailure;
import com.ali.latte.net.callback.ISuccess;
import com.ali.latte.utils.log.LatteLogger;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.dd.morphingbutton.MorphingAnimation;
import com.dd.morphingbutton.MorphingButton;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by 澄鱼 on 2018/3/24.
 */

public class LauncherDelegate extends LatteDelagate implements LauncherContract.View{

    Unbinder unbinder;
    /* splash */
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

    /* logo & arrow & mongol */
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
    @BindView(R2.id.layout_mongol)
    AppCompatImageView layoutMongol;

    /* login */
    @BindView(R2.id.but_signin)
    AppCompatButton butLogin;
    @BindView(R2.id.btn_signup)
    AppCompatButton butRegister;
    @BindView(R2.id.img_header_login)
    AppCompatImageView imgHeaderLogin;
    @BindView(R2.id.textinput_account)
    TextInputLayout textinputAccount;
    @BindView(R2.id.textinput_password)
    TextInputLayout textinputPassword;
    @BindView(R2.id.tv_account_name)
    AppCompatTextView tvAccountName;
    @BindView(R2.id.layout_bg_login)
    RelativeLayout layoutBgLogin;
    @BindView(R2.id.layout_login)
    RelativeLayout layoutLogin;

    /* register */
    @BindView(R2.id.layout_register)
    RelativeLayout layoutRegister;
    @BindView(R2.id.input_account)
    TextInputEditText mAccount;
    @BindView(R2.id.input_password)
    TextInputEditText mPassword;
    @BindView(R2.id.input_repassword)
    TextInputEditText mRePassword;
    @BindView(R2.id.input_yanzhen)
    TextInputEditText mYanzhen;
    @BindView(R2.id.but_show_sign_up_page)
    MorphingButton mSignUpButton;


    private static final int RET_GAIDE_PAGE = 0;
    private static final int RET_MAIN_PAGE = 0;
    private ISignListener mSignListener = null;
    private ILanucherListener mLanucherListener = null;
    private LauncherContract.Presenter presenter = null;

    /* show sign up page */
    @OnClick(R2.id.but_show_sign_up_page)
    void showSignUpOnClick() {

        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(500)
                .cornerRadius(110) // 56 dp
                .width(110) // 56 dp
                .height(110) // 56 dp
                .color(R.color.colorAccent) // normal state color
                .colorPressed(R.color.colorAccent) // pressed state color
                .animationListener(new MorphingAnimation.Listener() {
                    @Override
                    public void onAnimationEnd() {

                        int screenWidth = ScreenUtils.getScreenWidth();
                        int screenHight = ScreenUtils.getScreenHeight();
                        int []location=new int[2];
                        mSignUpButton.getLocationOnScreen(location);
                        int currentX =location[0];//获取当前位置的横坐标
                        int currentY =location[1];//获取当前位置的纵坐标


                        mSignUpButton.animate()
                                .translationXBy(0).translationX(screenWidth - currentX)
                                .translationYBy(0).translationY(currentY - screenHight)
                                .setDuration(500)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                    }
                                });
                    }
                });

        mSignUpButton.morph(circle);
    }

    /* show sign in page */
    @OnClick(R2.id.link_login)
    void showSignInOnClick() {

        presenter.showSignInPage();
    }

    /* register account */
    @OnClick(R2.id.btn_signup)
    void sigUpOnClick() {

        if (checkSignUpForm()) {
            RestClient.builder()
                    .url("http://192.168.199.104/RestServer/data/user_profile.json")
                    .params("account", mAccount.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .params("mRePassword", mRePassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("USER_PROFILE", response);
                            SignHandler.onSignUp(response, mSignListener);
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            LatteLogger.json("USER_PROFILE", msg);
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            LatteLogger.json("USER_PROFILE", "onFailure");
                        }
                    })
                    .build()
                    .post();
        }
    }

    /* login account */
    @OnClick(R2.id.but_signin)
    public void sigIpOnClick() {

        if (checkSignInForm()) {
            SignHandler.onSignIn(mSignListener);
        }
        // 进入主页
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mSignListener = (ISignListener) activity;
            mLanucherListener = (ILanucherListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        new LauncherPresenter(getProxyActivity(),this).start();
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

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == RET_GAIDE_PAGE && resultCode == RESULT_OK ) {
            if (data.getString("overback").equals("register")) {
                presenter.showSignUpPage();
            } else {
                presenter.showSignInPage();
            }
        } else if (requestCode == RET_MAIN_PAGE && resultCode == RESULT_OK ) {}
    }

    @Override
    public boolean onBackPressedSupport() {

        if (presenter.getIsShowSignIn()) {
            return super.onBackPressedSupport();
        } else {
            presenter.hideSignUpPage();
            presenter.showSignInPage();
            return true;
        }
    }

    public boolean checkSignUpForm() {
        final String account = mAccount.getText().toString();
        final String yanzhen = mYanzhen.getText().toString();
        final String password = mPassword.getText().toString();
        final String repassword = mRePassword.getText().toString();

        boolean isPass = true;

        if (account.isEmpty()
                || (!Patterns.EMAIL_ADDRESS.matcher(account).matches()
                && !Patterns.PHONE.matcher(account).matches())) {
            mAccount.setError("请输入邮箱或者电话号码作为账号!");
            isPass = false;
        } else {
            mAccount.setError(null);
        }
        if (password.isEmpty() || password.length() < 6 || password.length() > 13) {
            mPassword.setError("请输入大于6位小于13位的字母数字组合!");
            isPass = false;
        } else {
            mPassword.setError(null);
        }
        if (repassword.isEmpty() || !repassword.equals(password)) {
            mRePassword.setError("密码验证错误!");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }

        return isPass;
    }

    public boolean checkSignInForm() {

        final String account = textinputAccount.getEditText().getText().toString();
        final String password = textinputPassword.getEditText().getText().toString();

        boolean isPass = true;

        if (account.isEmpty()
                || (!Patterns.EMAIL_ADDRESS.matcher(account).matches()
                && !Patterns.PHONE.matcher(account).matches())) {
            mAccount.setError("请输入邮箱或者电话号码作为账号!");
            isPass = false;
        } else {
            mAccount.setError(null);
        }
        if (password.isEmpty() || password.length() < 6 || password.length() > 13) {
            mPassword.setError("请输入大于6位小于13位的字母数字组合!");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public void setPresenter(LauncherContract.Presenter presenter) {

        this.presenter = presenter;
    }

    @Override
    public AnimatorSet splashBgInit() {

        /* splash enter animation */
        setTranYAnimation(imgWall1, 800, 0, 120f, 0);
        setTranYAnimation(imgWall2, 800, 300, 120f, 0);
        setTranYAnimation(imgWall3, 600, 350, 120f, 0);
        setTranYAnimation(imgWall4, 800, 700, 120f, 0);
        setTranYAnimation(imgWall5, 500, 800, 120f, 0);
        setTranYAnimation(imgWall6, 500, 800, 120f, 0);
        setTranYAnimation(imgLogo, 900, 1800, 120f, 0);
        setTranYAnimation(imgLogoTitle, 800, 2000, 120f, 0);
        AnimatorSet set = setTranYAnimation(imgArrow, 1000, 2200, 100f, 0);

        return set;
    }


    @Override
    public AnimatorSet moveLogo() {

        // translationY img_logo & img_title to parent's top, alpha layoutMongol
        layoutLogin.animate().alphaBy(0).alpha(1).setDuration(300).setStartDelay(300).start();
        layoutLogin.setVisibility(View.VISIBLE);
        imgLogoTitle.animate().translationYBy(0).translationY(-1050f).setDuration(650).setStartDelay(300).start();
        imgLogo.animate().translationYBy(0).translationY(-1050f).setDuration(650).setStartDelay(300).start();
        AnimatorSet set = setTranYAnimation(layoutBgLogin, 400, 700, 700f, 0);

        return set;
    }

    @Override
    public void showLogin() {
        // Login animation
        setTranXAnimation(imgHeaderLogin, 400, 0, 300, 0);
        setTranXAnimation(textinputAccount, 400, 100, 300, 0);
        setTranXAnimation(textinputPassword, 400, 200, 300, 0);
        setTranXAnimation(butLogin, 400, 300, 300, 0);
        setTranXAnimation(mSignUpButton, 400, 400, 300, 0);

    }

    @Override
    public void hideLogin() {

    }

    @Override
    public void showRegister() {


        /* register enter animation */
        layoutLogin.animate().alphaBy(1).alpha(0).setDuration(500).start();
        layoutLogin.setVisibility(View.GONE);
        layoutRegister.setVisibility(View.VISIBLE);

        int centerX = mSignUpButton.getLeft();
        int centerY = mSignUpButton.getBottom();

        float finalRadius = (float) Math.hypot((double) centerX, (double) centerY);
        Animator mCircularReveal = ViewAnimationUtils.createCircularReveal(layoutRegister, centerX, centerY, 0, finalRadius);
        mCircularReveal.setDuration(1000);
        mCircularReveal.start();

        //layoutRegister.animate().alphaBy(0).alpha(1).setDuration(500).setStartDelay(500).start();

    }

    @Override
    public void hideRegister() {

        /* register enter animation */
        layoutRegister.animate().alphaBy(1).alpha(0).setDuration(500).setStartDelay(500).start();
        layoutRegister.setVisibility(View.GONE);
        layoutLogin.animate().alphaBy(0).alpha(1).setDuration(500).start();
        layoutLogin.setVisibility(View.VISIBLE);


    }

    @Override
    public void showMongol() {
        layoutMongol.animate().alpha(0.9f).setDuration(700).start();
    }

    @Override
    public AnimatorSet showArrow() {

        /* logo enter animation */
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

        return set;
    }

    @Override
    public void hideArrow() {
        // disapear imgCircleInside & imgCircleOutside & imgArrow
        imgCircleInside.animate().alphaBy(1f).alpha(0).setDuration(100).start();
        imgCircleOutside.animate().alphaBy(1f).alpha(0).setDuration(100).start();
        imgArrow.animate().alphaBy(1f).alpha(0).setDuration(100).setStartDelay(200).start();
    }


    @Override
    public void goToGaidePage() {
        // 这里是使用SharedElement的用例
        // LOLLIPOP(5.0)系统的 SharedElement支持有 系统BUG， 这里判断大于 > LOLLIPOP
        GaideDelegate guideFragment = new GaideDelegate();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            setExitTransition(new Fade());
            guideFragment.setEnterTransition(new Fade());

            // 25.1.0以下的support包,Material过渡动画只有在进栈时有,返回时没有;
            // 25.1.0+的support包，SharedElement正常
            extraTransaction()
                    .startForResult(guideFragment, RET_GAIDE_PAGE);
        } else {
            startForResult(guideFragment, RET_GAIDE_PAGE);
        }
    }


    private AnimatorSet setTranYAnimation(View view, int duration, int delay, float start, float end) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", start, end);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(duration);
        set.playTogether(animator, animator1);
        set.setStartDelay(delay);
        set.start();

        return set;
    }

    private AnimatorSet setTranXAnimation(View view, int duration, int delay, float start, float end) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", start, end);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(duration);
        set.playTogether(animator, animator1);
        set.setStartDelay(delay);
        set.start();

        return set;
    }

}
