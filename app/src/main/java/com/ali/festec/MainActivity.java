package com.ali.festec;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.ali.latte.activities.ProxyActivity;
import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.ec.launcher.LauncherDelegate;

public class MainActivity  extends ProxyActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }

    @Override
    public LatteDelagate setRootDelegate() {
        return new LauncherDelegate();
    }

}
