package com.ali.latte.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.ali.latte.R;
import com.ali.latte.delegates.LatteDelagate;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by 澄鱼 on 2018/3/17.
 * 单Activity界面应用，作为fragment容器
 */

public abstract  class ProxyActivity extends SupportActivity{

    public abstract LatteDelagate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(@Nullable Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);

        setContentView(container);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 因为是单页应用， 当Activity退出后，其实整个应用就退出了，
        // 可以进行一些建议的资源回收
        System.gc();
        System.runFinalization();
    }
}
