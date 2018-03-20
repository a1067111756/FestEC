package com.ali.festec;

import android.app.Application;
import android.util.Log;

import com.ali.latte.app.ConfigType;
import com.ali.latte.app.Latte;
import com.ali.latte.ec.icon.FontEcModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://127.0.0.1")
                .configure();
    }
}
