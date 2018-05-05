package com.ali.festec;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ali.latte.activities.ProxyActivity;
import com.ali.latte.app.Latte;
import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.ec.launcher.ISignListener;
import com.ali.latte.ec.launcher.LauncherDelegate;
import com.ali.latte.ui.launcher.ILanucherListener;
import com.ali.latte.ui.launcher.OnLauncherFinishTag;

import qiu.niorgai.StatusBarCompat;

public class MainActivity  extends ProxyActivity implements ISignListener, ILanucherListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // 设置activity， 用于初始化配置微信SDK
        Latte.getConfigurator().withActivity(this);
        // 设置沉浸式状态栏
        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    public LatteDelagate setRootDelegate() {
        return new LauncherDelegate();
    }

    /* 登陆成功回调 */
    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登陆成功", Toast.LENGTH_LONG).show();
    }

    /* 注册成功回调 */
    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
    }

    /* lanchuer结束方式回调 */
    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                Toast.makeText(this, "启动结束， 用户登陆了", Toast.LENGTH_LONG).show();
                break;
            case NOT_SIGNED:
                Toast.makeText(this, "启动结束， 用户没有登陆", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
