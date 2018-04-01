package com.ali.latte.app;

import android.content.Context;

import com.blankj.utilcode.util.Utils;

import java.util.HashMap;


/**
 * Created by 澄鱼 on 2018/3/14.
 */

public  final  class Latte {

    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        //必要初始化
        InitUtils();
        return Configurator.getInstance();
    };

    public static HashMap<Object, Object> getConfigurations() {
        return Configurator.getInstance().getConfigurations();
    }

    public static Context getApplicationContext() {
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }

    // 配置工具库
    private static void InitUtils() {
        Utils.init((Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name()));
    }
}
