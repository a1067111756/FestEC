package com.ali.latte.ec.launcher;

import com.ali.latte.app.AccountManager;
import com.ali.latte.ec.database.DatabaseManager;
import com.ali.latte.ec.database.UserProfile;
import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by 澄鱼 on 2018/4/6.
 * 用于登陆注册请求回调处理
 */

public class SignHandler {

    public static void onSignUp(String response, ISignListener mSignListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId =profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        // 将注册成功后，服务器返回的用户信息存入数据库
        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManager.getInstance().getmDao().insert(profile);

        // 设置登陆状态为已登陆，并回调注册成功回调
        AccountManager.setSignStatu(true);
        mSignListener.onSignUpSuccess();
    }

    public static void onSignIn(ISignListener mSignListener) {

        // 设置登陆状态为已登陆，并回调注册成功回调
        AccountManager.setSignStatu(true);
        mSignListener.onSignInSuccess();

    }
}
