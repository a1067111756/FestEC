package com.ali.latte.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by 澄鱼 on 2018/3/15.
 */

public enum  EcIcons  implements Icon{
    icon_call('\ue607');
    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
