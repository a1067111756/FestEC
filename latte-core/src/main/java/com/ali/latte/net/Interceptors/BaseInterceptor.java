package com.ali.latte.net.Interceptors;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 澄鱼 on 2018/3/23.
 * 自定义拦截器， 主要用来测试返回数据
 */

public abstract class BaseInterceptor implements Interceptor{

    // 让子类实现这个方法
    public abstract Response intercept(Chain chain) throws IOException;

    // GET获取请求参数，使用LinkedHashMap来保证数据的有序，
    protected LinkedHashMap<String, String> getUrlParamters(Chain chain) {
        final HttpUrl url = chain.request().url();
        int size = url.querySize();
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for (int i= 0; i < size; i++) {
            params.put(url.queryParameterName(i), url.queryParameterValue(i));
        }
        return params;
    }

    // 通过key值获取value
    protected String getUrlParamters(Chain chain, String key) {
        final Request request = chain.request();
        return request.url().queryParameter(key);
    }

    // POST获取请求参数
    protected LinkedHashMap<String, String> postBodyparamters(Chain chain) {
        final FormBody formBody = (FormBody) chain.request().body();
        int size = formBody.size();
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for (int i= 0; i < size; i++) {
            params.put(formBody.name(i), formBody.value(i));
        }
        return params;
    }

    protected String postBodyparamters(Chain chain, String key) {
        return postBodyparamters(chain).get(key);
    }
}
