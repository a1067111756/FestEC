package com.ali.latte.wechat;

import com.ali.latte.net.RestClient;
import com.ali.latte.net.callback.IError;
import com.ali.latte.net.callback.IFailure;
import com.ali.latte.net.callback.ISuccess;
import com.ali.latte.utils.log.LatteLogger;
import com.ali.latte.wechat.BaseWXActivity;
import com.ali.latte.wechat.LatteWeChat;
import com.alibaba.fastjson.JSON;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.json.JSONObject;

/**
 * Created by 澄鱼 on 2018/5/1.
 */

public abstract class BaseWXEntryActivity extends BaseWXActivity{

    // 用户登陆成功后回调
    protected abstract void onSignSuuccess(String userInfo);

    // 微信发送请求到第三方应用
    @Override
    public void onReq(BaseReq baseReq) {

    }

    //  第三方应用发送请求到微信后的回调
    @Override
    public void onResp(BaseResp baseResp) {
        final String code = ((SendAuth.Resp)baseResp).code;
        final StringBuilder authUrl = new StringBuilder();
        authUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(LatteWeChat.APP_ID)
                .append("&secret=")
                .append(LatteWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        LatteLogger.d("authUrl", authUrl.toString());
        getAuth(authUrl.toString());
    }

    // 获取access_token & openId
    private void getAuth(String authUrl) {
        RestClient
                .builder()
                .url(authUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                        final com.alibaba.fastjson.JSONObject authObj = JSON.parseObject(response);
                        final String accessTonken = authObj.getString("access_token");
                        final String openId =authObj.getString("openid");

                        final StringBuilder userInfoUrl = new StringBuilder();
                        userInfoUrl
                                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessTonken)
                                .append("&openid=")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");
                        LatteLogger.d("userInfoUrl", userInfoUrl.toString());
                        getUerInfo(userInfoUrl.toString());
                    }
                })
                .build()
                .get();
    }

    // 获取真正的用户信息
    private void getUerInfo(String userInfoUrl) {
        RestClient
                .builder()
                .url(userInfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        onSignSuuccess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}
