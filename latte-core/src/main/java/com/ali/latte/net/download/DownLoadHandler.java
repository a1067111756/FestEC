package com.ali.latte.net.download;

import android.os.AsyncTask;

import com.ali.latte.net.RestCreator;
import com.ali.latte.net.callback.IError;
import com.ali.latte.net.callback.IFailure;
import com.ali.latte.net.callback.IRequset;
import com.ali.latte.net.callback.ISuccess;

import java.net.URL;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 澄鱼 on 2018/3/22.
 */

public class DownLoadHandler {

    private  final String mUrl;
    private  static final Map<String, Object> PARAMS = RestCreator.geyParams();
    private  final IRequset mRequest;
    private  final String  mDownload_dir;
    private  final String  mExtension;
    private  final String  mName;
    private  final ISuccess mSuccess;
    private  final IFailure mFailure ;
    private  final IError mError;

    public DownLoadHandler(String mUrl,
                           IRequset mRequest,
                           String mDownload_dir,
                           String mExtension,
                           String mName,
                           ISuccess mSuccess,
                           IFailure mFailure,
                           IError mError) {
        this.mUrl = mUrl;
        this.mRequest = mRequest;
        this.mDownload_dir = mDownload_dir;
        this.mExtension = mExtension;
        this.mName = mName;
        this.mSuccess = mSuccess;
        this.mFailure = mFailure;
        this.mError = mError;
    }

    public void handleDownload() {
        if (mRequest != null) {
            mRequest.onRequestStart();
        }

        RestCreator.getRestService().download(mUrl, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        final ResponseBody responseBody = response.body();
                        final SaveFileTask task = new SaveFileTask(mRequest, mSuccess);
                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mDownload_dir, mExtension, responseBody, mName);

                        // 这里一定要注意判断，否则文件下载不全
                        if (task.isCancelled()) {
                            if (mRequest != null) {
                                mRequest.onRequestEnd();
                            }
                        } else {
                            if (mError != null) {
                                mError.onError(response.code(), response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        if (mFailure != null) {
                            mFailure.onFailure();
                        }
                    }
                });
    }

}
