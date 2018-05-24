package com.ali.latte.ec.main.cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.latte.delegates.bottom.BottomItemDelegate;
import com.ali.latte.ec.R;
import com.ali.latte.ec.R2;
import com.ali.latte.ec.main.sort.SortRecyclerAdapter;
import com.ali.latte.net.RestClient;
import com.ali.latte.net.callback.IError;
import com.ali.latte.net.callback.ISuccess;
import com.ali.latte.ui.recycle.MultipleItemEntity;
import com.ali.latte.utils.log.LatteLogger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 澄鱼 on 2018/5/24.
 */

public class ShopCartDelegate extends BottomItemDelegate implements ISuccess {

    @BindView(R2.id.tv_top_shop_cart_clear)
    AppCompatTextView tvTopShopCartClear;
    @BindView(R2.id.tv_top_shop_cart_delete)
    AppCompatTextView tvTopShopCartDelete;
    @BindView(R2.id.iv_shop_cart)
    RecyclerView mRecyclerView = null;
    Unbinder unbinder;

    private ShopCartAdapter mAdapter = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        RestClient.builder()
                .url("shop_cart.php")
                .load(getContext())
                .success(this)
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        LatteLogger.d("chengyu", msg);
                    }
                })
                .build()
                .get();

    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> data =
                new ShopCartDataConverter()
                        .setJsonData(response)
                        .convert();

        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mAdapter = new ShopCartAdapter(data);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
