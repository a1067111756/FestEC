package com.ali.latte.net.Interceptors;

import com.ali.latte.utils.storage.LattePreference;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 澄鱼 on 2018/5/20.
 */

public class AddCookieInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 拦截请求
        final Request.Builder builder = chain.request().newBuilder();
        Observable
                .just(LattePreference.getCustomAppProfile("cookie"))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String cookie) throws Exception {
                        // 给原生API请求附带上WebView拦截下来的Cookie
                        builder.addHeader("Cookie", cookie);
                    }
                });

        return chain.proceed(builder.build());
    }
}
