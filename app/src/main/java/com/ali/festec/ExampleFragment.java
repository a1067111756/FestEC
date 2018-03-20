package com.ali.festec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.net.RestClient;
import com.ali.latte.net.callback.IError;
import com.ali.latte.net.callback.IFailure;
import com.ali.latte.net.callback.ISuccess;

/**
 * Created by 澄鱼 on 2018/3/17.
 */

public class ExampleFragment extends LatteDelagate{
    @Override
    public Object setLayout() {
        return R.layout.activity_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        testRestClient();
    }

    private void testRestClient() {
        RestClient.builder()
                .url("https://www.cnblogs.com/whoislcj/p/5539239.html")
                .load(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.i("bbb", msg);
                    }
                })
                .build()
                .get();
    }
}
