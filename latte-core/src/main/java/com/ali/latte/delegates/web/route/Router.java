package com.ali.latte.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.delegates.web.WebDelegate;
import com.ali.latte.delegates.web.WebDelegateImpl;
import com.ali.latte.utils.log.LatteLogger;

import java.net.FileNameMap;

/**
 * Created by 澄鱼 on 2018/5/13.
 */

public class Router {

    private Router(){};

    private static class Holder {
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    public final boolean handleWebUrl(WebDelegate delegate, String url) {

        // 如果是电话协议
        if (url.contains("tel:")) {
            callPhone(delegate.getContext(), url);
            return true;
        }

        final LatteDelagate topDelegate = delegate.getTopDelegate();

        final WebDelegateImpl webDelegate = WebDelegateImpl.create(url);

        topDelegate.start(webDelegate);

        return true;
    }

    public void loadPage(WebView webView, String url) {
        // 如果是已经拼接好了的http链接或是本地链接 直接加载， 不用再去拼接
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    public final void loadPage(WebDelegate delegate, String url) {
        loadPage(delegate.getWebView(), url);
    }

    private void loadWebPage(WebView webView, String url) {
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw  new NullPointerException("webView is null!");
        }
    }

    private void loadLocalPage(WebView webView, String url) {
        LatteLogger.d("chengyu", url);
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    private void callPhone(Context mContext, String uri) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(uri);
        intent.setData(data);
        ContextCompat.startActivity(mContext, intent, null);
    }
}
