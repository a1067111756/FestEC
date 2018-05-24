package com.ali.latte.delegates.web;

import android.webkit.JavascriptInterface;

import com.ali.latte.delegates.web.event.Event;
import com.ali.latte.delegates.web.event.EventManager;

import com.alibaba.fastjson.JSON;

/**
 * Created by 澄鱼 on 2018/5/13.
 */

public class LatteWebInterface {

    private final WebDelegate DELEGATE;

    public LatteWebInterface(WebDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    public static LatteWebInterface create(WebDelegate DELEGATE) {
        return new LatteWebInterface(DELEGATE);
    }

    @SuppressWarnings("unused")
    @JavascriptInterface
    public String event(String params) {
        final String action = JSON.parseObject(params).getString("action");
        final Event event = EventManager.getInstance().createEvent(action);
        if (event != null) {
            event.setAction(action);
            event.setDelegate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            return event.execute(params);
        }
        return null;
    }
}
