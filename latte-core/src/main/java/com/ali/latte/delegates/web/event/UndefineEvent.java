package com.ali.latte.delegates.web.event;

import com.ali.latte.utils.log.LatteLogger;

/**
 * Created by 澄鱼 on 2018/5/15.
 */

public class UndefineEvent extends Event{

    @Override
    public String execute(String params) {
        LatteLogger.e("UndefindEvent", params);
        return null;
    }
}
