package com.ali.latte.wechat.template;

import com.ali.latte.activities.ProxyActivity;
import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.wechat.BaseWXActivity;
import com.ali.latte.wechat.BaseWXEntryActivity;
import com.ali.latte.wechat.LatteWeChat;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

/**
 * Created by 澄鱼 on 2018/4/23.
 */

public class WXEntryTemplate extends BaseWXEntryActivity{

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        // 无退出动画
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onSignSuuccess(String userInfo) {
        LatteWeChat.getInstance().getmSignInCallback().onSignSuccess(userInfo);
    }
}
