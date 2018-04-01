package com.ali.latte.net;

import android.util.Log;

import com.ali.latte.app.ConfigType;
import com.ali.latte.app.Latte;
import com.ali.latte.rx.RxRestService;

import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 澄鱼 on 2018/3/17.
 */

public class RestCreator {

    public static RestService getRestService () {
        return RestServiceHolder.REST_SERVICE;
    }

    public static RxRestService getRxRestService () {
        return RestServiceHolder.RX_REST_SERVICE;
    }

    public static WeakHashMap<String, Object> geyParams() {
        return new WeakHashMap<>();
    }

    private static final class RetorfitHolder {
        private static final String BASE_URL = (String) Latte.getConfigurations().get(ConfigType.API_HOST.name());
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    // 额外处理， eg:okhttp的惰性请求
    private static final class OkHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = (ArrayList<Interceptor>) Latte.getConfigurations().get(ConfigType.INTERCEPTORS);

        // 将配置文件设置的拦截器添加到OkHttpClient中
        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null || !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    // 将Service业务接口单独抽出封装成单例
    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE =
                RetorfitHolder.RETROFIT_CLIENT.create(RestService.class);

        private static final RxRestService RX_REST_SERVICE =
                RetorfitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }
}
