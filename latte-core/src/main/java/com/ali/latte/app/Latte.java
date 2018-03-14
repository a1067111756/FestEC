package com.ali.latte.app;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;


/**
 * Created by 澄鱼 on 2018/3/14.
 */

public  final  class Latte {

    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    };

    private static HashMap<String, Object> getConfigurations() {
        return Configurator.getInstance().getConfigurations();
    }

    public static Context getApplication() {
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}
