package com.ali.festec;

import com.ali.latte.activities.ProxyActivity;
import com.ali.latte.delegates.LatteDelagate;

public class MainActivity extends ProxyActivity {

    @Override
    public LatteDelagate setRootDelegate() {
        return new ExampleFragment();
    }
}
