package com.ali.latte.delegates.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.ali.latte.app.ConfigKeys;
import com.ali.latte.app.Latte;
import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.delegates.web.route.RountKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by 澄鱼 on 2018/5/13.
 */

public abstract class WebDelegate extends LatteDelagate implements IWebViewInitializer{

    private WebView mWebView = null;
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    private String mUrl = null;
    private boolean mIsWebViewAbailable = false;
    private LatteDelagate mTopDelegate = null;

    public WebDelegate() {

    }

    public abstract IWebViewInitializer setInitializer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mUrl = args.getString(RountKeys.URL.name());
        initWebView();
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        } else {
            final  IWebViewInitializer initializer = setInitializer();
            if (initializer != null) {
                final WeakReference<WebView> webViewWeakReference =
                        new WeakReference<WebView>(new WebView(getContext()), WEB_VIEW_QUEUE);

                mWebView = webViewWeakReference.get();
                mWebView = initializer.initWebView(mWebView);
                mWebView.setWebViewClient(initializer.initWebViewClient());
                mWebView.setWebChromeClient(initializer.initWebChromeClient());
                // 注入javascript接口对象
                final String name = (String) Latte.getConfigurations().get(ConfigKeys.JAVASCRIPT_INTERFACE.name());
                mWebView.addJavascriptInterface(LatteWebInterface.create(this), name);
                mIsWebViewAbailable = true;

            } else {
                throw new NullPointerException("Initializer is Null!");
            }
        }
    }

    public void setTopDelegate(LatteDelagate delegate) {
        mTopDelegate = delegate;
    }

    public LatteDelagate getTopDelegate() {
        if (mTopDelegate == null) {
            mTopDelegate = this;
        }
        return mTopDelegate;
    }

    public String getUrl() {
        if (mWebView == null) {
            throw new NullPointerException("WebView is null!");
        }
        return mUrl;
    }

    public WebView getWebView() {
        if (mWebView == null) {
            throw new NullPointerException("WebView is null");
        }
        return mIsWebViewAbailable ? mWebView: null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAbailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        }
    }
}
