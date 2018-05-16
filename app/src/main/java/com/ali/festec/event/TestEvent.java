package com.ali.festec.event;

import android.webkit.WebView;
import android.widget.Toast;

import com.ali.latte.delegates.web.event.Event;
import com.ali.latte.utils.log.LatteLogger;

/**
 * Created by 澄鱼 on 2018/5/15.
 */

public class TestEvent extends Event {

    @Override
    public String execute(String params) {

        Toast.makeText(getContext(), params, Toast.LENGTH_LONG).show();


        //调用javaScript
        if (getAction().equals("test")) {
            final WebView webView = getWebView();
            webView.post(new Runnable() {
                @Override
                public void run() {
                    LatteLogger.d("chengyu", getAction().toString());
                    webView.evaluateJavascript("naticeCall()", null);
                }
            });
        }
        return null;
    }
}
