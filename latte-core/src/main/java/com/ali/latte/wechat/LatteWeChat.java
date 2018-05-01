package com.ali.latte.wechat;

import android.app.Activity;

import com.ali.latte.app.ConfigKeys;
import com.ali.latte.app.Latte;
import com.ali.latte.wechat.callbacks.IWeChatSignInCallBack;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by 澄鱼 on 2018/4/23.
 */

public class LatteWeChat {

    public static final String APP_ID = (String) Latte.getConfigurations().get(ConfigKeys.WX_CHAT_APP_ID);
    public static final String APP_SECRET = (String) Latte.getConfigurations().get(ConfigKeys.WX_CHAT_APP_SECRET);
    private IWeChatSignInCallBack mSignInCallback = null;
    private final IWXAPI WXAPI;

    private static final class Holder {
        private static final LatteWeChat INSTANCE = new LatteWeChat();
    }

    public static LatteWeChat getInstance() {
        return Holder.INSTANCE;
    }

    private LatteWeChat () {
        final Activity activity = (Activity) Latte.getConfigurations().get(ConfigKeys.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        WXAPI.registerApp(APP_ID);
    }

    public final IWXAPI getWXAPI() {
        return WXAPI;
    }

    public LatteWeChat onSignInSuccess(IWeChatSignInCallBack callback) {
        this.mSignInCallback = callback;
        return this;
    }

    public IWeChatSignInCallBack getmSignInCallback() {
        return mSignInCallback;
    }

    public final void signIn() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);
    }
}
