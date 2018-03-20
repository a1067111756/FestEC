package com.ali.latte.net;

import android.content.Context;
import android.util.Log;

import com.ali.latte.app.ConfigType;
import com.ali.latte.app.Latte;
import com.ali.latte.net.callback.IError;
import com.ali.latte.net.callback.IFailure;
import com.ali.latte.net.callback.IRequset;
import com.ali.latte.net.callback.ISuccess;
import com.ali.latte.net.callback.RequestCallbacks;
import com.ali.latte.ui.LatteLoader;
import com.ali.latte.ui.LoaderStyle;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 澄鱼 on 2018/3/17.
 * 网络请求具体实现类
 */

public class RestClient {
    private final String URL;
    private static final Map<String, Object> PARAMS = RestCreator.geyParams();
    private final IRequset REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final Context CONTEXT;
    private final LoaderStyle LOADER_STYLE;

    public RestClient(String url,
                      Map<String, Object> params,
                      IRequset requset,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      Context context,
                      LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = requset;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private Callback<String> getRequstCallBack() {
        return new RequestCallbacks(REQUEST, SUCCESS, FAILURE, ERROR, LOADER_STYLE);
    }

    private void request(HttpMethod method) {

        RestService service = RestCreator.getRestService();

        Call<String> call = null;

        if(REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null) {
            LatteLoader.showLoding(CONTEXT, LOADER_STYLE);
        }

        switch (method) {

            case GET:
                call = service.get(URL, PARAMS);
                break;

            case POST:
                call = service.post(URL, PARAMS);
                break;

            case POST_RAW:
                break;

            case PUT:
                call = service.put(URL, PARAMS);
                break;

            case PUT_RAW:
                break;

            case DELETE:
                call = service.delete(URL, PARAMS);
                break;

            case UNLOAD:
                break;

            default:
                break;
        }

       if (call != null) {
            call.enqueue(getRequstCallBack());
       }

    }

    public void get() {
        request(HttpMethod.GET);
    }

    public void post() {
        request(HttpMethod.POST);
    }

    public void put() {
        request(HttpMethod.PUT);
    }

    public void delate() {
        request(HttpMethod.DELETE);
    }
}
