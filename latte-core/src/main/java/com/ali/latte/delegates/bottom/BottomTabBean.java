package com.ali.latte.delegates.bottom;

/**
 * Created by 澄鱼 on 2018/5/1.
 * 导航栏JavaBean
 */

public class BottomTabBean {

    private final CharSequence ICON;
    private final CharSequence TITLE;

    public BottomTabBean(CharSequence mIcon, CharSequence mTitle) {
        this.ICON = mIcon;
        this.TITLE = mTitle;
    }

    public CharSequence getICON() {
        return ICON;
    }

    public CharSequence getTITLE() {
        return TITLE;
    }
}
