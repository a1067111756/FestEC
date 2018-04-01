package com.ali.latte.net;

import android.content.Context;

import com.ali.latte.net.callback.IError;
import com.ali.latte.net.callback.IFailure;
import com.ali.latte.net.callback.IRequset;
import com.ali.latte.net.callback.ISuccess;
import com.ali.latte.net.callback.RequestCallbacks;
import com.ali.latte.net.download.DownLoadHandler;
import com.ali.latte.ui.loader.LatteLoader;
import com.ali.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by 澄鱼 on 2018/3/17.
 * 网络请求具体实现类
 */

public class RestClient {
    private final String URL;
    private static final Map<String, Object> PARAMS = RestCreator.geyParams();
    private final IRequset REQUEST;
    private final String  DOWNLOAD_DIR;
    private final String  EXTENSION;
    private final String  NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final Context CONTEXT;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;

    public RestClient(String url,
                      Map<String, Object> params,
                      IRequset requset,
                      String  download_dir,
                      String  extension,
                      String  name,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      Context context,
                      LoaderStyle loaderStyle,
                      File file) {
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = requset;
        this.DOWNLOAD_DIR = download_dir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = file;
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
                call = service.postRaw(URL, BODY);
                break;

            case PUT:
                call = service.put(URL, PARAMS);
                break;

            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;

            case DELETE:
                call = service.delete(URL, PARAMS);
                break;

            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName());
                call = RestCreator.getRestService().upload(URL, body);
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
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public void delate() {
        request(HttpMethod.DELETE);
    }

    public void upload() {
        if (FILE == null) {
            throw new RuntimeException("params must not be null");
        }
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
        new DownLoadHandler(URL, REQUEST, DOWNLOAD_DIR, EXTENSION, NAME, SUCCESS, FAILURE, ERROR).handleDownload();
    }
}
