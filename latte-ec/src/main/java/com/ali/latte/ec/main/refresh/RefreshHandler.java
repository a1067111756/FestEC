package com.ali.latte.ec.main.refresh;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;

import com.ali.latte.app.Latte;

/**
 * Created by 澄鱼 on 2018/5/3.
 * 上 / 下拉刷新助手
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener{

    private final SwipeRefreshLayout REFRESH_LAYOUT;

    public RefreshHandler(SwipeRefreshLayout mRefreshLayout) {
        this.REFRESH_LAYOUT = mRefreshLayout;
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

}
