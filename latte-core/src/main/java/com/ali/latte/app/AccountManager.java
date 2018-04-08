package com.ali.latte.app;

import com.ali.latte.ui.launcher.IUserChecker;
import com.ali.latte.utils.storage.LattePreference;

/**
 * Created by 澄鱼 on 2018/4/6.
 */

public class AccountManager {

    private enum SignTag{
        SIGN_TAG
    }

    // 保存用户登陆状态，登陆后调用
    public static void setSignStatu(boolean statu) {
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), statu);
    }

    private static boolean isSignIn() {
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker userChecker) {
        if (isSignIn()) {
            userChecker.onSignIn();
        } else {
            userChecker.onNotSignIn();
        }
    }
}
