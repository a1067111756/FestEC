package com.ali.latte.net.Interceptors;
import android.support.annotation.RawRes;
import com.ali.latte.utils.file.FileUtil;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 澄鱼 on 2018/3/23.
 */

public class DebugInterceptor extends BaseInterceptor{

    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String url, int rawId) {
        this.DEBUG_URL = url;
        this.DEBUG_RAW_ID = rawId;
    }

    // 返回Response
    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    private Response debugResponse(Chain chain, @RawRes int rawId) {
        final String json = FileUtil.getRawFile(rawId);
        return getResponse(chain, json);
    }

    /**
     * 查询请求参数，如果包含了我们设定的url，则进行拦截
     * 返回我们设定在/res/raw目录的jason数据
     * 没有则原样返回
     * @param chain
     * @return
     */
    @Override
    public Response intercept(Chain chain) throws IOException{
        final String url = chain.request().url().toString();
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID);
        }
        return chain.proceed(chain.request());
    }
}
