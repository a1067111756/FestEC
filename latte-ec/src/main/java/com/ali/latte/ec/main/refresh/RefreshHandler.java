package com.ali.latte.ec.main.refresh;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.ali.latte.app.Latte;
import com.ali.latte.ec.main.index.IndexDataConverter;
import com.ali.latte.net.RestClient;
import com.ali.latte.net.callback.IError;
import com.ali.latte.net.callback.IFailure;
import com.ali.latte.net.callback.ISuccess;
import com.ali.latte.ui.recycle.DataConverter;
import com.ali.latte.ui.recycle.MultipleItemEntity;
import com.ali.latte.ui.recycle.MultipleRecyleAdatper;
import com.ali.latte.utils.log.LatteLogger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

/**
 * Created by 澄鱼 on 2018/5/3.
 * 上 / 下拉刷新助手
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener{

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLEVIEW;
    private MultipleRecyleAdatper mAdapter = null;
    private final DataConverter CONVERTER;


    public RefreshHandler(SwipeRefreshLayout mRefreshLayout, RecyclerView mRecycleview, DataConverter mConverter, PagingBean bean) {
        this.REFRESH_LAYOUT = mRefreshLayout;
        this.RECYCLEVIEW = mRecycleview;
        this.CONVERTER = mConverter;
        this.BEAN = bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);

    }

    public static RefreshHandler create(SwipeRefreshLayout mRefreshLayout, RecyclerView mRecycleview, DataConverter mConverter) {
        return new RefreshHandler(mRefreshLayout, mRecycleview, mConverter, new PagingBean());
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 进行一些网络请求
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);

    }

    public void firstPage(String url) {
        RestClient
                .builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));
                        // 设置Adapter
                        mAdapter = MultipleRecyleAdatper.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLEVIEW);
                        RECYCLEVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        LatteLogger.d("Refresh", "failer");
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        LatteLogger.d("Refresh", msg.toString());
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
