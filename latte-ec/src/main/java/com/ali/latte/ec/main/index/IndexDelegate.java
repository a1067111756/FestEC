package com.ali.latte.ec.main.index;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.latte.delegates.bottom.BottomItemDelegate;
import com.ali.latte.ec.R;
import com.ali.latte.ec.R2;
import com.ali.latte.ec.main.EcBottomDelegate;
import com.ali.latte.ec.main.refresh.RefreshHandler;
import com.ali.latte.ui.recycle.BaseDecoration;
import com.ali.latte.utils.log.LatteLogger;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 澄鱼 on 2018/5/2.
 */

public class IndexDelegate extends BottomItemDelegate {
    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R2.id.icon_index_scan)
    IconTextView mIconScan;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mEtSearch;
    @BindView(R2.id.icon_index_message)
    IconTextView mIconMessage;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar;
    Unbinder unbinder;

    private RefreshHandler mRefreshHandler = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler =  RefreshHandler.create(mSwipeRefresh, mRecyclerView, new IndexDataConverter());
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecycleView();
        mRefreshHandler.firstPage("index.php");

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

    @SuppressLint("ResourceAsColor")
    private void initRefreshLayout() {
        mSwipeRefresh.setColorSchemeColors(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefresh.setProgressViewOffset(true, 120, 300);
    }

    private void initRecycleView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.colorWhite), 5));

        // 注意 这里点击跳转到详情页，是连同底部导航栏一起， 也就是说是其父fragment得跳转， 不是这个子fragment得跳转
        EcBottomDelegate ecBottomDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
    }
}
