package com.ali.latte.ec.main.index;

import android.view.View;

import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.ec.detail.GoodsDetailDelegate;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

/**
 * Created by 澄鱼 on 2018/5/6.
 */

public class IndexItemClickListener extends SimpleClickListener{

    private final LatteDelagate DELAGATE;


    public IndexItemClickListener(LatteDelagate DELAGATE) {
        this.DELAGATE = DELAGATE;
    }

    public static IndexItemClickListener create(LatteDelagate DELAGATE) {
        return new IndexItemClickListener(DELAGATE);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final GoodsDetailDelegate goodsDetailDelegate = GoodsDetailDelegate.create();
        DELAGATE.start(goodsDetailDelegate);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
