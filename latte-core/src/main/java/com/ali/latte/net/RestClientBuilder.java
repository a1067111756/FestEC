package com.ali.latte.net;

import android.content.Context;

import com.ali.latte.net.callback.IError;
import com.ali.latte.net.callback.IFailure;
import com.ali.latte.net.callback.IRequset;
import com.ali.latte.net.callback.ISuccess;
import com.ali.latte.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by 澄鱼 on 2018/3/17.
 */

public class RestClientBuilder {

    private  String mUrl;
    private  static final Map<String, Object> PARAMS = RestCreator.geyParams();
    private  IRequset mRequest = null;
    private  String  mDownload_dir = null;
    private  String  mExtension = null;
    private  String  mName = null;
    private  ISuccess mSuccess = null;
    private  IFailure mFailure = null;
    private  IError mError = null;
    private  RequestBody mBody = null;
    private  Context mContext = null;
    private  LoaderStyle mLoaderStyle = null;
    private  File mFile = null;

    // 只允许同包的RestClinet new新对象
    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder PARAMS(WeakHashMap<String, Object> mPramas) {
        PARAMS.putAll(mPramas);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder onRequst(IRequset iRequset) {
        this.mRequest = iRequset;
        return this;
    }

    public final RestClientBuilder dir(String dir) {
        this.mDownload_dir = dir;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }

    public final RestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mSuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mError = iError;
        return this;
    }

    public final RestClientBuilder load(Context context, LoaderStyle mLoaderStyle) {
        this.mContext = context;
        this.mLoaderStyle = mLoaderStyle;
        return this;
    }

    public final RestClientBuilder load(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mRequest, mDownload_dir, mExtension, mName, mSuccess,
                mFailure, mError, mBody, mContext, mLoaderStyle, mFile);
    }
}
