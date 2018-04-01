package com.ali.latte.rx;

import android.content.Context;

import com.ali.latte.net.RestCreator;
import com.ali.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by 澄鱼 on 2018/3/17.
 */

public class RxRestClientBuilder {

    private  String mUrl;
    private  static final Map<String, Object> PARAMS = RestCreator.geyParams();
    private  RequestBody mBody = null;
    private  Context mContext = null;
    private  LoaderStyle mLoaderStyle = null;
    private  File mFile = null;

    // 只允许同包的RestClinet new新对象
    RxRestClientBuilder() {
    }

    public final RxRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder PARAMS(WeakHashMap<String, Object> mPramas) {
        PARAMS.putAll(mPramas);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RxRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RxRestClientBuilder load(Context context, LoaderStyle mLoaderStyle) {
        this.mContext = context;
        this.mLoaderStyle = mLoaderStyle;
        return this;
    }

    public final RxRestClientBuilder load(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final RxRestClient build() {
        return new RxRestClient(mUrl, PARAMS, mBody, mContext, mLoaderStyle, mFile);
    }
}
