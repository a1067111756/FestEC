package com.ali.latte.delegates;

/**
 * Created by 澄鱼 on 2018/3/17.
 */

public abstract class LatteDelagate extends PermissionCheckerDelegate{

    @SuppressWarnings("unchecked")
    public <T extends LatteDelagate> T getParentDelegate() {
        return (T)getParentFragment();
    }

}
