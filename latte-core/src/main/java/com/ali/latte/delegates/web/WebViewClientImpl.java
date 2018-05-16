package com.ali.latte.delegates.web;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ali.latte.app.ConfigKeys;
import com.ali.latte.app.Latte;
import com.ali.latte.delegates.web.route.Router;
import com.ali.latte.ui.loader.LatteLoader;
import com.ali.latte.utils.log.LatteLogger;

/**
 * Created by 澄鱼 on 2018/5/13.
 */

public class WebViewClientImpl extends WebViewClient{

    private final WebDelegate DELEGATE;
    private IWebPageLoadListner mIWebPageLoadListner = null;
    private final Handler HANDLER = (Handler) Latte.getConfigurations().get(ConfigKeys.HANDLER.name());

    public WebViewClientImpl(WebDelegate delegate){
        this.DELEGATE = delegate;
    }

    public void setIWebPageLoadListner(IWebPageLoadListner mIWebPageLoadListner) {
        this.mIWebPageLoadListner = mIWebPageLoadListner;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("chengyu", url);
        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIWebPageLoadListner != null) {
            mIWebPageLoadListner.onLoadStart();
        }
        LatteLoader.showLoding(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mIWebPageLoadListner != null) {
            mIWebPageLoadListner.onLoadEnd();
        }
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatteLoader.cancelLoading();
            }
        }, 1000);

    }
}
