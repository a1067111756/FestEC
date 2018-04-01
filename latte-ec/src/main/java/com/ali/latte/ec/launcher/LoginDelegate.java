package com.ali.latte.ec.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.ec.R;
import com.ali.latte.ec.R2;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 澄鱼 on 2018/3/25.
 */

public class LoginDelegate extends LatteDelagate {


    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword;
    Unbinder unbinder;

    @OnClick(R2.id.btn_sign_in)
    void onClickLogin() {
        checkForm();
    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickRegister() {
        start(new RegisterDelegate());
    }

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeiXinLogin() {

    }


    private void checkForm() {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式!");
        } else {
            mEmail.setError(null);
        }
        if (password.isEmpty() || password.length() < 6 || password.length() > 13) {
            mPassword.setError("请输入大于6位小于13位的字母数字组合!");
        } else {
            mPassword.setError(null);
        }

        isPass = true;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

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
