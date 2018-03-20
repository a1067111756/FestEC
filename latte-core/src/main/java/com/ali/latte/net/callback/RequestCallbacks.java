package com.ali.latte.net.callback;

import android.os.Handler;
import android.util.Log;

import com.ali.latte.ui.LatteLoader;
import com.ali.latte.ui.LoaderStyle;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 澄鱼 on 2018/3/17.
 */

public class RequestCallbacks implements Callback<String> {

    private final IRequset REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;

    private static final Handler HANDLER = new Handler();

    public RequestCallbacks(IRequset requset, ISuccess success, IFailure failure, IError error, LoaderStyle loaderStyle) {
        this.REQUEST = requset;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = loaderStyle;

    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {

        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    Log.i("bbb", response.body());
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {

                ERROR.onError(response.code(), response.message());
            }
        }

        if (LOADER_STYLE != null) {
            stopLoad();
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) {
            Log.i("bbb", t.toString());
            FAILURE.onFailure();
        }

        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        if (LOADER_STYLE != null) {
            stopLoad();
        }
    }

    private void stopLoad() {
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatteLoader.cancelLoading();
            }
        }, 1000);

    }
}
